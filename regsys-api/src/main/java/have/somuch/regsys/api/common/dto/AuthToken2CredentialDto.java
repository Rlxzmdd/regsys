package have.somuch.regsys.api.common.dto;

import have.somuch.regsys.common.utils.JwtUtil;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AuthToken2CredentialDto {
    private String number;
    private String type;

    public static AuthToken2CredentialDto create(JwtUtil jwtUtil, String token) {
        return new AuthToken2CredentialDto()
                .setNumber(jwtUtil.getUserId(token))
                .setType(jwtUtil.getUserType(token));
    }
}