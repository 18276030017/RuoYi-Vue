package com.ruoyi.alarm.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 告警配置对象 alarm_config
 * 
 * @author lfx
 * @date 2024-04-07
 */
public class AlarmConfig extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Long id;

    /** 告警ID */
    @Excel(name = "告警ID")
    private String alarmId;

    /** 告警名称 */
    @Excel(name = "告警名称")
    private String name;

    /** 告警目标类型 */
    @Excel(name = "告警目标类型")
    private String targetType;

    /** 告警级别 */
    @Excel(name = "告警级别")
    private Long level;

    /** 关联场景名称 */
    @Excel(name = "关联场景名称")
    private String sceneName;

    /** 规则表达式 */
    @Excel(name = "规则表达式")
    private String alarmRule;

    /** 状态（0启用 1停用） */
    @Excel(name = "状态", readConverterExp = "0=启用,1=停用")
    private String state;

    /** 说明 */
    @Excel(name = "说明")
    private String description;

    /** 更新时间 */
    @Excel(name = "更新时间")
    private Long modifyTime;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setAlarmId(String alarmId) 
    {
        this.alarmId = alarmId;
    }

    public String getAlarmId() 
    {
        return alarmId;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setTargetType(String targetType) 
    {
        this.targetType = targetType;
    }

    public String getTargetType() 
    {
        return targetType;
    }
    public void setLevel(Long level) 
    {
        this.level = level;
    }

    public Long getLevel() 
    {
        return level;
    }
    public void setSceneName(String sceneName) 
    {
        this.sceneName = sceneName;
    }

    public String getSceneName() 
    {
        return sceneName;
    }
    public void setAlarmRule(String alarmRule) 
    {
        this.alarmRule = alarmRule;
    }

    public String getAlarmRule() 
    {
        return alarmRule;
    }
    public void setState(String state) 
    {
        this.state = state;
    }

    public String getState() 
    {
        return state;
    }
    public void setDescription(String description) 
    {
        this.description = description;
    }

    public String getDescription() 
    {
        return description;
    }
    public void setModifyTime(Long modifyTime) 
    {
        this.modifyTime = modifyTime;
    }

    public Long getModifyTime() 
    {
        return modifyTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("alarmId", getAlarmId())
            .append("name", getName())
            .append("targetType", getTargetType())
            .append("level", getLevel())
            .append("sceneName", getSceneName())
            .append("alarmRule", getAlarmRule())
            .append("state", getState())
            .append("description", getDescription())
            .append("createTime", getCreateTime())
            .append("modifyTime", getModifyTime())
            .toString();
    }
}
