package have.somuch.regsys.system.query;

import have.somuch.regsys.common.common.BaseQuery;
import lombok.Data;

/**
 * 部门查询条件
 */
@Data
public class DeptQuery extends BaseQuery {

    /**
     * 部门名称
     */
    private String name;

}
