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
import com.ruoyi.alarm.domain.AlarmConfig;
import com.ruoyi.alarm.service.IAlarmConfigService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 告警配置Controller
 * 
 * @author lfx
 * @date 2024-04-07
 */
@RestController
@RequestMapping("/alarm/config")
public class AlarmConfigController extends BaseController
{
    @Autowired
    private IAlarmConfigService alarmConfigService;

    /**
     * 查询告警配置列表
     */
    @PreAuthorize("@ss.hasPermi('alarm:config:list')")
    @GetMapping("/list")
    public TableDataInfo list(AlarmConfig alarmConfig)
    {
        startPage();
        List<AlarmConfig> list = alarmConfigService.selectAlarmConfigList(alarmConfig);
        return getDataTable(list);
    }

    /**
     * 导出告警配置列表
     */
    @PreAuthorize("@ss.hasPermi('alarm:config:export')")
    @Log(title = "告警配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AlarmConfig alarmConfig)
    {
        List<AlarmConfig> list = alarmConfigService.selectAlarmConfigList(alarmConfig);
        ExcelUtil<AlarmConfig> util = new ExcelUtil<AlarmConfig>(AlarmConfig.class);
        util.exportExcel(response, list, "告警配置数据");
    }

    /**
     * 获取告警配置详细信息
     */
    @PreAuthorize("@ss.hasPermi('alarm:config:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(alarmConfigService.selectAlarmConfigById(id));
    }

    /**
     * 新增告警配置
     */
    @PreAuthorize("@ss.hasPermi('alarm:config:add')")
    @Log(title = "告警配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AlarmConfig alarmConfig)
    {
        return toAjax(alarmConfigService.insertAlarmConfig(alarmConfig));
    }

    /**
     * 修改告警配置
     */
    @PreAuthorize("@ss.hasPermi('alarm:config:edit')")
    @Log(title = "告警配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AlarmConfig alarmConfig)
    {
        return toAjax(alarmConfigService.updateAlarmConfig(alarmConfig));
    }

    /**
     * 删除告警配置
     */
    @PreAuthorize("@ss.hasPermi('alarm:config:remove')")
    @Log(title = "告警配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(alarmConfigService.deleteAlarmConfigByIds(ids));
    }
}
