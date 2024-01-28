package have.somuch.regsys.api.common.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import have.somuch.regsys.api.common.constant.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;

// todo 修改
@Slf4j
@Component
public class JwtUtil {

    /*JWT 加密秘钥*/
    @Value("${jwt.secret}")
    private String secret;

    /**
     * 校验token是否正确, 以及是否过期
     *
     * @param token 密钥
     * @return 是否正确
     */
    public boolean verify(String token) {
        try {
            // secret 生成秘钥，用于解密
            Algorithm algorithm = Algorithm.HMAC256(secret);
            // 构建验证器
            JWTVerifier verifier = JWT.require(algorithm).build();
            // 效验TOKEN
            verifier.verify(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public String delBearerPrefix(String token) {
        return token.replaceAll("Bearer ", "");
    }

    /**
     * 获取Token 指定Claim 参数
     *
     * @param token 用户Token
     * @param key   指定key
     * @return
     */
    public String getTokenClaim(String token, String key) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(key).asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 获取Token Payload 中的用户类型
     *
     * @param token
     * @return
     */
    public String getUserType(String token) {
        if (StringUtils.startsWithIgnoreCase(token, "Bearer ")) {
            token = delBearerPrefix(token);
        }
        return getTokenClaim(token, Constant.TOKEN_CLAIM_USER_TYPE);
    }

    /**
     * 获取Token Payload 中的用户ID
     *
     * @return token中包含的用户名
     */
    public String getUserId(String token) {
        if (StringUtils.startsWithIgnoreCase(token, "Bearer ")) {
            token = delBearerPrefix(token);
        }
        return getTokenClaim(token, Constant.TOKEN_CLAIM_USER_ID);
    }

    /**
     * 签署生成jwt Token
     *
     * @param userId 用户id
     * @return 加密的token
     */
    public String sign(String userId, String type) {
        return sign(userId,type,Constant.TOKEN_EXPIRE_TIME);
    }


    /**
     * 签署生成jwt Token
     *
     * @param userId 用户id
     * @param time 可用时长
     * @return 加密的token
     */
    public String sign(String userId, String type,Integer time) {
        // 过期时间
        Date date = new Date(System.currentTimeMillis() + time);
        // 构建秘钥
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return Constant.TOKEN_PREFIX + JWT.create()
                .withClaim(Constant.TOKEN_CLAIM_USER_ID, userId)
                .withClaim(Constant.TOKEN_CLAIM_USER_TYPE, type)
                .withExpiresAt(date)
                .sign(algorithm);

    }
}
