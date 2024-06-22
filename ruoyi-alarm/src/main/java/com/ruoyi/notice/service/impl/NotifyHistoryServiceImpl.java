package com.ruoyi.notice.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.notice.mapper.NotifyHistoryMapper;
import com.ruoyi.notice.domain.NotifyHistory;
import com.ruoyi.notice.service.INotifyHistoryService;

/**
 * 通知记录Service业务层处理
 * 
 * @author lfx
 * @date 2024-04-07
 */
@Service
public class NotifyHistoryServiceImpl implements INotifyHistoryService 
{
    @Autowired
    private NotifyHistoryMapper notifyHistoryMapper;

    /**
     * 查询通知记录
     * 
     * @param id 通知记录主键
     * @return 通知记录
     */
    @Override
    public NotifyHistory selectNotifyHistoryById(Long id)
    {
        return notifyHistoryMapper.selectNotifyHistoryById(id);
    }

    /**
     * 查询通知记录列表
     * 
     * @param notifyHistory 通知记录
     * @return 通知记录
     */
    @Override
    public List<NotifyHistory> selectNotifyHistoryList(NotifyHistory notifyHistory)
    {
        return notifyHistoryMapper.selectNotifyHistoryList(notifyHistory);
    }

    /**
     * 新增通知记录
     * 
     * @param notifyHistory 通知记录
     * @return 结果
     */
    @Override
    public int insertNotifyHistory(NotifyHistory notifyHistory)
    {
        return notifyHistoryMapper.insertNotifyHistory(notifyHistory);
    }

    /**
     * 修改通知记录
     * 
     * @param notifyHistory 通知记录
     * @return 结果
     */
    @Override
    public int updateNotifyHistory(NotifyHistory notifyHistory)
    {
        return notifyHistoryMapper.updateNotifyHistory(notifyHistory);
    }

    /**
     * 批量删除通知记录
     * 
     * @param ids 需要删除的通知记录主键
     * @return 结果
     */
    @Override
    public int deleteNotifyHistoryByIds(Long[] ids)
    {
        return notifyHistoryMapper.deleteNotifyHistoryByIds(ids);
    }

    /**
     * 删除通知记录信息
     * 
     * @param id 通知记录主键
     * @return 结果
     */
    @Override
    public int deleteNotifyHistoryById(Long id)
    {
        return notifyHistoryMapper.deleteNotifyHistoryById(id);
    }
}
