package have.somuch.regsys.system.utils;

import have.somuch.regsys.common.utils.SpringUtils;
import have.somuch.regsys.system.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.crazycake.shiro.RedisSessionDAO;

/**
 * Shiro工具类
 */
public class ShiroUtils {

    /**
     * 私有构造器
     **/
    private ShiroUtils() {
    }

    private static RedisSessionDAO redisSessionDAO = SpringUtils.getBean(RedisSessionDAO.class);

    /**
     * 获取当前用户Session
     *
     * @return
     */
    public static Session getSession() {
        return SecurityUtils.getSubject().getSession();
    }

    /**
     * 用户退出
     */
    public static void logout() {
        SecurityUtils.getSubject().logout();
    }

    /**
     * 获取当前用户信息
     *
     * @return
     */
    public static User getUserInfo() {
        return (User) SecurityUtils.getSubject().getPrincipal();
    }

    /**
     * 获取用户编号
     *
     * @return
     */
    public static Integer getUserId() {
        User user = getUserInfo();
        return user.getId();
    }

}
