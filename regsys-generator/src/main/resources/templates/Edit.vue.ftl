<!-- ${tableAnnotation}编辑弹窗 -->
<template>
  <el-dialog
    :title="isUpdate?'修改${tableAnnotation}':'添加${tableAnnotation}'"
    :visible="visible"
    width="460px"
    :destroy-on-close="true"
    :lock-scroll="false"
    @update:visible="updateVisible">
    <el-form
      ref="form"
      :model="form"
      :rules="rules"
      label-width="82px">
<#if model_column?exists>
  <#list model_column as model>
    <#if model.changeColumnName?uncap_first != "createUser" && model.changeColumnName?uncap_first != "createTime" && model.changeColumnName?uncap_first != "updateUser" && model.changeColumnName?uncap_first != "updateTime" && model.changeColumnName?uncap_first != "mark">
      <#if (model.columnType = 'VARCHAR' || model.columnType = 'CHAR' || model.columnType = 'TEXT' || model.columnType = 'MEDIUMTEXT')>
        <#if model.columnImage == true>
        <el-form-item label="${model.columnComment}：">
          <uploadImage :limit="1" :updDir="updDir" v-model="form.${model.changeColumnName?uncap_first}"></uploadImage>
        </el-form-item>
        <#elseif model.columnTextArea == true>
        <el-form-item label="${model.columnComment}:">
          <el-input
            :rows="3"
            clearable
            type="textarea"
            :maxlength="200"
            v-model="form.${model.columnName}"
            placeholder="请输入${model.columnComment}"/>
        </el-form-item>
        <#else>
        <el-form-item
          label="${model.columnComment}:"
          prop="name">
          <el-input
            :maxlength="20"
            v-model="form.${model.columnName}"
            placeholder="请输入${model.columnComment}"
            clearable/>
        </el-form-item>
        </#if>
      </#if>
      <#if (model.columnType = 'DATETIME' || model.columnType = 'DATE' || model.columnType = 'TIME' || model.columnType = 'YEAR' || model.columnType = 'TIMESTAMP') >
        <el-form-item label="${model.columnComment}:">
          <el-date-picker
            type="datetime"
            class="ele-fluid"
            v-model="form.${model.columnName}"
            value-format="yyyy-MM-dd HH:mm:ss"
            placeholder="请选择${model.columnComment}"/>
        </el-form-item>
      </#if>
      <#if (model.columnType = 'TINYINT UNSIGNED' || model.columnType = 'TINYINT')>
        <#if model.columnSwitch == true>
        <el-form-item label="${model.columnCommentName}:" prop="${model.columnName}">
          <el-radio-group
            v-model="form.${model.columnName}">
        <#if model.columnCommentValue?exists>
          <#list model.columnCommentValue?keys as key>
            <el-radio :label="${key}">${model.columnCommentValue[key]}</el-radio>
          </#list>
        </#if>
          </el-radio-group>
        </el-form-item>
        <#else>
        <el-form-item label="${model.columnCommentName}:" prop="${model.columnName}">
          <el-select
            clearable
            class="ele-block"
            v-model="form.${model.columnName}"
            placeholder="请选择${model.columnCommentName}">
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
          <el-select
            clearable
            class="ele-block"
            v-model="form.${model.columnName}"
            placeholder="请选择${model.columnCommentName}">
            <#if model.columnCommentValue?exists>
              <#list model.columnCommentValue?keys as key>
                <el-option label="${model.columnCommentValue[key]}" :value="${key}"/>
              </#list>
            </#if>
          </el-select>
        </el-form-item>
        <#else>
        <el-form-item label="${model.columnComment}:" prop="${model.columnName}">
          <el-input-number
            :min="0"
            v-model="form.${model.columnName}"
            placeholder="请输入${model.columnComment}"
            controls-position="right"
            class="ele-fluid ele-text-left"/>
        </el-form-item>
        </#if>
      </#if>
    </#if>
  </#list>
</#if>
    </el-form>
    <div slot="footer">
      <el-button @click="updateVisible(false)">取消</el-button>
      <el-button
        type="primary"
        @click="save"
        :loading="loading">保存
      </el-button>
    </div>
  </el-dialog>
</template>

<script>
<#if model_column?exists>
<#list model_column as model>
<#if model.columnImage == true>
import uploadImage from '@/components/uploadImage'

</#if>
</#list>
</#if>
export default {
  name: '${entityName}Edit',
  props: {
    // 弹窗是否打开
    visible: Boolean,
    // 修改回显的数据
    data: Object
  },
<#if model_column?exists>
  <#list model_column as model>
    <#if model.columnImage == true>
  components: {uploadImage},
    </#if>
  </#list>
</#if>
  data() {
    return {
      // 表单数据
      form: Object.assign({}, this.data),
      // 表单验证规则
      rules: {
<#if model_column?exists>
  <#list model_column as model>
    <#if model.changeColumnName?uncap_first != "createUser" && model.changeColumnName?uncap_first != "createTime" && model.changeColumnName?uncap_first != "updateUser" && model.changeColumnName?uncap_first != "updateTime" && model.changeColumnName?uncap_first != "mark">
      <#if model.hasColumnCommentValue = true>
        ${model.columnName}: [
          {required: true, message: '请选择${model.columnCommentName}', trigger: 'blur'}
        ],
      <#else>
        ${model.columnName}: [
          {required: true, message: '请输入${model.columnComment}', trigger: 'blur'}
        ],
      </#if>
    </#if>
  </#list>
</#if>
      },
      // 提交状态
      loading: false,
      // 是否是修改
      isUpdate: false,
<#if model_column?exists>
  <#list model_column as model>
    <#if model.columnImage == true>
      // 上传目录
      updDir: '${entityName?lower_case}',
    </#if>
  </#list>
</#if>
    };
  },
  watch: {
    data() {
      if (this.data) {
        this.form = Object.assign({}, this.data);
        this.isUpdate = true;
      } else {
        this.form = {};
        this.isUpdate = false;
      }
    }
  },
  methods: {
    /* 保存编辑 */
    save() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          this.loading = true;
          this.$http[this.isUpdate ? 'put' : 'post'](this.isUpdate ? '/${entityName?lower_case}/edit' : '/${entityName?lower_case}/add', this.form).then(res => {
            this.loading = false;
            if (res.data.code === 0) {
              this.$message.success(res.data.msg);
              if (!this.isUpdate) {
                this.form = {};
              }
              this.updateVisible(false);
              this.$emit('done');
            } else {
              this.$message.error(res.data.msg);
            }
          }).catch(e => {
            this.loading = false;
            this.$message.error(e.message);
          });
        } else {
          return false;
        }
      });
    },
    /* 更新visible */
    updateVisible(value) {
      this.$emit('update:visible', value);
    }
  }
}
</script>

<style scoped>
</style>
