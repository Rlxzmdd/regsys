package ${packageName}.service.impl;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import have.somuch.regsys.common.config.CommonConfig;
import have.somuch.regsys.common.utils.CommonUtils;
import have.somuch.regsys.common.common.BaseQuery;
import have.somuch.regsys.common.common.BaseServiceImpl;
import ${packageName}.constant.${entityName}Constant;
import ${packageName}.entity.${entityName};
import ${packageName}.mapper.${entityName}Mapper;
import ${packageName}.query.${entityName}Query;
import ${packageName}.service.I${entityName}Service;
import have.somuch.regsys.common.utils.StringUtils;
import have.somuch.regsys.system.utils.ShiroUtils;
import ${packageName}.vo.${entityName?lower_case}.${entityName}InfoVo;
import ${packageName}.vo.${entityName?lower_case}.${entityName}ListVo;
import have.somuch.regsys.common.utils.DateUtils;
import have.somuch.regsys.common.utils.JsonResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;

/**
  * <p>
  * ${tableAnnotation} 服务类实现
  * </p>
  *
  * @author ${author}
  * @since ${date}
  */
@Service
public class ${entityName}ServiceImpl extends BaseServiceImpl<${entityName}Mapper, ${entityName}> implements I${entityName}Service {

    @Autowired
    private ${entityName}Mapper ${entityName?uncap_first}Mapper;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        ${entityName}Query ${entityName?uncap_first}Query = (${entityName}Query) query;
        // 查询条件
        QueryWrapper<${entityName}> queryWrapper = new QueryWrapper<>();
<#if model_column?exists>
    <#list model_column as model>
        <#if model.columnName = 'name' || model.columnName = 'title'>
        // ${model.columnComment!}
        if (!StringUtils.isEmpty(${entityName?uncap_first}Query.get${model.columnName?cap_first}())) {
            queryWrapper.like("${model.columnName}", ${entityName?uncap_first}Query.get${model.columnName?cap_first}());
        }
        </#if>
        <#if model.hasColumnCommentValue = true>
        // ${model.columnComment!}
        if (!StringUtils.isEmpty(${entityName?uncap_first}Query.get${model.columnName?cap_first}())) {
            queryWrapper.eq("${model.columnName2}", ${entityName?uncap_first}Query.get${model.columnName?cap_first}());
        }
        </#if>
    </#list>
</#if>
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByDesc("id");

