package have.somuch.regsys.system.query;

import have.somuch.regsys.common.common.BaseQuery;
import lombok.Data;

/**
 * 城市查询条件
 */
@Data
public class CityQuery extends BaseQuery {

    /**
     * 城市名称
     */
    private String name;

    /**
     * 父级ID
     */
    private Integer pid;

}
