package have.somuch.regsys.api.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import have.somuch.regsys.common.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


/**
 * <p>
 * 用户微信表
 * </p>
 *
 * @author isZhous
 * @since 2024-01-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("user_wechat")
public class UserWechat extends BaseEntity {

    private static final long serialVersionUID = 1L;

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

}