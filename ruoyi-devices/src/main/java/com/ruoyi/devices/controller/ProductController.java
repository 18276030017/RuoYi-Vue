package com.ruoyi.devices.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.devices.domain.product.*;
import com.ruoyi.system.service.ISysUserService;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import com.ruoyi.framework.web.service.TokenService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import org.slf4j.Logger;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.devices.service.IProductService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 产品Controller
 *
 * @author ruoyi
 * @date 2024-04-01
 */
@RestController
@RequestMapping("/devices/product")
public class ProductController extends BaseController
{
    private static final Logger log = LoggerFactory.getLogger(ProductController.class);


    @Autowired
    private IProductService productService;
    @Autowired
    private ISysUserService userService;
    @Autowired
    private TokenService tokenService;

    /**
     * 查询产品列表
     */
    @PreAuthorize("@ss.hasPermi('devices:product:list')")
    @GetMapping("/list")
    public TableDataInfo list(Product product)
    {
        startPage();
        List<Product> list = productService.selectProductList(product);
        return getDataTable(list);
    }

    /**
     * 导出产品列表
     */
    @PreAuthorize("@ss.hasPermi('devices:product:export')")
    @Log(title = "产品", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Product product)
    {
        List<Product> list = productService.selectProductList(product);
        ExcelUtil<Product> util = new ExcelUtil<Product>(Product.class);
        util.exportExcel(response, list, "产品数据");
    }

    /**
     * 获取产品详细信息
     */
    @PreAuthorize("@ss.hasPermi('devices:product:query')")
    @GetMapping(value = "/{productId}")
    public AjaxResult getInfo(@PathVariable("productId") Long productId)
    {
        return success(productService.selectProductByProductId(productId));
    }

    /**
     * 新增产品
     */
    @PreAuthorize("@ss.hasPermi('devices:product:add')")
    @Log(title = "产品", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Product product)
    {
        return toAjax(productService.insertProduct(product));
    }

    /**
     * 修改产品
     */
    @PreAuthorize("@ss.hasPermi('devices:product:edit')")
    @Log(title = "产品", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Product product)
    {
        product.setUpdateBy(getUsername());
        return toAjax(productService.updateProduct(product));
    }

    /**
     * 删除产品
     */
    @PreAuthorize("@ss.hasPermi('devices:product:remove')")
    @Log(title = "产品", businessType = BusinessType.DELETE)
	@DeleteMapping("/{productIds}")
    public AjaxResult remove(@PathVariable Long[] productIds)
    {
        return toAjax(productService.deleteProductByProductIds(productIds));
    }

    @PreAuthorize("@ss.hasPermi('devices:product:edit')")
    @Log(title = "产品", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody Product product,HttpServletRequest request) {
        // 验证用户权限和数据范围
        LoginUser loginUser = tokenService.getLoginUser(request);
        SysUser user = loginUser.getUser();
        userService.checkUserDataScope(user.getUserId());

        // 更新产品信息
        try {
            return toAjax(productService.updateProduct(product));
        } catch (Exception e) {
            // 记录异常日志
            log.error("Error occurred in " + ProductController.class.getSimpleName() + ", Error Message: " + e.getMessage(), e);
            return AjaxResult.error("更新产品状态失败");
        }
    }

    @Log(title = "产品", businessType = BusinessType.OTHER)
    @GetMapping("/count/{productName}")
    public AjaxResult count(@PathVariable("productName") String productName) {
            return success(productService.selectDeviceCountByProductName(productName));
    }

    @GetMapping("/tabs/list")
    public TableDataInfo tabsList(ProductTabs productTabs) {
        return getDataTable(productService.selecttabsList(productTabs));
    }

    @GetMapping("/tabs/{tabsName}")
    public AjaxResult tabsInfo(@PathVariable("tabsName") String tabsName) {
        return success(productService.selecttabsByTabsName(tabsName));
    }

    @PostMapping("/tabs")
    public AjaxResult tabsAdd(@RequestBody ProductTabs productTabs) {
        return toAjax(productService.inserttabs(productTabs));
    }

    @PutMapping("/tabs")
    public AjaxResult tabsEdit(@RequestBody ProductTabs productTabs) {
        return toAjax(productService.updatetabs(productTabs));
    }

    @DeleteMapping("/tabs/{tabsIds}")
    public AjaxResult tabsDelete(@PathVariable("tabsIds") String tabsIds) {
        return toAjax(productService.deletetabsByTabsIds(tabsIds));
    }

    @GetMapping("/attribute")
    public TableDataInfo attributeList(ProductAttribute productAttribute) {
        return getDataTable(productService.selectAttributeList(productAttribute));
    }

    @GetMapping("/attribute/{attributeName}")
    public AjaxResult attributeInfo(@PathVariable("attributeName") String attributeName) {
        return success(productService.selectAttributeByAttributeName(attributeName));
    }

    @PostMapping("/attribute")
    public AjaxResult attributeAdd(@RequestBody ProductAttribute productAttribute) {
        return toAjax(productService.insertAttribute(productAttribute));
    }

    @PutMapping("/attribute")
    public AjaxResult attributeEdit(@RequestBody ProductAttribute productAttribute) {
        return toAjax(productService.updateAttribute(productAttribute));
    }

    @DeleteMapping("/attribute/{attributeIds}")
    public AjaxResult attributeDelete(@PathVariable("attributeIds") String attributeIds) {
        return toAjax(productService.deleteAttributeByAttributeIds(attributeIds));
    }

    @GetMapping("/power")
    public TableDataInfo powerList(ProductPower productPower) {
        return getDataTable(productService.selectPowerList(productPower));
    }

    @GetMapping("/power/{powerName}")
    public AjaxResult powerInfo(@PathVariable("powerName") String powerName) {
        return success(productService.selectPowerByPowerName(powerName));
    }

    @PostMapping("/power")
    public AjaxResult powerAdd(@RequestBody ProductPower productPower) {
        return toAjax(productService.insertPower(productPower));
    }

    @PutMapping("/power")
    public AjaxResult powerEdit(@RequestBody ProductPower productPower) {
        return toAjax(productService.updatePower(productPower));
    }

    @DeleteMapping("/power/{powerIds}")
    public AjaxResult powerDelete(@PathVariable("powerIds") String powerIds) {
        return toAjax(productService.deletePowerByPowerIds(powerIds));
    }

    @GetMapping("/event")
    public TableDataInfo eventList(ProductEvent productEvent) {
        return getDataTable(productService.selectEventList(productEvent));
    }

    @GetMapping("/event/{eventName}")
    public AjaxResult eventInfo(@PathVariable("eventName") String eventName) {
        return success(productService.selectEventByEventName(eventName));
    }

    @PostMapping("/event")
    public AjaxResult eventAdd(@RequestBody ProductEvent productEvent) {
        return toAjax(productService.insertEvent(productEvent));
    }

    @PutMapping("/event")
    public AjaxResult eventEdit(@RequestBody ProductEvent productEvent) {
        return toAjax(productService.updateEvent(productEvent));
    }

    @DeleteMapping("/event/{eventIds}")
    public AjaxResult eventDelete(@PathVariable("eventIds") String eventIds) {
        return toAjax(productService.deleteEventByEventIds(eventIds));
    }
}
