package com.ruoyi.devices.domain.device;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * @author lfx
 * @program ruoyi
 * @description 客户端与服务端之间第一次连接的注册包信息
 * @date 2024/05/18
 */
public class DeviceRegistrationInfo {
    /** 设备唯一编码 */
    @Setter
    @Getter
    private String deviceCode;
    /** 设备类型 */
    @Setter
    @Getter
    private String deviceType;
    /** 操作系统版本 */
    @Setter
    @Getter
    private String osVersion;
    /** 固件版本 */
    @Setter
    @Getter
    private String firmwareVersion;
    /** IP地址 */
    @Setter
    @Getter
    private String ipAddress;
    /** MAC地址 */
    @Setter
    @Getter
    private String macAddress;
    /** 位置信息 */
    @Setter
    @Getter
    private String location; // 可能是GPS坐标字符串
    /** 是否激活 */
    @Setter
    @Getter
    private int isActive;
    /** 安全凭证 */
    @Setter
    @Getter
    private String securityCredential; // 密钥或Token
    /** 产品秘钥 */
    @Setter
    @Getter
    private String productKey;
    /** 设备秘钥 */
    @Setter
    @Getter
    private String deviceSecret;
    /** 附加元数据 */
    @Setter
    @Getter
    private Map<String, String> additionalMetadata; // 附加元数据

    // 构造方法
    public DeviceRegistrationInfo(String deviceCode, String deviceType, String osVersion, String firmwareVersion, String ipAddress, String macAddress, String location, int isActive, String securityCredential, String productKey, Map<String, String> additionalMetadata) {
        this.deviceCode = deviceCode;
        this.deviceType = deviceType;
        this.osVersion = osVersion;
        this.firmwareVersion = firmwareVersion;
        this.ipAddress = ipAddress;
        this.macAddress = macAddress;
        this.location = location;
        this.isActive = isActive;
        this.securityCredential = securityCredential;
        this.productKey = productKey;
        this.additionalMetadata = additionalMetadata;
    }

    @Override
    public String toString() {
        return "DeviceRegistrationInfo{" +
                "deviceCode='" + deviceCode + '\'' +
                ", deviceType='" + deviceType + '\'' +
                ", osVersion='" + osVersion + '\'' +
                ", firmwareVersion='" + firmwareVersion + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                ", macAddress='" + macAddress + '\'' +
                ", location='" + location + '\'' +
                ", isActive=" + isActive +
                ", securityCredential='" + securityCredential + '\'' +
                ", productKey='" + productKey + '\'' +
                ", additionalMetadata=" + additionalMetadata +
                '}';
    }

}
