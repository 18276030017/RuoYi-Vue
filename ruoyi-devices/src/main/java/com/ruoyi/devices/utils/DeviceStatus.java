package com.ruoyi.devices.utils;

/**
 * @author lfx
 * @program ruoyi
 * @description 设备状态枚举类
 * @date 2024/04/08
 */
public enum DeviceStatus {
    /**
     * 设备状态：（0在线 1离线 2锁定 3故障 4报警 5维护 6试用 7未激活 8保养） 9所有状态
     */
    TOTAL(9),ONLINE(0), OFFLINE(1), LOCK(2), FAULT(3), ALARM(4), MAINTENANCE(5), TRY(6), UNACTIVATED(7), KEEP(8);

    private int value;

    DeviceStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
