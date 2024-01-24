package have.somuch.regsys.system.filter;

import com.alibaba.fastjson.JSONObject;
import have.somuch.regsys.common.utils.JsonResult;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义登录过滤器
 */
public class ShiroLoginFilter extends FormAuthenticationFilter {

    /**
     * 判断是否登录(已登录状态下不会走此方法)
     *
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (request instanceof HttpServletRequest) {
            if ("OPTIONS".equals(((HttpServletRequest) request).getMethod().toUpperCase())) {
                return true;
            }
        }
        return super.isAccessAllowed(request, response, mappedValue);
    }

    /**
     * 是否是拒绝登录(没有登录的情况下会走此方法)
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-Control-Allow-Origin", ((HttpServletRequest) request).getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json");
        JsonResult jsonResult = new JsonResult();
        httpServletResponse.getWriter().write(JSONObject.toJSON(jsonResult.error(401, "请先登录")).toString());
        return false;
    }
}
