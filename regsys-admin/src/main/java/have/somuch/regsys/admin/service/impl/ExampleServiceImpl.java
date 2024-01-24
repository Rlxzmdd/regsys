package have.somuch.regsys.admin.service.impl;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import have.somuch.regsys.common.config.CommonConfig;
import have.somuch.regsys.common.utils.CommonUtils;
import have.somuch.regsys.common.common.BaseQuery;
import have.somuch.regsys.common.common.BaseServiceImpl;
import have.somuch.regsys.admin.constant.ExampleConstant;
import have.somuch.regsys.admin.entity.Example;
import have.somuch.regsys.admin.mapper.ExampleMapper;
import have.somuch.regsys.admin.query.ExampleQuery;
import have.somuch.regsys.admin.service.IExampleService;
import have.somuch.regsys.common.utils.StringUtils;
import have.somuch.regsys.system.utils.ShiroUtils;
import have.somuch.regsys.admin.vo.example.ExampleInfoVo;
import have.somuch.regsys.admin.vo.example.ExampleListVo;
import have.somuch.regsys.common.utils.DateUtils;
import have.somuch.regsys.common.utils.JsonResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
  * <p>
  * 演示案例一 服务类实现
  * </p>
  *
  * @author 鲲鹏
  * @since 2024-01-24
  */
@Service
public class ExampleServiceImpl extends BaseServiceImpl<ExampleMapper, Example> implements IExampleService {

    @Autowired
    private ExampleMapper exampleMapper;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        ExampleQuery exampleQuery = (ExampleQuery) query;
        // 查询条件
        QueryWrapper<Example> queryWrapper = new QueryWrapper<>();
        // 案例名称
        if (!StringUtils.isEmpty(exampleQuery.getName())) {
            queryWrapper.like("name", exampleQuery.getName());
        }
        // 类型：1京东 2淘宝 3拼多多 4唯品会
        if (!StringUtils.isEmpty(exampleQuery.getType())) {
            queryWrapper.eq("type", exampleQuery.getType());
        }
        // 是否VIP：1是 2否
        if (!StringUtils.isEmpty(exampleQuery.getIsVip())) {
            queryWrapper.eq("is_vip", exampleQuery.getIsVip());
        }
        // 状态：1正常 2停用
        if (!StringUtils.isEmpty(exampleQuery.getStatus())) {
            queryWrapper.eq("status", exampleQuery.getStatus());
        }
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByDesc("id");

        // 获取数据列表
        IPage<Example> page = new Page<>(exampleQuery.getPage(), exampleQuery.getLimit());
        IPage<Example> pageData = exampleMapper.selectPage(page, queryWrapper);
        pageData.convert(x -> {
            ExampleListVo exampleListVo = Convert.convert(ExampleListVo.class, x);
            // 头像地址
            if (!StringUtils.isEmpty(exampleListVo.getAvatar())) {
                exampleListVo.setAvatar(CommonUtils.getImageURL(exampleListVo.getAvatar()));
            }
            // 类型描述
            if (exampleListVo.getType() != null && exampleListVo.getType() > 0) {
                exampleListVo.setTypeName(ExampleConstant.EXAMPLE_TYPE_LIST.get(exampleListVo.getType()));
            }
            // 是否VIP描述
            if (exampleListVo.getIsVip() != null && exampleListVo.getIsVip() > 0) {
                exampleListVo.setIsVipName(ExampleConstant.EXAMPLE_ISVIP_LIST.get(exampleListVo.getIsVip()));
            }
            // 状态描述
            if (exampleListVo.getStatus() != null && exampleListVo.getStatus() > 0) {
                exampleListVo.setStatusName(ExampleConstant.EXAMPLE_STATUS_LIST.get(exampleListVo.getStatus()));
            }
            return exampleListVo;
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
        Example entity = (Example) super.getInfo(id);
        // 返回视图Vo
        ExampleInfoVo exampleInfoVo = new ExampleInfoVo();
        // 拷贝属性
        BeanUtils.copyProperties(entity, exampleInfoVo);
        // 头像
        if (!StringUtils.isEmpty(exampleInfoVo.getAvatar())) {
            exampleInfoVo.setAvatar(CommonUtils.getImageURL(exampleInfoVo.getAvatar()));
        }
        return exampleInfoVo;
    }

    /**
     * 添加、更新记录
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult edit(Example entity) {
        // 头像
        if (entity.getAvatar().contains(CommonConfig.imageURL)) {
            entity.setAvatar(entity.getAvatar().replaceAll(CommonConfig.imageURL, ""));
        }
        if (StringUtils.isNotNull(entity.getId()) && entity.getId() > 0) {
            entity.setUpdateUser(ShiroUtils.getUserId());
            entity.setUpdateTime(DateUtils.now());
        } else {
            entity.setCreateUser(ShiroUtils.getUserId());
            entity.setCreateTime(DateUtils.now());
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
    public JsonResult delete(Example entity) {
        entity.setUpdateUser(1);
        entity.setUpdateTime(DateUtils.now());
        entity.setMark(0);
        return super.delete(entity);
    }

    /**
     * 设置是否VIP
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult setIsVip(Example entity) {
        if (entity.getId() == null || entity.getId() <= 0) {
            return JsonResult.error("记录ID不能为空");
        }
        if (entity.getIsVip() == null) {
            return JsonResult.error("记录是否VIP不能为空");
        }
        entity.setUpdateUser(ShiroUtils.getUserId());
        entity.setUpdateTime(DateUtils.now());
        boolean result = this.updateById(entity);
        if (!result) {
            return JsonResult.error();
        }
        return JsonResult.success();
    }

    /**
     * 设置状态
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult setStatus(Example entity) {
        if (entity.getId() == null || entity.getId() <= 0) {
            return JsonResult.error("记录ID不能为空");
        }
        if (entity.getStatus() == null) {
            return JsonResult.error("记录状态不能为空");
        }
        return super.setStatus(entity);
    }

}