package com.ruoyi.common.core.redis;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;

import com.alibaba.fastjson2.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * spring redis 工具类
 *
 * @author ruoyi
 **/
@SuppressWarnings(value = { "unchecked", "rawtypes" })
@Component
public class RedisCache
{
    private static final Logger log = LoggerFactory.getLogger(RedisCache.class);
    @Autowired
    public RedisTemplate redisTemplate;

    /** 键名模板  */
    private static final String DEVICE_CONNECT_KEY_TEMPLATE = "device_connect:%s";
    private static final String DEVICE_RESCONNECT_KEY_TEMPLATE = "device_resConnect:%s";
    private static final String DEVICE_RESPONSE_KEY_TEMPLATE = "device_response:%s";
    private static final String DEVICE_REPORT_KEY_TEMPLATE = "device_report:%s";



    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key 缓存的键值
     * @param value 缓存的值
     */
    public <T> void setCacheObject(final String key, final T value)
    {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 将给定的值存储到缓存中，与指定的键关联，并设置过期时间。
     *
     * @param key 用于在缓存中标识对象的键，类型为 String。
     * @param value 要存储在缓存中的对象，其类型为泛型 T。
     * @param timeout 缓存项的过期时间。
     * @param timeUnit 过期时间的单位，例如秒、分钟等。
     * 该方法不返回任何内容，其主要作用是将指定的对象以键值对的形式存储到缓存中，并设定其过期时间。
     */
    public <T> void setCacheObject(final String key, final T value, final Integer timeout, final TimeUnit timeUnit)
    {
        // 使用 RedisTemplate 的 opsForValue() 方法设置给定键的值，并指定过期时间和单位
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    /**
     * 设置有效时间
     *
     * @param key Redis键
     * @param timeout 超时时间
     * @return true=设置成功；false=设置失败
     */
    public boolean expire(final String key, final long timeout)
    {
        return expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置有效时间
     *
     * @param key Redis键
     * @param timeout 超时时间
     * @param unit 时间单位
     * @return true=设置成功；false=设置失败
     */
    public boolean expire(final String key, final long timeout, final TimeUnit unit)
    {
        return redisTemplate.expire(key, timeout, unit);
    }

    /**
     * 获取有效时间
     *
     * @param key Redis键
     * @return 有效时间
     */
    public long getExpire(final String key)
    {
        return redisTemplate.getExpire(key);
    }

    /**
     * 判断 key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public Boolean hasKey(String key)
    {
        return redisTemplate.hasKey(key);
    }

    /**
     * 获得缓存的基本对象。
     *
     * @param key 缓存键值
     * @return 缓存键值对应的数据
     */
    public <T> T getCacheObject(final String key)
    {
        ValueOperations<String, T> operation = redisTemplate.opsForValue();
        return operation.get(key);
    }

    /**
     * 删除单个对象
     *
     * @param key
     */
    public boolean deleteObject(final String key)
    {
        return redisTemplate.delete(key);
    }

    /**
     * 删除集合对象
     *
     * @param collection 多个对象
     * @return
     */
    public boolean deleteObject(final Collection collection)
    {
        return redisTemplate.delete(collection) > 0;
    }

    /**
     * 缓存List数据
     *
     * @param key 缓存的键值
     * @param dataList 待缓存的List数据
     * @return 缓存的对象
     */
    public <T> long setCacheList(final String key, final List<T> dataList)
    {
        Long count = redisTemplate.opsForList().rightPushAll(key, dataList);
        return count == null ? 0 : count;
    }

    /**
     * 获得缓存的list对象
     *
     * @param key 缓存的键值
     * @return 缓存键值对应的数据
     */
    public <T> List<T> getCacheList(final String key)
    {
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    /**
     * 缓存Set
     *
     * @param key 缓存键值
     * @param dataSet 缓存的数据
     * @return 缓存数据的对象
     */
    public <T> BoundSetOperations<String, T> setCacheSet(final String key, final Set<T> dataSet)
    {
        BoundSetOperations<String, T> setOperation = redisTemplate.boundSetOps(key);
        Iterator<T> it = dataSet.iterator();
        while (it.hasNext())
        {
            setOperation.add(it.next());
        }
        return setOperation;
    }

    /**
     * 获得缓存的set
     *
     * @param key
     * @return
     */
    public <T> Set<T> getCacheSet(final String key)
    {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 缓存Map
     *
     * @param key
     * @param dataMap
     */
    public <T> void setCacheMap(final String key, final Map<String, T> dataMap)
    {
        if (dataMap != null) {
            redisTemplate.opsForHash().putAll(key, dataMap);
        }
    }

    /**
     * 获得缓存的Map
     *
     * @param key
     * @return
     */
    public <T> Map<String, T> getCacheMap(final String key)
    {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 往Hash中存入数据
     *
     * @param key Redis键
     * @param hKey Hash键
     * @param value 值
     */
    public <T> void setCacheMapValue(final String key, final String hKey, final T value)
    {
        redisTemplate.opsForHash().put(key, hKey, value);
    }

    /**
     * 获取Hash中的数据
     *
     * @param key Redis键
     * @param hKey Hash键
     * @return Hash中的对象
     */
    public <T> T getCacheMapValue(final String key, final String hKey)
    {
        HashOperations<String, String, T> opsForHash = redisTemplate.opsForHash();
        return opsForHash.get(key, hKey);
    }

    /**
     * 获取多个Hash中的数据
     *
     * @param key Redis键
     * @param hKeys Hash键集合
     * @return Hash对象集合
     */
    public <T> List<T> getMultiCacheMapValue(final String key, final Collection<Object> hKeys)
    {
        return redisTemplate.opsForHash().multiGet(key, hKeys);
    }

    /**
     * 删除Hash中的某条数据
     *
     * @param key Redis键
     * @param hKey Hash键
     * @return 是否成功
     */
    public boolean deleteCacheMapValue(final String key, final String hKey)
    {
        return redisTemplate.opsForHash().delete(key, hKey) > 0;
    }

    /**
     * 获得缓存的基本对象列表
     *
     * @param pattern 字符串前缀
     * @return 对象列表
     */
    public Collection<String> keys(final String pattern)
    {
        return redisTemplate.keys(pattern);
    }

    /**
     * 存储设备消息
     */
    public Mono<Void> saveDeviceMessage(String deviceCode, String messageType, String message) {
        // 获取当前时间戳作为有序集合中的分数
        long timestamp = System.currentTimeMillis();

        // 根据消息类型选择不同的Redis键值模版

        //根据设备唯一标识符和消息类型构造有序集合的键名
        String key = "";
        if (messageType.equals("Response")) {
            // 响应消息
            key = String.format(DEVICE_RESPONSE_KEY_TEMPLATE, deviceCode);
        } else if (messageType.equals("Report")) {
            // 上报消息
            key = String.format(DEVICE_REPORT_KEY_TEMPLATE, deviceCode);
        } else if (messageType.equals("Connect")) {
            // 连接消息
            key = String.format(DEVICE_CONNECT_KEY_TEMPLATE, deviceCode);
        } else if (messageType.equals("ResConnect")) {
            // 重连消息
            key = String.format(DEVICE_RESCONNECT_KEY_TEMPLATE, deviceCode);
        } else {
            // 未知消息类型
            return Mono.empty();
        }

        String messageKey = String.format("%s", message);

        // 使用Reactor框架的RedisTemplate尝试将消息和时间戳添加到有序集合中
        try {
            boolean result = redisTemplate.opsForZSet().add(key, messageKey, timestamp).booleanValue();
            if (result) {
                log.info("设备消息缓存至redis成功");
                log.info("设备消息缓存内容：{}" , messageKey);

                // 设置消息的过期时间，这里以响应消息为例设置不同的过期时间
                long expireTime = timestamp + (messageType.equals("Response") ? 30 * 60 * 1000 : 7 * 24 * 60 * 60 * 1000);
                redisTemplate.expireAt(key, new Date(expireTime));

                // 如果消息已成功添加到有序集合中，则返回Mono.empty()，表示消息已成功存储
                return Mono.empty();
            } else {
                log.error("设备消息缓存至redis失败,消息内容重复");
                // 如果消息添加到有序集合失败，则返回Mono.error()，其中包含一个异常
                return Mono.error(new RuntimeException("设备消息缓存至redis失败，消息内容重复"));
            }
        }catch (Exception e) {
            // 如果在执行过程中发生任何异常，则返回Mono.error()，其中包含捕获的异常
            return Mono.error(e);
        }
    }

    /**
     * 获取设备的最新消息
     */
     public String getLatestDeviceMessage(String deviceCode, String messageType) {
         String key = "";
         if (messageType.equals("Response")) {
             key = String.format(DEVICE_RESPONSE_KEY_TEMPLATE, deviceCode);
         } else if (messageType.equals("Report")) {
             key = String.format(DEVICE_REPORT_KEY_TEMPLATE, deviceCode);
         } else if (messageType.equals("Connect")) {
             key = String.format(DEVICE_CONNECT_KEY_TEMPLATE, deviceCode);
         } else if (messageType.equals("ResConnect")) {
             key = String.format(DEVICE_RESCONNECT_KEY_TEMPLATE, deviceCode);
         } else {
             // 未知消息类型
             return null;
         }

         String msg = "";
         if (redisTemplate.hasKey(key)) {
             try {
                 // 使用 zRevRangeByScore 获取有序集合第一个元素（最新消息）
                 Set<String> messages = redisTemplate.opsForZSet().reverseRange(key, 0, 0);
                 if (!messages.isEmpty()) {
                     msg=messages.iterator().next();
                     Double score = redisTemplate.opsForZSet().score(key, msg);
                     long timestamp = score.longValue();
                     return msg+";"+timestamp;
                 }
             } catch (Exception e) {
                 log.error("Failed to retrieve the latest device message from Redis", e);
                 // 处理异常，如记录日志、返回默认值或抛出异常等
                 return e.getMessage();
             }
         }
         return msg;
     }
}
