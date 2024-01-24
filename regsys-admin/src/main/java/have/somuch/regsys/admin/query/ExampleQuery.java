package have.somuch.regsys.admin.query;

import have.somuch.regsys.common.common.BaseQuery;
import lombok.Data;

/**
 * <p>
 * 演示案例一查询条件
 * </p>
 *
 * @author 鲲鹏
 * @since 2024-01-24
 */
@Data
public class ExampleQuery extends BaseQuery {

    /**
     * 案例名称
     */
    private String name;

    /**
     * 类型：1京东 2淘宝 3拼多多 4唯品会
     */
    private String type;
    /**
     * 是否VIP：1是 2否
     */
    private String isVip;
    /**
     * 状态：1正常 2停用
     */
    private String status;
}
