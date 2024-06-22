package com.ruoyi.alarm.service;

import java.util.List;
import com.ruoyi.alarm.domain.AlarmLevel;

/**
 * 告警级别Service接口
 * 
 * @author lfx
 * @date 2024-04-07
 */
public interface IAlarmLevelService 
{
    /**
     * 查询告警级别
     * 
     * @param id 告警级别主键
     * @return 告警级别
     */
    public AlarmLevel selectAlarmLevelById(Long id);

    /**
     * 查询告警级别列表
     * 
     * @param alarmLevel 告警级别
     * @return 告警级别集合
     */
    public List<AlarmLevel> selectAlarmLevelList(AlarmLevel alarmLevel);

    /**
     * 新增告警级别
     * 
     * @param alarmLevel 告警级别
     * @return 结果
     */
    public int insertAlarmLevel(AlarmLevel alarmLevel);

    /**
     * 修改告警级别
     * 
     * @param alarmLevel 告警级别
     * @return 结果
     */
    public int updateAlarmLevel(AlarmLevel alarmLevel);

    /**
     * 批量删除告警级别
     * 
     * @param ids 需要删除的告警级别主键集合
     * @return 结果
     */
    public int deleteAlarmLevelByIds(Long[] ids);

    /**
     * 删除告警级别信息
     * 
     * @param id 告警级别主键
     * @return 结果
     */
    public int deleteAlarmLevelById(Long id);
}
