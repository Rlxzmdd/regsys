package have.somuch.regsys.admin.query;

import have.somuch.regsys.common.common.BaseQuery;
import lombok.Data;

/**
 * <p>
 * 演示案例二查询条件
 * </p>
 *
 * @author 鲲鹏
 * @since 2023-03-11
 */
@Data
public class Example2Query extends BaseQuery {

    /**
     * 案例名称
     */
    private String name;

    /**
     * 类型：1京东 2淘宝 3拼多多 4唯品会
     */
    private String type;
    /**
     * 状态：1正常 2停用
     */
    private String status;
}
