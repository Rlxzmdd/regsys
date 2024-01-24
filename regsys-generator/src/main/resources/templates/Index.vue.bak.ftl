<template>
  <div class="ele-body">
    <el-card shadow="never">
      <!-- 搜索表单 -->
      <el-form :model="table.where" label-width="77px" class="ele-form-search"
               @keyup.enter.native="$refs.table.reload()" @submit.native.prevent>
        <el-row :gutter="15">
    <#if model_column?exists>
      <#list model_column as model>
        <#if model.columnName = 'name' || model.columnName = 'title'>

          <el-col :md="4" :sm="12">
            <el-form-item label="${model.columnComment}:">
              <el-input v-model="table.where.${model.columnName}" placeholder="请输入${model.columnComment}" clearable/>
            </el-form-item>
          </el-col>
        </#if>
        <#if model.hasColumnCommentValue = true>
          <!-- ${model.columnCommentName}下拉单选 -->
          <el-col :md="4" :sm="12">
            <el-form-item label="${model.columnCommentName}:">
              <el-select v-model="table.where.${model.columnName}" placeholder="请选择${model.columnCommentName}" clearable class="ele-fluid">
                <el-option label="全部" :value="''"/>
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
          <el-col :md="6" :sm="12">
            <div class="ele-form-actions">
              <el-button type="primary" @click="$refs.table.reload()" icon="el-icon-search" class="ele-btn-icon">查询
              </el-button>
              <el-button @click="(table.where={})&&$refs.table.reload()">重置</el-button>
            </div>
          </el-col>
        </el-row>
      </el-form>
      <!-- 操作按钮 -->
      <div class="ele-table-tool ele-table-tool-default">
        <el-button @click="showEdit=true" type="primary" icon="el-icon-plus" class="ele-btn-icon" size="small" v-if="permission.includes('sys:${entityName?lower_case}:add')">添加
        </el-button>
        <el-button @click="remove()" type="danger" icon="el-icon-delete" class="ele-btn-icon" size="small" v-if="permission.includes('sys:${entityName?lower_case}:dall')">批量删除
        </el-button>
      </div>
      <!-- 数据表格 -->
      <ele-data-table ref="table" :config="table" :choose.sync="choose" height="calc(100vh - 315px)" highlight-current-row>
        <template slot-scope="{index}">
          <el-table-column type="selection" width="45" align="center" fixed="left"/>
          <el-table-column type="index" :index="index" label="编号" width="60" align="center" fixed="left" show-overflow-tooltip/>
    <#if model_column?exists>
      <#list model_column as model>
        <#if model.changeColumnName?uncap_first != 'createUser' && model.changeColumnName?uncap_first != 'updateUser' && model.changeColumnName?uncap_first != 'mark'>
        <#if (model.columnType = 'DATETIME' || model.columnType = 'DATE' || model.columnType = 'TIME' || model.columnType = 'YEAR' || model.columnType = 'TIMESTAMP')>
          <el-table-column label="${model.columnComment}" align="center" show-overflow-tooltip min-width="160">
            <template slot-scope="{row}">{{ row.${model.changeColumnName?uncap_first} | toDateString }}</template>
          </el-table-column>
        <#elseif model.hasColumnCommentValue = true>
          <#if model.columnSwitch == true>
            <el-table-column prop="${model.columnName}" label="${model.columnCommentName}" align="center" :resizable="false" min-width="120">
              <template slot-scope="{row}">
                <el-switch v-model="row.${model.columnName}" @change="set${model.changeColumnName?cap_first}(row)" :active-value="1" :inactive-value="2"/>
              </template>
            </el-table-column>
          <#else>
            <el-table-column prop="${model.changeColumnName?uncap_first}" label="${model.columnCommentName}" align="center" min-width="100">
              <template slot-scope="{row}">
                <ele-dot :type="['success', 'danger', 'primary', 'warning', 'info'][row.${model.changeColumnName?uncap_first}-1]" :ripple="row.${model.changeColumnName?uncap_first}===0"
                         :text="[${model.columnValue}][row.${model.changeColumnName?uncap_first}-1]"/>
              </template>
            </el-table-column>
          </#if>
        <#elseif model.columnImage == true>
          <el-table-column label="${model.columnComment}" min-width="60" align="center">
            <template slot-scope="{row}">
              <el-avatar shape="square" :size="35" :src="row.${model.changeColumnName?uncap_first}"/>
            </template>
          </el-table-column>
        <#else>
          <el-table-column prop="${model.changeColumnName?uncap_first}" label="${model.columnComment}" align="center" show-overflow-tooltip min-width="200"/>
        </#if>
        </#if>
      </#list>
    </#if>
          <el-table-column label="操作" width="130px" align="center" :resizable="false"  fixed="right">
            <template slot-scope="{row}">
              <el-link @click="edit(row)" icon="el-icon-edit" type="primary" :underline="false" v-if="permission.includes('sys:${entityName?lower_case}:edit')">修改</el-link>
              <el-popconfirm title="确定要删除此${tableAnnotation}吗？" @confirm="remove(row)" class="ele-action">
                <el-link slot="reference" icon="el-icon-delete" type="danger" :underline="false" v-if="permission.includes('sys:${entityName?lower_case}:delete')">删除</el-link>
              </el-popconfirm>
            </template>
          </el-table-column>
        </template>
      </ele-data-table>
    </el-card>
    <!-- 编辑弹窗 -->
    <el-dialog :title="editForm.id?'修改${tableAnnotation}':'修改${tableAnnotation}'" :visible.sync="showEdit" width="400px"
               @closed="editForm={}" :destroy-on-close="true" :lock-scroll="false">
      <el-form :model="editForm" ref="editForm" :rules="editRules" label-width="82px">
