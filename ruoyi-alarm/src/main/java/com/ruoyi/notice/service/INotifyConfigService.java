package com.ruoyi.notice.service;

import java.util.List;
import com.ruoyi.notice.domain.NotifyConfig;

/**
 * 消息通知配置Service接口
 * 
 * @author lfx
 * @date 2024-04-07
 */
public interface INotifyConfigService 
{
    /**
     * 查询消息通知配置
     * 
     * @param id 消息通知配置主键
     * @return 消息通知配置
     */
    public NotifyConfig selectNotifyConfigById(Long id);

    /**
     * 查询消息通知配置列表
     * 
     * @param notifyConfig 消息通知配置
     * @return 消息通知配置集合
     */
    public List<NotifyConfig> selectNotifyConfigList(NotifyConfig notifyConfig);

    /**
     * 新增消息通知配置
     * 
     * @param notifyConfig 消息通知配置
     * @return 结果
     */
    public int insertNotifyConfig(NotifyConfig notifyConfig);

    /**
     * 修改消息通知配置
     * 
     * @param notifyConfig 消息通知配置
     * @return 结果
     */
    public int updateNotifyConfig(NotifyConfig notifyConfig);

    /**
     * 批量删除消息通知配置
     * 
     * @param ids 需要删除的消息通知配置主键集合
     * @return 结果
     */
    public int deleteNotifyConfigByIds(Long[] ids);

    /**
     * 删除消息通知配置信息
     * 
     * @param id 消息通知配置主键
     * @return 结果
     */
    public int deleteNotifyConfigById(Long id);
}
