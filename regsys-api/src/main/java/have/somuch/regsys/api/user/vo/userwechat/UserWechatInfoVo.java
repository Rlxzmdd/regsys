package have.somuch.regsys.api.user.vo.userwechat;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 用户微信表表单Vo
 * </p>
 *
 * @author isZhous
 * @since 2024-01-25
 */
@Data
public class UserWechatInfoVo {

    /**
     * 用户微信表ID
     */
    private Integer id;

    /**
     * 微信小程序用户ID
     */
    private String openId;

    /**
     * 微信用户识别ID
     */
    private String unionId;

    /**
     * 微信小程序用户Key
     */
    private String sessionKey;

    /**
     * 微信帐号名称
     */
    private String wechatName;

    /**
     * 创建记录用户
     */
    private Integer createUser;

    /**
     * 更新记录用户
     */
    private Integer updateUser;

    /**
     * 记录有效性
     */
    private Integer mark;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

}