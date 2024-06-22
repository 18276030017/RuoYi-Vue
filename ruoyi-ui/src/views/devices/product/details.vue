<template>
  <div class="app-container">
    <div class="center-container">
      <el-row v-if="productList">
        <el-col :span="1">
          <div class="box-item">
            <el-button type="primary" style="color: #eff8ff;" @click="handleReturn">返回</el-button>
          </div>
        </el-col>
        <el-col :span="1.2">
          <div class="product-info">
            <span>{{ productList.productName }}</span>
          </div>
        </el-col>
        <el-col :span="1">
          <div class="product-info">
            <el-switch
              v-model="productList.status"
              active-value="0"
              inactive-value="1"
              class="switch"
              @change="handleStatusChange"
            />
          </div>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="6">
          <div class="device-info">
            <span class="span1">该产品下的设备数量：</span>
            <span class="span2" v-if="devicesCount">{{ devicesCount }}</span>
            <span class="span2" v-else>0</span>
          </div>
        </el-col>
      </el-row>
      <el-row class="horizontal-line">
        <el-col v-for="(item, index) in routerList" :key="index" :span="2">
          <div
            :class="`line-${item.type} ${selectedItem === item ? 'selected' : ''}`"
            @click="handleDetails(item)"
          >
            <span v-if="selectedItem === item">
              <i class="el-icon-hand hand-icon" />{{ item.label }}
            </span>
            <span v-else>{{ item.label }}</span>
          </div>
        </el-col>
      </el-row>
    </div>
    <div v-if="open" class="box-card">
      <el-card class="box-card-a">
        <div slot="header" class="clearfix">
          <span class="span1">配置信息</span>
          <el-button icon="el-icon-edit" type="text" class="el-icon" @click="handleUpdate" />
        </div>
        <div class="box-card-b">
          <table>
            <tr>
              <!-- 为产品名称列添加边框样式 -->
              <th colspan="2" class="th">产品ID</th>
              <td colspan="2" class="td">{{ productList.productId }}</td>
              <th colspan="2" class="th">产品类别</th>
              <td v-if="!editMode" colspan="2" class="td">{{ productList.productSort }}</td>
              <td v-else colspan="2" class="td"><input v-model="productList.productSort " type="text"></td>
              <th colspan="2" class="th">产品名称</th>
              <td v-if="!editMode" colspan="2" class="td">{{ productList.productName }}</td>
              <td v-else colspan="2" class="td"><input v-model="productList.productName " type="text"></td>
            </tr>
            <tr>
              <th colspan="2" class="th">设备类型</th>
              <td v-if="!editMode" colspan="2" class="td">{{ productList.productType }}</td>
              <td v-else colspan="2" class="td"><input v-model="productList.productType " type="text"></td>
              <th colspan="2" class="th">接入方式</th>
              <td v-if="!editMode" colspan="2" class="td">{{ productList.productAccess }}</td>
              <td v-else colspan="2" class="td"><input v-model="productList.productAccess " type="text"></td>
              <th colspan="2" class="th">产品描述</th>
              <td v-if="!editMode" colspan="2" class="td">{{ productList.descripe }}</td>
              <td v-else colspan="2" class="td"><input v-model="productList.descripe " type="text"></td>
            </tr>
            <tr>
              <th colspan="2" class="th">创建时间</th>
              <td colspan="2" class="td">{{ productList.createTime }}</td>
              <th colspan="2" class="th">修改者</th>
              <td colspan="2" class="td">{{ productList.updateBy }}</td>
              <th colspan="2" class="th">修改时间</th>
              <td colspan="2" class="td">{{ productList.updateTime }}</td>
            </tr>
            <tr class="describe">
              <th colspan="2" class="th">说明</th>
              <td v-if="!editMode" colspan="10" class="td">{{ productList.descripe }}</td>
              <td v-else colspan="10" class="td"><input v-model="productList.descripe" style="width: 100%" type="text"></td>
            </tr>
            <tr class="btn">
              <el-button v-if="editMode" type="primary" @click="handleSave">保存</el-button>
              <el-button v-if="editMode" type="primary" @click="handleCancel">取消</el-button>
            </tr>
          </table>
        </div>
      </el-card>
    </div>
    <div v-if="open1" class="box-card">
      <el-card class="box-card-a">
        <div slot="header" class="clearfix">
          <el-col v-for="(option, index) in buttons" :key="index" :span="0.5">
            <div
              :class="`line-${option.type} ${selectedBtn === option ? 'selected' : ''}`"
              @click="handleButtons(option)"
            >
              <span v-if="selectedBtn === option">
                <el-tabs class="el-icon-hand hand-icon" />{{ option.label }}
              </span>
              <span v-else class="option-icon">
                <el-tabs />{{ option.label }}
              </span>
            </div>
          </el-col>
          <span style="font-size: 12px; float: left; margin-left: 50px; margin-top: 20px; color: red;">设备会默认继承产品的物模型，继承的物模型不支持删改</span>
          <el-button type="primary" style="float: right; margin-right: 8px" @click="handleImport">快速导入</el-button>
          <el-button type="primary" style="float: right; margin-right: 8px" @click="handleModelTSL">物模型TSL</el-button>
        </div>
        <div v-if="Attribute" class="box-card-b">
          <el-input
            v-model="AttributeName"
            type="text"
            placeholder="请输入属性名称"
            style="width: 200px; height: 32px; line-height: 32px;"
          />
          <el-button
            type="submit"
            icon="el-icon-search"
            tyle="height: 60px; line-height: 32px; margin-top: 0; margin-bottom: 0;"
            @click="handleAttributeQuery"
          />
          <el-button
            v-hasPermi="['devices:product:attribute:save']"
            type="primary"
            style="float: right;margin-right: 8px"
            plain
            @click="handleAttributeSave"
          >保存属性</el-button>
          <el-button
            v-hasPermi="['devices:product:attribute:add']"
            type="primary"
            style="float: right;margin-right: 8px"
            plain
            @click="handleAttributeAdd"
          >新增属性</el-button>
          <el-table :data="AttributeData" style="width: 100%">
            <el-table-column prop="id" label="序号" />
            <el-table-column prop="identification" label="标识符" />
            <el-table-column prop="name" label="属性名称" />
            <el-table-column prop="type" label="数据类型" />
            <el-table-column prop="source" label="属性来源" />
            <el-table-column prop="unit" label="单位" />
            <el-table-column prop="descripe" label="说明" />
            <el-table-column prop="operation" label="操作">
              <template slot-scope="scope">
                <el-button type="text" @click="handleEdit(scope.row)">编辑</el-button>
                <el-button type="text" @click="handleDelete(scope.row)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
        <div v-if="power" class="box-card-b">
          <el-input
            v-model="powerName"
            type="text"
            placeholder="请输入功能名称"
            style="width: 200px; height: 32px; line-height: 32px;"
          />
          <el-button
            type="submit"
            icon="el-icon-search"
            tyle="height: 60px; line-height: 32px; margin-top: 0; margin-bottom: 0;"
            @click="handlePowerQuery"
          />
          <el-button
            v-hasPermi="['devices:product:power:save']"
            type="primary"
            style="float: right;margin-right: 8px"
            plain
            @click="handlePowerSave"
          >保存功能</el-button>
          <el-button
            v-hasPermi="['devices:product:power:add']"
            type="primary"
            style="float: right;margin-right: 8px"
            plain
            @click="handlePowerAdd"
          >新增功能</el-button>
          <el-table :data="PowerData" style="width: 100%">
            <el-table-column prop="id" label="序号" />
            <el-table-column prop="identification" label="标识符" />
            <el-table-column prop="name" label="功能名称" />
            <el-table-column prop="type" label="是否异步" />
            <el-table-column prop="source" label="输入参数" />
            <el-table-column prop="unit" label="输出参数" />
            <el-table-column prop="descripe" label="说明" />
            <el-table-column prop="operation" label="操作">
              <template slot-scope="scope">
                <el-button type="text" @click="handleEdit(scope.row)">编辑</el-button>
                <el-button type="text" @click="handleDelete(scope.row)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
        <div v-if="event" class="box-card-b">
          <el-input
            v-model="eventName"
            type="text"
            placeholder="请输入事件名称"
            style="width: 200px; height: 32px; line-height: 32px;"
          />
          <el-button
            type="submit"
            icon="el-icon-search"
            tyle="height: 60px; line-height: 32px; margin-top: 0; margin-bottom: 0;"
            @click="handleEventQuery"
          />
          <el-button
            v-hasPermi="['devices:product:event:save']"
            type="primary"
            style="float: right;margin-right: 8px"
            plain
            @click="handleEventSave"
          >保存事件</el-button>
          <el-button
            v-hasPermi="['devices:product:event:add']"
            type="primary"
            style="float: right;margin-right: 8px"
            plain
            @click="handleEventAdd"
          >新增事件</el-button>
          <el-table :data="EventData" style="width: 100%">
            <el-table-column prop="id" label="序号" />
            <el-table-column prop="identification" label="标识符" />
            <el-table-column prop="name" label="事件名称" />
            <el-table-column prop="type" label="事件级别" />
            <el-table-column prop="source" label="输出参数" />
            <el-table-column prop="unit" label="配置参数" />
            <el-table-column prop="descripe" label="说明" />
            <el-table-column prop="operation" label="操作">
              <template slot-scope="scope">
                <el-button type="text" @click="handleEdit(scope.row)">编辑</el-button>
                <el-button type="text" @click="handleDelete(scope.row)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
        <div v-if="tabs" class="box-card-b">
          <el-input
            v-model="tabsName"
            type="text"
            placeholder="请输入标签名称"
            style="width: 200px; height: 32px; line-height: 32px;"
          />
          <el-button
            type="submit"
            icon="el-icon-search"
            tyle="height: 60px; line-height: 32px; margin-top: 0; margin-bottom: 0;"
            @click="handleTabsQuery"
          />
          <el-button
            v-hasPermi="['devices:product:tabs:add']"
            type="primary"
            style="float: right;margin-right: 8px"
            plain
            @click="handleTabsAdd"
          >新增标签</el-button>
          <el-table v-if="tabs" :data="TabsData" style="width: 100%">
            <el-table-column prop="id" label="序号">
            </el-table-column>
            <el-table-column prop="identification" label="标识符">
            </el-table-column>
            <el-table-column prop="name" label="标签名称">
            </el-table-column>
            <el-table-column prop="type" label="数据类型">
            </el-table-column>
            <el-table-column prop="source" label="读写类型">
            </el-table-column>
            <el-table-column prop="descripe" label="说明">
            </el-table-column>
            <el-table-column prop="operation" label="操作">
              <template slot-scope="scope">
                <el-button type="text" @click="handleEdit(scope.row)">编辑</el-button>
                <el-button type="text" @click="handleDelete(scope.row)">删除</el-button></template>
            </el-table-column>
          </el-table>
        </div>
      </el-card>
    </div>
    <div v-if="open2" class="box-card">
      <el-card class="box-card-a">
        <div slot="header" class="clearfix">
          <span class="span1">设备接入</span>
        </div>
      </el-card>
    </div>
  </div>
