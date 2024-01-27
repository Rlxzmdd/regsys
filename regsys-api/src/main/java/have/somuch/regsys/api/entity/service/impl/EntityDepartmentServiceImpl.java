package have.somuch.regsys.api.entity.service.impl;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import have.somuch.regsys.common.config.CommonConfig;
import have.somuch.regsys.common.utils.CommonUtils;
import have.somuch.regsys.common.common.BaseQuery;
import have.somuch.regsys.common.common.BaseServiceImpl;
import have.somuch.regsys.api.entity.constant.EntityDepartmentConstant;
import have.somuch.regsys.api.entity.entity.EntityDepartment;
import have.somuch.regsys.api.entity.mapper.EntityDepartmentMapper;
import have.somuch.regsys.api.entity.query.EntityDepartmentQuery;
import have.somuch.regsys.api.entity.service.IEntityDepartmentService;
import have.somuch.regsys.common.utils.StringUtils;
import have.somuch.regsys.system.utils.ShiroUtils;
import have.somuch.regsys.api.entity.vo.entitydepartment.EntityDepartmentInfoVo;
import have.somuch.regsys.api.entity.vo.entitydepartment.EntityDepartmentListVo;
import have.somuch.regsys.common.utils.DateUtils;
import have.somuch.regsys.common.utils.JsonResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;

/**
  * <p>
  * 部门信息表 服务类实现
  * </p>
  *
  * @author isZhous
  * @since 2024-01-27
  */
@Service
public class EntityDepartmentServiceImpl extends BaseServiceImpl<EntityDepartmentMapper, EntityDepartment> implements IEntityDepartmentService {

    @Autowired
    private EntityDepartmentMapper entityDepartmentMapper;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        EntityDepartmentQuery entityDepartmentQuery = (EntityDepartmentQuery) query;
        // 查询条件
        QueryWrapper<EntityDepartment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByDesc("id");

        // 获取数据列表
        IPage<EntityDepartment> page = new Page<>(entityDepartmentQuery.getPage(), entityDepartmentQuery.getLimit());
        IPage<EntityDepartment> pageData = entityDepartmentMapper.selectPage(page, queryWrapper);
        pageData.convert(x -> {
            EntityDepartmentListVo entityDepartmentListVo = Convert.convert(EntityDepartmentListVo.class, x);
            return entityDepartmentListVo;
        });
        return JsonResult.success(pageData);
    }

    /**
     * 获取详情Vo
     *
     * @param id 记录ID
     * @return
     */
    @Override
    public Object getInfo(Serializable id) {
        EntityDepartment entity = (EntityDepartment) super.getInfo(id);
        // 返回视图Vo
        EntityDepartmentInfoVo entityDepartmentInfoVo = new EntityDepartmentInfoVo();
        // 拷贝属性
        BeanUtils.copyProperties(entity, entityDepartmentInfoVo);
        return entityDepartmentInfoVo;
    }

    /**
     * 添加、更新记录
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult edit(EntityDepartment entity) {
        if (StringUtils.isNotNull(entity.getId()) && entity.getId() > 0) {
            entity.setUpdateTime(DateUtils.now());
            entity.setUpdateUser(ShiroUtils.getUserId());
        } else {
            entity.setCreateTime(DateUtils.now());
            entity.setCreateUser(ShiroUtils.getUserId());
        }
        return super.edit(entity);
    }

    /**
     * 删除记录
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult delete(EntityDepartment entity) {
        entity.setUpdateTime(DateUtils.now());
        entity.setUpdateUser(1);
        entity.setMark(0);
        return super.delete(entity);
    }

}