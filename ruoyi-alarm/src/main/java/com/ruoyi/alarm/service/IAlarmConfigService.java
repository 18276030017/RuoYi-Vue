package com.ruoyi.alarm.service;

import java.util.List;
import com.ruoyi.alarm.domain.AlarmConfig;

/**
 * 告警配置Service接口
 * 
 * @author lfx
 * @date 2024-04-07
 */
public interface IAlarmConfigService 
{
    /**
     * 查询告警配置
     * 
     * @param id 告警配置主键
     * @return 告警配置
     */
    public AlarmConfig selectAlarmConfigById(Long id);

    /**
     * 查询告警配置列表
     * 
     * @param alarmConfig 告警配置
     * @return 告警配置集合
     */
    public List<AlarmConfig> selectAlarmConfigList(AlarmConfig alarmConfig);

    /**
     * 新增告警配置
     * 
     * @param alarmConfig 告警配置
     * @return 结果
     */
    public int insertAlarmConfig(AlarmConfig alarmConfig);

    /**
     * 修改告警配置
     * 
     * @param alarmConfig 告警配置
     * @return 结果
     */
    public int updateAlarmConfig(AlarmConfig alarmConfig);

    /**
     * 批量删除告警配置
     * 
     * @param ids 需要删除的告警配置主键集合
     * @return 结果
     */
    public int deleteAlarmConfigByIds(Long[] ids);

    /**
     * 删除告警配置信息
     * 
     * @param id 告警配置主键
     * @return 结果
     */
    public int deleteAlarmConfigById(Long id);
}
