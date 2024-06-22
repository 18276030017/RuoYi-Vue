package com.ruoyi.alarm.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.alarm.mapper.AlarmConfigMapper;
import com.ruoyi.alarm.domain.AlarmConfig;
import com.ruoyi.alarm.service.IAlarmConfigService;

/**
 * 告警配置Service业务层处理
 * 
 * @author lfx
 * @date 2024-04-07
 */
@Service
public class AlarmConfigServiceImpl implements IAlarmConfigService 
{
    @Autowired
    private AlarmConfigMapper alarmConfigMapper;

    /**
     * 查询告警配置
     * 
     * @param id 告警配置主键
     * @return 告警配置
     */
    @Override
    public AlarmConfig selectAlarmConfigById(Long id)
    {
        return alarmConfigMapper.selectAlarmConfigById(id);
    }

    /**
     * 查询告警配置列表
     * 
     * @param alarmConfig 告警配置
     * @return 告警配置
     */
    @Override
    public List<AlarmConfig> selectAlarmConfigList(AlarmConfig alarmConfig)
    {
        return alarmConfigMapper.selectAlarmConfigList(alarmConfig);
    }

    /**
     * 新增告警配置
     * 
     * @param alarmConfig 告警配置
     * @return 结果
     */
    @Override
    public int insertAlarmConfig(AlarmConfig alarmConfig)
    {
        alarmConfig.setCreateTime(DateUtils.getNowDate());
        return alarmConfigMapper.insertAlarmConfig(alarmConfig);
    }

    /**
     * 修改告警配置
     * 
     * @param alarmConfig 告警配置
     * @return 结果
     */
    @Override
    public int updateAlarmConfig(AlarmConfig alarmConfig)
    {
        return alarmConfigMapper.updateAlarmConfig(alarmConfig);
    }

    /**
     * 批量删除告警配置
     * 
     * @param ids 需要删除的告警配置主键
     * @return 结果
     */
    @Override
    public int deleteAlarmConfigByIds(Long[] ids)
    {
        return alarmConfigMapper.deleteAlarmConfigByIds(ids);
    }

    /**
     * 删除告警配置信息
     * 
     * @param id 告警配置主键
     * @return 结果
     */
    @Override
    public int deleteAlarmConfigById(Long id)
    {
        return alarmConfigMapper.deleteAlarmConfigById(id);
    }
}
