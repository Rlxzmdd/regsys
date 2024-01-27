package have.somuch.regsys.api.entity.service.impl;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import config.have.somuch.regsys.common.CommonConfig;
import utils.have.somuch.regsys.common.CommonUtils;
import have.somuch.regsys.common.common.BaseQuery;
import have.somuch.regsys.common.common.BaseServiceImpl;
import have.somuch.regsys.api.entity.constant.EntityMajorConstant;
import have.somuch.regsys.api.entity.entity.EntityMajor;
import have.somuch.regsys.api.entity.mapper.EntityMajorMapper;
import have.somuch.regsys.api.entity.query.EntityMajorQuery;
import have.somuch.regsys.api.entity.service.IEntityMajorService;
import utils.have.somuch.regsys.common.StringUtils;
import utils.have.somuch.regsys.system.ShiroUtils;
import have.somuch.regsys.api.entity.vo.entitymajor.EntityMajorInfoVo;
import have.somuch.regsys.api.entity.vo.entitymajor.EntityMajorListVo;
import utils.have.somuch.regsys.common.DateUtils;
import utils.have.somuch.regsys.common.JsonResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;

/**
  * <p>
  * 专业信息表 服务类实现
  * </p>
  *
  * @author isZhous
  * @since 2024-01-27
  */
@Service
public class EntityMajorServiceImpl extends BaseServiceImpl<EntityMajorMapper, EntityMajor> implements IEntityMajorService {

    @Autowired
    private EntityMajorMapper entityMajorMapper;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        EntityMajorQuery entityMajorQuery = (EntityMajorQuery) query;
        // 查询条件
        QueryWrapper<EntityMajor> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByDesc("id");

        // 获取数据列表
        IPage<EntityMajor> page = new Page<>(entityMajorQuery.getPage(), entityMajorQuery.getLimit());
        IPage<EntityMajor> pageData = entityMajorMapper.selectPage(page, queryWrapper);
        pageData.convert(x -> {
            EntityMajorListVo entityMajorListVo = Convert.convert(EntityMajorListVo.class, x);
            return entityMajorListVo;
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
        EntityMajor entity = (EntityMajor) super.getInfo(id);
        // 返回视图Vo
        EntityMajorInfoVo entityMajorInfoVo = new EntityMajorInfoVo();
        // 拷贝属性
        BeanUtils.copyProperties(entity, entityMajorInfoVo);
        return entityMajorInfoVo;
    }

    /**
     * 添加、更新记录
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult edit(EntityMajor entity) {
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
    public JsonResult delete(EntityMajor entity) {
        entity.setUpdateTime(DateUtils.now());
        entity.setUpdateUser(1);
        entity.setMark(0);
        return super.delete(entity);
    }

}