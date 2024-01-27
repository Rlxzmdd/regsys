package have.somuch.regsys.api.entity.service.impl;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import config.have.somuch.regsys.common.CommonConfig;
import utils.have.somuch.regsys.common.CommonUtils;
import have.somuch.regsys.common.common.BaseQuery;
import have.somuch.regsys.common.common.BaseServiceImpl;
import have.somuch.regsys.api.entity.constant.EntityCollegeConstant;
import have.somuch.regsys.api.entity.entity.EntityCollege;
import have.somuch.regsys.api.entity.mapper.EntityCollegeMapper;
import have.somuch.regsys.api.entity.query.EntityCollegeQuery;
import have.somuch.regsys.api.entity.service.IEntityCollegeService;
import utils.have.somuch.regsys.common.StringUtils;
import utils.have.somuch.regsys.system.ShiroUtils;
import have.somuch.regsys.api.entity.vo.entitycollege.EntityCollegeInfoVo;
import have.somuch.regsys.api.entity.vo.entitycollege.EntityCollegeListVo;
import utils.have.somuch.regsys.common.DateUtils;
import utils.have.somuch.regsys.common.JsonResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;

/**
  * <p>
  * 学院信息表 服务类实现
  * </p>
  *
  * @author isZhous
  * @since 2024-01-27
  */
@Service
public class EntityCollegeServiceImpl extends BaseServiceImpl<EntityCollegeMapper, EntityCollege> implements IEntityCollegeService {

    @Autowired
    private EntityCollegeMapper entityCollegeMapper;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        EntityCollegeQuery entityCollegeQuery = (EntityCollegeQuery) query;
        // 查询条件
        QueryWrapper<EntityCollege> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByDesc("id");

        // 获取数据列表
        IPage<EntityCollege> page = new Page<>(entityCollegeQuery.getPage(), entityCollegeQuery.getLimit());
        IPage<EntityCollege> pageData = entityCollegeMapper.selectPage(page, queryWrapper);
        pageData.convert(x -> {
            EntityCollegeListVo entityCollegeListVo = Convert.convert(EntityCollegeListVo.class, x);
            return entityCollegeListVo;
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
        EntityCollege entity = (EntityCollege) super.getInfo(id);
        // 返回视图Vo
        EntityCollegeInfoVo entityCollegeInfoVo = new EntityCollegeInfoVo();
        // 拷贝属性
        BeanUtils.copyProperties(entity, entityCollegeInfoVo);
        return entityCollegeInfoVo;
    }

    /**
     * 添加、更新记录
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult edit(EntityCollege entity) {
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
    public JsonResult delete(EntityCollege entity) {
        entity.setUpdateTime(DateUtils.now());
        entity.setUpdateUser(1);
        entity.setMark(0);
        return super.delete(entity);
    }

}