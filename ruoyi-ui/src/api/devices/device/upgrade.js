import request from '@/utils/request'

// 查询设备升级列表
export function listUpgrade(query) {
  return request({
    url: '/devices/upgrade/list',
    method: 'get',
    params: query
  })
}

// 查询设备升级详细
export function getUpgrade(upgradeId) {
  return request({
    url: '/devices/upgrade/' + upgradeId,
    method: 'get'
  })
}

// 新增设备升级
export function addUpgrade(data) {
  return request({
    url: '/devices/upgrade/add',
    method: 'post',
    data: data
  })
}

// 修改设备升级
export function updateUpgrade(data) {
  return request({
    url: '/devices/upgrade/edit',
    method: 'put',
    data: data
  })
}

// 删除设备升级
export function delUpgrade(upgradeId) {
  return request({
    url: '/devices/upgrade/' + upgradeId,
    method: 'delete'
  })
}

// 设备升级信息推送
export function sendUpgrade(data) {
  return request({
    url: '/devices/upgrade/send',
    method: 'post',
    data: data
  })
}
