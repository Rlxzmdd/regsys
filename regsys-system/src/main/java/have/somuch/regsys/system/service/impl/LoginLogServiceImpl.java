package have.somuch.regsys.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import have.somuch.regsys.common.common.BaseQuery;
import have.somuch.regsys.common.common.BaseServiceImpl;
import have.somuch.regsys.common.utils.JsonResult;
import have.somuch.regsys.common.utils.StringUtils;
import have.somuch.regsys.system.entity.LoginLog;
import have.somuch.regsys.system.mapper.LoginLogMapper;
import have.somuch.regsys.system.query.LoginLogQuery;
import have.somuch.regsys.system.service.ILoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 登录日志表 服务实现类
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-10-31
 */
@Service
public class LoginLogServiceImpl extends BaseServiceImpl<LoginLogMapper, LoginLog> implements ILoginLogService {

    @Autowired
    private LoginLogMapper loginLogMapper;

    /**
     * 获取登录日志列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        LoginLogQuery loginLogQuery = (LoginLogQuery) query;
        // 查询条件
        QueryWrapper<LoginLog> queryWrapper = new QueryWrapper<>();
        // 用户账号
        if (!StringUtils.isEmpty(loginLogQuery.getUsername())) {
            queryWrapper.like("username", loginLogQuery.getUsername());
        }
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByDesc("id");

        // 查询分页数据
        IPage<LoginLog> page = new Page<>(loginLogQuery.getPage(), loginLogQuery.getLimit());
        IPage<LoginLog> pageData = loginLogMapper.selectPage(page, queryWrapper);
        return JsonResult.success(pageData);
    }

    /**
     * 创建系统登录日志
     *
     * @param loginLog 访问日志对象
     */
    @Override
    public void insertLoginLog(LoginLog loginLog) {
        loginLogMapper.insertLoginLog(loginLog);
    }

}
