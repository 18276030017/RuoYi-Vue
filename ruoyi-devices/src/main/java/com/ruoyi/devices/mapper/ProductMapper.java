package com.ruoyi.devices.mapper;

import java.util.List;

import com.ruoyi.devices.domain.product.*;

/**
 * 产品Mapper接口
 *
 * @author ruoyi
 * @date 2024-04-01
 */
public interface ProductMapper
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
     * 删除产品
     *
     * @param productId 产品主键
     * @return 结果
     */
    public int deleteProductByProductId(Long productId);

    /**
     * 批量删除产品
     *
     * @param productIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteProductByProductIds(Long[] productIds);

    public int selectDeviceCountByProductName(String productName);

    /**
     * 产品标签
     */
    public List<ProductTabs> selecttabsList(ProductTabs productTabs);
    public ProductTabs selectTabsByTabsName(String tabsName);
    public int insertTabs(ProductTabs tabs);
    public int updateTabs(ProductTabs tabs);
    public int deleteTabsByTabsId(Long tabsId);
    public int deleteTabsByTabsIds(String tabsIds);

    /**
     * 产品属性
     */
    public List<ProductAttribute> selectAttributeList(ProductAttribute attribute);
    public ProductAttribute selectAttributeByAttributeName(String attributeName);
    public int insertAttribute(ProductAttribute attribute);
    public int updateAttribute(ProductAttribute attribute);
    public int deleteAttributeByAttributeIds(String attributeIds);
    public int deleteAttributeByAttributeId(Long attributeId);

    /**
     * 产品事件
     */
    public List<ProductEvent> selectEventList(ProductEvent event);
    public ProductEvent selectEventByEventName(String eventName);
    public int insertEvent(ProductEvent event);
    public int updateEvent(ProductEvent event);
    public int deleteEventByEventIds(String eventIds);
    public int deleteEventByEventId(Long eventId);

    /**
     * 产品功能
     */
    public List<ProductPower> selectPowerList(ProductPower power);
    public ProductPower selectPowerByPowerName(String powerName);
    public int insertPower(ProductPower power);
    public int updatePower(ProductPower power);
    public int deletePowerByPowerIds(String powerIds);
    public int deletePowerByPowerId(Long powerId);
}
