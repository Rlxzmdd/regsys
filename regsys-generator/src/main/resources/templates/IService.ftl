package ${packageName}.service;

import utils.have.somuch.regsys.common.JsonResult;
import ${packageName}.entity.${entityName};
import have.somuch.regsys.common.common.IBaseService;

/**
 * <p>
 * ${tableAnnotation} 服务类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
public interface I${entityName}Service extends IBaseService<${entityName}> {

<#if model_column?exists>
 <#list model_column as model>
  <#if model.columnSwitch == true && model.changeColumnName?uncap_first != 'status'>
    /**
     * 设置${model.columnCommentName}
     *
     * @param entity 实体对象
     * @return
     */
    JsonResult set${model.changeColumnName?cap_first}(${entityName} entity);

  </#if>
 </#list>
</#if>

}