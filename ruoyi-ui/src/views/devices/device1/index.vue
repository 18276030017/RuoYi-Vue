<template>
  <div class="app-container">
    <el-form v-show="showSearch" ref="queryForm" :inline="true" :model="queryParams" label-width="68px" size="small">
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
      <el-form-item label="所属产品" prop="deviceProductName">
        <el-select
          v-model="queryParams.deviceProductName"
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
      <el-form-item label="设备状态" prop="deviceStatus">
        <el-select
          v-model="queryParams.deviceStatus"
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

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['devices:device1:add']"
          icon="el-icon-plus"
          plain
          size="mini"
          type="primary"
          @click="handleAdd"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['devices:device1:edit']"
          :disabled="single"
          icon="el-icon-edit"
          plain
          size="mini"
          type="success"
          @click="handleUpdate"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['devices:device1:remove']"
          :disabled="multiple"
          icon="el-icon-delete"
          plain
          size="mini"
          type="danger"
          @click="handleDelete"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['devices:device1:batchCommand']"
          icon="el-icon-s-operation"
          plain
          size="mini"
          type="primary"
          @click="sendBatchCommand"
        >
          批量下发指令
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['devices:device1:export']"
          icon="el-icon-download"
          plain
          size="mini"
          type="warning"
          @click="handleExport"
        >导出
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['devices:device1:import']"
          type="info"
          plain
          icon="el-icon-upload2"
          size="mini"
          @click="handleImport"
        >导入</el-button>
      </el-col>
      <i class="el-icon-warning" style="color: red; font-size: 12px; font-weight: 500;">执行下发指令功能之后需要等待五秒之后才能去操作其他的功能</i>
      <right-toolbar :show-search.sync="showSearch" @queryTable="getDeviceList" />
    </el-row>

    <el-table v-loading="loading" :data="device1List" @selection-change="handleSelectionChange">
      <el-table-column align="center" type="selection" width="55" />
      <el-table-column align="center" label="设备ID" prop="deviceId" />
      <el-table-column align="center" label="设备图片" prop="deviceImage" width="100">
        <template slot-scope="scope">
          <image-preview :height="50" :src="scope.row.deviceImage" :width="50" />
        </template>
      </el-table-column>
      <el-table-column align="center" label="设备编码" prop="deviceCode" />
      <el-table-column align="center" label="设备名称" prop="deviceName" />
      <el-table-column align="center" label="所属产品" prop="deviceProductName" />
      <!--<el-table-column align="center" label="显示顺序" prop="orderNum" />-->
      <el-table-column align="center" label="负责人" prop="leader" />
      <el-table-column align="center" label="联系电话" prop="phone" />
      <el-table-column align="center" label="邮箱" prop="email" />
      <el-table-column :formatter="formatDeviceStatus" align="center" label="设备状态" prop="deviceStatus">
        <template slot-scope="scope">
          <el-tag
            :type="statusColorMap[scope.row.deviceStatus]"
            :color="statusColorMap[scope.row.deviceStatus]"
            disable-transitions
          >{{ deviceStatusMapping[scope.row.deviceStatus] }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column align="center" label="说明" prop="descripe" />
      <el-table-column align="center" class-name="small-padding fixed-width" label="操作">
        <template slot-scope="scope">
          <el-button
            v-hasPermi="['devices:device1:query']"
            icon="el-icon-view"
            size="mini"
            type="text"
            @click="handleDetail(scope.row)"
          >详情
          </el-button>
          <el-button
            v-hasPermi="['devices:device1:edit']"
            icon="el-icon-edit"
            size="mini"
            type="text"
            @click="handleUpdate(scope.row)"
          >修改
          </el-button>
          <el-button
            v-hasPermi="['devices:device1:remove']"
            icon="el-icon-delete"
            size="mini"
            type="text"
            @click="handleDelete(scope.row)"
          >删除
          </el-button>
          <el-button
            v-hasPermi="['devices:device1:command']"
            icon="el-icon-command"
            size="mini"
            type="text"
            @click="handleCommand(scope.row)"
          >指令下发
          </el-button>
          <el-button
            v-hasPermi="['devices:device1:upgrade']"
            icon="el-icon-upgrade"
            size="mini"
            type="text"
            @click="handleupgrade(scope.row)"
          >升级
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :limit.sync="queryParams.pageSize"
      :page.sync="queryParams.pageNum"
      :total="total"
      @pagination="getDeviceList"
    />

    <el-dialog :title="title1" :visible.sync="open1" append-to-body width="500px">
      <el-form ref="commandform" :model="commandform" :rules="rules" label-width="80px">
        <el-form-item label="设备唯一编码" prop="deviceCode">
          <el-input v-model="commandform.deviceCode" />
        </el-form-item>
        <el-form-item label="设备指令" prop="deviceCommand">
          <el-select
            v-model="commandform.deviceCommand"
            placeholder="请选择或输入设备指令"
            allow-create
            class="filter-item"
            clearable
            default-first-option
            filterable
            style="width: 200px"
            @blur="selectBlur($event)"
            @select="handleSelect"
          >
            <el-option
              v-for="command2 in commands"
              :key="command2.label"
              :label="command2.label"
              :value="command2.value"
              class="suggestion-list"
              name="fade"
            >
              {{ command.label }}
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitOrder">指令下发</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 添加或修改设备对话框 -->
    <el-dialog :title="title" :visible.sync="open" append-to-body width="60%">
      <el-form ref="addeditform" :model="addeditform" :rules="rules" label-width="80px">
        <el-form-item label="设备图片" prop="deviceImage">
          <image-upload v-model="addeditform.deviceImage" />
        </el-form-item>
        <el-row :gutter="10"> <!-- 添加一行，并设置间距 -->
          <el-col :span="9"> <!-- 第二列，占6格 -->
            <el-form-item label="设备ID" prop="deviceId">
              <el-input v-model="addeditform.deviceId" placeholder="请输入设备ID或不输入自动生成" :disabled="title==='修改设备'" />
            </el-form-item>
          </el-col>
          <el-col :span="7"> <!-- 第三列，占6格 -->
            <el-form-item label="设备编码" prop="deviceCode">
              <el-input v-model="addeditform.deviceCode" placeholder="请输入设备编码" :disabled="title==='修改设备'" />
            </el-form-item>
          </el-col>
          <el-col :span="6"> <!-- 第四列，占6格 -->
            <el-form-item label="设备名称" prop="deviceName">
              <el-input v-model="addeditform.deviceName" placeholder="请输入设备名称" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="10"> <!-- 添加一行，并设置间距 -->
          <el-col :span="9"> <!-- 第一列，占6格 -->
            <el-form-item label="设备类型" prop="deviceType">
              <el-radio v-model="addeditform.deviceType" label="1">直连设备</el-radio>
              <el-radio v-model="addeditform.deviceType" label="2">网关设备</el-radio>
              <el-radio v-model="addeditform.deviceType" label="3">网关子设备</el-radio>
            </el-form-item>
          </el-col>
          <el-col :span="6"> <!-- 第二列，占6格 -->
            <el-form-item label="所属产品" prop="deviceProductName">
              <el-select
                v-model="addeditform.deviceProductName"
                :placeholder="placeholderProduct"
                allow-create
                class="filter-item"
                clearable
                default-first-option
                filterable
                style="width: 200px"
                @blur="selectBlur($event)"
                @input="searchProducts"
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
          </el-col>
        </el-row>
        <!--<el-form-item label="显示顺序" prop="orderNum">-->
        <!--<el-input v-model="addeditform.orderNum" placeholder="请输入显示顺序" />-->
        <!--</el-form-item>-->
        <el-row :gutter="10"> <!-- 添加一行，并设置间距 -->
          <el-col :span="9"> <!-- 第二列，占6格 -->
            <el-form-item label="负责人" prop="leader">
              <el-select
                v-model="addeditform.leader"
                placeholder="请输入负责人"
                allow-create
                class="filter-item"
                clearable
                default-first-option
                filterable
                style="width: 200px"
                @blur="selectBlur($event)"
                @input="searchUsers"
                @change="updateContactInfo"
              >
                <el-option
                  v-for="user in users"
                  :key="user.id"
                  :label="user.name"
                  :value="user.name"
                  class="suggestion-list"
                  name="fade"
                >
                  {{ user.name }}
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="7"> <!-- 第三列，占6格 -->
            <el-form-item label="联系电话" prop="phone">
              <el-input v-model="addeditform.phone" placeholder="请输入联系电话" />
            </el-form-item>
          </el-col>
          <el-col :span="6"> <!-- 第四列，占6格 -->
            <el-form-item label="邮箱" prop="email">
              <el-input v-model="addeditform.email" placeholder="请输入邮箱" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="10"> <!-- 添加一行，并设置间距 -->
          <el-col :span="9"> <!-- 第一列，占6格 -->
            <el-form-item label="产品秘钥" prop="productKey">
              <el-input v-model="addeditform.productKey" type="password" placeholder="请输入认证秘钥" :disabled="title==='修改设备'" />
            </el-form-item>
          </el-col>
          <el-col :span="6"> <!-- 第二列，占6格 -->
            <el-form-item label="设备秘钥" prop="deviceSecret">
              <el-input v-model="addeditform.deviceSecret" type="password" placeholder="请输入设备秘钥" :disabled="title==='修改设备'" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="10">
          <el-form-item label="设备描述" prop="descripe">
            <el-input v-model="addeditform.descripe" placeholder="请输入说明" />
          </el-form-item>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 设备导入 -->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
      <el-upload
        ref="upload"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="upload.url + '?updateSupport=' + upload.updateSupport"
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :auto-upload="false"
        drag
      >
        <i class="el-icon-upload" />
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div slot="tip" class="el-upload__tip text-center">
          <div slot="tip" class="el-upload__tip">
            <el-checkbox v-model="upload.updateSupport" /> 是否更新已经存在的用户数据
          </div>
          <span>仅允许导入.xlsx, .xls格式文件。</span>
          <el-link type="primary" :underline="false" style="font-size:12px;vertical-align: baseline;" @click="importTemplate">下载模板</el-link>
        </div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog :title="command.title" :visible.sync="command.open" width="800px" append-to-body>
      <el-table :data="command.data" border fit highlight-current-row style="width: 100%">
        <el-table-column label="设备ID" align="center" prop="deviceId" />
        <el-table-column label="设备编码" align="center" prop="deviceCode" />
        <el-table-column label="设备名称" align="center" prop="deviceName" />
        <el-table-column label="所属产品" align="center" prop="deviceProductName" />
        <el-table-column label="负责人" align="center" prop="leader" />
        <el-table-column label="联系电话" align="center" prop="phone" />
        <el-table-column label="邮箱" align="center" prop="email" />
      </el-table>
      <div>
        <div style="padding-top: 30px; font-weight: 600">设备指令：
          <el-select
            v-model="command.deviceCommand"
            placeholder="请选择或输入设备指令"
            allow-create
            class="filter-item"
            clearable
            default-first-option
            filterable
            style="width: 200px"
            @blur="selectBlur($event)"
            @select="handleSelect"
          >
            <el-option
              v-for="command1 in commands"
              :key="command1.label"
              :label="command1.label"
              :value="command1.value"
              class="suggestion-list"
              name="fade"
            >
              {{ command.label }}
            </el-option>
          </el-select>
        </div>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="sendCommands">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import {
  addDevice1,
  delDevice1,
  getDevice1,
  listDevice1,
  updateDevice1,
  sendCommand,
  queryDeviceMessage
} from '@/api/devices/device/device1'
import { listProduct } from '@/api/devices/product/product'
import { listUser } from '@/api/system/user'
import { getToken } from '@/utils/auth'

export default {
  name: 'Device1',
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 设备表格数据
      device1List: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        deviceName: null,
        deviceCode: null,
        deviceProductName: null,
        deviceStatus: null,
        leader: null,
        phone: null,
        email: null
      },
      title1: '指令下发页面',
      open1: false,
      // 产品列表
      productList: [],
      // 设备导入参数
      upload: {
        // 是否显示弹出层（设备导入）
        open: false,
        // 弹出层标题（设备导入）
        title: '',
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的设备数据
        updateSupport: 0,
        // 设置上传的请求头部
        headers: { Authorization: 'Bearer ' + getToken() },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + '/devices/device1/importData'
      },
      // 批量下发指令
      command: {
        data: [],
        // 是否显示弹出层（指令下发）
        open: false,
        // 弹出层标题（指令下发）
        title: '批量指令下发',
        deviceCommand: null
      },
      placeholderProduct: '请输入或选择产品名称',
      productions: [],
      deviceStatusMapping: {
        0: '在线',
        1: '离线',
        2: '锁定',
        3: '故障',
        4: '报警',
        5: '维护',
        6: '试用',
        7: '未激活',
        8: '保养'
      },
      statusColorMap: {
        0: 'rgba(0,255,0,0.6)', // 在线, 绿色减弱
        1: 'rgba(206,4,4,0.6)', // 离线, 红色减弱
        2: 'rgba(0, 0, 255, 0.6)', // 锁定, 蓝色减弱
        3: 'rgba(255, 165, 0, 0.6)', // 故障, 橙色减弱
        4: 'rgba(128, 0, 128, 0.6)', // 报警, 紫色减弱
        5: 'rgba(128, 128, 128, 0.6)', // 维护, 灰色减弱
        6: 'rgba(173, 216, 230, 0.6)', // 试用, 黄色减弱
        7: 'rgba(243,243,171,0.6)', // 未激活, 浅蓝色减弱
        8: 'rgba(2,63,2,0.7)' // 保养, 棕色减弱
      },
      users: [],
      // 指令下发表单参数
      commandform: {
        deviceCode: null,
        deviceCommand: null
      },
      // 修改或者新增表单参数
      addeditform: {
        deviceId: null,
        deviceImage: null,
        deviceCode: null,
        deviceName: null,
        deviceType: null,
        productId: null,
        orderNum: null,
        leader: null,
        phone: null,
        email: null,
        descripe: null,
        productKey: null,
        deviceSecret: null
      },
      // 表单校验
      rules: {
        deviceCode: [
          { required: true, message: '请输入设备唯一编码', trigger: 'blur' },
          { min: 5, max: 20, message: '长度应在5-20个字符之间', trigger: 'blur' }
        ],
        productKey: [
          { required: true, message: '请输入产品密钥', trigger: 'blur' }
        ],
        deviceSecret: [
          { required: true, message: '请输入设备密钥', trigger: 'blur' }
        ],
        deviceCommand: [
          { required: true, message: '请输入设备指令', trigger: 'blur' }
        ]
      },
      commands: [
        { label: '设备开灯', value: '{"*111*":"Led2Set"}' },
        { label: '设备关灯', value: '{"*222*":"Led2Set"}' },
        { label: '设备开机', value: '{"*1*":"PowerStatusSet"}' },
        { label: '设备关机', value: '{"*2*":"PowerStatusSet"}' },
        { label: '设备重启', value: '{"*3*":"PowerStatusSet"}' },
        { label: '设备锁定', value: '{"*1*":"LockStatusSet"}' },
        { label: '设备解锁', value: '{"*2*":"LockStatusSet"}' },
        { label: '设备保养', value: '{"*1*":"MaintainStatusSet"}' },
        { label: '设备试运行', value: '{"*1*":"TrialStatusSet"}' },
        { label: '设备试运行结束', value: '{"*2*":"TrialStatusSet"}' },
        { label: '设备升级', value: '{"*1*":"UpgradeStatusSet"}' },
        { label: '设备升级结束', value: '{"*2*":"UpgradeStatusSet"}' },
        { label: '设备恢复出厂设置', value: '{"*1*":"ResetStatusSet"}' }

      ],
      filteredCommands: [],
      refreshTimer: null
    }
  },
  created() {
    this.getDeviceList()
    this.searchProducts()
    this.searchUsers()
    this.startRefresh()
  },
  beforeDestroy() {
    this.stopRefresh()
  },
  methods: {
    startRefresh() {
      this.stopRefresh()
      this.refreshInterval = setInterval(() => {
        this.getDeviceList()
      }, 10000)
    },
    stopRefresh() {
      if (this.refreshInterval) {
        clearInterval(this.refreshInterval)
        this.refreshInterval = null
      }
    },
    sendBatchCommand() {
      if (this.ids.length === 0) {
        this.$message({
          message: '请至少选择一个设备',
          type: 'warning'
        })
        return
      }

      this.command.open = true
      // 基于ids从device1List中筛选出被选中的设备详细信息
      const selectedDevices = this.device1List.filter(device => this.ids.includes(device.deviceId))
      this.command.data = selectedDevices.map(device => ({
        deviceId: device.deviceId,
        deviceCode: device.deviceCode,
        deviceName: device.deviceName,
        deviceProductName: device.deviceProductName,
        leader: device.leader,
        phone: device.phone,
        email: device.email
      }))
      // 现在，selectedDevices数组包含了所有被选中设备的详细信息
      // 根据需要处理这些数据，比如传递给命令下发的逻辑
      console.log('被选中的设备信息:', this.command.data)
    },
    async sendCommands() {
      const selectedDeviceCode = this.command.data.map(device => device.deviceCode)
      const selectedDeviceCommand = this.command.deviceCommand
      // 检查是否有设备ID和设备指令
      console.log(selectedDeviceCode, selectedDeviceCommand)
      // 检查是否有设备编码和设备指令
      if (selectedDeviceCode.length === 0 || !selectedDeviceCommand) {
        this.$message.error('请先选择设备和设备指令')
        return
      }

      this.command.open = false

      for (const deviceCode of selectedDeviceCode) {
        try {
          const response = await sendCommand({ deviceCode, deviceCommand: selectedDeviceCommand })
          if (response === '客户端成功接收下发的指令') {
            this.$message.success(`设备${deviceCode}指令下发成功`)
            // 延迟5秒查询设备消息
            setTimeout(async() => {
              queryDeviceMessage(deviceCode).then(response => {
                const deviceid = response.DeviceID
                console.log(response)
                console.log(deviceid)
                // 将响应对象转换为字符串并显示
                const responseStr = JSON.stringify(response, null, 2) // 2是缩进空格数，可自定义
                this.$modal.msgSuccess(`设备 ${deviceCode} 下发指令成功！响应的详细信息：\n\n${responseStr}`)
              }).catch(error => {
                // 这里处理请求失败的情况，包括网络错误和非200状态码
                if (error.response) {
                  // 这表示请求已发出，但服务器响应了一个错误状态码
                  console.log('Error Status:', error.response.status)
                  console.log('Error Body:', error.response.data) // 这里就是您想获取的错误信息
                  this.$modal.msgError(`设备 ${deviceCode} 的响应失败: ${error.response.data}`)
                } else {
                  // 这里处理网络故障等其他错误
                  console.error('Error during request:', error.message)
                }
              })
            }, 5000)
          } else {
            this.$modal.msgError(`设备 ${deviceCode} 的指令下发失败: ${response}`)
          }
        } catch (error) {
          this.$modal.msgError(`设备 ${deviceCode} 的响应出错: ${error.message}`)
        }
      }
    },
    handleSelect(item) {
      // 当选择一个选项时，更新表单中的设备指令
      this.commandform.deviceCommand = item.value
      this.$message('设备指令已更新为：' + item.label)
    },
    formatDeviceStatus(row) {
      const statusText = this.deviceStatusMapping[row.deviceStatus]
      // // 返回一个带有颜色状态文本
      return statusText
    },
    // sendEmail() {
    //   sendEmail().then(response => {
    //     this.$message({
    //       message: '发送成功',
    //       type: 'success'
    //     })
    //   })
    // },
    /** 查询设备列表 */
    getDeviceList() {
      this.loading = true
      listDevice1(this.queryParams).then(response => {
        this.device1List = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    searchUsers() {
      listUser().then(response => {
        this.users = response.rows.map(user => ({
          name: user.nickName,
          phone: user.phonenumber,
          email: user.email,
          id: user.userId
        }))
      })
    },
    /**
     * 更新联系电话和邮箱信息
     */
    updateContactInfo(selectedLeaderName) {
      const selectedUser = this.users.find(
        (user) => user.name === selectedLeaderName
      )

      if (selectedUser) {
        this.addeditform.phone = selectedUser.phone
        this.addeditform.email = selectedUser.email
      } else {
        // 如果找不到匹配的用户（例如用户手动输入的新负责人），清空联系电话和邮箱字段
        this.addeditform.phone = ''
        this.addeditform.email = ''
      }
    },
    // 产品下拉框
    searchProducts() {
      listProduct().then(response => {
        this.productions = response.rows.map(product => ({
          name: product.productName
        }))
      })
    },
    selectProduct(selectedProduct) {
      this.queryParams.deviceProductName = selectedProduct.name
      this.productions = []
    },
    // 下拉框可输可选
    selectBlur(e) {
      if (e.target.value !== '') {
        this.$set(this.queryParams, 'network', e.target.value)
      }
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.open1 = false
      this.upload.open = false
      this.command.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.command = {
        data: [],
        deviceCommand: null,
        open: false
      }
      this.addeditform = {
        deviceId: null,
        deviceImage: null,
        deviceCode: null,
        deviceName: null,
        deviceCommand: null,
        deviceProductName: null,
        descripe: null,
        orderNum: null,
        leader: null,
        phone: null,
        email: null,
        deviceStatus: null,
        productKey: null,
        delFlag: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        deviceSecret: null,
        deviceType: null
      }
      this.resetForm('addeditform')
      this.resetForm('commandform')
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getDeviceList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.deviceId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = '用户导入'
      this.upload.open = true
    },
    /** 下载模板操作 */
    importTemplate() {
      this.download('devices/device1/importTemplate', {
      }, `device_template_${new Date().getTime()}.xlsx`)
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.upload.open = false
      this.upload.isUploading = false
      this.$refs.upload.clearFiles()
      this.$alert("<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" + response.msg + '</div>', '导入结果', { dangerouslyUseHTMLString: true })
      this.getDeviceList()
    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit()
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = '添加设备'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const deviceCode = row.deviceCode || this.ids
      getDevice1(deviceCode).then(response => {
        this.addeditform = response.data
        this.open = true
        this.title = '修改设备'
      })
    },
    handleDetail(row) {
      this.reset()
      const deviceCode = row.deviceCode || this.ids
      getDevice1(deviceCode).then(response => {
        this.addeditform = response.data
        this.open = true
        this.title = '查看设备'
      })
    },
    async submitOrder() {
      // 检查表单数据是否完整
      if (!this.commandform.deviceCode || !this.commandform.deviceCommand) {
        this.$modal.msgError('设备编码和指令不能为空')
        return
      }

      // 验证表单
      if (!this.$refs['commandform'].validate()) {
        return
      }

      // 准备发送的数据
      const data = {
        deviceCode: this.commandform.deviceCode,
        deviceCommand: this.commandform.deviceCommand
      }

      // 发送指令并处理响应
      const response = await sendCommand(data)
      this.open1 = false
      const msg = response
      if (msg === '客户端成功接收下发的指令') {
        this.$modal.msgSuccess('指令下发成功')

        try {
          // 发送指令并获取响应
          setTimeout(async() => {
            queryDeviceMessage(this.commandform.deviceCode).then(response => {
              const deviceid = response.DeviceID
              console.log(response)
              console.log(deviceid)
              // 将响应对象转换为字符串并显示
              const responseStr = JSON.stringify(response, null, 2) // 2是缩进空格数，可自定义
              this.$modal.msgSuccess(`\n\n设备 ${deviceid} 响应成功！详细信息：\n\n${responseStr}`)
            }).catch(error => {
              // 这里处理请求失败的情况，包括网络错误和非200状态码
              if (error.response) {
                // 这表示请求已发出，但服务器响应了一个错误状态码
                console.log('Error Status:', error.response.status)
                console.log('Error Body:', error.response.data) // 这里就是您想获取的错误信息
                this.$modal.msgError(error.response.data)

                // 根据状态码进行具体处理
                if (error.response.status === 404) {
                  console.log('设备响应为空')
                  // 这里处理404的情况，比如展示提示给用户
                  this.$modal.msgError('设备响应为空')
                } else {
                  console.error('其他错误状态', error.response.status, error.response.data)
                }
              } else {
                // 这里处理网络故障等其他错误
                console.error('Error during request:', error.message)
                this.$modal.msgError('设备没有响应')
              }
            })
          }, 5000)
        } catch (error) {
          this.$modal.msgError('设备响应超时')
        }
      } else {
        this.$modal.msgError(msg)
      }
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['addeditform'].validate((valid) => { // 验证表单
        if (valid) {
          if (this.addeditform.deviceId == null) {
            const chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789'
            let result = ''
            for (let i = 0; i < 12; i++) {
              result += chars.charAt(Math.floor(Math.random() * chars.length))
            }
            this.addeditform.deviceId = result
          }
          // 收集表单数据
          const formData = {
            deviceId: this.addeditform.deviceId,
            deviceCode: this.addeditform.deviceCode,
            deviceName: this.addeditform.deviceName,
            deviceType: this.addeditform.deviceType,
            deviceProductName: this.addeditform.deviceProductName,
            leader: this.addeditform.leader,
            phone: this.addeditform.phone,
            email: this.addeditform.email,
            productKey: this.addeditform.productKey,
            deviceSecret: this.addeditform.deviceSecret,
            descripe: this.addeditform.descripe,
            // 如果有上传的图片，需要处理imageUpload组件返回的文件数据，这里假设直接使用URL
            deviceImage: this.addeditform.deviceImage
          }
          if (this.addeditform.deviceId != null && this.title === '修改设备') {
            updateDevice1(formData).then(response => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getDeviceList()
            })
          } else {
            addDevice1(formData).then(response => {
              this.$modal.msgSuccess('新增成功')
              this.open = false
              this.getDeviceList()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const deviceIds = row.deviceId || this.ids
      this.$modal.confirm('是否确认删除设备编号为"' + deviceIds + '"的数据项？').then(function() {
        return delDevice1(deviceIds)
      }).then(() => {
        this.getDeviceList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('devices/device1/export', {
        ...this.queryParams
      }, `device1_${new Date().getTime()}.xlsx`)
    },
    /** 指令下发操作 */
    handleCommand(row) {
      this.reset()
      this.open1 = true
      const deviceCode = row.deviceCode || this.ids
      this.commandform.deviceCode = deviceCode
    },
    handleupgrade: function(row) {
      const deviceCode = row.deviceCode
      console.log(deviceCode)
      this.$router.push('/devices/device-auth/upgrade/' + deviceCode)
    }
  }
}
</script>

<style lang="scss" scoped>
.dashboard {
  &-container {
    margin: 30px;
  }
  &-text {
    font-size: 30px;
    line-height: 46px;
  }
}
.el-icon-warning {
  position: absolute;
  right: 100px;
  top: 10px;
}
.key-toggle {
  display: flex;
  align-items: center;
}

.key-toggle button {
  margin-left: 4px;
}

.active {
  font-weight: bold;
}
</style>
