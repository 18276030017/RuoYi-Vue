package com.ruoyi.devices.service;

import java.util.List;

import com.ruoyi.devices.domain.product.*;

/**
 * 产品Service接口
 *
 * @author ruoyi
 * @date 2024-04-01
 */
public interface IProductService
{
    /**
     * 查询产品
     *
     * @param productId 产品主键
     * @return 产品
     */
    public Product selectProductByProductId(Long productId);

    /**
     * 查询产品列表
     *
     * @param product 产品
     * @return 产品集合
     */
    public List<Product> selectProductList(Product product);

    /**
     * 新增产品
     *
     * @param product 产品
     * @return 结果
     */
    public int insertProduct(Product product);

    /**
     * 修改产品
     *
     * @param product 产品
     * @return 结果
     */
    public int updateProduct(Product product);

    /**
     * 批量删除产品
     *
     * @param productIds 需要删除的产品主键集合
     * @return 结果
     */
    public int deleteProductByProductIds(Long[] productIds);

    /**
     * 删除产品信息
     *
     * @param productId 产品主键
     * @return 结果
     */
    public int deleteProductByProductId(Long productId);

    /**
     * 查询当前产品下的设备数量
     */
    public int selectDeviceCountByProductName(String productName);


    /**
     * 查询产品标签列表
     *
     * @param tabs 产品标签
     * @return 产品集合集合
     */
    public List<ProductTabs> selecttabsList(ProductTabs tabs);

    /**
     * 查询产品标签
     *
     * @param tabsName 产品标签主键
     * @return 产品标签
     */
    public ProductTabs selecttabsByTabsName(String tabsName);

    /**
     * 新增产品标签
     * @param tabs
     * @return
     */
    public int inserttabs(ProductTabs tabs);

    /**
     * 修改产品标签
     * @param tabs
     * @return
     */
    public int updatetabs(ProductTabs tabs);

    /**
     * 批量删除产品标签
     * @param tabsIds
     * @return
     */
    public int deletetabsByTabsIds(String tabsIds);
    /**
     * 删除产品标签信息
     * @param tabsId
     * @return
     */
    public int deletetabsByTabsId(Long tabsId);


    /**
     * 查询产品属性列表
     * @param attribute 产品属性
     * @return 产品集合
     */
    public List<ProductAttribute> selectAttributeList(ProductAttribute attribute);

    /**
     * 查询产品属性
     * @param attributeName 产品属性主键
     * @return 产品属性
     */
    public ProductAttribute selectAttributeByAttributeName(String attributeName);

    /**
     * 新增产品属性
     * @param attribute
     * @return
     */
    public int insertAttribute(ProductAttribute attribute);

    /**
     * 修改产品属性
     * @param attribute
     * @return
     */
    public int updateAttribute(ProductAttribute attribute);

    /**
     * 删除产品属性信息
     * @param attributeId
     * @return
     */
    public int deleteAttributeByAttributeId(Long attributeId);

    /**
     * 批量删除产品属性
     * @param attributeIds
     * @return
     */
    public int deleteAttributeByAttributeIds(String attributeIds);


    /**
     * 查询产品事件列表
     * @param event 产品事件
     * @return 产品集合
     */
    public List<ProductEvent> selectEventList(ProductEvent event);

    /**
     * 查询产品事件
     * @param eventName 产品事件主键
     * @return 产品事件
     */
    public ProductEvent selectEventByEventName(String eventName);

    /**
     * 新增产品事件
     * @param event
     * @return
     */
    public int insertEvent(ProductEvent event);

    /**
     * 修改产品事件
     * @param event
     * @return
     */
    public int updateEvent(ProductEvent event);

    /**
     * 删除产品事件信息
     * @param eventId
     * @return
     */
    public int deleteEventByEventId(Long eventId);

    /**
     * 批量删除产品事件
     * @param eventIds
     * @return
     */
    public int deleteEventByEventIds(String eventIds);


    /**
     * 查询产品功能列表
     * @param power 产品功能
     * @return 产品集合
     */
    public List<ProductPower> selectPowerList(ProductPower power);

    /**
     * 查询产品功能
     * @param powerName 产品功能主键
     * @return 产品功能
     */
    public ProductPower selectPowerByPowerName(String powerName);

    /**
     * 新增产品功能
     * @param power
     * @return
     */
    public int insertPower(ProductPower power);

    /**
     * 修改产品功能
     * @param power
     * @return
     */
    public int updatePower(ProductPower power);

    /**
     * 删除产品功能信息
     * @param powerId
     * @return
     */
    public int deletePowerByPowerId(Long powerId);

    /**
     * 批量删除产品功能
     * @param powerIds
     * @return
     */
    public int deletePowerByPowerIds(String powerIds);
}
