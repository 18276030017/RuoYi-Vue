package com.ruoyi.notice.mapper;

import java.util.List;
import com.ruoyi.notice.domain.NotifyTemplate;

/**
 * 消息通知模板Mapper接口
 * 
 * @author ruoyi
 * @date 2024-04-07
 */
public interface NotifyTemplateMapper 
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
     * 删除消息通知模板
     * 
     * @param id 消息通知模板主键
     * @return 结果
     */
    public int deleteNotifyTemplateById(Long id);

    /**
     * 批量删除消息通知模板
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteNotifyTemplateByIds(Long[] ids);
}
