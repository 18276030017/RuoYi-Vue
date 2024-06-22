package com.ruoyi.notice.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 通知记录对象 notify_history
 * 
 * @author lfx
 * @date 2024-04-07
 */
public class NotifyHistory extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Long id;

    /** 通知配置ID */
    @Excel(name = "通知配置ID")
    private String notifierId;

    /** 配置名称（标题名称） */
    @Excel(name = "配置名称", readConverterExp = "标=题名称")
    private String noteName;

    /** 模版ID */
    @Excel(name = "模版ID")
    private String templateId;

    /** 模版内容 */
    @Excel(name = "模版内容")
    private String template;

    /** 上下文 */
    @Excel(name = "上下文")
    private String context;

    /** 服务商 */
    @Excel(name = "服务商")
    private String provider;

    /** 通知类型 */
    @Excel(name = "通知类型")
    private String notifyType;

    /** 通知时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "通知时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date notifyTime;

    /** 重试次数 */
    @Excel(name = "重试次数")
    private Long retryTimes;

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
    public void setNoteName(String noteName) 
    {
        this.noteName = noteName;
    }

    public String getNoteName() 
    {
        return noteName;
    }
    public void setTemplateId(String templateId) 
    {
        this.templateId = templateId;
    }

    public String getTemplateId() 
    {
        return templateId;
    }
    public void setTemplate(String template) 
    {
        this.template = template;
    }

    public String getTemplate() 
    {
        return template;
    }
    public void setContext(String context) 
    {
        this.context = context;
    }

    public String getContext() 
    {
        return context;
    }
    public void setProvider(String provider) 
    {
        this.provider = provider;
    }

    public String getProvider() 
    {
        return provider;
    }
    public void setNotifyType(String notifyType) 
    {
        this.notifyType = notifyType;
    }

    public String getNotifyType() 
    {
        return notifyType;
    }
    public void setNotifyTime(Date notifyTime) 
    {
        this.notifyTime = notifyTime;
    }

    public Date getNotifyTime() 
    {
        return notifyTime;
    }
    public void setRetryTimes(Long retryTimes) 
    {
        this.retryTimes = retryTimes;
    }

    public Long getRetryTimes() 
    {
        return retryTimes;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("notifierId", getNotifierId())
            .append("noteName", getNoteName())
            .append("templateId", getTemplateId())
            .append("template", getTemplate())
            .append("context", getContext())
            .append("provider", getProvider())
            .append("notifyType", getNotifyType())
            .append("notifyTime", getNotifyTime())
            .append("retryTimes", getRetryTimes())
            .toString();
    }
}
