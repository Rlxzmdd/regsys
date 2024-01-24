package have.somuch.regsys.system.query;

import have.somuch.regsys.common.common.BaseQuery;
import lombok.Data;

/**
 * 菜单查询条件
 */
@Data
public class MenuQuery extends BaseQuery {

    /**
     * 菜单标题
     */
    private String title;

    /**
     * 上级ID
     */
    private Integer pid;

}
