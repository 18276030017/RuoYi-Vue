package com.ruoyi.devices.service;

/**
 * @author lfx
 * @description 创建kafka生产者的服务接口
 * @date 2024/05/07
 */
public interface KafkaService {
    // 创建kafka生产者
    void createKafkaProducer();
    // 发送消息到kafka消息队列
    void sendMessageToKafka(String topic, String key, String value);

    // 创建kafka消费者
    void createKafkaConsumer();
    // 获取kafka消息队列中的消息
    void getMessageFromKafka(String topic);
}
