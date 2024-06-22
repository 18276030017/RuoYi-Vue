package com.ruoyi.devices.controller;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.devices.netty.server.DtuManage;
import com.ruoyi.devices.utils.DeviceStatus;
import com.ruoyi.system.service.ISysUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.devices.domain.device.Device1;
import com.ruoyi.devices.service.IDevice1Service;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;


/**
 * 设备Controller
 *
 * @author ruoyi
 * @date 2024-04-01
 */
@RestController
@RequestMapping("/devices/device1")
public class Device1Controller extends BaseController
{
    @Autowired
    private IDevice1Service device1Service;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ISysUserService userService;

    /**
     * 查询设备列表
     */
    @PreAuthorize("@ss.hasPermi('devices:device1:list')")
    @GetMapping("/list")
    public TableDataInfo list(Device1 device1)
    {
        startPage();
        List<Device1> list = device1Service.selectDevice1List(device1);
        return getDataTable(list);
    }

    /**
     * 导出设备列表
     */
    @PreAuthorize("@ss.hasPermi('devices:device1:export')")
    @Log(title = "设备", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Device1 device1)
    {
        List<Device1> list = device1Service.selectDevice1List(device1);
        ExcelUtil<Device1> util = new ExcelUtil<Device1>(Device1.class);
        util.exportExcel(response, list, "设备数据");
    }

    @Log(title = "设备导入", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('devices:device1:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<Device1> util = new ExcelUtil<Device1>(Device1.class);
        List<Device1> deviceList = util.importExcel(file.getInputStream());
        String operName = getUsername();
        String message = device1Service.importDevice(deviceList, updateSupport, operName);
        return success(message);
    }

    /**
     * 获取导入设备的模版
     */
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response)
    {
        ExcelUtil<Device1> util = new ExcelUtil<Device1>(Device1.class);
        util.importTemplateExcel(response, "用户数据");
    }

    @PreAuthorize("@ss.hasPermi('devices:device1:command')")
    @Log(title = "设备", businessType = BusinessType.OTHER)
    @PostMapping("/command")
    public  ResponseEntity<String> sendCommand(@Valid @RequestBody Device1 device1) {
        try {
            DtuManage dtuManage = new DtuManage();
            // 调用业务逻辑方法，返回CompletableFuture
            CompletableFuture<String> resultFuture = dtuManage.sendMsg(device1);
            // 使用CompletableFuture的get()方法阻塞等待结果
            String result;
            try {
                // 可能抛出ExecutionException和InterruptedException
                result = resultFuture.get();
            } catch (Exception e) {
                logger.error("无法从 CompletableFuture 获取结果", e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("内部服务器错误");
            }
            return ResponseEntity.ok(result);

        } catch (IllegalArgumentException e) { // 针对其他非网络异常，如参数验证失败等
            logger.error("无效参数导致设备命令发送失败", e);
            return ResponseEntity.badRequest().body("无效参数导致设备命令发送失败");
        } catch (Exception e) {
            logger.error("未知异常，设备命令发送失败", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("未知异常，设备命令发送失败");
        }
    }

    /**
     * 获取设备详细信息
     */
    @PreAuthorize("@ss.hasPermi('devices:device1:query')")
    @GetMapping(value = "/{deviceCode}")
    public AjaxResult getInfo(@PathVariable("deviceCode") String deviceCode)
    {
        return success(device1Service.selectDevice1ByDeviceCode(deviceCode));
    }

    /**
     * 获取设备个状态的数量
     */
    @GetMapping("/count")
    public Map<DeviceStatus, Integer> getDeviceStatusCounts()
    {
        return device1Service.getDeviceStatusCounts();
    }


    /**
     * 获取设备的新消息
     */
    @GetMapping("/message/{deviceCode}")
    public ResponseEntity<String> getLatestDevicesMessage(@PathVariable String deviceCode){
            String message = redisCache.getLatestDeviceMessage(deviceCode,"Response");
            if (message == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("设备响应为空");
            }
            logger.info("获取设备的最新消息: {}", message);
            // 找到第一个冒号的位置
            int colonIndex = message.indexOf(";");
            String msg = "";
            // 获取冒号前的字符串
            if (colonIndex != -1) {
                msg = message.substring(0, colonIndex);
            }
            // 获取冒号后的字符串
            String timestamp = "";
            if (colonIndex < message.length() - 1) {
                timestamp = message.substring(colonIndex + 1);
            }

            long currentTimeMillis = System.currentTimeMillis();
            long timeDifference = currentTimeMillis - Long.parseLong(timestamp);
            if (timeDifference > 5000) {
                // 处理超时的情况，如记录日志或返回默认值
                logger.error("获取设备的最新消息超时: {}", message);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("设备响应超时");
            }
            Pattern pattern = Pattern.compile("^\\{.*\\}");
            Matcher matcher = pattern.matcher(msg);
            String statusValue = "";
            if (matcher.find()) {
                try {
                    String jsonPart = matcher.group();
                    ObjectMapper mapper = new ObjectMapper();
                    JsonNode jsonNode = mapper.readTree(jsonPart);
                    // statusValue = jsonNode.get("status").asText();
                    statusValue = String.valueOf(jsonNode);
                } catch (JsonProcessingException e) {
                    logger.error("解析消息内容时出错", e);
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("解析消息内容时出错");
                }
            }
            logger.info("获取设备的最新消息成功: {}", message);
            return ResponseEntity.status(HttpStatus.OK).body(statusValue);
    }

    /**
     * 新增设备
     */
    @PreAuthorize("@ss.hasPermi('devices:device1:add')")
    @Log(title = "设备", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Device1 device1)
    {
        return toAjax(device1Service.insertDevice1(device1));
    }

    /**
     * 修改设备
     */
    @PreAuthorize("@ss.hasPermi('devices:device1:edit')")
    @Log(title = "设备", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Device1 device1)
    {
        return toAjax(device1Service.updateDevice1(device1));
    }

    /**
     * 删除设备
     */
    @PreAuthorize("@ss.hasPermi('devices:device1:remove')")
    @Log(title = "设备", businessType = BusinessType.DELETE)
    @DeleteMapping("/{deviceIds}")
    public AjaxResult remove(@PathVariable String[] deviceIds)
    {
        return toAjax(device1Service.deleteDevice1ByDeviceIds(deviceIds));
    }


/*
    @Value("${spring.mail.from}")
    private String from;

    @GetMapping("/email")
    public void sendEmail(){
        //  定制邮件内容
        StringBuffer content = new StringBuffer();
        content.append("心态还需努力呀~").append("\n");


        //三个参数:1.接收者  2.邮件标题  3.发送的内容
        device1Service.sendSimpleMail(from,"加油",content.toString());

    }*/


}
