<script>

</script>

<template>
  <el-form
    v-show="showSearch"
    ref="queryForm"
    :inline="true"
    :model="queryParams"
    label-width="68px"
    size="small"
  >
    <el-form-item label="设备编码" prop="deviceCode">
      <el-input
        v-model="queryParams.deviceCode"
        clearable
        placeholder="请输入设备编码"
        @keyup.enter.native="handleQuery"
      />
    </el-form-item>
    <el-form-item label="设备名称" prop="deviceName">
      <el-input
        v-model="queryParams.deviceName"
        clearable
        placeholder="请输入设备名称"
        @blur="selectBlur($event)"
        @keyup.enter.native="handleQuery"
      />
    </el-form-item>
    <el-form-item label="所属产品" prop="productName">
      <el-select
        v-model="queryParams.productName"
        :placeholder="placeholderProduct"
        allow-create
        class="filter-item"
        clearable
        default-first-option
        filterable
        style="width: 200px"
        @blur="selectBlur($event)"
        @input="searchProducts"
        @keyup.enter.native="handleQuery"
      >
        <el-option
          v-for="product in productions"
          :key="product.id"
          :label="product.name"
          :value="product.name"
          class="suggestion-list"
          name="fade"
          @mousedown.prevent="selectProduct(product)"
        >
          {{ product.name }}
        </el-option>
      </el-select>
    </el-form-item>
    <el-form-item label="设备状态" prop="status">
      <el-select
        v-model="queryParams.status"
        clearable
        placeholder="设备状态"
        style="width: 120px"
        @keyup.enter.native="handleQuery"
      >
        <el-option
          v-for="(value, label) in deviceStatusMapping"
          :key="label"
          :label="value"
          :value="label"
        />
      </el-select>
    </el-form-item>
    <el-form-item label="负责人" prop="leader">
      <el-input
        v-model="queryParams.leader"
        clearable
        placeholder="请输入负责人"
        @keyup.enter.native="handleQuery"
      />
    </el-form-item>
    <el-form-item label="联系电话" prop="phone">
      <el-input
        v-model="queryParams.phone"
        clearable
        placeholder="请输入联系电话"
        @keyup.enter.native="handleQuery"
      />
    </el-form-item>
    <el-form-item label="邮箱" prop="email">
      <el-input
        v-model="queryParams.email"
        clearable
        placeholder="请输入邮箱"
        @keyup.enter.native="handleQuery"
      />
    </el-form-item>
    <el-form-item>
      <el-button icon="el-icon-search" size="mini" type="primary" @click="handleQuery">搜索</el-button>
      <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
    </el-form-item>
  </el-form>
</template>

<style scoped lang="scss">

</style>
