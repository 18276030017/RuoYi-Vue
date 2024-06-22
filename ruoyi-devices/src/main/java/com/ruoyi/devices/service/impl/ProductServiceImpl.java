package com.ruoyi.devices.service.impl;

import java.util.Collections;
import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.devices.domain.product.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.devices.mapper.ProductMapper;
import com.ruoyi.devices.service.IProductService;

/**
 * 产品Service业务层处理
 *
 * @author ruoyi
 * @date 2024-04-01
 */
@Service
public class ProductServiceImpl implements IProductService
{
    @Autowired
    private ProductMapper productMapper;

    /**
     * 查询产品
     *
     * @param productId 产品主键
     * @return 产品
     */
    @Override
    public Product selectProductByProductId(Long productId)
    {
        return productMapper.selectProductByProductId(productId);
    }

    /**
     * 查询产品列表
     *
     * @param product 产品
     * @return 产品
     */
    @Override
    public List<Product> selectProductList(Product product)
    {
        return productMapper.selectProductList(product);
    }

    /**
     * 新增产品
     *
     * @param product 产品
     * @return 结果
     */
    @Override
    public int insertProduct(Product product)
    {
        product.setCreateTime(DateUtils.getNowDate());
        return productMapper.insertProduct(product);
    }

    /**
     * 修改产品
     *
     * @param product 产品
     * @return 结果
     */
    @Override
    public int updateProduct(Product product)
    {
        product.setUpdateTime(DateUtils.getNowDate());
        return productMapper.updateProduct(product);
    }

    /**
     * 批量删除产品
     *
     * @param productIds 需要删除的产品主键
     * @return 结果
     */
    @Override
    public int deleteProductByProductIds(Long[] productIds)
    {
        return productMapper.deleteProductByProductIds(productIds);
    }

    /**
     * 删除产品信息
     *
     * @param productId 产品主键
     * @return 结果
     */
    @Override
    public int deleteProductByProductId(Long productId)
    {
        return productMapper.deleteProductByProductId(productId);
    }

    @Override
    public int selectDeviceCountByProductName(String productName) {
        return productMapper.selectDeviceCountByProductName(productName);
    }

    @Override
    public List<ProductTabs> selecttabsList(ProductTabs tabs) {
        return productMapper.selecttabsList(tabs);
    }

    @Override
    public ProductTabs selecttabsByTabsName(String tabsName) {
        return productMapper.selectTabsByTabsName(tabsName);
    }

    @Override
    public int inserttabs(ProductTabs tabs) {
        return productMapper.insertTabs(tabs);
    }

    @Override
    public int updatetabs(ProductTabs tabs) {
        return productMapper.updateTabs(tabs);
    }

    @Override
    public int deletetabsByTabsIds(String tabsIds) {
        return productMapper.deleteTabsByTabsIds(tabsIds);
    }

    @Override
    public int deletetabsByTabsId(Long tabsId) {
        return productMapper.deleteTabsByTabsId(tabsId);
    }

    @Override
    public List<ProductAttribute> selectAttributeList(ProductAttribute attribute) {
        return productMapper.selectAttributeList(attribute);
    }

    @Override
    public ProductAttribute selectAttributeByAttributeName(String attributeName) {
        return productMapper.selectAttributeByAttributeName(attributeName);
    }

    @Override
    public int insertAttribute(ProductAttribute attribute) {
        return productMapper.insertAttribute(attribute);
    }

    @Override
    public int updateAttribute(ProductAttribute attribute) {
        return productMapper.updateAttribute(attribute);
    }

    @Override
    public int deleteAttributeByAttributeId(Long attributeId) {
        return productMapper.deleteAttributeByAttributeId(attributeId);
    }

    @Override
    public int deleteAttributeByAttributeIds(String attributeIds) {
        return productMapper.deleteAttributeByAttributeIds(attributeIds);
    }

    @Override
    public List<ProductEvent> selectEventList(ProductEvent event) {
        return productMapper.selectEventList(event);
    }

    @Override
    public ProductEvent selectEventByEventName(String eventName) {
        return productMapper.selectEventByEventName(eventName);
    }

    @Override
    public int insertEvent(ProductEvent event) {
        return productMapper.insertEvent(event);
    }

    @Override
    public int updateEvent(ProductEvent event) {
        return productMapper.updateEvent(event);
    }

    @Override
    public int deleteEventByEventId(Long eventId) {
        return productMapper.deleteEventByEventId(eventId);
    }

    @Override
    public int deleteEventByEventIds(String eventIds) {
        return productMapper.deleteEventByEventIds(eventIds);
    }

    @Override
    public List<ProductPower> selectPowerList(ProductPower power) {
        return productMapper.selectPowerList(power);
    }

    @Override
    public ProductPower selectPowerByPowerName(String powerName) {
        return productMapper.selectPowerByPowerName(powerName);
    }

    @Override
    public int insertPower(ProductPower power) {
        return productMapper.insertPower(power);
    }

    @Override
    public int updatePower(ProductPower power) {
        return productMapper.updatePower(power);
    }

    @Override
    public int deletePowerByPowerId(Long powerId) {
        return productMapper.deletePowerByPowerId(powerId);
    }

    @Override
    public int deletePowerByPowerIds(String powerIds) {
        return productMapper.deletePowerByPowerIds(powerIds);
    }
}
