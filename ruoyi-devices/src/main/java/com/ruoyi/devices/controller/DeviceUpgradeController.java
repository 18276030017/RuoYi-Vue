package com.ruoyi.devices.controller;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.devices.domain.device.DeviceUpgrade;
import com.ruoyi.devices.domain.device.Upgrade;
import com.ruoyi.devices.service.UpgradeService;
import com.ruoyi.devices.utils.devicetcp.SendDeviceManage;
import com.ruoyi.framework.config.ServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.ruoyi.common.utils.DateUtils.getNowDate;

/**
 * @author lfx
 * @program ruoyi
 * @description 设备升级管理控制类
 * @date 2024/04/29
 */
@RestController
@RequestMapping("/devices/upgrade")
public class DeviceUpgradeController extends BaseController {
    @Autowired
    private UpgradeService upgradeService;
    @Autowired
    private ServerConfig serverConfig;

    /**
     * 查询升级包列表
     */
    @PreAuthorize("@ss.hasPermi('devices:upgrade:list')")
    @GetMapping("/list")
    public TableDataInfo list(DeviceUpgrade deviceUpgrade)
    {
        startPage();
        List<DeviceUpgrade> list = upgradeService.selectUpgradeList(deviceUpgrade);
        return getDataTable(list);
    }

    /**
     * 导出设备升级列表
     */
    @PreAuthorize("@ss.hasPermi('system:upgrade:export')")
    @Log(title = "设备升级", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DeviceUpgrade deviceUpgrade)
    {
        List<DeviceUpgrade> list = upgradeService.selectUpgradeList(deviceUpgrade);
        ExcelUtil<DeviceUpgrade> util = new ExcelUtil<DeviceUpgrade>(DeviceUpgrade.class);
        util.exportExcel(response, list, "设备升级数据");
    }

    /**
     * 获取设备升级详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:upgrade:query')")
    @GetMapping(value = "/{upgradeId}")
    public AjaxResult getInfo(@PathVariable("upgradeId") Long upgradeId)
    {
        return success(upgradeService.selectDeviceUpgradeByUpgradeId(upgradeId));
    }

    /**
     * 新增设备升级
     */
    @PreAuthorize("@ss.hasPermi('system:upgrade:add')")
    @Log(title = "设备升级", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody DeviceUpgrade deviceUpgrade)
    {
        deviceUpgrade.setCreateBy(getUsername());
        deviceUpgrade.setCreateTime(getNowDate());
        return toAjax(upgradeService.insertUpgrade(deviceUpgrade));
    }

    /**
     * 修改设备升级
     */
    @PreAuthorize("@ss.hasPermi('system:upgrade:edit')")
    @Log(title = "修改升级", businessType = BusinessType.UPDATE)
    @PutMapping("/edit")
    public AjaxResult edit(@RequestBody DeviceUpgrade deviceUpgrade)
    {
        return toAjax(upgradeService.updateUpgrade(deviceUpgrade));
    }
    /**
     * 删除设备升级
     */
    @PreAuthorize("@ss.hasPermi('system:upgrade:remove')")
    @Log(title = "删除升级", businessType = BusinessType.DELETE)
    @DeleteMapping("/{upgradeIds}")
    public AjaxResult remove(@PathVariable Long[] upgradeIds)
    {
        return toAjax(upgradeService.deleteUpgradeByUpgradeIds(upgradeIds));
    }
    @PreAuthorize("@ss.hasPermi('devices:upgrade:add')")
    @PostMapping("/upload")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        if (file == null || file.isEmpty()){
            return error("上传文件不能为空");
        }
        try {
            // 上传文件路径
            String filePath = RuoYiConfig.getUploadPath();
            // 上传并返回新文件名称
            String fileName = FileUploadUtils.upload(filePath, file);
            String url = serverConfig.getUrl() + fileName;
            // 构建返回结果，包含文件路径
            JSONObject result = new JSONObject();
            result.put("success", true);
            result.put("message", "文件上传成功");
            result.put("url", url);
            result.put("fileName", fileName);
            result.put("newFileName", FileUtils.getName(fileName));
            result.put("originalFilename", file.getOriginalFilename());

            return success(result.toJSONString());
        }catch (Exception e){
            return error("文件上传失败："+e.getMessage());
        }
    }

    /**
     * 发送升级信息给对应的设备
     */
    @PreAuthorize("@ss.hasPermi('devices:upgrade:send')")
    @PostMapping("/send")
    public  ResponseEntity<String> send(@RequestBody Upgrade upgrade)
    {
        logger.info("发送升级信息给对应的设备"+upgrade);
        try {
            SendDeviceManage sendDeviceManage = new SendDeviceManage();
            // 调用业务逻辑方法，返回CompletableFuture
            CompletableFuture<String> resultFuture = sendDeviceManage.sendMsg(upgrade);
            // 使用CompletableFuture的get()方法阻塞等待结果
            String result;
            try {
                // 可能抛出ExecutionException和InterruptedException
                result = resultFuture.get();
            } catch (Exception e) {
                logger.error("无法从 CompletableFuture 获取结果", e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("内部服务器错误");
            }
            return ResponseEntity.status(HttpStatus.OK).body(result);

        } catch (IllegalArgumentException e) { // 针对其他非网络异常，如参数验证失败等
            logger.error("无效参数导致设备命令发送失败", e);
            return ResponseEntity.badRequest().body("无效参数导致设备命令发送失败");
        } catch (Exception e) {
            logger.error("未知异常，设备命令发送失败", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("未知异常，设备命令发送失败");
        }
    }
}
