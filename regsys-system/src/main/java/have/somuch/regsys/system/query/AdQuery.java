package have.somuch.regsys.system.query;

import have.somuch.regsys.common.common.BaseQuery;
import lombok.Data;

/**
 * 广告查询条件
 */
@Data
public class AdQuery extends BaseQuery {

    /**
     * 广告标题
     */
    private String title;

    /**
     * 广告位ID
     */
    private Integer adSortId;

}
