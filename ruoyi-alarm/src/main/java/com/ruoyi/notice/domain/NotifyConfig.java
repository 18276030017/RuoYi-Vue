package com.ruoyi.notice.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 消息通知配置对象 notify_config
 * 
 * @author lfx
 * @date 2024-04-07
 */
public class NotifyConfig extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Long id;

    /** 通知配置ID */
    @Excel(name = "通知配置ID")
    private String notifierId;

    /** 配置名称（标题名称） */
    @Excel(name = "配置名称", readConverterExp = "标=题名称")
    private String name;

    /** 通知方式 */
    @Excel(name = "通知方式")
    private String type;

    /** 服务提供商 */
    @Excel(name = "服务提供商")
    private String provider;

    /** 描述 */
    @Excel(name = "描述")
    private String description;

    /** 重试策略,如: ["1s","20s","5m","15m"] */
    @Excel(name = "重试策略,如: [1s,20s,5m,15m]")
    private String retryPolicy;

    /** 最大重试次数 */
    @Excel(name = "最大重试次数")
    private Long maxRetryTimes;

    /** 创建者ID(只读) */
    @Excel(name = "创建者ID(只读)")
    private String creatorId;

    /** 配置信息 */
    @Excel(name = "配置信息")
    private String configuration;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setNotifierId(String notifierId) 
    {
        this.notifierId = notifierId;
    }

    public String getNotifierId() 
    {
        return notifierId;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setType(String type) 
    {
        this.type = type;
    }

    public String getType() 
    {
        return type;
    }
    public void setProvider(String provider) 
    {
        this.provider = provider;
    }

    public String getProvider() 
    {
        return provider;
    }
    public void setDescription(String description) 
    {
        this.description = description;
    }

    public String getDescription() 
    {
        return description;
    }
    public void setRetryPolicy(String retryPolicy) 
    {
        this.retryPolicy = retryPolicy;
    }

    public String getRetryPolicy() 
    {
        return retryPolicy;
    }
    public void setMaxRetryTimes(Long maxRetryTimes) 
    {
        this.maxRetryTimes = maxRetryTimes;
    }

    public Long getMaxRetryTimes() 
    {
        return maxRetryTimes;
    }
    public void setCreatorId(String creatorId) 
    {
        this.creatorId = creatorId;
    }

    public String getCreatorId() 
    {
        return creatorId;
    }
    public void setConfiguration(String configuration) 
    {
        this.configuration = configuration;
    }

    public String getConfiguration() 
    {
        return configuration;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("notifierId", getNotifierId())
            .append("name", getName())
            .append("type", getType())
            .append("provider", getProvider())
            .append("description", getDescription())
            .append("retryPolicy", getRetryPolicy())
            .append("maxRetryTimes", getMaxRetryTimes())
            .append("creatorId", getCreatorId())
            .append("createTime", getCreateTime())
            .append("configuration", getConfiguration())
            .toString();
    }
}
