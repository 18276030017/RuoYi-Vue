package com.ruoyi.devices.netty.server.tls;

import org.junit.experimental.theories.DataPoint;

/**
 * @author lfx
 * @program ruoyi
 * @description 对上报的数据进行清洗设置清洗规则
 * @date 2024/05/29
 */
public class DataCleanRules {
    public static String cleanData(String data) {
        // 清洗规则1：去除空格
        data = data.replaceAll("\\s", "");
        // 清洗规则2：去除特殊字符
        data = data.replaceAll("[^a-zA-Z0-9]", "");
        // 清洗规则3：去除数字
        data = data.replaceAll("\\d", "");
        // 清洗规则4：去除字母
        data = data.replaceAll("[a-zA-Z]", "");
        // 清洗规则5：去除中文字符
        data = data.replaceAll("[\\u4e00-\\u9fa5]", "");

        return data;
    }

    public boolean isTemperatureValid(double temperature) {
        // 假设温度范围为-50到100摄氏度
        return temperature >= -50 && temperature <= 100;
    }

    public boolean isHumidityValid(double humidity) {
        // 假设湿度范围为0到100
        return humidity >= 0 && humidity <= 100;
    }

    public boolean isPressureValid(double pressure) {
        // 假设压力范围为0到100
        return pressure >= 0 && pressure <= 100;
    }
    public boolean isDuplicateData(DataPoint prevData, DataPoint newData) {
        // 根据数据点的唯一标识判断是否重复
        return true;
    }


}
