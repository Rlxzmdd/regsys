package have.somuch.regsys.system.query;

import have.somuch.regsys.common.common.BaseQuery;
import lombok.Data;

/**
 * 操作日志查询条件
 */
@Data
public class OperLogQuery extends BaseQuery {

    /**
     * 用户账号
     */
    private String operName;

    /**
     * 名称名称
     */
    private String title;

}
