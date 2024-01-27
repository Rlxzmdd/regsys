package have.somuch.regsys.api.user.service.impl;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import have.somuch.regsys.api.user.entity.UserWechat;
import have.somuch.regsys.api.user.mapper.UserWechatMapper;
import have.somuch.regsys.api.user.query.UserWechatQuery;
import have.somuch.regsys.api.user.service.IUserWechatService;
import have.somuch.regsys.common.common.BaseQuery;
import have.somuch.regsys.common.common.BaseServiceImpl;
import have.somuch.regsys.common.utils.StringUtils;
import have.somuch.regsys.system.utils.ShiroUtils;
import have.somuch.regsys.api.user.vo.userwechat.UserWechatInfoVo;
import have.somuch.regsys.api.user.vo.userwechat.UserWechatListVo;
import have.somuch.regsys.common.utils.DateUtils;
import have.somuch.regsys.common.utils.JsonResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
  * <p>
  * 用户微信表 服务类实现
  * </p>
  *
  * @author isZhous
  * @since 2024-01-25
  */
@Service
public class UserWechatServiceImpl extends BaseServiceImpl<UserWechatMapper, UserWechat> implements IUserWechatService {

    @Autowired
    private UserWechatMapper userWechatMapper;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        UserWechatQuery userWechatQuery = (UserWechatQuery) query;
        // 查询条件
        QueryWrapper<UserWechat> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByDesc("id");

        // 获取数据列表
        IPage<UserWechat> page = new Page<>(userWechatQuery.getPage(), userWechatQuery.getLimit());
        IPage<UserWechat> pageData = userWechatMapper.selectPage(page, queryWrapper);
        pageData.convert(x -> {
            UserWechatListVo userWechatListVo = Convert.convert(UserWechatListVo.class, x);
            return userWechatListVo;
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
        UserWechat entity = (UserWechat) super.getInfo(id);
        // 返回视图Vo
        UserWechatInfoVo userWechatInfoVo = new UserWechatInfoVo();
        // 拷贝属性
        BeanUtils.copyProperties(entity, userWechatInfoVo);
        return userWechatInfoVo;
    }

    /**
     * 添加、更新记录
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult edit(UserWechat entity) {
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
    public JsonResult delete(UserWechat entity) {
        entity.setUpdateUser(1);
        entity.setUpdateTime(DateUtils.now());
        entity.setMark(0);
        return super.delete(entity);
    }

}