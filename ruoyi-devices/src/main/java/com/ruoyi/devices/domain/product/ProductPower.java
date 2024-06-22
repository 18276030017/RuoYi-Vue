package com.ruoyi.devices.domain.product;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author lfx
 * @program ruoyi
 * @description 产品功能
 * @date 2024/05/13
 */
public class ProductPower {
    private Long powerId;
    private String powerAlias;
    private String powerName;
    private String powerAsync;
    private String powerOut;
    private String powerInput;
    private String powerDecribe;
    private String createBy;
    private String createTime;
    private String updateBy;
    private String updateTime;

    public Long getPowerId() {
        return powerId;
    }

    public void setPowerId(Long powerId) {
        this.powerId = powerId;
    }

    public String getPowerAlias() {
        return powerAlias;
    }

    public void setPowerAlias(String powerAlias) {
        this.powerAlias = powerAlias;
    }

    public String getPowerName() {
        return powerName;
    }

    public void setPowerName(String powerName) {
        this.powerName = powerName;
    }

    public String getPowerAsync() {
        return powerAsync;
    }

    public void setPowerAsync(String powerAsync) {
        this.powerAsync = powerAsync;
    }

    public String getPowerOut() {
        return powerOut;
    }

    public void setPowerOut(String powerOut) {
        this.powerOut = powerOut;
    }

    public String getPowerInput() {
        return powerInput;
    }

    public void setPowerInput(String powerInput) {
        this.powerInput = powerInput;
    }

    public String getPowerDecribe() {
        return powerDecribe;
    }

    public void setPowerDecribe(String powerDecribe) {
        this.powerDecribe = powerDecribe;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("powerId", getPowerId())
                .append("powerAlias", getPowerAlias())
                .append("powerName", getPowerName())
                .append("powerAsync", getPowerAsync())
                .append("powerOut", getPowerOut())
                .append("powerInput", getPowerInput())
                .append("powerDecribe", getPowerDecribe())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
