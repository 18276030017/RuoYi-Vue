import request from '@/utils/request'

// 查询告警级别列表
export function listLevel(query) {
  return request({
    url: '/alarm/level/list',
    method: 'get',
    params: query
  })
}

// 查询告警级别详细
export function getLevel(id) {
  return request({
    url: '/alarm/level/' + id,
    method: 'get'
  })
}

// 新增告警级别
export function addLevel(data) {
  return request({
    url: '/alarm/level',
    method: 'post',
    data: data
  })
}

// 修改告警级别
export function updateLevel(data) {
  return request({
    url: '/alarm/level',
    method: 'put',
    data: data
  })
}

// 删除告警级别
export function delLevel(id) {
  return request({
    url: '/alarm/level/' + id,
    method: 'delete'
  })
}
