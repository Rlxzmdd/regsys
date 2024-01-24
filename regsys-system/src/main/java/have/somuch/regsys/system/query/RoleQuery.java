package have.somuch.regsys.system.query;

import have.somuch.regsys.common.common.BaseQuery;
import lombok.Data;

/**
 * 角色查询条件
 */
@Data
public class RoleQuery extends BaseQuery {

    /**
     * 角色名称
     */
    private String name;

}
