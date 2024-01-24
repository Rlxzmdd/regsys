<template>
  <div class="ele-body">
    <el-card shadow="never">
      <!-- 搜索表单 -->
      <el-form
        :model="where"
        label-width="77px"
        class="ele-form-search"
        @keyup.enter.native="reload"
        @submit.native.prevent>
        <el-row :gutter="15">
    <#if model_column?exists>
      <#list model_column as model>
        <#if model.columnName = 'name' || model.columnName = 'title'>

          <el-col :lg="6" :md="12">
            <el-form-item label="${model.columnComment}:">
              <el-input
                clearable
                v-model="where.${model.columnName}"
                placeholder="请输入${model.columnComment}"/>
            </el-form-item>
          </el-col>
        </#if>
        <#if model.hasColumnCommentValue = true>
          <!-- ${model.columnCommentName}下拉单选 -->
          <el-col :lg="6" :md="12">
            <el-form-item label="${model.columnCommentName}:">
              <el-select
                clearable
                v-model="where.${model.columnName}"
                placeholder="请选择${model.columnCommentName}"
                class="ele-fluid">
                <el-option label="全部" value=""/>
            <#if model.columnCommentValue?exists>
              <#list model.columnCommentValue?keys as key>
                <el-option label="${model.columnCommentValue[key]}" :value="${key}"/>
              </#list>
            </#if>
              </el-select>
            </el-form-item>
          </el-col>
        </#if>
      </#list>
    </#if>
          <el-col :lg="6" :md="12">
            <div class="ele-form-actions">
              <el-button
                type="primary"
                icon="el-icon-search"
                class="ele-btn-icon"
                @click="reload">查询
              </el-button>
              <el-button @click="reset">重置</el-button>
            </div>
          </el-col>
        </el-row>
      </el-form>
      <!-- 数据表格 -->
      <ele-pro-table
        ref="table"
        :where="where"
        :datasource="url"
        :columns="columns"
        :selection.sync="selection"
        height="calc(100vh - 315px)">
        <!-- 表头工具栏 -->
        <template slot="toolbar">
          <el-button
            size="small"
            type="primary"
            icon="el-icon-plus"
            class="ele-btn-icon"
            @click="openEdit(null)"
            v-if="permission.includes('sys:${entityName?lower_case}:add')">添加
          </el-button>
          <el-button
            size="small"
            type="danger"
            icon="el-icon-delete"
            class="ele-btn-icon"
            @click="removeBatch"
            v-if="permission.includes('sys:${entityName?lower_case}:dall')">删除
          </el-button>
        </template>
        <!-- 操作列 -->
        <template slot="action" slot-scope="{row}">
          <el-link
            type="primary"
            :underline="false"
            icon="el-icon-edit"
            @click="openEdit(row)"
            v-if="permission.includes('sys:${entityName?lower_case}:edit')">修改
          </el-link>
          <el-popconfirm
            class="ele-action"
            title="确定要删除此${tableAnnotation}吗？"
            @confirm="remove(row)">
            <el-link
              type="danger"
              slot="reference"
              :underline="false"
              icon="el-icon-delete"
              v-if="permission.includes('sys:${entityName?lower_case}:delete')">删除
            </el-link>
          </el-popconfirm>
        </template>
    <#if model_column?exists>
      <#list model_column as model>
        <#if model.columnImage == true>
        <!-- ${model.columnComment}列 -->
        <template slot="${model.columnName}" slot-scope="{row}">
          <el-avatar shape="square" :size="25" :src="row.${model.columnName}"/>
        </template>
        <#elseif model.columnSwitch == true>
        <!-- ${model.columnCommentName}列 -->
        <template slot="${model.columnName}" slot-scope="{row}">
          <el-switch
            v-model="row.${model.columnName}"
            @change="edit${model.changeColumnName?cap_first}(row)"
            :active-value="1"
            :inactive-value="2"/>
        </template>
        <#elseif model.hasColumnCommentValue = true>
        <!-- 使${model.columnCommentName}列 -->
        <template slot="${model.columnName}" slot-scope="{row}">
      <#if model.columnCommentValue?exists>
        <#list model.columnCommentValue?keys as key>
          <el-tag v-if="row.${model.columnName} === ${key}" type="${model.columnValueStyleList[key]}" size="small">${model.columnCommentValue[key]}</el-tag>
        </#list>
      </#if>
        </template>
        </#if>
      </#list>
    </#if>
      </ele-pro-table>
    </el-card>
    <!-- 编辑弹窗 -->
    <${entityName?lower_case}-edit
      :data="current"
      :visible.sync="showEdit"
      @done="reload"/>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import ${entityName}Edit from './${entityName?lower_case}-edit';

