package have.somuch.regsys.api.entity.service.impl;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import have.somuch.regsys.common.config.CommonConfig;
import have.somuch.regsys.common.utils.CommonUtils;
import have.somuch.regsys.common.common.BaseQuery;
import have.somuch.regsys.common.common.BaseServiceImpl;
import have.somuch.regsys.api.entity.constant.EntityClassesConstant;
import have.somuch.regsys.api.entity.entity.EntityClasses;
import have.somuch.regsys.api.entity.mapper.EntityClassesMapper;
import have.somuch.regsys.api.entity.query.EntityClassesQuery;
import have.somuch.regsys.api.entity.service.IEntityClassesService;
import have.somuch.regsys.common.utils.StringUtils;
import have.somuch.regsys.system.utils.ShiroUtils;
import have.somuch.regsys.api.entity.vo.entityclasses.EntityClassesInfoVo;
import have.somuch.regsys.api.entity.vo.entityclasses.EntityClassesListVo;
import have.somuch.regsys.common.utils.DateUtils;
import have.somuch.regsys.common.utils.JsonResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;

/**
  * <p>
  * 班级信息表 服务类实现
  * </p>
  *
  * @author isZhous
  * @since 2024-01-27
  */
@Service
public class EntityClassesServiceImpl extends BaseServiceImpl<EntityClassesMapper, EntityClasses> implements IEntityClassesService {

    @Autowired
    private EntityClassesMapper entityClassesMapper;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        EntityClassesQuery entityClassesQuery = (EntityClassesQuery) query;
        // 查询条件
        QueryWrapper<EntityClasses> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByDesc("id");

        // 获取数据列表
        IPage<EntityClasses> page = new Page<>(entityClassesQuery.getPage(), entityClassesQuery.getLimit());
        IPage<EntityClasses> pageData = entityClassesMapper.selectPage(page, queryWrapper);
        pageData.convert(x -> {
            EntityClassesListVo entityClassesListVo = Convert.convert(EntityClassesListVo.class, x);
            return entityClassesListVo;
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
        EntityClasses entity = (EntityClasses) super.getInfo(id);
        // 返回视图Vo
        EntityClassesInfoVo entityClassesInfoVo = new EntityClassesInfoVo();
        // 拷贝属性
        BeanUtils.copyProperties(entity, entityClassesInfoVo);
        return entityClassesInfoVo;
    }

    /**
     * 添加、更新记录
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult edit(EntityClasses entity) {
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
    public JsonResult delete(EntityClasses entity) {
        entity.setUpdateTime(DateUtils.now());
        entity.setUpdateUser(1);
        entity.setMark(0);
        return super.delete(entity);
    }

}