<#if model_column?exists>
  <#list model_column as model>
    <#if model.changeColumnName?uncap_first != "createUser" && model.changeColumnName?uncap_first != "createTime" && model.changeColumnName?uncap_first != "updateUser" && model.changeColumnName?uncap_first != "updateTime" && model.changeColumnName?uncap_first != "mark">
      <#if (model.columnType = 'VARCHAR' || model.columnType = 'CHAR' || model.columnType = 'TEXT' || model.columnType = 'MEDIUMTEXT')>
        <#if model.columnImage == true>
        <el-form-item label="${model.columnComment}:">
          <uploadImage :limit="1" :updDir="updDir" v-model="editForm.${model.changeColumnName?uncap_first}"></uploadImage>
        </el-form-item>
        <#elseif model.columnTextArea == true>
        <el-form-item label="${model.columnComment}:">
          <el-input v-model="editForm.${model.columnName}" placeholder="请输入${model.columnComment}" :rows="3" type="textarea"/>
        </el-form-item>
        <#else>
        <el-form-item label="${model.columnComment}:" prop="${model.columnName}">
          <el-input v-model="editForm.${model.columnName}" placeholder="请输入${model.columnComment}" clearable/>
        </el-form-item>
        </#if>
      </#if>
      <#if (model.columnType = 'DATETIME' || model.columnType = 'DATE' || model.columnType = 'TIME' || model.columnType = 'YEAR' || model.columnType = 'TIMESTAMP') >
        <el-form-item label="${model.columnComment}:" prop="${model.columnName}">
          <el-date-picker
                  v-model="editForm.${model.columnName}"
                  type="datetime"
                  placeholder="选择${model.columnComment}"
                  size="small"
                  value-format="yyyy-MM-dd HH:mm:ss"
                  format="yyyy-MM-dd HH:mm:ss">
          </el-date-picker>
        </el-form-item>
      </#if>
      <#if (model.columnType = 'TINYINT UNSIGNED' || model.columnType = 'TINYINT')>
        <#if model.columnSwitch == true>
        <el-form-item label="${model.columnCommentName}:">
          <el-radio-group v-model="editForm.${model.columnName}">
          <#if model.columnCommentValue?exists>
            <#list model.columnCommentValue?keys as key>
            <el-radio :label="${key}">${model.columnCommentValue[key]}</el-radio>
            </#list>
          </#if>
          </el-radio-group>
        </el-form-item>
        <#else>
          <el-form-item label="${model.columnCommentName}:" prop="${model.columnName}">
            <el-select v-model="editForm.${model.columnName}" placeholder="请选择${model.columnCommentName}" class="ele-block" clearable>
              <#if model.columnCommentValue?exists>
                <#list model.columnCommentValue?keys as key>
                  <el-option label="${model.columnCommentValue[key]}" :value="${key}"/>
                </#list>
              </#if>
            </el-select>
          </el-form-item>
        </#if>
      </#if>
      <#if (model.columnType = 'INT UNSIGNED' || model.columnType = 'INT' || model.columnType = 'SMALLINT UNSIGNED' || model.columnType = 'SMALLINT' || model.columnType = 'BIGINT UNSIGNED' || model.columnType = 'BIGINT' || model.columnType = 'MEDIUMINT UNSIGNED' || model.columnType = 'MEDIUMINT')>
      <#if model.hasColumnCommentValue = true>
        <el-form-item label="${model.columnCommentName}:" prop="${model.columnName}">
          <el-select v-model="editForm.${model.columnName}" placeholder="请选择${model.columnCommentName}" class="ele-block" clearable>
            <#if model.columnCommentValue?exists>
              <#list model.columnCommentValue?keys as key>
              <el-option label="${model.columnCommentValue[key]}" :value="${key}"/>
            </#list>
            </#if>
          </el-select>
        </el-form-item>
      <#else>
        <el-form-item label="${model.columnComment}:" prop="${model.columnName}">
          <el-input-number v-model="editForm.${model.columnName}" controls-position="right" :min="0"
                           placeholder="请输入${model.columnComment}" class="ele-fluid ele-text-left"/>
        </el-form-item>
      </#if>
      </#if>
    </#if>
  </#list>
