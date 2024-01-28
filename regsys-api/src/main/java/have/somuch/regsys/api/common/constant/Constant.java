package have.somuch.regsys.api.common.constant;

public class Constant {
    /*Token 用户ID Key*/
    public static final String TOKEN_CLAIM_USER_ID = "user_id";
    /*Token 用户类型 Key*/
    public static final String TOKEN_CLAIM_USER_TYPE = "user_type";
    /*Token 前缀*/
    public static final String TOKEN_PREFIX = "Bearer ";
    /*小程序请求携带自定义Header*/
    public static final String WECHAT_MINI_PROGRAM_HEADER = "wechat-mini-program";
    /*Token 用户类型 学生*/
    public static final String TOKEN_USER_TYPE_STUDENT = "student";
    /*Token 用户类型 教师*/
    public static final String TOKEN_USER_TYPE_TEACHER = "teacher";
    /*Token 过期时间*/
    public static final Integer TOKEN_EXPIRE_TIME = 86400 * 1000;
    /*QrCode 信息码过期时间*/
    public static final Integer QRCODE_EXPIRE_TIME = 90 * 1000;
    /*小程序平台API 请求Token*/
    public static final String WECHAT_ACCESS_TOKEN_REDIS_KEY = "wechat:program:access_token";
}
