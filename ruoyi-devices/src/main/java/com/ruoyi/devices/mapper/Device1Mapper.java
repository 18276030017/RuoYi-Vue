package com.ruoyi.devices.mapper;

import java.util.List;

import com.ruoyi.devices.domain.device.Device1;

/**
 * 设备Mapper接口
 *
 * @author ruoyi
 * @date 2024-04-01
 */
public interface Device1Mapper
{
    /**
     * 查询设备
     *
     * @param deviceCode 设备主键
     * @return 设备
     */
    public Device1 selectDevice1ByDeviceCode(String deviceCode);

    /**
     * 查询设备列表
     *
     * @param device1 设备
     * @return 设备集合
     */
    public List<Device1> selectDevice1List(Device1 device1);

    /**
     * 查询离线设备数量
     *
     * @param status 设备状态
     * @return 结果
     */
    int selectDeviceCountByStatus(int status);


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
     * 删除设备
     *
     * @param deviceId 设备主键
     * @return 结果
     */
    public int deleteDevice1ByDeviceId(String deviceId);

    /**
     * 批量删除设备
     *
     * @param deviceIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDevice1ByDeviceIds(String[] deviceIds);

    /**
     * 查询设备在线时间戳
     *
     * @param
     * @return 结果
     */
    public Device1 selectOnlineTimestamp();

    public int updateDevice1ByChannelId(Device1 device1);
}
