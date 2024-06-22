package com.ruoyi.alarm.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.alarm.mapper.AlarmHandleHistoryMapper;
import com.ruoyi.alarm.domain.AlarmHandleHistory;
import com.ruoyi.alarm.service.IAlarmHandleHistoryService;

/**
 * 告警处理记录Service业务层处理
 * 
 * @author lfx
 * @date 2024-04-07
 */
@Service
public class AlarmHandleHistoryServiceImpl implements IAlarmHandleHistoryService 
{
    @Autowired
    private AlarmHandleHistoryMapper alarmHandleHistoryMapper;

    /**
     * 查询告警处理记录
     * 
     * @param id 告警处理记录主键
     * @return 告警处理记录
     */
    @Override
    public AlarmHandleHistory selectAlarmHandleHistoryById(Long id)
    {
        return alarmHandleHistoryMapper.selectAlarmHandleHistoryById(id);
    }

    /**
     * 查询告警处理记录列表
     * 
     * @param alarmHandleHistory 告警处理记录
     * @return 告警处理记录
     */
    @Override
    public List<AlarmHandleHistory> selectAlarmHandleHistoryList(AlarmHandleHistory alarmHandleHistory)
    {
        return alarmHandleHistoryMapper.selectAlarmHandleHistoryList(alarmHandleHistory);
    }

    /**
     * 新增告警处理记录
     * 
     * @param alarmHandleHistory 告警处理记录
     * @return 结果
     */
    @Override
    public int insertAlarmHandleHistory(AlarmHandleHistory alarmHandleHistory)
    {
        return alarmHandleHistoryMapper.insertAlarmHandleHistory(alarmHandleHistory);
    }

    /**
     * 修改告警处理记录
     * 
     * @param alarmHandleHistory 告警处理记录
     * @return 结果
     */
    @Override
    public int updateAlarmHandleHistory(AlarmHandleHistory alarmHandleHistory)
    {
        return alarmHandleHistoryMapper.updateAlarmHandleHistory(alarmHandleHistory);
    }

    /**
     * 批量删除告警处理记录
     * 
     * @param ids 需要删除的告警处理记录主键
     * @return 结果
     */
    @Override
    public int deleteAlarmHandleHistoryByIds(Long[] ids)
    {
        return alarmHandleHistoryMapper.deleteAlarmHandleHistoryByIds(ids);
    }

    /**
     * 删除告警处理记录信息
     * 
     * @param id 告警处理记录主键
     * @return 结果
     */
    @Override
    public int deleteAlarmHandleHistoryById(Long id)
    {
        return alarmHandleHistoryMapper.deleteAlarmHandleHistoryById(id);
    }
}
