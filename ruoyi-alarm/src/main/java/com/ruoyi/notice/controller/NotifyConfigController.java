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
import com.ruoyi.notice.domain.NotifyConfig;
import com.ruoyi.notice.service.INotifyConfigService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 消息通知配置Controller
 * 
 * @author lfx
 * @date 2024-04-07
 */
@RestController
@RequestMapping("/notice/config")
public class NotifyConfigController extends BaseController
{
    @Autowired
    private INotifyConfigService notifyConfigService;

    /**
     * 查询消息通知配置列表
     */
    @PreAuthorize("@ss.hasPermi('notice:config:list')")
    @GetMapping("/list")
    public TableDataInfo list(NotifyConfig notifyConfig)
    {
        startPage();
        List<NotifyConfig> list = notifyConfigService.selectNotifyConfigList(notifyConfig);
        return getDataTable(list);
    }

    /**
     * 导出消息通知配置列表
     */
    @PreAuthorize("@ss.hasPermi('notice:config:export')")
    @Log(title = "消息通知配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, NotifyConfig notifyConfig)
    {
        List<NotifyConfig> list = notifyConfigService.selectNotifyConfigList(notifyConfig);
        ExcelUtil<NotifyConfig> util = new ExcelUtil<NotifyConfig>(NotifyConfig.class);
        util.exportExcel(response, list, "消息通知配置数据");
    }

    /**
     * 获取消息通知配置详细信息
     */
    @PreAuthorize("@ss.hasPermi('notice:config:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(notifyConfigService.selectNotifyConfigById(id));
    }

    /**
     * 新增消息通知配置
     */
    @PreAuthorize("@ss.hasPermi('notice:config:add')")
    @Log(title = "消息通知配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody NotifyConfig notifyConfig)
    {
        return toAjax(notifyConfigService.insertNotifyConfig(notifyConfig));
    }

    /**
     * 修改消息通知配置
     */
    @PreAuthorize("@ss.hasPermi('notice:config:edit')")
    @Log(title = "消息通知配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody NotifyConfig notifyConfig)
    {
        return toAjax(notifyConfigService.updateNotifyConfig(notifyConfig));
    }

    /**
     * 删除消息通知配置
     */
    @PreAuthorize("@ss.hasPermi('notice:config:remove')")
    @Log(title = "消息通知配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(notifyConfigService.deleteNotifyConfigByIds(ids));
    }
}
