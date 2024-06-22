package com.ruoyi.devices.service;

import java.util.List;
import java.util.Map;

import com.ruoyi.devices.domain.device.Device1;
import com.ruoyi.devices.utils.DeviceStatus;

/**
 * 设备Service接口
 *
 * @author ruoyi
 * @date 2024-04-01
 */
public interface IDevice1Service
{
    /**
     * 查询设备
     *
     * @param deviceCode 设备唯一标识编码
     * @return 设备
     */
    public Device1 selectDevice1ByDeviceCode(String deviceCode);

    /**
     * 查询设备状态
     *
     * @param
     * @return 设备状态
     */

    Map<DeviceStatus, Integer> getDeviceStatusCounts();

    /**
     * 查询设备列表
     *
     * @param device1 设备
     * @return 设备集合
     */
    public List<Device1> selectDevice1List(Device1 device1);

    /**
     * 新增设备
     *
     * @param device1 设备
     * @return 结果
     */
    public int insertDevice1(Device1 device1);

    /**
     * 修改设备
     *
     * @param device1 设备
     * @return 结果
     */
    public int updateDevice1(Device1 device1);

    /**
     * 批量删除设备
     *
     * @param deviceIds 需要删除的设备主键集合
     * @return 结果
     */
    public int deleteDevice1ByDeviceIds(String[] deviceIds);

    /**
     * 导入用户数据
     *
     * @param deviceList 设备数据
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public String importDevice(List<Device1> deviceList, Boolean isUpdateSupport, String operName);
    /**
     * 删除设备信息
     *
     * @param deviceId 设备主键
     * @return 结果
     */
    public int deleteDevice1ByDeviceId(String deviceId);

    void sendSimpleMail(String to, String subject, String content);

    void sendHtmlMail(String to, String subject, String content);

    void sendAttachmentsMail(String to, String subject, String content, String filePath);
}
