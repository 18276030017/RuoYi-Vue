package com.ruoyi.alarm.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 告警记录对象 alarm_record
 * 
 * @author lfx
 * @date 2024-04-07
 */
public class AlarmRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Long id;

    /** 告警ID */
    @Excel(name = "告警ID")
    private String alarmId;

    /** 告警配置名称 */
    @Excel(name = "告警配置名称")
    private String alarmName;

    /** 告警级别（0提示 1通知 2告警 3严重 4紧急） */
    @Excel(name = "告警级别", readConverterExp = "0=提示,1=通知,2=告警,3=严重,4=紧急")
    private Long level;

    /** 告警目标类型(0设备 1产品 2其他) */
    @Excel(name = "告警目标类型(0设备 1产品 2其他)")
    private String targetType;

    /** 告警目标名称（关联场景名称） */
    @Excel(name = "告警目标名称", readConverterExp = "关=联场景名称")
    private String targetName;

    /** 告警源内容（具体告警信息） */
    @Excel(name = "告警源内容", readConverterExp = "具=体告警信息")
    private String message;

    /** 最近一次告警时间 */
    @Excel(name = "最近一次告警时间")
    private Long alarmTime;

    /** 告警记录状态（0告警中  1处理中  2已处理） */
    @Excel(name = "告警记录状态", readConverterExp = "0=告警中,1=处理中,2=已处理")
    private String state;

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
    public void setAlarmName(String alarmName) 
    {
        this.alarmName = alarmName;
    }

    public String getAlarmName() 
    {
        return alarmName;
    }
    public void setLevel(Long level) 
    {
        this.level = level;
    }

    public Long getLevel() 
    {
        return level;
    }
    public void setTargetType(String targetType) 
    {
        this.targetType = targetType;
    }

    public String getTargetType() 
    {
        return targetType;
    }
    public void setTargetName(String targetName) 
    {
        this.targetName = targetName;
    }

    public String getTargetName() 
    {
        return targetName;
    }
    public void setMessage(String message) 
    {
        this.message = message;
    }

    public String getMessage() 
    {
        return message;
    }
    public void setAlarmTime(Long alarmTime) 
    {
        this.alarmTime = alarmTime;
    }

    public Long getAlarmTime() 
    {
        return alarmTime;
    }
    public void setState(String state) 
    {
        this.state = state;
    }

    public String getState() 
    {
        return state;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("alarmId", getAlarmId())
            .append("alarmName", getAlarmName())
            .append("level", getLevel())
            .append("targetType", getTargetType())
            .append("targetName", getTargetName())
            .append("message", getMessage())
            .append("alarmTime", getAlarmTime())
            .append("state", getState())
            .toString();
    }
}
