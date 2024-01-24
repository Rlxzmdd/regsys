package have.somuch.regsys.system.query;

import have.somuch.regsys.common.common.BaseQuery;
import lombok.Data;

/**
 * 用户查询条件
 */
@Data
public class UserQuery extends BaseQuery {

    /**
     * 用户账号
     */
    private String username;

    /**
     * 真实姓名
     */
    private String realname;

    /**
     * 性别：1男 2女 3保密
     */
    private Integer gender;

}
