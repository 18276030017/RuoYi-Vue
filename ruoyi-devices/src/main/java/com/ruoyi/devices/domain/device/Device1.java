package com.ruoyi.devices.domain.device;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.Date;

/**
 * 设备对象 device1
 *
 * @author ruoyi
 * @date 2024-04-01
 */
public class Device1 extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    @Setter
    @Getter
    @Excel(name = "设备id")
    private String deviceId;
    @Setter
    @Getter
    @Excel(name = "设备图片")
    private String deviceImage;
    @Setter
    @Getter
    @Excel(name = "设备编码")
    private String deviceCode;
    @Setter
    @Getter
    @Excel(name = "设备名称")
    private String deviceName;
    @Setter
    @Getter
    @Excel(name = "设备所属产品")
    private String deviceProductName;
    @Setter
    @Getter
    @Excel(name = "设备展示序号")
    private String orderNum;
    @Setter
    @Getter
    @Excel(name = "设备负责人")
    private String leader;
    @Setter
    @Getter
    @Excel(name = "联系电话")
    private String phone;
    @Setter
    @Getter
    @Excel(name = "邮箱")
    private String email;
    @Setter
    @Getter
    @Excel(name = "备注")
    private String descripe;
    @Setter
    @Getter
    private String deviceStatus;
    @Setter
    @Getter
    private String channelId;
    @Setter
    @Getter
    private String delFlag;
    @Setter
    @Getter
    private String createBy;
    @Setter
    @Getter
    private Date createTime;
    @Setter
    @Getter
    private String updateBy;
    @Setter
    @Getter
    private Date updateTime;
    @Setter
    @Getter
    private String offTime;
    @Setter
    @Getter
    private String deviceCommand;
    @Setter
    @Getter
    private Date lastOnlineTime;
    @Setter
    @Getter
    @Excel(name = "产品密钥")
    private String productKey;
    @Setter
    @Getter
    private Date newOnlineTime;
    @Setter
    @Getter
    private String accessToken;
    @Setter
    @Getter
    private String devicePort;
    @Setter
    @Getter
    @Excel(name = "设备密钥")
    private String deviceSecret;
    @Setter
    @Getter
    private String firmwareVersion;
    @Setter
    @Getter
    @Excel(name = "设备类型")
    private String deviceType;
    @Setter
    @Getter
    private String deviceIP;
    @Setter
    @Getter
    private String firmwareFileUrl;
    @Setter
    @Getter
    @Excel(name = "设备型号")
    private String deviceModel;


    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("deviceId", getDeviceId())
                .append("deviceImage", getDeviceImage())
                .append("deviceCode", getDeviceCode())
                .append("deviceName", getDeviceName())
                .append("deviceProductName", getDeviceProductName())
                .append("orderNum", getOrderNum())
                .append("leader", getLeader())
                .append("phone", getPhone())
                .append("email", getEmail())
                .append("descripe", getDescripe())
                .append("deviceStatus",getDeviceStatus())
                .append("channelId", getChannelId())
                .append("delFlag", getDelFlag())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("offTime", getOffTime())
                .append("deviceCommand", getDeviceCommand())
                .append("lastOnlineTime", getLastOnlineTime())
                .append("productKey", getProductKey())
                .append("newOnlineTime", getNewOnlineTime())
                .append("accessToken", getAccessToken())
                .append("devicePort", getDevicePort())
                .append("deviceSecret", getDeviceSecret())
                .append("firmwareVersion", getFirmwareVersion())
                .append("deviceType", getDeviceType())
                .append("deviceIP", getDeviceIP())
                .append("firmwareFileUrl", getFirmwareFileUrl())
                .toString();
    }
}