</#if>
      </el-form>
      <div slot="footer">
        <el-button @click="showEdit=false">取消</el-button>
        <el-button type="primary" @click="save">保存</el-button>
      </div>
    </el-dialog>
  </div>
</template>


<script>
  import { mapGetters } from "vuex";
<#if model_column?exists>
  <#list model_column as model>
  <#if model.columnImage == true>
  import uploadImage from '@/components/uploadImage'
  </#if>
  </#list>
</#if>
  export default {
    name: "Sys${entityName}",
    data() {
      return {
        table: {url: '/${entityName?lower_case}/index', where: {}},  // 表格配置
        choose: [],  // 表格选中数据
        showEdit: false,  // 是否显示表单弹窗
        editForm: {},  // 表单数据
        editRules: {  // 表单验证规则

        },
    <#if model_column?exists>
      <#list model_column as model>
        <#if model.columnImage == true>
        updDir: '${entityName?lower_case}',
        </#if>
      </#list>
    </#if>
      }
    },
    computed: {
      ...mapGetters(["permission"]),
    },
<#if model_column?exists>
  <#list model_column as model>
    <#if model.columnImage == true>
    components: {uploadImage},
    </#if>
  </#list>
</#if>
    mounted() {
    },
    methods: {
      /* 显示编辑 */
      edit(row) {
        this.$http.get('/${entityName?lower_case}/info/' + row.id).then(res => {
          if (res.data.code === 0) {
            this.editForm = res.data.data;
            this.showEdit = true;
          } else {
            this.$message.error(res.data.msg);
          }
        }).catch(e => {
          this.$message.error(e.message);
        });
      },
      /* 保存编辑 */
      save() {
        this.$refs['editForm'].validate((valid) => {
          if (valid) {
            const loading = this.$loading({lock: true});
            this.$http[this.editForm.id ? 'put' : 'post'](this.editForm.id ? '/${entityName?lower_case}/edit' : '/${entityName?lower_case}/add', this.editForm).then(res => {
              loading.close();
              if (res.data.code === 0) {
                this.showEdit = false;
                this.$message({type: 'success', message: res.data.msg});
                this.$refs.table.reload();
              } else {
                this.$message.error(res.data.msg);
              }
            }).catch(e => {
              loading.close();
              this.$message.error(e.message);
            });
          } else {
            return false;
          }
        });
      },
      /* 删除 */
      remove(row) {
        if (!row) {  // 批量删除
          if (this.choose.length === 0) return this.$message.error('请至少选择一条数据');
          let ids = this.choose.map(d => d.id);
          this.$confirm('确定要删除选中的${tableAnnotation}吗?', '提示', {type: 'warning'}).then(() => {
            const loading = this.$loading({lock: true});
            this.$http.delete('/${entityName?lower_case}/delete/' + ids).then(res => {
              loading.close();
              if (res.data.code === 0) {
                this.$message({type: 'success', message: res.data.msg});
                this.$refs.table.reload();
              } else {
                this.$message.error(res.data.msg);
              }
            }).catch(e => {
              loading.close();
              this.$message.error(e.message);
            });
          }).catch(() => 0);
        } else {  // 单个删除
          const loading = this.$loading({lock: true});
          this.$http.delete('/${entityName?lower_case}/delete/' + [row.id]).then(res => {
            loading.close();
            if (res.data.code === 0) {
              this.$message({type: 'success', message: res.data.msg});
              this.$refs.table.reload();
            } else {
              this.$message.error(res.data.msg);
            }
          }).catch(e => {
            loading.close();
            this.$message.error(e.message);
          });
        }
      },
      <#if model_column?exists>
      <#list model_column as model>
      <#if model.columnSwitch == true>
      /* 更改状态 */
      set${model.changeColumnName?cap_first}(row) {
        const loading = this.$loading({lock: true});
        //let params = new FormData();
        // params.append('status', row.status);
        let params = {"id":row.id, '${model.changeColumnName?uncap_first}': row.${model.changeColumnName?uncap_first}};
        this.$http.put('/${entityName?lower_case}/set${model.changeColumnName?cap_first}', params).then(res => {
          loading.close();
          if (res.data.code === 0) {
            this.$message({type: 'success', message: res.data.msg});
          } else {
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
  .ele-block >>> .el-upload, .ele-block >>> .el-upload-dragger {
    width: 100%;
  }
</style>