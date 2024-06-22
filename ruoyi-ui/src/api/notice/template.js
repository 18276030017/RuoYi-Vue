import request from '@/utils/request'

// 查询消息通知模板列表
export function listTemplate(query) {
  return request({
    url: '/notice/template/list',
    method: 'get',
    params: query
  })
}

// 查询消息通知模板详细
export function getTemplate(id) {
  return request({
    url: '/notice/template/' + id,
    method: 'get'
  })
}

// 新增消息通知模板
export function addTemplate(data) {
  return request({
    url: '/notice/template',
    method: 'post',
    data: data
  })
}

// 修改消息通知模板
export function updateTemplate(data) {
  return request({
    url: '/notice/template',
    method: 'put',
    data: data
  })
}

// 删除消息通知模板
export function delTemplate(id) {
  return request({
    url: '/notice/template/' + id,
    method: 'delete'
  })
}
