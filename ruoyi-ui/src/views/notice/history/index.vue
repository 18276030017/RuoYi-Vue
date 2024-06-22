<template>
  <div class="app-container">
    <el-form v-show="showSearch" ref="queryForm" :model="queryParams" size="small" :inline="true" label-width="68px">
      <el-form-item label="通知配置ID" prop="notifierId">
        <el-input
          v-model="queryParams.notifierId"
          placeholder="请输入通知配置ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="配置名称" prop="noteName">
        <el-input
          v-model="queryParams.noteName"
          placeholder="请输入配置名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="模版ID" prop="templateId">
        <el-input
          v-model="queryParams.templateId"
          placeholder="请输入模版ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="服务商" prop="provider">
        <el-input
          v-model="queryParams.provider"
          placeholder="请输入服务商"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="通知时间" prop="notifyTime">
        <el-date-picker
          v-model="queryParams.notifyTime"
          clearable
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择通知时间"
        />
      </el-form-item>
      <el-form-item label="重试次数" prop="retryTimes">
        <el-input
          v-model="queryParams.retryTimes"
          placeholder="请输入重试次数"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['notice:history:add']"
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['notice:history:edit']"
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['notice:history:remove']"
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['notice:history:export']"
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
        >导出</el-button>
      </el-col>
      <right-toolbar :show-search.sync="showSearch" @queryTable="getList" />
    </el-row>

    <el-table v-loading="loading" :data="historyList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="id" />
      <el-table-column label="通知配置ID" align="center" prop="notifierId" />
      <el-table-column label="配置名称" align="center" prop="noteName" />
      <el-table-column label="模版ID" align="center" prop="templateId" />
      <el-table-column label="模版内容" align="center" prop="template" />
      <el-table-column label="上下文" align="center" prop="context" />
      <el-table-column label="服务商" align="center" prop="provider" />
      <el-table-column label="通知类型" align="center" prop="notifyType" />
      <el-table-column label="通知时间" align="center" prop="notifyTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.notifyTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="重试次数" align="center" prop="retryTimes" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            v-hasPermi="['notice:history:edit']"
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
          >修改</el-button>
          <el-button
            v-hasPermi="['notice:history:remove']"
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改通知记录对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="通知配置ID" prop="notifierId">
          <el-input v-model="form.notifierId" placeholder="请输入通知配置ID" />
        </el-form-item>
        <el-form-item label="配置名称" prop="noteName">
          <el-input v-model="form.noteName" placeholder="请输入配置名称" />
        </el-form-item>
        <el-form-item label="模版ID" prop="templateId">
          <el-input v-model="form.templateId" placeholder="请输入模版ID" />
        </el-form-item>
        <el-form-item label="模版内容" prop="template">
          <el-input v-model="form.template" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="上下文" prop="context">
          <el-input v-model="form.context" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="服务商" prop="provider">
          <el-input v-model="form.provider" placeholder="请输入服务商" />
        </el-form-item>
        <el-form-item label="通知时间" prop="notifyTime">
          <el-date-picker
            v-model="form.notifyTime"
            clearable
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择通知时间"
          />
        </el-form-item>
        <el-form-item label="重试次数" prop="retryTimes">
          <el-input v-model="form.retryTimes" placeholder="请输入重试次数" />
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
import { listHistory, getHistory, delHistory, addHistory, updateHistory } from '@/api/notice/history'

export default {
  name: 'NoticeHistory',
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
      // 通知记录表格数据
      historyList: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        notifierId: null,
        noteName: null,
        templateId: null,
        template: null,
        context: null,
        provider: null,
        notifyType: null,
        notifyTime: null,
        retryTimes: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        notifierId: [
          { required: true, message: '通知配置ID不能为空', trigger: 'blur' }
        ],
        templateId: [
          { required: true, message: '模版ID不能为空', trigger: 'blur' }
        ],
        provider: [
          { required: true, message: '服务商不能为空', trigger: 'blur' }
        ],
        notifyType: [
          { required: true, message: '通知类型不能为空', trigger: 'change' }
        ]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询通知记录列表 */
    getList() {
      this.loading = true
      listHistory(this.queryParams).then(response => {
        this.historyList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        notifierId: null,
        noteName: null,
        templateId: null,
        template: null,
        context: null,
        provider: null,
        notifyType: null,
        notifyTime: null,
        retryTimes: null
      }
      this.resetForm('form')
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = '添加通知记录'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getHistory(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = '修改通知记录'
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateHistory(this.form).then(response => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addHistory(this.form).then(response => {
              this.$modal.msgSuccess('新增成功')
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$modal.confirm('是否确认删除通知记录编号为"' + ids + '"的数据项？').then(function() {
        return delHistory(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('notice/history/export', {
        ...this.queryParams
      }, `history_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
