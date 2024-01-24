package have.somuch.regsys.admin.query;

import have.somuch.regsys.common.common.BaseQuery;
import lombok.Data;

/**
 * 会员查询条件
 */
@Data
public class MemberQuery extends BaseQuery {

    /**
     * 会员账号
     */
    private String username;

    /**
     * 性别：1男 2女 3保密
     */
    private Integer gender;

}
