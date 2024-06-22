import request from '@/utils/request'

// 查询告警记录列表
export function listRecord(query) {
  return request({
    url: '/alarm/record/list',
    method: 'get',
    params: query
  })
}

// 查询告警记录详细
export function getRecord(id) {
  return request({
    url: '/alarm/record/' + id,
    method: 'get'
  })
}

// 新增告警记录
export function addRecord(data) {
  return request({
    url: '/alarm/record',
    method: 'post',
    data: data
  })
}

// 修改告警记录
export function updateRecord(data) {
  return request({
    url: '/alarm/record',
    method: 'put',
    data: data
  })
}

// 删除告警记录
export function delRecord(id) {
  return request({
    url: '/alarm/record/' + id,
    method: 'delete'
  })
}
