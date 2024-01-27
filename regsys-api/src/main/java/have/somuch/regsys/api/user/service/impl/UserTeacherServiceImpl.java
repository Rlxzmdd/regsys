package have.somuch.regsys.api.user.service.impl;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import config.have.somuch.regsys.common.CommonConfig;
import have.somuch.regsys.api.user.mapper.UserTeacherMapper;
import have.somuch.regsys.api.user.query.UserTeacherQuery;
import utils.have.somuch.regsys.common.CommonUtils;
import have.somuch.regsys.common.common.BaseQuery;
import have.somuch.regsys.common.common.BaseServiceImpl;
import have.somuch.regsys.api.user.entity.UserTeacher;
import have.somuch.regsys.api.user.service.IUserTeacherService;
import utils.have.somuch.regsys.common.StringUtils;
import utils.have.somuch.regsys.system.ShiroUtils;
import have.somuch.regsys.api.user.vo.userteacher.UserTeacherInfoVo;
import have.somuch.regsys.api.user.vo.userteacher.UserTeacherListVo;
import utils.have.somuch.regsys.common.DateUtils;
import utils.have.somuch.regsys.common.JsonResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
  * <p>
  *  服务类实现
  * </p>
  *
  * @author isZhous
  * @since 2024-01-27
  */
@Service
public class UserTeacherServiceImpl extends BaseServiceImpl<UserTeacherMapper, UserTeacher> implements IUserTeacherService {

    @Autowired
    private UserTeacherMapper userTeacherMapper;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        UserTeacherQuery userTeacherQuery = (UserTeacherQuery) query;
        // 查询条件
        QueryWrapper<UserTeacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByDesc("id");

        // 获取数据列表
        IPage<UserTeacher> page = new Page<>(userTeacherQuery.getPage(), userTeacherQuery.getLimit());
        IPage<UserTeacher> pageData = userTeacherMapper.selectPage(page, queryWrapper);
        pageData.convert(x -> {
            UserTeacherListVo userTeacherListVo = Convert.convert(UserTeacherListVo.class, x);
            return userTeacherListVo;
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
        UserTeacher entity = (UserTeacher) super.getInfo(id);
        // 返回视图Vo
        UserTeacherInfoVo userTeacherInfoVo = new UserTeacherInfoVo();
        // 拷贝属性
        BeanUtils.copyProperties(entity, userTeacherInfoVo);
        return userTeacherInfoVo;
    }

    /**
     * 添加、更新记录
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult edit(UserTeacher entity) {
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
    public JsonResult delete(UserTeacher entity) {
        entity.setUpdateUser(1);
        entity.setUpdateTime(DateUtils.now());
        entity.setMark(0);
        return super.delete(entity);
    }

}