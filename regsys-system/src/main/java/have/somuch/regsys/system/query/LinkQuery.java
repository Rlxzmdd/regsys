package have.somuch.regsys.system.query;

import have.somuch.regsys.common.common.BaseQuery;
import lombok.Data;

/**
 * 友链查询条件
 */
@Data
public class LinkQuery extends BaseQuery {

    /**
     * 友链名称
     */
    private String name;

    /**
     * 友链类型
     */
    private Integer type;

    /**
     * 友链平台
     */
    private Integer platform;

}
