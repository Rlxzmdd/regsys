package have.somuch.regsys.system.query;

import have.somuch.regsys.common.common.BaseQuery;
import lombok.Data;

/**
 * <p>
 * 文章管理表查询条件
 * </p>
 *
 * @author 鲲鹏
 * @since 2021-10-11
 */
@Data
public class ArticleQuery extends BaseQuery {

    /**
     * 文章标题
     */
    private String title;

    /**
     * 状态：1正常 2停用
     */
    private String status;
}
