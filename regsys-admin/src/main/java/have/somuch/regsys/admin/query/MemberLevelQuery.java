package have.somuch.regsys.admin.query;

import have.somuch.regsys.common.common.BaseQuery;
import lombok.Data;

/**
 * 会员等级查询条件
 */
@Data
public class MemberLevelQuery extends BaseQuery {

    /**
     * 等级名称
     */
    private String name;

}
