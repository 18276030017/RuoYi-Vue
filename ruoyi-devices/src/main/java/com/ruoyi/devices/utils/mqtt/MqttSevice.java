package com.ruoyi.devices.utils.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * @author lfx
 * @program ruoyi
 * @description 尝试连接mqtt监控数据
 * @date 2024/04/09
 */
public class MqttSevice {

    public static void main(String[] args) {
        String subTopic = "$sys/fx2IJNC897/ML307A_MQTT/#";
        String broker = "tcp://183.230.40.96:1883";
        String clientId = "ML307A_MQTT";

        String username = "fx2IJNC897";
        String password = "version=2018-10-31&res=products%2Ffx2IJNC897%2Fdevices%2FML307A_MQTT&et=2027986806&method=md5&sign=yrK3T3JKsRRhUODyHDCz3g%3D%3D";

        MemoryPersistence persistence = new MemoryPersistence();

        try {
            MqttClient client = new MqttClient(broker, clientId, persistence);

            // MQTT 连接选项
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setUserName(username);
            connOpts.setPassword(password.toCharArray());
            // 保留会话
            connOpts.setCleanSession(true);

            // 设置回调
            client.setCallback(new EmqxOnMessageCallback());

            // 建立连接
            System.out.println("Connecting to broker: " + broker);
            client.connect(connOpts);

            System.out.println("Connected");
            // 订阅
            client.subscribe(subTopic);

        } catch (MqttException me) {
            System.out.println("reason " + me.getReasonCode());
            System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("excep " + me);
            me.printStackTrace();
        }
    }

}