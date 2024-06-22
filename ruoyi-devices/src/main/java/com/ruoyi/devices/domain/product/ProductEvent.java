package com.ruoyi.devices.domain.product;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author lfx
 * @program ruoyi
 * @description 产品事件
 * @date 2024/05/13
 */
public class ProductEvent {
    private Long eventId;
    private String eventAlias;
    private String eventName;
    private String eventLevel;
    private String eventOut;
    private String eventConfig;
    private String eventDecribe;
    private String createBy;
    private String createTime;
    private String updateBy;
    private String updateTime;

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getEventAlias() {
        return eventAlias;
    }

    public void setEventAlias(String eventAlias) {
        this.eventAlias = eventAlias;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventLevel() {
        return eventLevel;
    }

    public void setEventLevel(String eventLevel) {
        this.eventLevel = eventLevel;
    }

    public String getEventOut() {
        return eventOut;
    }

    public void setEventOut(String eventOut) {
        this.eventOut = eventOut;
    }

    public String getEventConfig() {
        return eventConfig;
    }

    public void setEventConfig(String eventConfig) {
        this.eventConfig = eventConfig;
    }

    public String getEventDecribe() {
        return eventDecribe;
    }

    public void setEventDecribe(String eventDecribe) {
        this.eventDecribe = eventDecribe;
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
                .append("eventId", getEventId())
                .append("eventAlias", getEventAlias())
                .append("eventName", getEventName())
                .append("eventLevel", getEventLevel())
                .append("eventOut", getEventOut())
                .append("eventConfig", getEventConfig())
                .append("eventDecribe", getEventDecribe())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
