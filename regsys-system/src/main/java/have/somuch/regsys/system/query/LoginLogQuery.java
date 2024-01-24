package have.somuch.regsys.system.query;

import have.somuch.regsys.common.common.BaseQuery;
import lombok.Data;

/**
 * 登录日志查询条件
 */
@Data
public class LoginLogQuery extends BaseQuery {

    /**
     * 登录账号
     */
    private String username;
}
