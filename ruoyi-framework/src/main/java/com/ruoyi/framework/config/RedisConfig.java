package com.ruoyi.framework.config;

import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * redis配置
 * 
 * @author ruoyi
 */
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport
{
    @Bean
    @SuppressWarnings(value = { "unchecked", "rawtypes" })
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory connectionFactory)
    {
        //调用RedisTemplate的构造函数来创建一个新的RedisTemplate实例
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        // 创建和管理与Redis服务器的连接
        template.setConnectionFactory(connectionFactory);

        // Object.class：这个参数指示了序列化器处理的对象类型。在这里使用Object.class暗示序列化器可以用于任意类型的Java对象。
        FastJson2JsonRedisSerializer serializer = new FastJson2JsonRedisSerializer(Object.class);

        // 使用StringRedisSerializer来序列化和反序列化redis的key值
        // 设置模板的键序列化器为StringRedisSerializer，确保键的序列化方式为字符串类型。
        template.setKeySerializer(new StringRedisSerializer());
        // 设置模板的值序列化器为传入的serializer参数，允许自定义值的序列化方式。
        template.setValueSerializer(serializer);

        // Hash的key也采用StringRedisSerializer的序列化方式
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(serializer);

        /*
         * 设置模板的序列化器，用于序列化和反序列化Redis中的数据。
         *
         * 1. setKeySerializer：设置键的序列化器。
         * 2. setValueSerializer：设置值的序列化器。
         * 3. setHashKeySerializer：设置哈希键的序列化器。
         * 4. setHashValueSerializer：设置哈希值的序列化器。
         *
         * 在Redis中，键和值都是以字节数组（byte array）的形式存储的。因此，需要指定序列化器来将Java对象转换为字节数组，
         * 并将字节数组转换为Java对象。
         */
        template.afterPropertiesSet();
        return template;
    }

    /**
     * 创建并配置一个用于限流的Redis脚本。
     * 这个脚本将根据提供的键和限制次数来控制访问频率。
     *
     * @return DefaultRedisScript<Long> 一个配置好的Redis脚本，其结果类型为长整型。
     */
    @Bean
    public DefaultRedisScript<Long> limitScript()
    {
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptText(limitScriptText());
        redisScript.setResultType(Long.class);
        return redisScript;
    }

    /**
     * 获取限流脚本的文本内容。
     * 脚本用于在Redis中实现基于键的访问频率限制。
     *
     * @return String 限流脚本的Lua代码文本。
     */
    private String limitScriptText()
    {
        return "local key = KEYS[1]\n" +
                "local count = tonumber(ARGV[1])\n" +
                "local time = tonumber(ARGV[2])\n" +
                "local current = redis.call('get', key);\n" +
                "if current and tonumber(current) > count then\n" +
                "    return tonumber(current);\n" +
                "end\n" +
                "current = redis.call('incr', key)\n" +
                "if tonumber(current) == 1 then\n" +
                "    redis.call('expire', key, time)\n" +
                "end\n" +
                "return tonumber(current);";
    }
}
