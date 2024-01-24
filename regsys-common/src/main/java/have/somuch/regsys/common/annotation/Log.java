package have.somuch.regsys.common.annotation;

import have.somuch.regsys.common.enums.LogType;
import have.somuch.regsys.common.enums.OperType;

import java.lang.annotation.*;

/**
 * 自定义操作日志注解
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

    /**
     * 模块
     */
    String title() default "";

    /**
     * 日志类型
     */
    LogType logType() default LogType.OTHER;

    /**
     * 操作人类别
     */
    OperType operType() default OperType.MANAGE;

    /**
     * 是否保存请求的参数
     */
    boolean isSaveRequestData() default true;

}
