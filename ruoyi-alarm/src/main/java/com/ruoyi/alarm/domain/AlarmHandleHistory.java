package com.ruoyi.alarm.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 告警处理记录对象 alarm_handle_history
 * 
 * @author lfx
 * @date 2024-04-07
 */
public class AlarmHandleHistory extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Long id;

    /** 告警ID */
    @Excel(name = "告警ID")
    private String alarmId;

    /** 告警级别（0提示 1通知 2告警 3严重 4紧急） */
    @Excel(name = "告警级别", readConverterExp = "0=提示,1=通知,2=告警,3=严重,4=紧急")
    private Long level;

    /** 告警配置名称 */
    @Excel(name = "告警配置名称")
    private String alarmName;

    /** 告警目标名称（关联场景名称） */
    @Excel(name = "告警目标名称", readConverterExp = "关=联场景名称")
    private String sceneName;

    /** 告警时间 */
    @Excel(name = "告警时间")
    private Long alarmTime;

    /** 告警处理类型（0人工  1系统） */
    @Excel(name = "告警处理类型", readConverterExp = "0=人工,1=系统")
    private String handleType;

    /** 说明 */
    @Excel(name = "说明")
    private String description;

    /** 处理时间 */
    @Excel(name = "处理时间")
    private Long handleTime;

    /** 处理者(只读) */
    @Excel(name = "处理者(只读)")
    private String userName;

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
    public void setLevel(Long level) 
    {
        this.level = level;
    }

    public Long getLevel() 
    {
        return level;
    }
    public void setAlarmName(String alarmName) 
    {
        this.alarmName = alarmName;
    }

    public String getAlarmName() 
    {
        return alarmName;
    }
    public void setSceneName(String sceneName) 
    {
        this.sceneName = sceneName;
    }

    public String getSceneName() 
    {
        return sceneName;
    }
    public void setAlarmTime(Long alarmTime) 
    {
        this.alarmTime = alarmTime;
    }

    public Long getAlarmTime() 
    {
        return alarmTime;
    }
    public void setHandleType(String handleType) 
    {
        this.handleType = handleType;
    }

    public String getHandleType() 
    {
        return handleType;
    }
    public void setDescription(String description) 
    {
        this.description = description;
    }

    public String getDescription() 
    {
        return description;
    }
    public void setHandleTime(Long handleTime) 
    {
        this.handleTime = handleTime;
    }

    public Long getHandleTime() 
    {
        return handleTime;
    }
    public void setUserName(String userName) 
    {
        this.userName = userName;
    }

    public String getUserName() 
    {
        return userName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("alarmId", getAlarmId())
            .append("level", getLevel())
            .append("alarmName", getAlarmName())
            .append("sceneName", getSceneName())
            .append("alarmTime", getAlarmTime())
            .append("handleType", getHandleType())
            .append("description", getDescription())
            .append("handleTime", getHandleTime())
            .append("userName", getUserName())
            .toString();
    }
}
