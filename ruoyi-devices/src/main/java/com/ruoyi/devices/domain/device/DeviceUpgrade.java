package com.ruoyi.devices.domain.device;

import com.ruoyi.common.core.domain.BaseEntity;

import java.util.Date;

/**
 * @author lfx
 * @program ruoyi
 * @description 设备升级包表对象类
 * @date 2024/04/29
 */
public class DeviceUpgrade extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 设备编码 */
    private String deviceCode;
    /** 主键 id */
    private Long upgradeId;
    /** 升级包名称 */
    private String upgradeName;
    /** 升级包版本 */
    private String upgradeVersion;
    /** 升级包地址 */
    private String upgradeUrl;
    /** 升级包大小 */
    private String upgradeSize;
    /** 升级包描述 */
    private String upgradeDesc;
    /** 升级包状态 */
    private String upgradeStatus;
    /** 删除标志 */
    private String delFlag;
    /** 创建人 */
    private String createBy;
    /** 创建时间 */
    private Date createTime;
    /** 更新人 */
    private String updateBy;
    /** 更新时间 */
    private Date updateTime;
    /** 升级包标题 */
    private String upgradeTitle;

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public Long getUpgradeId() {
        return upgradeId;
    }

    public void setUpgradeId(Long upgradeId) {
        this.upgradeId = upgradeId;
    }

    public String getUpgradeName() {
        return upgradeName;
    }

    public void setUpgradeName(String upgradeName) {
        this.upgradeName = upgradeName;
    }

    public String getUpgradeVersion() {
        return upgradeVersion;
    }

    public void setUpgradeVersion(String upgradeVersion) {
        this.upgradeVersion = upgradeVersion;
    }

    public String getUpgradeUrl() {
        return upgradeUrl;
    }

    public void setUpgradeUrl(String upgradeUrl) {
        this.upgradeUrl = upgradeUrl;
    }

    public String getUpgradeSize() {
        return upgradeSize;
    }

    public void setUpgradeSize(String upgradeSize) {
        this.upgradeSize = upgradeSize;
    }

    public String getUpgradeDesc() {
        return upgradeDesc;
    }

    public void setUpgradeDesc(String upgradeDesc) {
        this.upgradeDesc = upgradeDesc;
    }

    public String getUpgradeStatus() {
        return upgradeStatus;
    }

    public void setUpgradeStatus(String upgradeStatus) {
        this.upgradeStatus = upgradeStatus;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpgradeTitle() {
        return upgradeTitle;
    }

    public void setUpgradeTitle(String upgradeTitle) {
        this.upgradeTitle = upgradeTitle;
    }
    @Override
    public String toString() {
        return "DeviceUpgrade{" +
                "deviceCode='" + deviceCode + '\'' +
                ",upgradeId=" + upgradeId +
                ", upgradeName='" + upgradeName + '\'' +
                ", upgradeVersion='" + upgradeVersion + '\'' +
                ", upgradeUrl='" + upgradeUrl + '\'' +
                ", upgradeSize='" + upgradeSize + '\'' +
                ", upgradeDesc='" + upgradeDesc + '\'' +
                ", upgradeStatus='" + upgradeStatus + '\'' +
                ", delFlag='" + delFlag + '\'' +
                ", createBy='" + createBy + '\'' +
                ", createTime=" + createTime +
                ", updateBy='" + updateBy + '\'' +
                ", updateTime=" + updateTime +
                ", upgradeTitle='" + upgradeTitle + '\'' +
                '}';
            }
}
