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
import com.ruoyi.alarm.domain.AlarmLevel;
import com.ruoyi.alarm.service.IAlarmLevelService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 告警级别Controller
 * 
 * @author lfx
 * @date 2024-04-07
 */
@RestController
@RequestMapping("/alarm/level")
public class AlarmLevelController extends BaseController
{
    @Autowired
    private IAlarmLevelService alarmLevelService;

    /**
     * 查询告警级别列表
     */
    @PreAuthorize("@ss.hasPermi('alarm:level:list')")
    @GetMapping("/list")
    public TableDataInfo list(AlarmLevel alarmLevel)
    {
        startPage();
        List<AlarmLevel> list = alarmLevelService.selectAlarmLevelList(alarmLevel);
        return getDataTable(list);
    }

    /**
     * 导出告警级别列表
     */
    @PreAuthorize("@ss.hasPermi('alarm:level:export')")
    @Log(title = "告警级别", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AlarmLevel alarmLevel)
    {
        List<AlarmLevel> list = alarmLevelService.selectAlarmLevelList(alarmLevel);
        ExcelUtil<AlarmLevel> util = new ExcelUtil<AlarmLevel>(AlarmLevel.class);
        util.exportExcel(response, list, "告警级别数据");
    }

    /**
     * 获取告警级别详细信息
     */
    @PreAuthorize("@ss.hasPermi('alarm:level:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(alarmLevelService.selectAlarmLevelById(id));
    }

    /**
     * 新增告警级别
     */
    @PreAuthorize("@ss.hasPermi('alarm:level:add')")
    @Log(title = "告警级别", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AlarmLevel alarmLevel)
    {
        return toAjax(alarmLevelService.insertAlarmLevel(alarmLevel));
    }

    /**
     * 修改告警级别
     */
    @PreAuthorize("@ss.hasPermi('alarm:level:edit')")
    @Log(title = "告警级别", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AlarmLevel alarmLevel)
    {
        return toAjax(alarmLevelService.updateAlarmLevel(alarmLevel));
    }

    /**
     * 删除告警级别
     */
    @PreAuthorize("@ss.hasPermi('alarm:level:remove')")
    @Log(title = "告警级别", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(alarmLevelService.deleteAlarmLevelByIds(ids));
    }
}
