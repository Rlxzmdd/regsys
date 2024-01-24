package have.somuch.regsys.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import have.somuch.regsys.common.common.BaseQuery;
import have.somuch.regsys.common.common.BaseServiceImpl;
import have.somuch.regsys.common.utils.JsonResult;
import have.somuch.regsys.common.utils.StringUtils;
import have.somuch.regsys.system.entity.Position;
import have.somuch.regsys.system.mapper.PositionMapper;
import have.somuch.regsys.system.query.PositionQuery;
import have.somuch.regsys.system.service.IPositionService;
import have.somuch.regsys.system.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 岗位表 服务实现类
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-11-02
 */
@Service
public class PositionServiceImpl extends BaseServiceImpl<PositionMapper, Position> implements IPositionService {

    @Autowired
    private PositionMapper positionMapper;

    /**
     * 获取岗位列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        PositionQuery positionQuery = (PositionQuery) query;
        // 查询条件
        QueryWrapper<Position> queryWrapper = new QueryWrapper<>();
        // 岗位名称
        if (!StringUtils.isEmpty(positionQuery.getName())) {
            queryWrapper.like("name", positionQuery.getName());
        }
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByAsc("sort");

        // 查询分页数据
        IPage<Position> page = new Page<>(positionQuery.getPage(), positionQuery.getLimit());
        IPage<Position> pageData = positionMapper.selectPage(page, queryWrapper);
        return JsonResult.success(pageData);
    }

    /**
     * 添加或编辑
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult edit(Position entity) {
        if (StringUtils.isNotNull(entity.getId()) && entity.getId() > 0) {
            entity.setUpdateUser(ShiroUtils.getUserId());
        } else {
            entity.setCreateUser(ShiroUtils.getUserId());
        }
        return super.edit(entity);
    }

    /**
     * 获取岗位列表
     *
     * @return
     */
    @Override
    public JsonResult getPositionList() {
        QueryWrapper<Position> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", 1);
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByAsc("sort");
        List<Position> positionList = list(queryWrapper);
        return JsonResult.success(positionList);
    }
}
