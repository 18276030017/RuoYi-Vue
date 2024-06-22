package com.ruoyi.notice.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 消息通知模板对象 notify_template
 * 
 * @author ruoyi
 * @date 2024-04-07
 */
public class NotifyTemplate extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Long id;

    /** 模板ID(只能由数字,字母,下划线和中划线组成) */
    @Excel(name = "模板ID(只能由数字,字母,下划线和中划线组成)")
    private String templateId;

    /** 通知方式 */
    @Excel(name = "通知方式")
    private String type;

    /** 模版名称 */
    @Excel(name = "模版名称")
    private String name;

    /** 模版内容(根据服务商不同而不同) */
    @Excel(name = "模版内容(根据服务商不同而不同)")
    private String template;

    /** 创建者ID(只读) */
    @Excel(name = "创建者ID(只读)")
    private String creatorId;

    /** 说明 */
    @Excel(name = "说明")
    private String description;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setTemplateId(String templateId) 
    {
        this.templateId = templateId;
    }

    public String getTemplateId() 
    {
        return templateId;
    }
    public void setType(String type) 
    {
        this.type = type;
    }

    public String getType() 
    {
        return type;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setTemplate(String template) 
    {
        this.template = template;
    }

    public String getTemplate() 
    {
        return template;
    }
    public void setCreatorId(String creatorId) 
    {
        this.creatorId = creatorId;
    }

    public String getCreatorId() 
    {
        return creatorId;
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
            .append("templateId", getTemplateId())
            .append("type", getType())
            .append("name", getName())
            .append("template", getTemplate())
            .append("creatorId", getCreatorId())
            .append("createTime", getCreateTime())
            .append("description", getDescription())
            .toString();
    }
}
