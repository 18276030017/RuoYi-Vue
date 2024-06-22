<template>
  <div class="app-container">
    <el-form v-show="showSearch" ref="queryForm" :model="queryParams" size="small" :inline="true" label-width="68px">
      <el-form-item label="告警ID" prop="alarmId">
        <el-input
          v-model="queryParams.alarmId"
          placeholder="请输入告警ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="告警级别" prop="level">
        <el-input
          v-model="queryParams.level"
          placeholder="请输入告警级别"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="告警配置名称" prop="alarmName">
        <el-input
          v-model="queryParams.alarmName"
          placeholder="请输入告警配置名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="告警目标名称" prop="sceneName">
        <el-input
          v-model="queryParams.sceneName"
          placeholder="请输入告警目标名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="告警时间" prop="alarmTime">
        <el-input
          v-model="queryParams.alarmTime"
          placeholder="请输入告警时间"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="说明" prop="description">
        <el-input
          v-model="queryParams.description"
          placeholder="请输入说明"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="处理时间" prop="handleTime">
        <el-input
          v-model="queryParams.handleTime"
          placeholder="请输入处理时间"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="处理者(只读)" prop="userName">
        <el-input
          v-model="queryParams.userName"
          placeholder="请输入处理者(只读)"
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
          v-hasPermi="['alarm:history:add']"
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['alarm:history:edit']"
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
          v-hasPermi="['alarm:history:remove']"
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
          v-hasPermi="['alarm:history:export']"
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
      <el-table-column label="告警ID" align="center" prop="alarmId" />
      <el-table-column label="告警级别" align="center" prop="level" />
      <el-table-column label="告警配置名称" align="center" prop="alarmName" />
      <el-table-column label="告警目标名称" align="center" prop="sceneName" />
      <el-table-column label="告警时间" align="center" prop="alarmTime" />
      <el-table-column label="告警处理类型" align="center" prop="handleType" />
      <el-table-column label="说明" align="center" prop="description" />
      <el-table-column label="处理时间" align="center" prop="handleTime" />
      <el-table-column label="处理者(只读)" align="center" prop="userName" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            v-hasPermi="['alarm:history:edit']"
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
          >修改</el-button>
          <el-button
            v-hasPermi="['alarm:history:remove']"
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

    <!-- 添加或修改告警处理记录对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="告警ID" prop="alarmId">
          <el-input v-model="form.alarmId" placeholder="请输入告警ID" />
        </el-form-item>
        <el-form-item label="告警级别" prop="level">
          <el-input v-model="form.level" placeholder="请输入告警级别" />
        </el-form-item>
        <el-form-item label="告警配置名称" prop="alarmName">
          <el-input v-model="form.alarmName" placeholder="请输入告警配置名称" />
        </el-form-item>
        <el-form-item label="告警目标名称" prop="sceneName">
          <el-input v-model="form.sceneName" placeholder="请输入告警目标名称" />
        </el-form-item>
        <el-form-item label="告警时间" prop="alarmTime">
          <el-input v-model="form.alarmTime" placeholder="请输入告警时间" />
        </el-form-item>
        <el-form-item label="说明" prop="description">
          <el-input v-model="form.description" placeholder="请输入说明" />
        </el-form-item>
        <el-form-item label="处理时间" prop="handleTime">
          <el-input v-model="form.handleTime" placeholder="请输入处理时间" />
        </el-form-item>
        <el-form-item label="处理者(只读)" prop="userName">
          <el-input v-model="form.userName" placeholder="请输入处理者(只读)" />
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
import { listHistory, getHistory, delHistory, addHistory, updateHistory } from '@/api/alarm/history'

export default {
  name: 'AlarmHistory',
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
      // 告警处理记录表格数据
      historyList: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        alarmId: null,
        level: null,
        alarmName: null,
        sceneName: null,
        alarmTime: null,
        handleType: null,
        description: null,
        handleTime: null,
        userName: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        alarmId: [
          { required: true, message: '告警ID不能为空', trigger: 'blur' }
        ],
        alarmName: [
          { required: true, message: '告警配置名称不能为空', trigger: 'blur' }
        ],
        sceneName: [
          { required: true, message: '告警目标名称不能为空', trigger: 'blur' }
        ],
        handleType: [
          { required: true, message: '告警处理类型不能为空', trigger: 'change' }
        ],
        description: [
          { required: true, message: '说明不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询告警处理记录列表 */
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
        alarmId: null,
        level: null,
        alarmName: null,
        sceneName: null,
        alarmTime: null,
        handleType: null,
        description: null,
        handleTime: null,
        userName: null
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
      this.title = '添加告警处理记录'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getHistory(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = '修改告警处理记录'
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
      this.$modal.confirm('是否确认删除告警处理记录编号为"' + ids + '"的数据项？').then(function() {
        return delHistory(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('alarm/history/export', {
        ...this.queryParams
      }, `history_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
