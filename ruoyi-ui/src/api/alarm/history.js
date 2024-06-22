import request from '@/utils/request'

// 查询告警处理记录列表
export function listHistory(query) {
  return request({
    url: '/alarm/history/list',
    method: 'get',
    params: query
  })
}

// 查询告警处理记录详细
export function getHistory(id) {
  return request({
    url: '/alarm/history/' + id,
    method: 'get'
  })
}

// 新增告警处理记录
export function addHistory(data) {
  return request({
    url: '/alarm/history',
    method: 'post',
    data: data
  })
}

// 修改告警处理记录
export function updateHistory(data) {
  return request({
    url: '/alarm/history',
    method: 'put',
    data: data
  })
}

// 删除告警处理记录
export function delHistory(id) {
  return request({
    url: '/alarm/history/' + id,
    method: 'delete'
  })
}
