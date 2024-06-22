package com.ruoyi.alarm.mapper;

import java.util.List;
import com.ruoyi.alarm.domain.AlarmRecord;

/**
 * 告警记录Mapper接口
 * 
 * @author lfx
 * @date 2024-04-07
 */
public interface AlarmRecordMapper 
{
    /**
     * 查询告警记录
     * 
     * @param id 告警记录主键
     * @return 告警记录
     */
    public AlarmRecord selectAlarmRecordById(Long id);

    /**
     * 查询告警记录列表
     * 
     * @param alarmRecord 告警记录
     * @return 告警记录集合
     */
    public List<AlarmRecord> selectAlarmRecordList(AlarmRecord alarmRecord);

    /**
     * 新增告警记录
     * 
     * @param alarmRecord 告警记录
     * @return 结果
     */
    public int insertAlarmRecord(AlarmRecord alarmRecord);

    /**
     * 修改告警记录
     * 
     * @param alarmRecord 告警记录
     * @return 结果
     */
    public int updateAlarmRecord(AlarmRecord alarmRecord);

    /**
     * 删除告警记录
     * 
     * @param id 告警记录主键
     * @return 结果
     */
    public int deleteAlarmRecordById(Long id);

    /**
     * 批量删除告警记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAlarmRecordByIds(Long[] ids);
}
