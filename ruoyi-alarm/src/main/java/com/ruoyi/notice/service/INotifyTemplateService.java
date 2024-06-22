package com.ruoyi.notice.service;

import java.util.List;
import com.ruoyi.notice.domain.NotifyTemplate;

/**
 * 消息通知模板Service接口
 * 
 * @author ruoyi
 * @date 2024-04-07
 */
public interface INotifyTemplateService 
{
    /**
     * 查询消息通知模板
     * 
     * @param id 消息通知模板主键
     * @return 消息通知模板
     */
    public NotifyTemplate selectNotifyTemplateById(Long id);

    /**
     * 查询消息通知模板列表
     * 
     * @param notifyTemplate 消息通知模板
     * @return 消息通知模板集合
     */
    public List<NotifyTemplate> selectNotifyTemplateList(NotifyTemplate notifyTemplate);

    /**
     * 新增消息通知模板
     * 
     * @param notifyTemplate 消息通知模板
     * @return 结果
     */
    public int insertNotifyTemplate(NotifyTemplate notifyTemplate);

    /**
     * 修改消息通知模板
     * 
     * @param notifyTemplate 消息通知模板
     * @return 结果
     */
    public int updateNotifyTemplate(NotifyTemplate notifyTemplate);

    /**
     * 批量删除消息通知模板
     * 
     * @param ids 需要删除的消息通知模板主键集合
     * @return 结果
     */
    public int deleteNotifyTemplateByIds(Long[] ids);

    /**
     * 删除消息通知模板信息
     * 
     * @param id 消息通知模板主键
     * @return 结果
     */
    public int deleteNotifyTemplateById(Long id);
}