export default {
  name: 'Exam${entityName}',
  components: {${entityName}Edit},
  computed: {
    ...mapGetters(["permission"]),
  },
  data() {
    return {
      // 表格数据接口
      url: '/${entityName?lower_case}/index',
      // 表格列配置
      columns: [
        {
          columnKey: 'selection',
          type: 'selection',
          width: 45,
          align: 'center',
          fixed: "left"
        },
        {
          prop: 'id',
          label: 'ID',
          width: 60,
          align: 'center',
          showOverflowTooltip: true,
          fixed: "left"
        },
<#if model_column?exists>
  <#list model_column as model>
    <#if model.changeColumnName?uncap_first != "createUser" && model.changeColumnName?uncap_first != "createTime" && model.changeColumnName?uncap_first != "updateUser" && model.changeColumnName?uncap_first != "updateTime" && model.changeColumnName?uncap_first != "mark">
      <#if (model.columnType = 'DATETIME' || model.columnType = 'DATE' || model.columnType = 'TIME' || model.columnType = 'YEAR' || model.columnType = 'TIMESTAMP')>
        {
          prop: '${model.changeColumnName?uncap_first}',
          label: '${model.columnComment}',
          sortable: 'custom',
          showOverflowTooltip: true,
          minWidth: 160,
          align: 'center',
          formatter: (row, column, cellValue) => {
            return this.$util.toDateString(cellValue);
          }
        },
      <#elseif model.columnSwitch == true>
        {
          prop: '${model.columnName}',
          label: '${model.columnCommentName}',
          align: 'center',
          showOverflowTooltip: true,
          minWidth: 100,
          slot: '${model.columnName}',
        },
      <#elseif model.hasColumnCommentValue = true>
        {
          prop: '${model.changeColumnName?uncap_first}',
          label: '${model.columnCommentName}',
          align: 'center',
          showOverflowTooltip: true,
          minWidth: 100,
          slot: '${model.changeColumnName?uncap_first}',
        },
      <#elseif model.columnImage == true>
        {
          columnKey: '${model.changeColumnName?uncap_first}',
          label: '${model.columnComment}',
          align: 'center',
          showOverflowTooltip: true,
          minWidth: 100,
          slot: '${model.changeColumnName?uncap_first}'
        },
      <#else>
        {
          prop: '${model.changeColumnName?uncap_first}',
          label: '${model.columnComment}',
          showOverflowTooltip: true,
          minWidth: 100,
          align: 'center'
        },
      </#if>
    </#if>
  </#list>
</#if>
        {
          prop: 'createTime',
          label: '创建时间',
          sortable: 'custom',
          showOverflowTooltip: true,
          minWidth: 160,
          align: 'center',
          formatter: (row, column, cellValue) => {
            return this.$util.toDateString(cellValue);
          }
        },
        {
          prop: 'updateTime',
          label: '更新时间',
          sortable: 'custom',
          showOverflowTooltip: true,
          minWidth: 160,
          align: 'center',
          formatter: (row, column, cellValue) => {
            return this.$util.toDateString(cellValue);
          }
        },
        {
          columnKey: 'action',
          label: '操作',
          width: 150,
          align: 'center',
          resizable: false,
          slot: 'action',
          fixed: "right"
        }
      ],
      // 表格搜索条件
      where: {},
      // 表格选中数据
      selection: [],
      // 当前编辑数据
      current: null,
      // 是否显示编辑弹窗
      showEdit: false,
    };
  },
  methods: {
    /* 刷新表格 */
    reload() {
      this.$refs.table.reload({where: this.where});
    },
    /* 重置搜索 */
    reset() {
      this.where = {};
      this.reload();
    },
    /* 显示编辑 */
    openEdit(row) {
      this.current = row;
      this.showEdit = true;
    },
    /* 删除 */
    remove(row) {
      const loading = this.$loading({lock: true});
      this.$http.delete('/${entityName?lower_case}/delete', {id: row.id}).then(res => {
        loading.close();
        if (res.data.code === 0) {
          this.$message.success(res.data.msg);
          this.reload();
        } else {
          this.$message.error(res.data.msg);
        }
      }).catch(e => {
        loading.close();
        this.$message.error(e.message);
      });
    },
    /* 批量删除 */
    removeBatch() {
      if (!this.selection.length) {
        this.$message.error('请至少选择一条数据');
        return;
      }
      this.$confirm('确定要删除选中的${tableAnnotation}吗?', '提示', {
        type: 'warning'
      }).then(() => {
        const loading = this.$loading({lock: true});
        this.$http.delete('/${entityName?lower_case}/delete', {id: this.selection.map(d => d.id)}).then(res => {
          loading.close();
          if (res.data.code === 0) {
            this.$message.success(res.data.msg);
            this.reload();
          } else {
            this.$message.error(res.data.msg);
          }
        }).catch(e => {
          loading.close();
          this.$message.error(e.message);
        });
      }).catch(() => {
      });
    },
<#if model_column?exists>
  <#list model_column as model>
    <#if model.columnSwitch == true>
    /* 更改状态 */
    edit${model.changeColumnName?cap_first}(row) {
      const loading = this.$loading({lock: true});
      let params = Object.assign({
        "id": row.id,
        "${model.changeColumnName?uncap_first}": row.${model.changeColumnName?uncap_first}
      })
      this.$http.put('/${entityName?lower_case}/set${model.changeColumnName?cap_first}', params).then(res => {
        loading.close();
        if (res.data.code === 0) {
          this.$message.success(res.data.msg);
        } else {
          row.status = !row.status ? 1 : 2;
          this.$message.error(res.data.msg);
        }
      }).catch(e => {
        loading.close();
        this.$message.error(e.message);
      });
    },
    </#if>
  </#list>
</#if>
  }
}
</script>

<style scoped>
</style>
