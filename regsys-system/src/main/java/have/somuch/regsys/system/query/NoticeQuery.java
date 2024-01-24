package have.somuch.regsys.system.query;

import have.somuch.regsys.common.common.BaseQuery;
import lombok.Data;

/**
 * 通知公告查询条件
 */
@Data
public class NoticeQuery extends BaseQuery {

    /**
     * 通知标题
     */
    private String title;

    /**
     * 通知状态
     */
    private Integer status;

}
