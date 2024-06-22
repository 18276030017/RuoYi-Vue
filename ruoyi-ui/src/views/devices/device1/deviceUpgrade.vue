<template>
  <div class="app-container">
    <div class="form-container">
      <h4 class="form-header h4">设备基本信息</h4>
      <table class="el-table el-table--fit el-table--border el-table--scrollable-x">
        <thead ref="tableHeader" class="el-table__header" style="background-color: #e4edff">
          <td class="el-table__cell is-leaf"><div class="cell">设备ID</div></td>
          <td class="el-table__cell is-leaf"><div class="cell">设备名称</div></td>
          <td class="el-table__cell is-leaf"><div class="cell">设备编号</div></td>
          <td class="el-table__cell is-leaf"><div class="cell">设备状态</div></td>
          <td class="el-table__cell is-leaf"><div class="cell">所属产品</div></td>
          <td class="el-table__cell is-leaf"><div class="cell">负责人</div></td>
          <td class="el-table__cell is-leaf"><div class="cell">联系电话</div></td>
          <td class="el-table__cell is-leaf"><div class="cell">电子邮箱</div></td>
        </thead>
        <tbody class="el-table__body">
          <td class="el-table__cell is-leaf">
            <div class="cell">{{ device1List.deviceId }}</div>
          </td>
          <td class="el-table__cell is-leaf">
            <div class="cell">{{ device1List.deviceName }}</div>
          </td>
          <td class="el-table__cell is-leaf">
            <div class="cell">{{ device1List.deviceCode }}</div>
          </td>
          <td class="el-table__cell is-leaf">
            <div class="cell">{{ deviceStatusMapping[device1List.deviceStatus] }}</div>
          </td>
          <td class="el-table__cell is-leaf">
            <div class="cell">{{ device1List.deviceProductName }}</div>
          </td>
          <td class="el-table__cell is-leaf">
            <div class="cell">{{ device1List.leader }}</div>
          </td>
          <td class="el-table__cell is-leaf">
            <div class="cell">{{ device1List.phone }}</div>
          </td>
          <td class="el-table__cell is-leaf">
            <div class="cell">{{ device1List.email }}</div>
          </td>
        </tbody>
      </table>
    </div>

    <div class="form-container1">
      <h4 class="form-header h4">升级包信息</h4>
      <el-form v-show="showSearch" ref="queryForm" :inline="true" :model="queryParams" label-width="68px" size="small">
        <el-form-item label="标题" prop="upgradeTitle">
          <el-input
            v-model="queryParams.upgradeTitle"
            clearable
            placeholder="请输入升级标题"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="创建时间">
          <el-date-picker
            v-model="dateRange"
            style="width: 240px"
            value-format="yyyy-MM-dd"
            type="daterange"
            range-separator="-"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
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
            v-hasPermi="['devices:upgrade:add']"
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
            v-hasPermi="['devices:upgrade:remove']"
            :disabled="multiple"
            icon="el-icon-delete"
            plain
            size="mini"
            type="danger"
            @click="handleDelete"
          >删除
          </el-button>
        </el-col>
        <right-toolbar :show-search.sync="showSearch" :columns="columns" @queryTable="getList" />
      </el-row>

      <el-table v-loading="loading" :data="upgrades" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" />
<!--        <el-table-column v-if="columns[0].visible" key="upgradeId" label="序号" align="center" prop="upgradeId" />-->
        <el-table-column v-if="columns[0].visible" key="createTime" label="创建时间" align="center" prop="createTime" width="180">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.createTime) }}</span>
          </template>
        </el-table-column>
        <el-table-column v-if="columns[1].visible" key="upgradeTitle" label="升级标题" align="center" prop="upgradeTitle" />
        <el-table-column v-if="columns[2].visible" key="upgradeName" label="升级包名称" align="center" prop="upgradeName" />
        <el-table-column v-if="columns[3].visible" key="upgradeSize" label="升级包大小" align="center" prop="upgradeSize" />
        <el-table-column v-if="columns[4].visible" key="upgradeVersion" label="升级包版本" align="center" prop="upgradeVersion" />
        <el-table-column v-if="columns[5].visible" key="upgradeUrl" label="升级包地址" align="center" prop="upgradeUrl" />
        <el-table-column v-if="columns[6].visible" key="upgradeDesc" label="升级包描述" align="center" prop="upgradeDesc" />
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleUpdate(scope.row)"
              v-hasPermi="['devices:upgrade:edit']"
            >修改</el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              @click="handleDelete(scope.row)"
              v-hasPermi="['devices:upgrade:remove']"
            >删除</el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-upload"
              @click="handleUpgrade(scope.row)"
              v-hasPermi="['devices:upgrade:send']"
            >升级推送</el-button>
          </template>
        </el-table-column>
      </el-table>
      <pagination v-show="total>0" :total="total" :page.sync="pageNum" :limit.sync="pageSize" />
    </div>

    <el-dialog :title="title" :visible.sync="open" append-to-body width="500px">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="升级标题" prop="upgradeTitle">
          <el-input v-model="form.upgradeTitle" placeholder="请输入升级标题" />
        </el-form-item>
        <el-form-item label="升级版本" prop="upgradeVersion">
          <el-input v-model="form.upgradeVersion" placeholder="请输入升级包版本" />
        </el-form-item>
        <el-form-item label="更新说明" prop="upgradeDesc">
          <el-input v-model="form.upgradeDesc" placeholder="请输入更新内容" />
        </el-form-item>
        <el-form-item label="文件名" prop="upgradeName" v-if="title !== '添加设备升级包'">
          <el-input v-model="form.upgradeName" placeholder="" readonly disabled />
        </el-form-item>
        <el-form-item label="文件大小" prop="upgradeSize" v-if="title !== '添加设备升级包'">
          <el-input v-model="form.upgradeSize" placeholder="" readonly disabled />
        </el-form-item>
        <el-form-item v-if="editStatus" label="上传文件" prop="upgradeUrl">
          <el-upload
            ref="upload"
            :limit="1"
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
            <!--            <div slot="tip" class="el-upload__tip">只能上传.rbl, .bin, .hex文件，且不超过500MB。</div>-->
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getDevice1, queryDeviceMessage } from '@/api/devices/device/device1'
import {
  listUpgrade,
  delUpgrade,
  addUpgrade,
  updateUpgrade,
  getUpgrade,
  sendUpgrade
} from '@/api/devices/device/upgrade'
import { parseTime } from '@/utils/ruoyi'
import { getToken } from '@/utils/auth'

