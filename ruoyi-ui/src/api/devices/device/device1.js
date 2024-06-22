import request from '@/utils/request'

// 查询设备列表
export function listDevice1(query) {
  return request({
    url: '/devices/device1/list',
    method: 'get',
    params: query
  })
}

// 查询设备详细
export function getDevice1(deviceCode) {
  return request({
    url: '/devices/device1/' + deviceCode,
    method: 'get'
  })
}

//指令下发
export function sendCommand(data) {
  return request({
    url: '/devices/device1/command',
    method: 'post',
    data: data
  })
}

// 新增设备
export function addDevice1(data) {
  return request({
    url: '/devices/device1',
    method: 'post',
    data: data
  })
}

// 修改设备
export function updateDevice1(data) {
  return request({
    url: '/devices/device1',
    method: 'put',
    data: data
  })
}

// 删除设备
export function delDevice1(deviceId) {
  return request({
    url: '/devices/device1/' + deviceId,
    method: 'delete'
  })
}

// 查询设备在线数量
export function queryDeviceCount(query) {
  return request({
    url: '/devices/device1/count',
    method: 'get',
    params: query
  })
}

export function queryDeviceMessage(deviceCode) {
  return request({
    url: '/devices/device1/message/' + deviceCode,
    method: 'get'
  })
}

/*  export function sendEmail() {
    return request({
      url: '/devices/device1/email',
      method: 'get'
    })
}*/
