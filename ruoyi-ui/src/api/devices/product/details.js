import request from "@/utils/request";

export function getDevicesCount(productName)
{
    return request({
        url: '/devices/product/count/'+productName,
        method: 'get',
        params: productName
    })
}

// 操作tabs
export function TabsList(query)
{
    return request({
        url: '/devices/product/tabs/list',
        method: 'get',
        params: query
    })
}

export function getTabsData(tabName)
{
    return request({
        url: '/devices/product/tabs/'+tabName,
        method: 'get',
        params: tabName
    })
}

export function saveTabsData(data)
{
    return request({
        url: '/devices/product/tabs/save',
        method: 'post',
        data: data
    })
}

export function deleteTabsData(tabsId)
{
    return request({
        url: '/devices/product/tabs/delete'+tabsId,
        method: 'post',
        data: tabsId
    })
}
