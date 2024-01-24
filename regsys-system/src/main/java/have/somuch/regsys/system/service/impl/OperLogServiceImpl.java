package have.somuch.regsys.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import have.somuch.regsys.common.common.BaseQuery;
import have.somuch.regsys.common.common.BaseServiceImpl;
import have.somuch.regsys.common.utils.JsonResult;
import have.somuch.regsys.common.utils.StringUtils;
import have.somuch.regsys.system.entity.OperLog;
import have.somuch.regsys.system.mapper.OperLogMapper;
import have.somuch.regsys.system.query.OperLogQuery;
import have.somuch.regsys.system.service.IOperLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 操作日志记录 服务实现类
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-10-31
 */
@Service
public class OperLogServiceImpl extends BaseServiceImpl<OperLogMapper, OperLog> implements IOperLogService {

    @Autowired
    private OperLogMapper operLogMapper;

    /**
     * 获取操作日志列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        OperLogQuery operLogQuery = (OperLogQuery) query;
        // 查询条件
        QueryWrapper<OperLog> queryWrapper = new QueryWrapper<>();
        // 操作用户
        if (!StringUtils.isEmpty(operLogQuery.getOperName())) {
            queryWrapper.like("oper_name", operLogQuery.getOperName());
        }
        // 模块名称
        if (!StringUtils.isEmpty(operLogQuery.getTitle())) {
            queryWrapper.like("title", operLogQuery.getTitle());
        }
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByDesc("id");

        // 查询分页数据
        IPage<OperLog> page = new Page<>(operLogQuery.getPage(), operLogQuery.getLimit());
        IPage<OperLog> pageData = operLogMapper.selectPage(page, queryWrapper);
        return JsonResult.success(pageData);
    }

    /**
     * 创建系统操作日志
     *
     * @param operLog 操作日志对象
     */
    @Override
    public void insertOperlog(OperLog operLog) {
        operLogMapper.insertOperlog(operLog);
    }

}