</template>

<script>
import { changeProductStatus, getProduct, updateProduct } from '@/api/devices/product/product'
import {
  TabsList,
  getTabsData,
  saveTabsData,
  deleteTabsData,
  getDevicesCount
} from '@/api/devices/product/details'

export default {
  data() {
    return {
      open: true,
      open1: false,
      open2: false,
      editMode: false,
      Attribute: true,
      power: false,
      event: false,
      tabs: false,
      title: '',
      AttributeData: [],
      PowerData: [],
      EventData: [],
      TabsData: [],
      newRowIndex: null,
      form: {
        productId: null,
        productSort: null,
        productName: null,
        orderNum: null,
        descripe: null,
        status: null,
        delFlag: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null
      },
      selectedItem: null,
      routerList: [
        { label: '配置信息', type: 'config', value: 'configValue' },
        { label: '物模型', type: 'model', value: 'modelValue' },
        { label: '设备接入', type: 'device', value: 'deviceValue' }
      ],
      selectedBtn: null,
      buttons: [
        { label: '属性定义', type: 'Attribute', value: 'AttributeValue' },
        { label: '功能定义', type: 'power', value: 'powerValue' },
        { label: '事件定义', type: 'event', value: 'eventValue' },
        { label: '标签定义', type: 'tabs', value: 'tabsValue' }
      ],
      AttributeName: '',
      powerName: '',
      eventName: '',
      tabsName: '',
      productList: {},
      devicesCount: [],
      productStatusMapping: [
        { value: 0, label: '正常' },
        { value: 1, label: '停用' }
      ]
    }
  },
  created() {
    this.selectedItem = this.routerList[0]
    const productId = this.$route.params && this.$route.params.productId
    if (productId) {
      getProduct(productId).then(response => {
        this.productList = response.data
        getDevicesCount(this.productList.productName).then(response => {
          this.devicesCount = response.data
        }).catch(error => {
          this.$message.error('获取设备数量失败！', error)
        })
      }).catch(error => {
        this.$message.error('获取产品信息失败！', error)
      })
    }
  },
  methods: {
    handleUpdate() {
      this.editMode = true
    },
    handleSave() {
      // 在这里保存数据到数据库
      // 保存成功后，将数据更新到 productList，并关闭编辑模式
      // 假设有一个名为 saveProduct 的 API
      updateProduct(this.productList).then(() => {
        // 如果保存成功，更新 productList
        this.$modal.msgSuccess('保存成功')
        getProduct(this.productList.productId).then(response => {
          this.productList = response.data
        })
        // 注意：实际应用中，你应该从服务器获取最新的数据
        this.editMode = false
      }).catch((error) => {
        console.error('保存失败:', error)
      })
    },
    handleCancel() {
      // 取消编辑，恢复到未编辑状态
      this.editMode = false
    },
    handleDetails(item) {
      this.selectedItem = item
      this.$emit('openPanel', { type: item.type, value: item.value })
      if (item.type === 'config') {
        this.open = true
        this.open1 = false
        this.open2 = false
      }
      if (item.type === 'device') {
        this.open = false
        this.open1 = false
        this.open2 = true
      }
      if (item.type === 'model') {
        this.open = false
        this.open1 = true
        this.open2 = false
      }
    },
    handleButtons(item) {
      this.selectedBtn = item
      this.$emit('openPanel', { type: item.type, value: item.value })
      if (item.type === 'Attribute') {
        this.Attribute = true
        this.power = false
        this.event = false
        this.tabs = false
      }
      if (item.type === 'power') {
        this.Attribute = false
        this.power = true
        this.event = false
        this.tabs = false
      }
      if (item.type === 'event') {
        this.Attribute = false
        this.power = false
        this.event = true
        this.tabs = false
      }
      if (item.type === 'tabs') {
        this.Attribute = false
        this.power = false
        this.event = false
        this.tabs = true
        TabsList().then(response => {
          this.TabsData = response.data
        })
      }
    },
    handleReturn() {
      this.$router.push({ name: 'Product' })
    },
    // 用户状态修改
    handleStatusChange() {
      console.log(this.productList.status)
      const text = this.productList.status === '0' ? '启用' : '停用'
      this.$modal.confirm(`确认要${text} "${this.productList.productName}" 产品吗？`).then(() => { // 使用箭头函数
        return changeProductStatus(this.productList.productId, this.productList.status)
      }).then(() => {
        this.$modal.msgSuccess(`${text}成功`)
      }).catch(() => { // 使用箭头函数
        this.productList.status = this.productList.status === 0 ? 1 : 0
      })
    },
    handleAttributeQuery() {
      this.$message('查询成功' + this.AttributeName)
    },
    handleAttributeAdd() {
      this.$message('添加成功' + this.AttributeName)
    },
    handlePowerQuery() {
      this.$message('查询成功' + this.powerName)
    },
    handlePowerAdd() {
      this.$message('添加成功' + this.powerName)
    },
    handleEventQuery() {
      this.$message('查询成功' + this.eventName)
    },
    handleEventAdd() {
      this.$message('添加成功')
    },
    handleTabsQuery() {
      getTabsData(this.tabsName).then(response => {
        this.TabsData = response.data
      }).catch(error => {
        this.$message.error('获取标签列表失败！', error)
      })
    },
    handleTabsAdd() {
    },
    handleEdit(row) {
      row.isEditing = true
    },
    handleTabsRowSave(row) {
      // 这里应该有保存到服务器的逻辑
      // 如果成功，设置 isEditing 为 false
      row.isEditing = false
      // 保存到数据库
      saveTabsData(row).then(response => {
        this.$message.success('保存成功')
      }).catch(error => {
        this.$message.error('保存失败', error)
      })
      // 如果失败，显示错误信息
    },
    handleDelete(row) {
      // 删除逻辑
      this.$confirm('此操作将永久删除"' + row.name + '"该数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteTabsData(row.id).then(response => {
          this.$message.success('删除成功')
        }).catch(error => {
          this.$message.error('删除失败', error)
        })
        this.TabsData.splice(this.TabsData.indexOf(row), 1)
      }).catch(() => {
        this.$message({
          type: 'error',
          message: '已取消删除'
        })
      })
    }
  }
}
</script>

