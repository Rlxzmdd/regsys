package have.somuch.regsys.system.query;

import have.somuch.regsys.common.common.BaseQuery;
import lombok.Data;

/**
 * 广告位查询条件
 */
@Data
public class AdSortQuery extends BaseQuery {

    /**
     * 广告位名称
     */
    private String name;

}
