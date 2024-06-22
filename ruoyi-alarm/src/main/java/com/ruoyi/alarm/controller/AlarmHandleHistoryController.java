package com.ruoyi.alarm.controller;

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
import com.ruoyi.alarm.domain.AlarmHandleHistory;
import com.ruoyi.alarm.service.IAlarmHandleHistoryService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 告警处理记录Controller
 * 
 * @author lfx
 * @date 2024-04-07
 */
@RestController
@RequestMapping("/alarm/history")
public class AlarmHandleHistoryController extends BaseController
{
    @Autowired
    private IAlarmHandleHistoryService alarmHandleHistoryService;

    /**
     * 查询告警处理记录列表
     */
    @PreAuthorize("@ss.hasPermi('alarm:history:list')")
    @GetMapping("/list")
    public TableDataInfo list(AlarmHandleHistory alarmHandleHistory)
    {
        startPage();
        List<AlarmHandleHistory> list = alarmHandleHistoryService.selectAlarmHandleHistoryList(alarmHandleHistory);
        return getDataTable(list);
    }

    /**
     * 导出告警处理记录列表
     */
    @PreAuthorize("@ss.hasPermi('alarm:history:export')")
    @Log(title = "告警处理记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AlarmHandleHistory alarmHandleHistory)
    {
        List<AlarmHandleHistory> list = alarmHandleHistoryService.selectAlarmHandleHistoryList(alarmHandleHistory);
        ExcelUtil<AlarmHandleHistory> util = new ExcelUtil<AlarmHandleHistory>(AlarmHandleHistory.class);
        util.exportExcel(response, list, "告警处理记录数据");
    }

    /**
     * 获取告警处理记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('alarm:history:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(alarmHandleHistoryService.selectAlarmHandleHistoryById(id));
    }

    /**
     * 新增告警处理记录
     */
    @PreAuthorize("@ss.hasPermi('alarm:history:add')")
    @Log(title = "告警处理记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AlarmHandleHistory alarmHandleHistory)
    {
        return toAjax(alarmHandleHistoryService.insertAlarmHandleHistory(alarmHandleHistory));
    }

    /**
     * 修改告警处理记录
     */
    @PreAuthorize("@ss.hasPermi('alarm:history:edit')")
    @Log(title = "告警处理记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AlarmHandleHistory alarmHandleHistory)
    {
        return toAjax(alarmHandleHistoryService.updateAlarmHandleHistory(alarmHandleHistory));
    }

    /**
     * 删除告警处理记录
     */
    @PreAuthorize("@ss.hasPermi('alarm:history:remove')")
    @Log(title = "告警处理记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(alarmHandleHistoryService.deleteAlarmHandleHistoryByIds(ids));
    }
}
