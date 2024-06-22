package com.ruoyi.alarm.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 告警级别对象 alarm_level
 * 
 * @author lfx
 * @date 2024-04-07
 */
public class AlarmLevel extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Long id;

    /** 名称 */
    @Excel(name = "名称")
    private String name;

    /** 配置信息 */
    @Excel(name = "配置信息")
    private String config;

    /** 说明（0提示 1通知 2告警 3严重 4紧急） */
    @Excel(name = "说明", readConverterExp = "0=提示,1=通知,2=告警,3=严重,4=紧急")
    private String description;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setConfig(String config) 
    {
        this.config = config;
    }

    public String getConfig() 
    {
        return config;
    }
    public void setDescription(String description) 
    {
        this.description = description;
    }

    public String getDescription() 
    {
        return description;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("config", getConfig())
            .append("description", getDescription())
            .toString();
    }
}
