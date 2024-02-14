package have.somuch.regsys.api.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import com.fasterxml.jackson.annotation.JsonFormat;
import have.somuch.regsys.common.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


/**
 * <p>
 * 用户账号信息表
 * </p>
 *
 * @author isZhous
 * @since 2024-01-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("user_account")
public class UserAccount extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 信息类型
     */
    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 绑定类型: 0学生 1教师
     */
    private Integer bindType;

    /**
     * 绑定工号
     */
    private String bindNumber;

}