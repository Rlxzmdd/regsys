package have.somuch.regsys.api.common.constant;

public enum LoginTypeEnum {
    /**
     * 学生学号登录,number、password
     */
    STU_NUMBER,
    /**
     * 学生考生号登录,examNum、password(存储真实姓名)
     */
    EXAM_NUMBER,
    /**
     * 教师工号登录,number、password
     */
    TCH_NUMBER,

    UNKNOWN
}