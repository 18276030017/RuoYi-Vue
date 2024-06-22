package com.ruoyi.devices.domain.device;

/**
 * @author lfx
 * @program ruoyi
 * @description 设备升级所需的各种信息
 * @date 2024/05/07
 */
public class Upgrade {
    private Long upgradeId;
    private String upgradeUrl;
    private String upgradeVersion;
    private String upgradeTitle;
    private String upgradeStatus;
    private String upgradeDesc;
    private String upgradeName;
    private String upgradeSize;
    private String deviceCode;
    private String deviceName;
    private String deviceType;
    private String deviceStatus;
    private String deviceDesc;
    private String channelId;
    private String leader;
    private String email;
    private String phone;
    private String productName;

    public Long getUpgradeId() {
        return upgradeId;
    }

    public void setUpgradeId(Long upgradeId) {
        this.upgradeId = upgradeId;
    }

    public String getUpgradeUrl() {
        return upgradeUrl;
    }

    public void setUpgradeUrl(String upgradeUrl) {
        this.upgradeUrl = upgradeUrl;
    }

    public String getUpgradeVersion() {
        return upgradeVersion;
    }

    public void setUpgradeVersion(String upgradeVersion) {
        this.upgradeVersion = upgradeVersion;
    }

    public String getUpgradeTitle() {
        return upgradeTitle;
    }

    public void setUpgradeTitle(String upgradeTitle) {
        this.upgradeTitle = upgradeTitle;
    }

    public String getUpgradeStatus() {
        return upgradeStatus;
    }

    public void setUpgradeStatus(String upgradeStatus) {
        this.upgradeStatus = upgradeStatus;
    }

    public String getUpgradeDesc() {
        return upgradeDesc;
    }

    public void setUpgradeDesc(String upgradeDesc) {
        this.upgradeDesc = upgradeDesc;
    }

    public String getUpgradeName() {
        return upgradeName;
    }

    public void setUpgradeName(String upgradeName) {
        this.upgradeName = upgradeName;
    }

    public String getUpgradeSize() {
        return upgradeSize;
    }

    public void setUpgradeSize(String upgradeSize) {
        this.upgradeSize = upgradeSize;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(String deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public String getDeviceDesc() {
        return deviceDesc;
    }

    public void setDeviceDesc(String deviceDesc) {
        this.deviceDesc = deviceDesc;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String toString() {
        return "Upgrade{" +
                "deviceCode='" + deviceCode + '\'' +
                "upgradeId=" + upgradeId +
                ", upgradeUrl='" + upgradeUrl + '\'' +
                ", upgradeVersion='" + upgradeVersion + '\'' +
                ", upgradeTitle='" + upgradeTitle + '\'' +
                ", upgradeStatus='" + upgradeStatus + '\'' +
                ", upgradeDesc='" + upgradeDesc + '\'' +
                ", upgradeName='" + upgradeName + '\'' +
                ", upgradeSize='" + upgradeSize + '\'' +
                ", deviceCode='"+ deviceCode + '\'' +
                ", deviceName='" + deviceName + '\'' +
                ", deviceType='" + deviceType + '\'' +
                ", deviceStatus='" + deviceStatus + '\'' +
                ", deviceDesc='" + deviceDesc + '\'' +
                ", channelId='" + channelId + '\'' +
                ", leader='" + leader + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", productName='" + productName + '\'' +
                '}';
            }
}
