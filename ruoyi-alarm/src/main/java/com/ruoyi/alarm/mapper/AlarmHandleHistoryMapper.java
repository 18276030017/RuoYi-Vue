package com.ruoyi.alarm.mapper;

import java.util.List;
import com.ruoyi.alarm.domain.AlarmHandleHistory;

/**
 * 告警处理记录Mapper接口
 * 
 * @author lfx
 * @date 2024-04-07
 */
public interface AlarmHandleHistoryMapper 
{
    /**
     * 查询告警处理记录
     * 
     * @param id 告警处理记录主键
     * @return 告警处理记录
     */
    public AlarmHandleHistory selectAlarmHandleHistoryById(Long id);

    /**
     * 查询告警处理记录列表
     * 
     * @param alarmHandleHistory 告警处理记录
     * @return 告警处理记录集合
     */
    public List<AlarmHandleHistory> selectAlarmHandleHistoryList(AlarmHandleHistory alarmHandleHistory);

    /**
     * 新增告警处理记录
     * 
     * @param alarmHandleHistory 告警处理记录
     * @return 结果
     */
    public int insertAlarmHandleHistory(AlarmHandleHistory alarmHandleHistory);

    /**
     * 修改告警处理记录
     * 
     * @param alarmHandleHistory 告警处理记录
     * @return 结果
     */
    public int updateAlarmHandleHistory(AlarmHandleHistory alarmHandleHistory);

    /**
     * 删除告警处理记录
     * 
     * @param id 告警处理记录主键
     * @return 结果
     */
    public int deleteAlarmHandleHistoryById(Long id);

    /**
     * 批量删除告警处理记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAlarmHandleHistoryByIds(Long[] ids);
}
