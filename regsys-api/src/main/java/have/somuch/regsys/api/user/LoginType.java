package have.somuch.regsys.api.user;

public enum LoginType {
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
    TCH_NUMBER
}