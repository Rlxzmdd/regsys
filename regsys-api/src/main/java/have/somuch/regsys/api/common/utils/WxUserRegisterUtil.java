package have.somuch.regsys.api.common.utils;

import have.somuch.regsys.api.common.constant.Constant;
import have.somuch.regsys.api.common.dto.WechatProgramIdentityDto;
import have.somuch.regsys.common.utils.RedisUtils;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 微信注册工具
 * 换取用户小程序openid 等
 *
 * @author Cheney
 */
@Slf4j
@Data
@NoArgsConstructor
@Component("wxUserRegisterUtil")
public class WxUserRegisterUtil {

    @Value("${wechat.program.appId}")
    private String appId;

    @Value("${wechat.program.appSecret}")
    private String appSecret;

    @Value("${wechat.program.AuthTokenUrl}")
    private String authTokenURL;

    @Value("${wechat.program.Code2SessionUrl}")
    private String code2SessionURL;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private HttpClientUtil httpClientUtil;

    /**
     * 替换URL请求参数
     *
     * @return REQUEST URL
     */
    private String generateAccessTokenURL() {
        return String.format(this.authTokenURL, this.appId, this.appSecret);
    }

    /**
     * 生成换取openid 的请求地址
     *
     * @param code 小程序用户code
     * @return
     */
    private String generateCode2SessionURL(String code) {
        return String.format(this.code2SessionURL, this.appId, this.appSecret, code);
    }

    /**
     * 从Redis 缓存中获取AccessToken
     *
     * @return
     */
    private String getAccessTokenForRedis() {
        return redisUtils.get(Constant.WECHAT_ACCESS_TOKEN_REDIS_KEY).toString();
    }

    /**
     * 设置AccessToken 至Redis中
     *
     * @param token
     * @param expire
     * @return
     */
    private Boolean setAccessToken2Redis(String token, Integer expire) {
        redisUtils.set(Constant.WECHAT_ACCESS_TOKEN_REDIS_KEY, token);
        return redisUtils.expire(Constant.WECHAT_ACCESS_TOKEN_REDIS_KEY, expire);
    }

    /**
     * 请求wx Access_token
     * 若请求失败，将返回null
     *
     * @return Access_token
     */
    private String requestAccessToken() {
        Map<String, Object> response = httpClientUtil.sendGetData(this.generateAccessTokenURL(), "UTF-8");
        String access_token = null;
        if (response.containsKey("access_token") && response.containsKey("expires_in")) {
            this.setAccessToken2Redis(String.valueOf(response.get("access_token")), (Integer) response.get("expires_in"));
            access_token = String.valueOf(response.get("access_token"));
        } else {
            log.info(String.format("Get wechat access_token error (error code : %s) ,error message : %s", response.get("errcode"), response.get("errmsg")));
        }
        return access_token;
    }

    /**
     * 获取wx AccessToken ,若redis无token则设置一份置于redis中
     *
     * @return access_token
     */
    public String getAccessToken() {
        String access_token = getAccessTokenForRedis();
        if (access_token != null) return access_token;
        return requestAccessToken();
    }

    /**
     * 请求微信用户OpenId
     *
     * @return response
     */
    public WechatProgramIdentityDto requestCode2Session(String code) {
        String url = generateCode2SessionURL(code);
        Map<String, Object> response = httpClientUtil.sendGetData(url, "UTF-8");
        log.warn(response.toString());
        if (response.containsKey("openid") && response.containsKey("session_key")) {
            return new WechatProgramIdentityDto().setOpenid(String.valueOf(response.get("openid")))
                    .setSessionKey(String.valueOf(response.get("session_key")))
                    .setUnionId(String.valueOf(response.get("unionid")))
                    .setErrorCode(String.valueOf(response.get("errcode")))
                    .setErrorMsg(String.valueOf(response.get("errmsg")));
        }
        log.info(String.format("Get wechat Code2Session error (error code : %s) ,error message : %s", response.get("errcode"), response.get("errmsg")));
        return null;
    }

}
