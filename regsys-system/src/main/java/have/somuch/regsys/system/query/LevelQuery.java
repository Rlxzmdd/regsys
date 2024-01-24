package have.somuch.regsys.system.query;

import have.somuch.regsys.common.common.BaseQuery;
import lombok.Data;

/**
 * 职级查询条件
 */
@Data
public class LevelQuery extends BaseQuery {

    /**
     * 职级名称
     */
    private String name;

}
