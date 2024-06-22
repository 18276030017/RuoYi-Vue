package com.ruoyi.alarm.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.alarm.mapper.AlarmRecordMapper;
import com.ruoyi.alarm.domain.AlarmRecord;
import com.ruoyi.alarm.service.IAlarmRecordService;

/**
 * 告警记录Service业务层处理
 * 
 * @author lfx
 * @date 2024-04-07
 */
@Service
public class AlarmRecordServiceImpl implements IAlarmRecordService 
{
    @Autowired
    private AlarmRecordMapper alarmRecordMapper;

    /**
     * 查询告警记录
     * 
     * @param id 告警记录主键
     * @return 告警记录
     */
    @Override
    public AlarmRecord selectAlarmRecordById(Long id)
    {
        return alarmRecordMapper.selectAlarmRecordById(id);
    }

    /**
     * 查询告警记录列表
     * 
     * @param alarmRecord 告警记录
     * @return 告警记录
     */
    @Override
    public List<AlarmRecord> selectAlarmRecordList(AlarmRecord alarmRecord)
    {
        return alarmRecordMapper.selectAlarmRecordList(alarmRecord);
    }

    /**
     * 新增告警记录
     * 
     * @param alarmRecord 告警记录
     * @return 结果
     */
    @Override
    public int insertAlarmRecord(AlarmRecord alarmRecord)
    {
        return alarmRecordMapper.insertAlarmRecord(alarmRecord);
    }

    /**
     * 修改告警记录
     * 
     * @param alarmRecord 告警记录
     * @return 结果
     */
    @Override
    public int updateAlarmRecord(AlarmRecord alarmRecord)
    {
        return alarmRecordMapper.updateAlarmRecord(alarmRecord);
    }

    /**
     * 批量删除告警记录
     * 
     * @param ids 需要删除的告警记录主键
     * @return 结果
     */
    @Override
    public int deleteAlarmRecordByIds(Long[] ids)
    {
        return alarmRecordMapper.deleteAlarmRecordByIds(ids);
    }

    /**
     * 删除告警记录信息
     * 
     * @param id 告警记录主键
     * @return 结果
     */
    @Override
    public int deleteAlarmRecordById(Long id)
    {
        return alarmRecordMapper.deleteAlarmRecordById(id);
    }
}
