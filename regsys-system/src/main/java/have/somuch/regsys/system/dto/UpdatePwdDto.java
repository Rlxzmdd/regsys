package have.somuch.regsys.system.dto;

import lombok.Data;

/**
 * 修改密码Dto
 */
@Data
public class UpdatePwdDto {

    /**
     * 旧密码
     */
    private String oldPassword;

    /**
     * 新密码
     */
    private String newPassword;

}
