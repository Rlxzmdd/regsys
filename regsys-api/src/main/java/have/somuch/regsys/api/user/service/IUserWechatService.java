package have.somuch.regsys.api.user.service;

import have.somuch.regsys.api.common.dto.AuthToken2CredentialDto;
import have.somuch.regsys.api.common.utils.JsonResultS;
import have.somuch.regsys.api.user.dto.WechatLoginDto;
import have.somuch.regsys.api.user.entity.UserWechat;
import have.somuch.regsys.common.common.IBaseService;

/**
 * <p>
 * 用户微信表 服务类
 * </p>
 *
 * @author isZhous
 * @since 2024-01-25
 */
public interface IUserWechatService extends IBaseService<UserWechat> {
    /**
     * 绑定微信
     *
     * @param code   小程序Code
     * @param dto
     * @return
     */
    JsonResultS login(String code, WechatLoginDto dto);

    /**
     * 根据Code 获取绑定的微信信息
     *
     * @param code 小程序code
     * @return
     */
    JsonResultS obtain(String code);

}