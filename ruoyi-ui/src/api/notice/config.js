import request from '@/utils/request'

// 查询消息通知配置列表
export function listConfig(query) {
  return request({
    url: '/notice/config/list',
    method: 'get',
    params: query
  })
}

// 查询消息通知配置详细
export function getConfig(id) {
  return request({
    url: '/notice/config/' + id,
    method: 'get'
  })
}

// 新增消息通知配置
export function addConfig(data) {
  return request({
    url: '/notice/config',
    method: 'post',
    data: data
  })
}

// 修改消息通知配置
export function updateConfig(data) {
  return request({
    url: '/notice/config',
    method: 'put',
    data: data
  })
}

// 删除消息通知配置
export function delConfig(id) {
  return request({
    url: '/notice/config/' + id,
    method: 'delete'
  })
}
