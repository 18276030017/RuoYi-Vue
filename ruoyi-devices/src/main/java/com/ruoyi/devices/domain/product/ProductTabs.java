package com.ruoyi.devices.domain.product;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author lfx
 * @program ruoyi
 * @description 产品标签表数据
 * @date 2024/05/13
 */
public class ProductTabs {
    private static final long serialVersionUID = 1L;
    /** 产品标签ID */
    private Long tabsId;
    /** 产品标签别名 */
    private String tabsAlias;
    /** 产品标签名称 */
    private String tabsName;
    /** 产品标签类型 */
    private String tabsType;
    /** 产品标签读权限 */
    private String tabsRead;
    /** 产品标签写权限 */
    private String tabsWrite;
    /** 产品标签读写权限 */
    private String tabsReadWrite;
    /** 产品标签描述 */
    private String tabsDescribe;
    /** 创建者 */
    private String createBy;
    private String createTime;
    private String updateBy;
    private String updateTime;

    public Long getTabsId() {
        return tabsId;
    }

    public void setTabsId(Long tabsId) {
        this.tabsId = tabsId;
    }

    public String getTabsAlias() {
        return tabsAlias;
    }

    public void setTabsAlias(String tabsAlias) {
        this.tabsAlias = tabsAlias;
    }

    public String getTabsName() {
        return tabsName;
    }

    public void setTabsName(String tabsName) {
        this.tabsName = tabsName;
    }

    public String getTabsType() {
        return tabsType;
    }

    public void setTabsType(String tabsType) {
        this.tabsType = tabsType;
    }

    public String getTabsRead() {
        return tabsRead;
    }

    public void setTabsRead(String tabsRead) {
        this.tabsRead = tabsRead;
    }

    public String getTabsWrite() {
        return tabsWrite;
    }

    public void setTabsWrite(String tabsWrite) {
        this.tabsWrite = tabsWrite;
    }

    public String getTabsReadWrite() {
        return tabsReadWrite;
    }

    public void setTabsReadWrite(String tabsReadWrite) {
        this.tabsReadWrite = tabsReadWrite;
    }

    public String getTabsDescribe() {
        return tabsDescribe;
    }

    public void setTabsDescribe(String tabsDescribe) {
        this.tabsDescribe = tabsDescribe;
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
            .append("tabsId", getTabsId())
            .append("tabsAlias", getTabsAlias())
            .append("tabsName", getTabsName())
            .append("tabsType", getTabsType())
            .append("tabsRead", getTabsRead())
            .append("tabsWrite", getTabsWrite())
            .append("tabsReadWrite", getTabsReadWrite())
            .append("tabsDescribe", getTabsDescribe())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
