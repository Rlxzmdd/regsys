package have.somuch.regsys.system.filter;

import com.alibaba.fastjson.JSONObject;
import have.somuch.regsys.common.utils.JsonResult;
import have.somuch.regsys.common.utils.MessageUtils;
import have.somuch.regsys.common.utils.StringUtils;
import have.somuch.regsys.system.constant.Constants;
import have.somuch.regsys.system.entity.User;
import have.somuch.regsys.system.manager.AsyncFactory;
import have.somuch.regsys.system.manager.AsyncManager;
import have.somuch.regsys.system.utils.ShiroUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.session.SessionException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;
import java.util.Deque;

/**
 * 退出过滤器
 */
public class ShiroLogoutFilter extends org.apache.shiro.web.filter.authc.LogoutFilter {

    private static final Logger log = LoggerFactory.getLogger(ShiroLogoutFilter.class);

    private Cache<String, Deque<Serializable>> cache;

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        try {
            Subject subject = getSubject(request, response);
            try {
                User user = ShiroUtils.getUserInfo();
                if (StringUtils.isNotNull(user)) {
                    String loginName = user.getUsername();
                    // 记录用户退出日志
                    AsyncManager.me().execute(AsyncFactory.recordLogininfor(loginName, Constants.LOGOUT, MessageUtils.message("user.logout.success")));
                    // 清理缓存
                    cache.remove(loginName);
                }
                // 退出登录
                subject.logout();
            } catch (SessionException ise) {
                log.error("logout fail.", ise);
            }
            // 提示注销成功
            JsonResult jsonResult = new JsonResult();
            response.getWriter().write(JSONObject.toJSON(jsonResult.success("注销成功")).toString());
        } catch (Exception e) {
            log.error("Encountered session exception during logout.  This can generally safely be ignored.", e);
        }
        return false;
    }
}
