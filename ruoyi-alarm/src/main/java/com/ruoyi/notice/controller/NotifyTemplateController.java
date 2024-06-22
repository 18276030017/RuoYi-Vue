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
import com.ruoyi.notice.domain.NotifyTemplate;
import com.ruoyi.notice.service.INotifyTemplateService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 消息通知模板Controller
 * 
 * @author ruoyi
 * @date 2024-04-07
 */
@RestController
@RequestMapping("/notice/template")
public class NotifyTemplateController extends BaseController
{
    @Autowired
    private INotifyTemplateService notifyTemplateService;

    /**
     * 查询消息通知模板列表
     */
    @PreAuthorize("@ss.hasPermi('notice:template:list')")
    @GetMapping("/list")
    public TableDataInfo list(NotifyTemplate notifyTemplate)
    {
        startPage();
        List<NotifyTemplate> list = notifyTemplateService.selectNotifyTemplateList(notifyTemplate);
        return getDataTable(list);
    }

    /**
     * 导出消息通知模板列表
     */
    @PreAuthorize("@ss.hasPermi('notice:template:export')")
    @Log(title = "消息通知模板", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, NotifyTemplate notifyTemplate)
    {
        List<NotifyTemplate> list = notifyTemplateService.selectNotifyTemplateList(notifyTemplate);
        ExcelUtil<NotifyTemplate> util = new ExcelUtil<NotifyTemplate>(NotifyTemplate.class);
        util.exportExcel(response, list, "消息通知模板数据");
    }

    /**
     * 获取消息通知模板详细信息
     */
    @PreAuthorize("@ss.hasPermi('notice:template:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(notifyTemplateService.selectNotifyTemplateById(id));
    }

    /**
     * 新增消息通知模板
     */
    @PreAuthorize("@ss.hasPermi('notice:template:add')")
    @Log(title = "消息通知模板", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody NotifyTemplate notifyTemplate)
    {
        return toAjax(notifyTemplateService.insertNotifyTemplate(notifyTemplate));
    }

    /**
     * 修改消息通知模板
     */
    @PreAuthorize("@ss.hasPermi('notice:template:edit')")
    @Log(title = "消息通知模板", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody NotifyTemplate notifyTemplate)
    {
        return toAjax(notifyTemplateService.updateNotifyTemplate(notifyTemplate));
    }

    /**
     * 删除消息通知模板
     */
    @PreAuthorize("@ss.hasPermi('notice:template:remove')")
    @Log(title = "消息通知模板", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(notifyTemplateService.deleteNotifyTemplateByIds(ids));
    }
}
