package ${packageName}.query;

import have.somuch.regsys.common.common.BaseQuery;
import lombok.Data;

/**
 * <p>
 * ${tableAnnotation}查询条件
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@Data
public class ${entityName}Query extends BaseQuery {

<#if model_column?exists>
    <#list model_column as model>
    <#if model.columnName = 'name' || model.columnName = 'title'>
    /**
     * ${model.columnComment!}
     */
    private String ${model.columnName};

    </#if>
    <#if model.hasColumnCommentValue = true>
    /**
     * ${model.columnComment!}
     */
    private String ${model.columnName};
    </#if>
    </#list>
</#if>
}
