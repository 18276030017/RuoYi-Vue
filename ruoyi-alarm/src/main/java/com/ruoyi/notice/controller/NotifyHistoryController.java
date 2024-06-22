package com.ruoyi.notice.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.notice.domain.NotifyHistory;
import com.ruoyi.notice.service.INotifyHistoryService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 通知记录Controller
 * 
 * @author lfx
 * @date 2024-04-07
 */
@RestController
@RequestMapping("/notice/history")
public class NotifyHistoryController extends BaseController
{
    @Autowired
    private INotifyHistoryService notifyHistoryService;

    /**
     * 查询通知记录列表
     */
    @PreAuthorize("@ss.hasPermi('notice:history:list')")
    @GetMapping("/list")
    public TableDataInfo list(NotifyHistory notifyHistory)
    {
        startPage();
        List<NotifyHistory> list = notifyHistoryService.selectNotifyHistoryList(notifyHistory);
        return getDataTable(list);
    }

    /**
     * 导出通知记录列表
     */
    @PreAuthorize("@ss.hasPermi('notice:history:export')")
    @Log(title = "通知记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, NotifyHistory notifyHistory)
    {
        List<NotifyHistory> list = notifyHistoryService.selectNotifyHistoryList(notifyHistory);
        ExcelUtil<NotifyHistory> util = new ExcelUtil<NotifyHistory>(NotifyHistory.class);
        util.exportExcel(response, list, "通知记录数据");
    }

    /**
     * 获取通知记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('notice:history:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(notifyHistoryService.selectNotifyHistoryById(id));
    }

    /**
     * 新增通知记录
     */
    @PreAuthorize("@ss.hasPermi('notice:history:add')")
    @Log(title = "通知记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody NotifyHistory notifyHistory)
    {
        return toAjax(notifyHistoryService.insertNotifyHistory(notifyHistory));
    }

    /**
     * 修改通知记录
     */
    @PreAuthorize("@ss.hasPermi('notice:history:edit')")
    @Log(title = "通知记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody NotifyHistory notifyHistory)
    {
        return toAjax(notifyHistoryService.updateNotifyHistory(notifyHistory));
    }

    /**
     * 删除通知记录
     */
    @PreAuthorize("@ss.hasPermi('notice:history:remove')")
    @Log(title = "通知记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(notifyHistoryService.deleteNotifyHistoryByIds(ids));
    }
}
