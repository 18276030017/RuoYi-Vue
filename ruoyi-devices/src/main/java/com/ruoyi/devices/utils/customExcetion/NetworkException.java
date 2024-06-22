package com.ruoyi.devices.utils.customExcetion;

import java.io.IOException;

/**
 * @author lfx
 * @program ruoyi
 * @description 自定义的网络异常类
 * @date 2024/04/24
 */
public class NetworkException extends IOException {

    private static final long serialVersionUID = 1L; // 用于序列化和反序列化

    public NetworkException() {
        super(); // 调用父类的无参构造方法
    }

    public NetworkException(String message) {
        super(message); // 提供详细的错误信息
    }

    public NetworkException(String message, Throwable cause) {
        super(message, cause); // 包含引起此异常的原因的详细信息
    }

    public NetworkException(Throwable cause) {
        super(cause); // 如果异常是由其他异常引起的，则可以传递该异常
    }

}