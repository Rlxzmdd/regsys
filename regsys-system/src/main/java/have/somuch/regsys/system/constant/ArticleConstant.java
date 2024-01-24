package have.somuch.regsys.system.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 文章管理表 模块常量
 * </p>
 *
 * @author 鲲鹏
 * @since 2021-10-11
 */
public class ArticleConstant {

    /**
     * 状态
     */
    public static Map<Integer, String> ARTICLE_STATUS_LIST = new HashMap<Integer, String>() {
        {
            put(1, "正常");
            put(2, "停用");
        }
    };
}