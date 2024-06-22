package com.ruoyi.notice.service;

import java.util.List;
import com.ruoyi.notice.domain.NotifyHistory;

/**
 * 通知记录Service接口
 * 
 * @author lfx
 * @date 2024-04-07
 */
public interface INotifyHistoryService 
{
    /**
     * 查询通知记录
     * 
     * @param id 通知记录主键
     * @return 通知记录
     */
    public NotifyHistory selectNotifyHistoryById(Long id);

    /**
     * 查询通知记录列表
     * 
     * @param notifyHistory 通知记录
     * @return 通知记录集合
     */
    public List<NotifyHistory> selectNotifyHistoryList(NotifyHistory notifyHistory);

    /**
     * 新增通知记录
     * 
     * @param notifyHistory 通知记录
     * @return 结果
     */
    public int insertNotifyHistory(NotifyHistory notifyHistory);

    /**
     * 修改通知记录
     * 
     * @param notifyHistory 通知记录
     * @return 结果
     */
    public int updateNotifyHistory(NotifyHistory notifyHistory);

    /**
     * 批量删除通知记录
     * 
     * @param ids 需要删除的通知记录主键集合
     * @return 结果
     */
    public int deleteNotifyHistoryByIds(Long[] ids);

    /**
     * 删除通知记录信息
     * 
     * @param id 通知记录主键
     * @return 结果
     */
    public int deleteNotifyHistoryById(Long id);
}
