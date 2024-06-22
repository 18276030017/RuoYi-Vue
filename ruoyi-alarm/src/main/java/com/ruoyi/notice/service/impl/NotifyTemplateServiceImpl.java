package com.ruoyi.notice.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.notice.mapper.NotifyTemplateMapper;
import com.ruoyi.notice.domain.NotifyTemplate;
import com.ruoyi.notice.service.INotifyTemplateService;

/**
 * 消息通知模板Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-04-07
 */
@Service
public class NotifyTemplateServiceImpl implements INotifyTemplateService 
{
    @Autowired
    private NotifyTemplateMapper notifyTemplateMapper;

    /**
     * 查询消息通知模板
     * 
     * @param id 消息通知模板主键
     * @return 消息通知模板
     */
    @Override
    public NotifyTemplate selectNotifyTemplateById(Long id)
    {
        return notifyTemplateMapper.selectNotifyTemplateById(id);
    }

    /**
     * 查询消息通知模板列表
     * 
     * @param notifyTemplate 消息通知模板
     * @return 消息通知模板
     */
    @Override
    public List<NotifyTemplate> selectNotifyTemplateList(NotifyTemplate notifyTemplate)
    {
        return notifyTemplateMapper.selectNotifyTemplateList(notifyTemplate);
    }

    /**
     * 新增消息通知模板
     * 
     * @param notifyTemplate 消息通知模板
     * @return 结果
     */
    @Override
    public int insertNotifyTemplate(NotifyTemplate notifyTemplate)
    {
        notifyTemplate.setCreateTime(DateUtils.getNowDate());
        return notifyTemplateMapper.insertNotifyTemplate(notifyTemplate);
    }

    /**
     * 修改消息通知模板
     * 
     * @param notifyTemplate 消息通知模板
     * @return 结果
     */
    @Override
    public int updateNotifyTemplate(NotifyTemplate notifyTemplate)
    {
        return notifyTemplateMapper.updateNotifyTemplate(notifyTemplate);
    }

    /**
     * 批量删除消息通知模板
     * 
     * @param ids 需要删除的消息通知模板主键
     * @return 结果
     */
    @Override
    public int deleteNotifyTemplateByIds(Long[] ids)
    {
        return notifyTemplateMapper.deleteNotifyTemplateByIds(ids);
    }

    /**
     * 删除消息通知模板信息
     * 
     * @param id 消息通知模板主键
     * @return 结果
     */
    @Override
    public int deleteNotifyTemplateById(Long id)
    {
        return notifyTemplateMapper.deleteNotifyTemplateById(id);
    }
}
