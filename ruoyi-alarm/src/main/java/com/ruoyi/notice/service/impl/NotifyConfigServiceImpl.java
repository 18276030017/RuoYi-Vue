package com.ruoyi.notice.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.notice.mapper.NotifyConfigMapper;
import com.ruoyi.notice.domain.NotifyConfig;
import com.ruoyi.notice.service.INotifyConfigService;

/**
 * 消息通知配置Service业务层处理
 * 
 * @author lfx
 * @date 2024-04-07
 */
@Service
public class NotifyConfigServiceImpl implements INotifyConfigService 
{
    @Autowired
    private NotifyConfigMapper notifyConfigMapper;

    /**
     * 查询消息通知配置
     * 
     * @param id 消息通知配置主键
     * @return 消息通知配置
     */
    @Override
    public NotifyConfig selectNotifyConfigById(Long id)
    {
        return notifyConfigMapper.selectNotifyConfigById(id);
    }

    /**
     * 查询消息通知配置列表
     * 
     * @param notifyConfig 消息通知配置
     * @return 消息通知配置
     */
    @Override
    public List<NotifyConfig> selectNotifyConfigList(NotifyConfig notifyConfig)
    {
        return notifyConfigMapper.selectNotifyConfigList(notifyConfig);
    }

    /**
     * 新增消息通知配置
     * 
     * @param notifyConfig 消息通知配置
     * @return 结果
     */
    @Override
    public int insertNotifyConfig(NotifyConfig notifyConfig)
    {
        notifyConfig.setCreateTime(DateUtils.getNowDate());
        return notifyConfigMapper.insertNotifyConfig(notifyConfig);
    }

    /**
     * 修改消息通知配置
     * 
     * @param notifyConfig 消息通知配置
     * @return 结果
     */
    @Override
    public int updateNotifyConfig(NotifyConfig notifyConfig)
    {
        return notifyConfigMapper.updateNotifyConfig(notifyConfig);
    }

    /**
     * 批量删除消息通知配置
     * 
     * @param ids 需要删除的消息通知配置主键
     * @return 结果
     */
    @Override
    public int deleteNotifyConfigByIds(Long[] ids)
    {
        return notifyConfigMapper.deleteNotifyConfigByIds(ids);
    }

    /**
     * 删除消息通知配置信息
     * 
     * @param id 消息通知配置主键
     * @return 结果
     */
    @Override
    public int deleteNotifyConfigById(Long id)
    {
        return notifyConfigMapper.deleteNotifyConfigById(id);
    }
}
