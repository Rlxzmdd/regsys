package have.somuch.regsys.api.user.service.impl;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import have.somuch.regsys.common.config.CommonConfig;
import have.somuch.regsys.api.user.query.UserStudentQuery;
import have.somuch.regsys.common.utils.CommonUtils;
import have.somuch.regsys.common.common.BaseQuery;
import have.somuch.regsys.common.common.BaseServiceImpl;
import have.somuch.regsys.api.user.entity.UserStudent;
import have.somuch.regsys.api.user.mapper.UserStudentMapper;
import have.somuch.regsys.api.user.service.IUserStudentService;
import have.somuch.regsys.common.utils.StringUtils;
import have.somuch.regsys.system.utils.ShiroUtils;
import have.somuch.regsys.api.user.vo.userstudent.UserStudentInfoVo;
import have.somuch.regsys.api.user.vo.userstudent.UserStudentListVo;
import have.somuch.regsys.common.utils.DateUtils;
import have.somuch.regsys.common.utils.JsonResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
  * <p>
  * 学生信息表 服务类实现
  * </p>
  *
  * @author isZhous
  * @since 2024-01-27
  */
@Service
public class UserStudentServiceImpl extends BaseServiceImpl<UserStudentMapper, UserStudent> implements IUserStudentService {

    @Autowired
    private UserStudentMapper userStudentMapper;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        UserStudentQuery userStudentQuery = (UserStudentQuery) query;
        // 查询条件
        QueryWrapper<UserStudent> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByDesc("id");

        // 获取数据列表
        IPage<UserStudent> page = new Page<>(userStudentQuery.getPage(), userStudentQuery.getLimit());
        IPage<UserStudent> pageData = userStudentMapper.selectPage(page, queryWrapper);
        pageData.convert(x -> {
            UserStudentListVo userStudentListVo = Convert.convert(UserStudentListVo.class, x);
            // 学生头像照片地址
            if (!StringUtils.isEmpty(userStudentListVo.getAvatar())) {
                userStudentListVo.setAvatar(CommonUtils.getImageURL(userStudentListVo.getAvatar()));
            }
            return userStudentListVo;
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
        UserStudent entity = (UserStudent) super.getInfo(id);
        // 返回视图Vo
        UserStudentInfoVo userStudentInfoVo = new UserStudentInfoVo();
        // 拷贝属性
        BeanUtils.copyProperties(entity, userStudentInfoVo);
        // 学生头像照片
        if (!StringUtils.isEmpty(userStudentInfoVo.getAvatar())) {
            userStudentInfoVo.setAvatar(CommonUtils.getImageURL(userStudentInfoVo.getAvatar()));
        }
        return userStudentInfoVo;
    }

    /**
     * 添加、更新记录
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult edit(UserStudent entity) {
        // 学生头像照片
        if (entity.getAvatar().contains(CommonConfig.imageURL)) {
            entity.setAvatar(entity.getAvatar().replaceAll(CommonConfig.imageURL, ""));
        }
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
    public JsonResult delete(UserStudent entity) {
        entity.setUpdateTime(DateUtils.now());
        entity.setUpdateUser(1);
        entity.setMark(0);
        return super.delete(entity);
    }

}