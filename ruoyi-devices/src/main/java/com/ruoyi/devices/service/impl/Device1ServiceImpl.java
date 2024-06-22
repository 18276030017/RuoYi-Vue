package com.ruoyi.devices.service.impl;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanValidators;
import com.ruoyi.devices.domain.device.Device1;
import com.ruoyi.devices.utils.DeviceStatus;
import com.ruoyi.devices.mapper.Device1Mapper;
import com.ruoyi.devices.service.IDevice1Service;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.validation.Validator;
import java.io.*;
import java.util.*;

import static com.ruoyi.common.utils.SecurityUtils.getUsername;

/**
 * 设备Service业务层处理
 *
 * @author ruoyi
 * @date 2024-04-01
 */
@Service
@Slf4j
public class Device1ServiceImpl implements IDevice1Service {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private Device1Mapper device1Mapper;

    @Autowired
    protected Validator validator;

    @Value("${spring.mail.from}")
    private String from;

    /**
     * 查询设备
     *
     * @param deviceCode 设备编码
     * @return 设备
     */
    @Override
    public Device1 selectDevice1ByDeviceCode(String deviceCode) {
        return device1Mapper.selectDevice1ByDeviceCode(deviceCode);
    }

    /**
     * 查询设备离线数量
     *
     * @param
     * @return 设备离线数量
     */
    @Override
    public Map<DeviceStatus, Integer> getDeviceStatusCounts() {
        Map<DeviceStatus, Integer> map = new EnumMap<>(DeviceStatus.class);
        for (DeviceStatus status : DeviceStatus.values()) {
            map.put(status, device1Mapper.selectDeviceCountByStatus(status.getValue()));
        }

        // DeviceStatusUpdate deviceStatusUpdate = new DeviceStatusUpdate();
        // Device1 datetime = device1Mapper.selectOnlineTimestamp();
        // LocalDateTime date1 = datetime.getCreateTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        // LocalDateTime date2 = datetime.getUpdateTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        // int offline = map.get(DeviceStatus.OFFLINE);
        // int alarm = map.get(DeviceStatus.ALARM);
        // int fault = map.get(DeviceStatus.FAULT);
        // if (offline > 0 || alarm > 0 || fault > 0){
        //     //当有设备离线，报警，故障，则将这些设备信息发送到对应负责人
        //     Device1 device1 = new Device1();
        //     if (deviceStatusUpdate.getOfflineDuration(date2, date1).toMinutes() > 5){
        //         String content = deviceStatusUpdate.getFormattedOfflineDuration(date2, date1);
        //         device1.setOfftime(content);
        //
        //         //device1Mapper.insertDevice1(device1);
        //         //sendSimpleMail(from, "hello", "您好呀！ String content");
        //     }
        //
        //     // 通过邮件发送设备信息给负责人
        //     // sendEmail("responsiblePerson@example.com", "设备信息", "详情: " + "设备信息");
        // }
        return map;
    }

    /**
     * 查询设备列表
     *
     * @param device1 设备
     * @return 设备
     */
    @Override
    public List<Device1> selectDevice1List(Device1 device1) {
        return device1Mapper.selectDevice1List(device1);
    }

    /**
     * 新增设备
     *
     * @param device1 设备
     * @return 结果
     */
    @Override
    public int insertDevice1(Device1 device1) {
        device1.setCreateTime(DateUtils.getNowDate());
        device1.setCreateBy(getUsername());
       device1.setDeviceStatus("7");
        return device1Mapper.insertDevice1(device1);
    }

    /**
     * 修改设备
     *
     * @param device1 设备
     * @return 结果
     */
    @Override
    public int updateDevice1(Device1 device1) {
        device1.setUpdateTime(DateUtils.getNowDate());
        return device1Mapper.updateDevice1(device1);
    }

    /**
     * 批量删除设备
     *
     * @param deviceIds 需要删除的设备主键
     * @return 结果
     */
    @Override
    public int deleteDevice1ByDeviceIds(String[] deviceIds) {
        return device1Mapper.deleteDevice1ByDeviceIds(deviceIds);
    }

    @Override
    public String importDevice(List<Device1> deviceList, Boolean isUpdateSupport, String operName) {
        if (StringUtils.isNull(deviceList) || deviceList.isEmpty())
        {
            throw new ServiceException("导入用户数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (Device1 device : deviceList)
        {
            try
            {
                // 验证是否存在这个用户
                Device1 u = device1Mapper.selectDevice1ByDeviceCode(device.getDeviceCode());
                if (StringUtils.isNull(u))
                {
                    BeanValidators.validateWithException(validator, device);
                    device.setCreateTime(DateUtils.getNowDate());
                    device.setCreateBy(operName);
                    device.setDeviceStatus("7");
                    device1Mapper.insertDevice1(device);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、账号 " + device.getDeviceCode() + " 导入成功");
                }
                else if (isUpdateSupport)
                {
                    BeanValidators.validateWithException(validator, device);
                    device.setUpdateTime(DateUtils.getNowDate());
                    device.setDeviceCode(u.getDeviceCode());
                    device.setUpdateBy(operName);
                    device1Mapper.updateDevice1(device);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、设备 " + device.getDeviceCode() + " 更新成功");
                }
                else
                {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、设备 " + device.getDeviceCode() + " 已存在");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、设备 " + device.getDeviceCode() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        }
        else
        {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

    /**
     * 删除设备信息
     *
     * @param deviceId 设备主键
     * @return 结果
     */
    @Override
    public int deleteDevice1ByDeviceId(String deviceId) {
        return device1Mapper.deleteDevice1ByDeviceId(deviceId);
    }

    @Override
    public void sendSimpleMail(String to, String subject, String content) {
        //创建SimpleMailMessage对象
        SimpleMailMessage message = new SimpleMailMessage();
        //邮件发送人
        message.setFrom(from);
        //邮件接收人
        message.setTo(to);
        //邮件主题
        message.setSubject(subject);
        //邮件内容
        message.setText(content);
        //发送邮件
        mailSender.send(message);
    }

    /**
     * html邮件
     * @param to 收件人,多个时参数形式 ："xxx@xxx.com,xxx@xxx.com,xxx@xxx.com"
     * @param subject 主题
     * @param content 内容
     */
    @Override
    public void sendHtmlMail(String to, String subject, String content) {
        //获取MimeMessage对象
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper;
        try {
            messageHelper = new MimeMessageHelper(message, true);
            //邮件发送人
            messageHelper.setFrom(from);
            //邮件接收人,设置多个收件人地址
            InternetAddress[] internetAddressTo = InternetAddress.parse(to);
            messageHelper.setTo(internetAddressTo);
            //messageHelper.setTo(to);
            //邮件主题
            message.setSubject(subject);
            //邮件内容，html格式
            messageHelper.setText(content, true);
            //发送
            mailSender.send(message);
            //日志信息
            logger.info("邮件已经发送。");
        } catch (Exception e) {
            logger.error("发送邮件时发生异常！", e);
        }
    }

    /**
     * 带附件的邮件
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     * @param filePath 附件
     */
    @Override
    public void sendAttachmentsMail(String to, String subject, String content, String filePath) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            FileSystemResource file = new FileSystemResource(new File(filePath));
            String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
            helper.addAttachment(fileName, file);
            mailSender.send(message);
            //日志信息
            logger.info("邮件已经发送。");
        } catch (Exception e) {
            logger.error("发送邮件时发生异常！", e);
        }
    }
}
