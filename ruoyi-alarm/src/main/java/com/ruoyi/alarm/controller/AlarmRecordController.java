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
import com.ruoyi.alarm.domain.AlarmRecord;
import com.ruoyi.alarm.service.IAlarmRecordService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 告警记录Controller
 * 
 * @author lfx
 * @date 2024-04-07
 */
@RestController
@RequestMapping("/alarm/record")
public class AlarmRecordController extends BaseController
{
    @Autowired
    private IAlarmRecordService alarmRecordService;

    /**
     * 查询告警记录列表
     */
    @PreAuthorize("@ss.hasPermi('alarm:record:list')")
    @GetMapping("/list")
    public TableDataInfo list(AlarmRecord alarmRecord)
    {
        startPage();
        List<AlarmRecord> list = alarmRecordService.selectAlarmRecordList(alarmRecord);
        return getDataTable(list);
    }

    /**
     * 导出告警记录列表
     */
    @PreAuthorize("@ss.hasPermi('alarm:record:export')")
    @Log(title = "告警记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AlarmRecord alarmRecord)
    {
        List<AlarmRecord> list = alarmRecordService.selectAlarmRecordList(alarmRecord);
        ExcelUtil<AlarmRecord> util = new ExcelUtil<AlarmRecord>(AlarmRecord.class);
        util.exportExcel(response, list, "告警记录数据");
    }

    /**
     * 获取告警记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('alarm:record:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(alarmRecordService.selectAlarmRecordById(id));
    }

    /**
     * 新增告警记录
     */
    @PreAuthorize("@ss.hasPermi('alarm:record:add')")
    @Log(title = "告警记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AlarmRecord alarmRecord)
    {
        return toAjax(alarmRecordService.insertAlarmRecord(alarmRecord));
    }

    /**
     * 修改告警记录
     */
    @PreAuthorize("@ss.hasPermi('alarm:record:edit')")
    @Log(title = "告警记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AlarmRecord alarmRecord)
    {
        return toAjax(alarmRecordService.updateAlarmRecord(alarmRecord));
    }

    /**
     * 删除告警记录
     */
    @PreAuthorize("@ss.hasPermi('alarm:record:remove')")
    @Log(title = "告警记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(alarmRecordService.deleteAlarmRecordByIds(ids));
    }
}
