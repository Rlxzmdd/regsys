package have.somuch.regsys.api.common.dto;


import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class WechatProgramIdentityDto {
    private String openid;
    private String sessionKey;
    private String unionId;
    private String errorCode;
    private String errorMsg;
}
