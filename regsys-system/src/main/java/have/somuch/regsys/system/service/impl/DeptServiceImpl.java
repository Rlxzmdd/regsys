package have.somuch.regsys.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import have.somuch.regsys.common.common.BaseQuery;
import have.somuch.regsys.common.common.BaseServiceImpl;
import have.somuch.regsys.common.utils.JsonResult;
import have.somuch.regsys.common.utils.StringUtils;
import have.somuch.regsys.system.entity.Dept;
import have.somuch.regsys.system.mapper.DeptMapper;
import have.somuch.regsys.system.query.DeptQuery;
import have.somuch.regsys.system.service.IDeptService;
import have.somuch.regsys.system.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 部门表 服务实现类
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-11-03
 */
@Service
public class DeptServiceImpl extends BaseServiceImpl<DeptMapper, Dept> implements IDeptService {

    @Autowired
    private DeptMapper deptMapper;

    /**
     * 获取部门列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        DeptQuery deptQuery = (DeptQuery) query;
        // 查询条件
        QueryWrapper<Dept> queryWrapper = new QueryWrapper<>();
        // 部门名称
        if (!StringUtils.isEmpty(deptQuery.getName())) {
            queryWrapper.like("name", deptQuery.getName());
        }
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByAsc("sort");

        // 查询分页数据
        List<Dept> deptList = deptMapper.selectList(queryWrapper);
        return JsonResult.success(deptList);
    }

    /**
     * 添加或编辑
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult edit(Dept entity) {
        if (StringUtils.isNotNull(entity.getId()) && entity.getId() > 0) {
            entity.setUpdateUser(ShiroUtils.getUserId());
        } else {
            entity.setCreateUser(ShiroUtils.getUserId());
        }
        return super.edit(entity);
    }

    /**
     * 获取部门列表
     *
     * @return
     */
    @Override
    public JsonResult getDeptList() {
        // 查询条件
        QueryWrapper<Dept> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByAsc("sort");

        // 查询分页数据
        List<Dept> deptList = deptMapper.selectList(queryWrapper);
        return JsonResult.success(deptList);
    }
}
