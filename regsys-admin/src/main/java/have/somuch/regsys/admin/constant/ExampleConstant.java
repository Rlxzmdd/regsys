package have.somuch.regsys.admin.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 演示案例一 模块常量
 * </p>
 *
 * @author 鲲鹏
 * @since 2024-01-24
 */
public class ExampleConstant {

    /**
     * 类型
     */
    public static Map<Integer, String> EXAMPLE_TYPE_LIST = new HashMap<Integer, String>() {
        {
            put(1, "京东");
            put(2, "淘宝");
            put(3, "拼多多");
            put(4, "唯品会");
        }
    };
    /**
     * 是否VIP
     */
    public static Map<Integer, String> EXAMPLE_ISVIP_LIST = new HashMap<Integer, String>() {
        {
            put(1, "是");
            put(2, "否");
        }
    };
    /**
     * 状态
     */
    public static Map<Integer, String> EXAMPLE_STATUS_LIST = new HashMap<Integer, String>() {
        {
            put(1, "正常");
            put(2, "停用");
        }
    };
}