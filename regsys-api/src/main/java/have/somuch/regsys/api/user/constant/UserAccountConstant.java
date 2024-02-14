package have.somuch.regsys.api.user.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 用户账号信息表 模块常量
 * </p>
 *
 * @author isZhous
 * @since 2024-01-29
 */
public class UserAccountConstant {

    /**
     * 绑定类型
     */
    public static Map<Integer, String> USERACCOUNT_BINDTYPE_LIST = new HashMap<Integer, String>() {
        {
            put(0, "学生");
            put(1, "教师");
        }
    };
}