<style scoped lang="scss">
.app-container {
  background-color: #fafafc;
}
.box-card{
  margin-top: 20px;
  margin-left: 10px;
  position: relative;
  height: 80%;
}
.box-item {
  display: inline-block;
}
.product-info {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 8px;
  font-weight: 600;
  color: #606266;
  span {
    margin-left: 30px;
  }
  .switch {
    margin-left: 30px;
  }
}
.device-info {
  position: absolute;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 30px;
  font-weight: 600;
  color: #606266;
}
.span2 {
  margin-left: 10px;
  color: #1890ff;
  font-weight: 600;
}
.horizontal-line {
  display: flex; /* 或者使用 clear: both; */
  margin-top: 80px;
}
.line-b {
  margin-left: 20px;
}
.line-c {
  margin-left: 20px;
}
#details {
  .app-container {
    margin-top: 20px;
    background-color: #1890ff;
  }
}
.selected {
  color: blue;
}
.horizontal-line {
  cursor: pointer;
}
.selected .hand-icon {
  cursor: pointer;
  margin-left: 4px;
  font-size: 16px;
}
.el-icon {
  margin-left: 10px;
}
.span1 {
  font-size: 16px;
  font-weight: 600;
  color: #606266;
}
.th {
  height: 40px;
  width: 250px;
  border: 0.5px solid #e6e6e6;
  background-color: #ebf3f5;
  text-align: left;
  padding-left: 10px;
}
.td {
  height: 40px;
  width: 250px;
  border: 0.5px solid #e6e6e6;
  text-align: left;
}
.td1 {
  border: 0.5px solid #e6e6e6;
}
.btn {
  position: relative;
  text-align: center;
  top: 20px;
  left: 500px;
}
.box-card-b {
  position: relative;
  width: auto;
  height: 100%;
}
.option-icon {
  display: inline-block; /* 确保元素按行内元素显示 */
  white-space: nowrap; /* 防止换行 */
  padding-left: 20px; /* 根据需要调整间隔大小 */
}
</style>
