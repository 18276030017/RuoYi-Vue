package com.ruoyi.alarm.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.alarm.mapper.AlarmLevelMapper;
import com.ruoyi.alarm.domain.AlarmLevel;
import com.ruoyi.alarm.service.IAlarmLevelService;

/**
 * 告警级别Service业务层处理
 * 
 * @author lfx
 * @date 2024-04-07
 */
@Service
public class AlarmLevelServiceImpl implements IAlarmLevelService 
{
    @Autowired
    private AlarmLevelMapper alarmLevelMapper;

    /**
     * 查询告警级别
     * 
     * @param id 告警级别主键
     * @return 告警级别
     */
    @Override
    public AlarmLevel selectAlarmLevelById(Long id)
    {
        return alarmLevelMapper.selectAlarmLevelById(id);
    }

    /**
     * 查询告警级别列表
     * 
     * @param alarmLevel 告警级别
     * @return 告警级别
     */
    @Override
    public List<AlarmLevel> selectAlarmLevelList(AlarmLevel alarmLevel)
    {
        return alarmLevelMapper.selectAlarmLevelList(alarmLevel);
    }

    /**
     * 新增告警级别
     * 
     * @param alarmLevel 告警级别
     * @return 结果
     */
    @Override
    public int insertAlarmLevel(AlarmLevel alarmLevel)
    {
        return alarmLevelMapper.insertAlarmLevel(alarmLevel);
    }

    /**
     * 修改告警级别
     * 
     * @param alarmLevel 告警级别
     * @return 结果
     */
    @Override
    public int updateAlarmLevel(AlarmLevel alarmLevel)
    {
        return alarmLevelMapper.updateAlarmLevel(alarmLevel);
    }

    /**
     * 批量删除告警级别
     * 
     * @param ids 需要删除的告警级别主键
     * @return 结果
     */
    @Override
    public int deleteAlarmLevelByIds(Long[] ids)
    {
        return alarmLevelMapper.deleteAlarmLevelByIds(ids);
    }

    /**
     * 删除告警级别信息
     * 
     * @param id 告警级别主键
     * @return 结果
     */
    @Override
    public int deleteAlarmLevelById(Long id)
    {
        return alarmLevelMapper.deleteAlarmLevelById(id);
    }
}
