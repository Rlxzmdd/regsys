package have.somuch.regsys.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import have.somuch.regsys.system.entity.LoginLog;

/**
 * <p>
 * 登录日志表 Mapper 接口
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-10-31
 */
public interface LoginLogMapper extends BaseMapper<LoginLog> {

    /**
     * 创建系统登录日志
     *
     * @param loginLog 登录信息
     */
    void insertLoginLog(LoginLog loginLog);

}