        // 获取数据列表
        IPage<${entityName}> page = new Page<>(${entityName?uncap_first}Query.getPage(), ${entityName?uncap_first}Query.getLimit());
        IPage<${entityName}> pageData = ${entityName?uncap_first}Mapper.selectPage(page, queryWrapper);
        pageData.convert(x -> {
            ${entityName}ListVo ${entityName?uncap_first}ListVo = Convert.convert(${entityName}ListVo.class, x);
<#if model_column?exists>
    <#list model_column as model>
        <#if model.hasColumnCommentValue = true>
            // ${model.columnCommentName}描述
            <#if model.columnNumberValue = true>
            if (${entityName?uncap_first}ListVo.get${model.changeColumnName}() != null && ${entityName?uncap_first}ListVo.get${model.changeColumnName}() > 0) {
                ${entityName?uncap_first}ListVo.set${model.changeColumnName}Name(${entityName}Constant.${entityName?upper_case}_${model.changeColumnName?upper_case}_LIST.get(${entityName?uncap_first}ListVo.get${model.changeColumnName}()));
            }
            <#else>
            if (!StringUtils.isEmpty(${entityName?uncap_first}ListVo.get${model.changeColumnName}())) {
                ${entityName?uncap_first}ListVo.set${model.changeColumnName}Name(${entityName}Constant.${entityName?upper_case}_${model.changeColumnName?upper_case}_LIST.get(${entityName?uncap_first}ListVo.get${model.changeColumnName}()));
            }
            </#if>
        </#if>
        <#if model.columnImage == true>
            // ${model.columnComment}地址
            if (!StringUtils.isEmpty(${entityName?uncap_first}ListVo.get${model.changeColumnName}())) {
                ${entityName?uncap_first}ListVo.set${model.changeColumnName}(CommonUtils.getImageURL(${entityName?uncap_first}ListVo.get${model.changeColumnName}()));
            }
        </#if>
    </#list>
</#if>
            return ${entityName?uncap_first}ListVo;
        });
        return JsonResult.success(pageData);
    }

    /**
     * 获取详情Vo
     *
     * @param id 记录ID
     * @return
     */
    @Override
    public Object getInfo(Serializable id) {
        ${entityName} entity = (${entityName}) super.getInfo(id);
        // 返回视图Vo
        ${entityName}InfoVo ${entityName?uncap_first}InfoVo = new ${entityName}InfoVo();
        // 拷贝属性
        BeanUtils.copyProperties(entity, ${entityName?uncap_first}InfoVo);
<#if model_column?exists>
    <#list model_column as model>
        <#if model.columnImage == true>
        // ${model.columnComment}
        if (!StringUtils.isEmpty(${entityName?uncap_first}InfoVo.get${model.changeColumnName}())) {
            ${entityName?uncap_first}InfoVo.set${model.changeColumnName}(CommonUtils.getImageURL(${entityName?uncap_first}InfoVo.get${model.changeColumnName}()));
        }
        </#if>
    </#list>
</#if>
        return ${entityName?uncap_first}InfoVo;
    }

    /**
     * 添加、更新记录
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult edit(${entityName} entity) {
<#if model_column?exists>
    <#list model_column as model>
        <#if model.columnImage == true>
        // ${model.columnComment}
        if (entity.get${model.changeColumnName}().contains(CommonConfig.imageURL)) {
            entity.set${model.changeColumnName}(entity.get${model.changeColumnName}().replaceAll(CommonConfig.imageURL, ""));
        }
        </#if>
    </#list>
</#if>
        if (StringUtils.isNotNull(entity.getId()) && entity.getId() > 0) {
    <#if model_column?exists>
        <#list model_column as model>
            <#if model.changeColumnName?uncap_first = 'updateUser'>
            entity.setUpdateUser(ShiroUtils.getUserId());
            </#if>
            <#if model.changeColumnName?uncap_first = 'updateTime'>
            entity.setUpdateTime(DateUtils.now());
            </#if>
        </#list>
    </#if>
        } else {
    <#if model_column?exists>
        <#list model_column as model>
            <#if model.changeColumnName?uncap_first = 'createUser'>
            entity.setCreateUser(ShiroUtils.getUserId());
            </#if>
            <#if model.changeColumnName?uncap_first = 'createTime'>
            entity.setCreateTime(DateUtils.now());
            </#if>
        </#list>
    </#if>
        }
        return super.edit(entity);
    }

    /**
     * 删除记录
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult delete(${entityName} entity) {
<#if model_column?exists>
    <#list model_column as model>
        <#if model.changeColumnName?uncap_first = 'updateUser'>
        entity.setUpdateUser(1);
        </#if>
        <#if model.changeColumnName?uncap_first = 'updateTime'>
        entity.setUpdateTime(DateUtils.now());
        </#if>
    </#list>
</#if>
        entity.setMark(0);
        return super.delete(entity);
    }

<#if model_column?exists>
    <#list model_column as model>
    <#if model.columnSwitch == true>
    /**
     * 设置${model.columnCommentName}
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult set${model.changeColumnName?cap_first}(${entityName} entity) {
        if (entity.getId() == null || entity.getId() <= 0) {
            return JsonResult.error("记录ID不能为空");
        }
        if (entity.get${model.changeColumnName?cap_first}() == null) {
            return JsonResult.error("记录${model.columnCommentName}不能为空");
        }
        <#if model.columnName == "status">
        return super.set${model.changeColumnName?cap_first}(entity);
        <#else>
        entity.setUpdateUser(ShiroUtils.getUserId());
        entity.setUpdateTime(DateUtils.now());
        boolean result = this.updateById(entity);
        if (!result) {
            return JsonResult.error();
        }
        return JsonResult.success();
        </#if>
    }

    </#if>
    </#list>
</#if>
}