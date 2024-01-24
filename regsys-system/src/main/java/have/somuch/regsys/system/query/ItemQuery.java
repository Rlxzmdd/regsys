package have.somuch.regsys.system.query;

import have.somuch.regsys.common.common.BaseQuery;
import lombok.Data;

/**
 * 站点查询条件
 */
@Data
public class ItemQuery extends BaseQuery {

    /**
     * 站点名称
     */
    private String name;

    /**
     * 站点类型
     */
    private Integer type;

    /**
     * 站点状态
     */
    private Integer status;

}
