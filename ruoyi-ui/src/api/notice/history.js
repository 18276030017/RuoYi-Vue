import request from '@/utils/request'

// 查询通知记录列表
export function listHistory(query) {
  return request({
    url: '/notice/history/list',
    method: 'get',
    params: query
  })
}

// 查询通知记录详细
export function getHistory(id) {
  return request({
    url: '/notice/history/' + id,
    method: 'get'
  })
}

// 新增通知记录
export function addHistory(data) {
  return request({
    url: '/notice/history',
    method: 'post',
    data: data
  })
}

// 修改通知记录
export function updateHistory(data) {
  return request({
    url: '/notice/history',
    method: 'put',
    data: data
  })
}

// 删除通知记录
export function delHistory(id) {
  return request({
    url: '/notice/history/' + id,
    method: 'delete'
  })
}
