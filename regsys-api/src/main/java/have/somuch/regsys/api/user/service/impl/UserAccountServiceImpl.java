package have.somuch.regsys.api.user.service.impl;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import have.somuch.regsys.common.config.CommonConfig;
import have.somuch.regsys.common.utils.CommonUtils;
import have.somuch.regsys.common.common.BaseQuery;
import have.somuch.regsys.common.common.BaseServiceImpl;
import have.somuch.regsys.api.user.constant.UserAccountConstant;
import have.somuch.regsys.api.user.entity.UserAccount;
import have.somuch.regsys.api.user.mapper.UserAccountMapper;
import have.somuch.regsys.api.user.query.UserAccountQuery;
import have.somuch.regsys.api.user.service.IUserAccountService;
import have.somuch.regsys.common.utils.StringUtils;
import have.somuch.regsys.system.utils.ShiroUtils;
import have.somuch.regsys.api.user.vo.useraccount.UserAccountInfoVo;
import have.somuch.regsys.api.user.vo.useraccount.UserAccountListVo;
import have.somuch.regsys.common.utils.DateUtils;
import have.somuch.regsys.common.utils.JsonResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;

/**
  * <p>
  * 用户账号信息表 服务类实现
  * </p>
  *
  * @author isZhous
  * @since 2024-01-29
  */
@Service
public class UserAccountServiceImpl extends BaseServiceImpl<UserAccountMapper, UserAccount> implements IUserAccountService {

    @Autowired
    private UserAccountMapper userAccountMapper;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        UserAccountQuery userAccountQuery = (UserAccountQuery) query;
        // 查询条件
        QueryWrapper<UserAccount> queryWrapper = new QueryWrapper<>();
        // 绑定类型: 0学生 1教师
        if (!StringUtils.isEmpty(userAccountQuery.getBindType())) {
            queryWrapper.eq("bind_type", userAccountQuery.getBindType());
        }
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByDesc("id");

        // 获取数据列表
        IPage<UserAccount> page = new Page<>(userAccountQuery.getPage(), userAccountQuery.getLimit());
        IPage<UserAccount> pageData = userAccountMapper.selectPage(page, queryWrapper);
        pageData.convert(x -> {
            UserAccountListVo userAccountListVo = Convert.convert(UserAccountListVo.class, x);
            // 绑定类型描述
            if (userAccountListVo.getBindType() != null && userAccountListVo.getBindType() > 0) {
                userAccountListVo.setBindTypeName(UserAccountConstant.USERACCOUNT_BINDTYPE_LIST.get(userAccountListVo.getBindType()));
            }
            return userAccountListVo;
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
        UserAccount entity = (UserAccount) super.getInfo(id);
        // 返回视图Vo
        UserAccountInfoVo userAccountInfoVo = new UserAccountInfoVo();
        // 拷贝属性
        BeanUtils.copyProperties(entity, userAccountInfoVo);
        return userAccountInfoVo;
    }

    /**
     * 添加、更新记录
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult edit(UserAccount entity) {
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
    public JsonResult delete(UserAccount entity) {
        entity.setUpdateUser(1);
        entity.setUpdateTime(DateUtils.now());
        entity.setMark(0);
        return super.delete(entity);
    }

}