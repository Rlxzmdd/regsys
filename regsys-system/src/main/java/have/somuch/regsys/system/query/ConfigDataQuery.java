package have.somuch.regsys.system.query;

import have.somuch.regsys.common.common.BaseQuery;
import lombok.Data;

/**
 * 配置项数据查询条件
 */
@Data
public class ConfigDataQuery extends BaseQuery {

    /**
     * 配置标题
     */
    private String title;

    /**
     * 配置ID
     */
    private Integer configId;

}
