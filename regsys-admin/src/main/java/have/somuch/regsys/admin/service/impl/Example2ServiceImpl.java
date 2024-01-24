package have.somuch.regsys.admin.service.impl;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import have.somuch.regsys.common.config.CommonConfig;
import have.somuch.regsys.common.utils.CommonUtils;
import have.somuch.regsys.common.common.BaseQuery;
import have.somuch.regsys.common.common.BaseServiceImpl;
import have.somuch.regsys.admin.constant.Example2Constant;
import have.somuch.regsys.admin.entity.Example2;
import have.somuch.regsys.admin.mapper.Example2Mapper;
import have.somuch.regsys.admin.query.Example2Query;
import have.somuch.regsys.admin.service.IExample2Service;
import have.somuch.regsys.common.utils.StringUtils;
import have.somuch.regsys.system.utils.ShiroUtils;
import have.somuch.regsys.admin.vo.example2.Example2InfoVo;
import have.somuch.regsys.admin.vo.example2.Example2ListVo;
import have.somuch.regsys.common.utils.DateUtils;
import have.somuch.regsys.common.utils.JsonResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
  * <p>
  * 演示案例二 服务类实现
  * </p>
  *
  * @author 鲲鹏
  * @since 2023-03-11
  */
@Service
public class Example2ServiceImpl extends BaseServiceImpl<Example2Mapper, Example2> implements IExample2Service {

    @Autowired
    private Example2Mapper example2Mapper;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        Example2Query example2Query = (Example2Query) query;
        // 查询条件
        QueryWrapper<Example2> queryWrapper = new QueryWrapper<>();
        // 案例名称
        if (!StringUtils.isEmpty(example2Query.getName())) {
            queryWrapper.like("name", example2Query.getName());
        }
        // 类型：1京东 2淘宝 3拼多多 4唯品会
        if (!StringUtils.isEmpty(example2Query.getType())) {
            queryWrapper.eq("type", example2Query.getType());
        }
        // 状态：1正常 2停用
        if (!StringUtils.isEmpty(example2Query.getStatus())) {
            queryWrapper.eq("status", example2Query.getStatus());
        }
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByDesc("id");

        // 获取数据列表
        IPage<Example2> page = new Page<>(example2Query.getPage(), example2Query.getLimit());
        IPage<Example2> pageData = example2Mapper.selectPage(page, queryWrapper);
        pageData.convert(x -> {
            Example2ListVo example2ListVo = Convert.convert(Example2ListVo.class, x);
            // 头像地址
            if (!StringUtils.isEmpty(example2ListVo.getAvatar())) {
                example2ListVo.setAvatar(CommonUtils.getImageURL(example2ListVo.getAvatar()));
            }
            // 类型描述
            if (example2ListVo.getType() != null && example2ListVo.getType() > 0) {
                example2ListVo.setTypeName(Example2Constant.EXAMPLE2_TYPE_LIST.get(example2ListVo.getType()));
            }
            // 状态描述
            if (example2ListVo.getStatus() != null && example2ListVo.getStatus() > 0) {
                example2ListVo.setStatusName(Example2Constant.EXAMPLE2_STATUS_LIST.get(example2ListVo.getStatus()));
            }
            return example2ListVo;
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
        Example2 entity = (Example2) super.getInfo(id);
        // 返回视图Vo
        Example2InfoVo example2InfoVo = new Example2InfoVo();
        // 拷贝属性
        BeanUtils.copyProperties(entity, example2InfoVo);
        // 头像
        if (!StringUtils.isEmpty(example2InfoVo.getAvatar())) {
            example2InfoVo.setAvatar(CommonUtils.getImageURL(example2InfoVo.getAvatar()));
        }
        return example2InfoVo;
    }

    /**
     * 添加、更新记录
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult edit(Example2 entity) {
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
    public JsonResult delete(Example2 entity) {
        entity.setUpdateUser(1);
        entity.setUpdateTime(DateUtils.now());
        entity.setMark(0);
        return super.delete(entity);
    }

    /**
     * 设置状态
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult setStatus(Example2 entity) {
        if (entity.getId() == null || entity.getId() <= 0) {
            return JsonResult.error("记录ID不能为空");
        }
        if (entity.getStatus() == null) {
            return JsonResult.error("记录状态不能为空");
        }
        return super.setStatus(entity);
    }

}