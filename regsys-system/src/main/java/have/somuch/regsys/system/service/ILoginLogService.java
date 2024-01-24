package have.somuch.regsys.system.service;

import have.somuch.regsys.common.common.IBaseService;
import have.somuch.regsys.system.entity.LoginLog;

/**
 * <p>
 * 登录日志表 服务类
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-10-31
 */
public interface ILoginLogService extends IBaseService<LoginLog> {

    /**
     * 创建系统登录日志
     *
     * @param loginLog 访问日志对象
     */
    void insertLoginLog(LoginLog loginLog);

}
