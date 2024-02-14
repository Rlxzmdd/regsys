package have.somuch.regsys.api.user.query;

import have.somuch.regsys.common.common.BaseQuery;
import lombok.Data;

/**
 * <p>
 * 用户账号信息表查询条件
 * </p>
 *
 * @author isZhous
 * @since 2024-01-29
 */
@Data
public class UserAccountQuery extends BaseQuery {

    /**
     * 绑定类型: 0学生 1教师
     */
    private String bindType;
}
