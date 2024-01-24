package have.somuch.regsys.system.query;

import have.somuch.regsys.common.common.BaseQuery;
import lombok.Data;

/**
 * 配置查询条件
 */
@Data
public class ConfigQuery extends BaseQuery {

    /**
     * 分组名称
     */
    private String name;

}