export default {
  name: 'Upgrade',
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
      // 日期范围
      dateRange: [],
      // 分页信息
      total: 0,
      pageNum: 1,
      pageSize: 10,
      // 显示搜索条件
      showSearch: true,
      // 升级包的列信息
      columns: [
        { key: 0, label: '创建时间', visible: true },
        { key: 1, label: '升级标题', visible: true },
        { key: 2, label: '升级包名称', visible: true },
        { key: 3, label: '升级包大小', visible: true },
        { key: 4, label: '升级包版本', visible: true },
        { key: 5, label: '升级包地址', visible: true },
        { key: 6, label: '升级包描述', visible: true }
      ],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        deviceCode: this.$route.params && this.$route.params.deviceCode,
        upgradeVersion: null,
        upgradeName: null,
        upgradeUrl: null,
        upgradeDesc: null,
        upgradeTitle: null,
        upgradeStatus: null,
        upgradeSize: null
      },
      // 修改状态
      editStatus: true,
      // 选中升级包编号
      upgradeIds: [],
      // 升级包信息
      upgrades: [],
      // 设备信息
      device1List: [],
      deviceStatusMapping: {
        0: '在线',
        1: '离线',
        2: '锁定',
        3: '故障',
        4: '报警',
        5: '维护',
        6: '试用',
        7: '未激活'
      },
      deviceTypeMapping: {
        0: '直连设备',
        1: '网关设备',
        2: '网关子设备'
      },
      // 新增或修改页面
      title: '',
      open: false,
      form: {},
      rules: {
        upgradeTitle: [
          { required: true, message: '升级标题不能为空', trigger: 'blur' }
        ],
        upgradeVersion: [
          { required: true, message: '升级包版本不能为空', trigger: 'blur' }
        ],
        upgradeDesc: [
          { required: true, message: '更新内容不能为空', trigger: 'blur' }
        ],
        upgradeUrl: [
          { required: true, message: '请上传文件', trigger: 'blur' }
        ]
      },
      // 升级包上传参数
      upload: {
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的文件
        updateSupport: true,
        // 设置上传的请求头部
        headers: { Authorization: 'Bearer ' + getToken() },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + '/devices/upgrade/upload'
      }
    }
  },
  created() {
    const deviceCode = this.$route.params && this.$route.params.deviceCode
    if (deviceCode) {
      this.loading = true
      getDevice1(deviceCode).then((response) => {
        this.device1List = response.data
        this.loading = false
      })
    }
    this.getList()
  },
  methods: {
    parseTime,
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.dateRange = []
      this.resetForm('queryForm')
      this.handleQuery()
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        upgradeId: null,
        upgradeVersion: null,
        upgradeName: null,
        upgradeUrl: null,
        upgradeDesc: null,
        upgradeTitle: null,
        upgradeStatus: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        delFlag: null,
        upgradeSize: null
      }
      this.resetForm('form')
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.open = true
      this.editStatus = true
      this.title = '添加设备升级包'
      this.reset()
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const upgradeId = row.upgradeId || this.ids
      getUpgrade(upgradeId).then(response => {
        this.form = response.data
        this.open = true
        this.editStatus = false
        this.title = '修改升级包信息'
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const upgradeIds = row.upgradeId || this.ids
      this.$modal.confirm('是否确认删除序号为"' + upgradeIds + '"的数据项？').then(function() {
        return delUpgrade(upgradeIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {
      })
    },
    /** 查询升级包列表 */
    getList() {
      this.loading = true
      listUpgrade(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
        this.upgrades = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.deviceId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
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
      this.form.upgradeName = file.name
      // 将JSON字符串转换为JavaScript对象
      const jsonObject = JSON.parse(response.msg)
      // 提取url的值
      this.form.upgradeUrl = jsonObject.url
      this.form.upgradeSize = this.formatFileSize(file.size)
      this.$message.success('上传成功')
      // this.$alert("<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" + response.msg + '</div>', '导入结果', { dangerouslyUseHTMLString: true })
      // 文件上传成功后，继续提交整个表单
      this.submitToBackend()
    },
    // 将字节大小转换为可读格式，如 B, KB, MB
    formatFileSize(size) {
      if (size < 1024) {
        return `${size} B`
      } else if (size < 1024 * 1024) {
        return `${(size / 1024).toFixed(2)} KB`
      } else {
        return `${(size / (1024 * 1024)).toFixed(2)} MB`
      }
    },
    submitForm() {
      // 如果文件尚未上传（此处需根据实际情况判断，例如检查是否有文件URL）
      if (!this.form.upgradeUrl) {
        // 触发文件上传
        this.$refs.upload.submit()
      } else {
        // 如果文件已上传，则直接提交表单
        this.submitToBackend()
      }
    },
    submitToBackend() {
      // 使用this.$refs.form获取表单实例并验证
      this.$refs.form.validate((valid) => {
        if (valid) {
          // 整理表单数据
          const formData = {
            upgradeTitle: this.form.upgradeTitle,
            upgradeVersion: this.form.upgradeVersion,
            upgradeDesc: this.form.upgradeDesc,
            upgradeName: this.form.upgradeName,
            upgradeUrl: this.form.upgradeUrl,
            upgradeSize: this.form.upgradeSize,
            upgradeStatus: this.form.upgradeStatus,
            upgradeId: this.form.upgradeId,
            deviceCode: this.device1List.deviceCode
            // 其它表单项...
          }
          if (this.form.upgradeId != null) {
            updateUpgrade(this.form).then(response => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addUpgrade(formData).then(response => {
              this.$modal.msgSuccess('新增成功')
              this.open = false
              this.getList()
            }).catch((error) => {
              console.error('表单提交失败', error)
              // 显示错误消息
              this.$message.error('提交失败，请重试')
            })
          }
        } else {
          console.log('表单验证未通过')
          return false
        }
      })
    },
    handleUpgrade(row) {
      const Data = {
        upgradeId: row.upgradeId,
        upgradeUrl: row.upgradeUrl,
        upgradeVersion: row.upgradeVersion,
        upgradeTitle: row.upgradeTitle,
        upgradeDesc: row.upgradeDesc,
        upgradeName: row.upgradeName,
        upgradeSize: row.upgradeSize,
        deviceCode: this.device1List.deviceCode,
        deviceName: this.device1List.deviceName,
        deviceType: this.device1List.deviceType,
        deviceStatus: this.device1List.deviceStatus,
        deviceDesc: this.device1List.deviceDesc,
        channelId: this.device1List.channelId,
        leader: this.device1List.leader,
        email: this.device1List.email,
        phone: this.device1List.phone,
        deviceProductName: this.device1List.deviceProductName
      }
      sendUpgrade(Data).then(response => {
        const msg = response
        if (msg === '客户端成功接收下发的指令') {
          this.$modal.msgSuccess('指令下发成功')

          try {
            // 发送指令并获取响应
            setTimeout(async() => {
              queryDeviceMessage(Data.deviceCode).then(response => {
                const deviceid = response.DeviceID
                console.log(response)
                console.log(deviceid)
                // 将响应对象转换为字符串并显示
                const responseStr = JSON.stringify(response, null, 2); // 2是缩进空格数，可自定义
                this.$modal.msgSuccess(`\n\n设备 ${deviceid} 响应成功！详细信息：\n\n${responseStr}`)
              }).catch(error => {
                // 这里处理请求失败的情况，包括网络错误和非200状态码
                if (error.response) {
                  // 这表示请求已发出，但服务器响应了一个错误状态码
                  console.log('Error Status:', error.response.status)
                  console.log('Error Body:', error.response.data) // 这里就是您想获取的错误信息
                  this.$modal.msgError(error.response.data)
                } else {
                  // 这里处理网络故障等其他错误
                  console.error('Error during request:', error.message)
                }
              })
            }, 5000)
          } catch (error) {
            this.$modal.msgError('设备响应超时')
          }
        } else {
          this.$modal.msgError(msg)
        }
      })
    }
  }
}
</script>
<style scoped>
  .form-container1 {
    margin-top: 30px;
    border: 1px solid #DCDFE6;
  }
  .upload-demo {
    width: 300px;
    height: auto;
  }
</style>
