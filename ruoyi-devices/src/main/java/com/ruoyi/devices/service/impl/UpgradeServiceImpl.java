package com.ruoyi.devices.service.impl;

import com.ruoyi.devices.domain.device.DeviceUpgrade;
import com.ruoyi.devices.mapper.UpgradeMapper;
import com.ruoyi.devices.service.UpgradeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lfx
 * @program ruoyi
 * @description 升级包具体实现类
 * @date 2024/04/29
 */
@Service
@Slf4j
public class UpgradeServiceImpl implements UpgradeService {
    @Autowired
    private UpgradeMapper upgradeMapper;

    /**
     * 查询设备升级
     *
     * @param upgradeId 设备升级主键
     * @return 设备升级
     */
    @Override
    public DeviceUpgrade selectDeviceUpgradeByUpgradeId(Long upgradeId)
    {
        return upgradeMapper.selectDeviceUpgradeByUpgradeId(upgradeId);
    }

    /**
     * 选择设备升级列表。
     *
     * @param deviceUpgrade 设备升级对象，包含用于查询的条件。
     * @return 返回一个设备升级列表。
     */
    @Override
    public List<DeviceUpgrade> selectUpgradeList(DeviceUpgrade deviceUpgrade) {
        return upgradeMapper.selectUpgradeList(deviceUpgrade);
    }

    /**
     * 插入一个新的设备升级记录。
     *
     * @param deviceUpgrade 需要插入的设备升级对象。
     * @return 返回插入的记录数。
     */
    @Override
    public int insertUpgrade(DeviceUpgrade deviceUpgrade) {
        return upgradeMapper.insertUpgrade(deviceUpgrade);
    }

    /**
     * 更新一个设备升级记录。
     *
     * @param deviceUpgrade 需要更新的设备升级对象。
     * @return 返回更新的记录数。
     */
    @Override
    public int updateUpgrade(DeviceUpgrade deviceUpgrade) {
        return upgradeMapper.updateUpgrade(deviceUpgrade);
    }

    /**
     * 根据升级ID删除一个设备升级记录。
     *
     * @param upgradeId 需要删除的设备升级记录的ID。
     * @return 返回删除的记录数。
     */
    @Override
    public int deleteUpgradeById(Long upgradeId) {
        return upgradeMapper.deleteUpgradeById(upgradeId);
    }

    @Override
    public int deleteUpgradeByUpgradeIds(Long[] upgradeIds) {
        return upgradeMapper.deleteUpgradeByUpgradeIds(upgradeIds);
    }

    @Override
    public String importDevice(List<DeviceUpgrade> deviceList, Boolean isUpdateSupport, String operName) {
        return "1111111";
    }

}
