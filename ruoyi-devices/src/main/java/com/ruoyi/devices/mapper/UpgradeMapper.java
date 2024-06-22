package com.ruoyi.devices.mapper;

import com.ruoyi.devices.domain.device.DeviceUpgrade;

import java.util.List;

/**
 * @author lfx
 * @program ruoyi
 * @description 设备升级mapper类
 * @date 2024/04/29
 */
public interface UpgradeMapper {
    /**
     * 查询设备升级
     *
     * @param upgradeId 设备升级主键
     * @return 设备升级
     */
    public DeviceUpgrade selectDeviceUpgradeByUpgradeId(Long upgradeId);
    /**
     * 选择设备升级列表。
     * @param deviceUpgrade 设备升级对象，包含用于查询的条件。
     * @return 返回设备升级信息的列表。
     */
    public List<DeviceUpgrade> selectUpgradeList(DeviceUpgrade deviceUpgrade);

    /**
     * 插入设备升级信息。
     * @param deviceUpgrade 需要插入的设备升级对象。
     * @return 返回插入操作影响的行数。
     */
    public int insertUpgrade(DeviceUpgrade deviceUpgrade);

    /**
     * 更新设备升级信息。
     * @param deviceUpgrade 需要更新的设备升级对象。
     * @return 返回更新操作影响的行数。
     */
    public int updateUpgrade(DeviceUpgrade deviceUpgrade);

    /**
     * 通过升级ID删除设备升级信息。
     * @param upgradeId 需要删除的设备升级的ID。
     * @return 返回删除操作影响的行数。
     */
    public int deleteUpgradeById(Long upgradeId);

    public int deleteUpgradeByUpgradeIds(Long[] upgradeIds);

}
