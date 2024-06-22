package com.ruoyi.devices.domain.product;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author lfx
 * @program ruoyi
 * @description 产品属性
 * @date 2024/05/13
 */
public class ProductAttribute {
    private Long attributeId;
    private String attributeAlias;
    private String attributeName;
    private String attributeType;
    private String attributeUnit;
    private String attributeSource;
    private String attributeDecribe;
    private String createBy;
    private String createTime;
    private String updateBy;
    private String updateTime;

    public Long getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(Long attributeId) {
        this.attributeId = attributeId;
    }

    public String getAttributeAlias() {
        return attributeAlias;
    }

    public void setAttributeAlias(String attributeAlias) {
        this.attributeAlias = attributeAlias;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getAttributeType() {
        return attributeType;
    }

    public void setAttributeType(String attributeType) {
        this.attributeType = attributeType;
    }

    public String getAttributeUnit() {
        return attributeUnit;
    }

    public void setAttributeUnit(String attributeUnit) {
        this.attributeUnit = attributeUnit;
    }

    public String getAttributeSource() {
        return attributeSource;
    }

    public void setAttributeSource(String attributeSource) {
        this.attributeSource = attributeSource;
    }

    public String getAttributeDecribe() {
        return attributeDecribe;
    }

    public void setAttributeDecribe(String attributeDecribe) {
        this.attributeDecribe = attributeDecribe;
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
                .append("attributeId", getAttributeId())
                .append("attributeAlias", getAttributeAlias())
                .append("attributeName", getAttributeName())
                .append("attributeType", getAttributeType())
                .append("attributeUnit", getAttributeUnit())
                .append("attributeSource", getAttributeSource())
                .append("attributeDecribe", getAttributeDecribe())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
