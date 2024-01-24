package have.somuch.regsys.system.query;

import have.somuch.regsys.common.common.BaseQuery;
import lombok.Data;

/**
 * 栏目查询条件
 */
@Data
public class ItemCateQuery extends BaseQuery {

    /**
     * 栏目名称
     */
    private String name;

}
