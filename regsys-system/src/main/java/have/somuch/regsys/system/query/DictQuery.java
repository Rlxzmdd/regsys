package have.somuch.regsys.system.query;

import have.somuch.regsys.common.common.BaseQuery;
import lombok.Data;

/**
 * 字典查询条件
 */
@Data
public class DictQuery extends BaseQuery {

    /**
     * 字典名称
     */
    private String name;

}
