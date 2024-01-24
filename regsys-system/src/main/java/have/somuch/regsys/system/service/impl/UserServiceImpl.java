package have.somuch.regsys.system.service.impl;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import have.somuch.regsys.common.config.CommonConfig;
import have.somuch.regsys.common.utils.CommonUtils;
import have.somuch.regsys.common.utils.DateUtils;
import have.somuch.regsys.common.utils.JsonResult;
import have.somuch.regsys.common.utils.StringUtils;
import have.somuch.regsys.system.constant.UserConstant;
import have.somuch.regsys.system.dto.ResetPwdDto;
import have.somuch.regsys.system.dto.UpdatePwdDto;
import have.somuch.regsys.system.dto.UpdateUserInfoDto;
import have.somuch.regsys.system.entity.*;
import have.somuch.regsys.system.mapper.*;
import have.somuch.regsys.system.query.UserQuery;
import have.somuch.regsys.system.service.IMenuService;
import have.somuch.regsys.system.service.IUserRoleService;
import have.somuch.regsys.system.service.IUserService;
import have.somuch.regsys.system.utils.ShiroUtils;
import have.somuch.regsys.system.vo.user.UserInfoVo;
import have.somuch.regsys.system.vo.user.UserListVo;
import have.somuch.regsys.system.entity.*;
import have.somuch.regsys.system.mapper.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 后台用户管理表 服务实现类
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-10-30
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private IMenuService menuService;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private IUserRoleService userRoleService;
    @Autowired
    private LevelMapper levelMapper;
    @Autowired
    private PositionMapper positionMapper;
    @Autowired
    private DeptMapper deptMapper;

    /**
     * 获取用户列表
     *
     * @param userQuery 查询条件
     * @return
     */
    @Override
    public JsonResult getList(UserQuery userQuery) {
        // 查询条件
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        // 真实姓名
        if (!StringUtils.isEmpty(userQuery.getRealname())) {
            queryWrapper.like("realname", userQuery.getRealname());
        }
        // 用户账号
        if (!StringUtils.isEmpty(userQuery.getUsername())) {
            queryWrapper.eq("username", userQuery.getUsername());
        }
        // 性别
        if (StringUtils.isNotNull(userQuery.getGender())) {
            queryWrapper.eq("gender", userQuery.getGender());
        }
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByAsc("sort");

        // 查询分页数据
        IPage<User> page = new Page<>(userQuery.getPage(), userQuery.getLimit());
        IPage<User> pageData = userMapper.selectPage(page, queryWrapper);
        pageData.convert(x -> {
            UserListVo userListVo = Convert.convert(UserListVo.class, x);
            // 用户头像
            if (!StringUtils.isEmpty(x.getAvatar())) {
                userListVo.setAvatar(CommonUtils.getImageURL(x.getAvatar()));
            }
            // 性别描述
            if (x.getGender() != null && x.getGender() > 0) {
                userListVo.setGenderName(UserConstant.USER_GENDER_LIST.get(x.getGender()));
            }
            // 职级
            if (StringUtils.isNotNull(x.getLevelId())) {
                Level levelInfo = levelMapper.selectById(x.getLevelId());
                if (StringUtils.isNotNull(levelInfo)) {
                    userListVo.setLevelName(levelInfo.getName());
                }
            }
            // 岗位
            if (StringUtils.isNotNull(x.getPositionId())) {
                Position positionInfo = positionMapper.selectById(x.getPositionId());
                if (StringUtils.isNotNull(positionInfo)) {
                    userListVo.setPositionName(positionInfo.getName());
                }
            }
            // 部门
            if (StringUtils.isNotNull(x.getDeptId())) {
                Dept dept = deptMapper.selectById(x.getDeptId());
                if (StringUtils.isNotNull(dept)) {
                    userListVo.setDeptName(dept.getName());
                }
            }
            // 城市数据处理
            if (StringUtils.isNotNull(x.getProvinceCode()) &&
                    StringUtils.isNotNull(x.getCityCode()) &&
                    StringUtils.isNotNull(x.getDistrictCode())) {
                // 初始化数组
                String[] strings = new String[3];
                strings[0] = x.getProvinceCode();
                strings[1] = x.getCityCode();
                strings[2] = x.getDistrictCode();
                userListVo.setCity(strings);
            }
            // 获取角色信息
            List<Role> roleList = roleMapper.getRolesByUserId(x.getId());
            userListVo.setRoles(roleList);
            return userListVo;
        });
        return JsonResult.success(pageData);
    }

    /**
     * 获取用户详情
     *
     * @param userId 用户ID
     * @return
     */
    @Override
    public JsonResult info(Integer userId) {
        User user = super.getById(userId);

        // 拷贝属性
        UserListVo userListVo = new UserListVo();
        BeanUtils.copyProperties(user, userListVo);
        // 用户头像
        if (!StringUtils.isEmpty(user.getAvatar())) {
            userListVo.setAvatar(CommonUtils.getImageURL(user.getAvatar()));
        }
        // 城市数据处理
        if (StringUtils.isNotNull(user.getProvinceCode()) &&
                StringUtils.isNotNull(user.getCityCode()) &&
                StringUtils.isNotNull(user.getDistrictCode())) {
            // 初始化数组
            String[] strings = new String[3];
            strings[0] = user.getProvinceCode();
            strings[1] = user.getCityCode();
            strings[2] = user.getDistrictCode();
            userListVo.setCity(strings);
        }
        // 获取角色信息
        List<Role> roleList = roleMapper.getRolesByUserId(user.getId());
        userListVo.setRoles(roleList);
        return JsonResult.success(userListVo);
    }

    /**
     * 添加或编辑
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult edit(User entity) {
        if (CommonConfig.appDebug) {
            return JsonResult.error("演示环境禁止操作");
        }
        // 头像处理
        if (!StringUtils.isEmpty(entity.getAvatar()) && entity.getAvatar().contains(CommonConfig.imageURL)) {
            entity.setAvatar(entity.getAvatar().replaceAll(CommonConfig.imageURL, ""));
        }
        // 用户密码MD5加密
        if (StringUtils.isNotEmpty(entity.getPassword())) {
            entity.setPassword(CommonUtils.password(entity.getPassword()));
        } else {
            entity.setPassword(null);
        }
        // 省市区处理
        if (entity.getCity().length != 3) {
            return JsonResult.error("请选择省市区");
        }
        // 省份
        entity.setProvinceCode(entity.getCity()[0]);
        // 城市
        entity.setCityCode(entity.getCity()[1]);
        // 省份
        entity.setDistrictCode(entity.getCity()[2]);
        if (StringUtils.isNotNull(entity.getId()) && entity.getId() > 0) {
            entity.setUpdateUser(ShiroUtils.getUserId());
        } else {
            entity.setCreateUser(ShiroUtils.getUserId());
        }
        boolean result = this.saveOrUpdate(entity);
        if (!result) {
            return JsonResult.error();
        }
        // 删除已存在的用户角色关系数据
        userRoleService.deleteUserRole(entity.getId());
        // 插入用户角色关系数据
        userRoleService.insertUserRole(entity.getId(), entity.getRoleIds());
        return JsonResult.success();
    }

    /**
     * 根据用户ID删除用户
     *
     * @param ids 记录ID
     * @return
     */
    @Override
    public JsonResult deleteByIds(Integer[] ids) {
        if (StringUtils.isNull(ids)) {
            return JsonResult.error("记录ID不能为空");
        }
        // 设置Mark=0
        Integer totalNum = 0;
        for (Integer id : ids) {
            UpdateWrapper updateWrapper = new UpdateWrapper();
            updateWrapper.set("mark", 0);
            updateWrapper.eq("id", id);
            boolean result = update(updateWrapper);
            if (result) {
                totalNum++;
            }
        }
        if (totalNum != ids.length) {
            return JsonResult.error();
        }
        return JsonResult.success("删除成功");
    }

    /**
     * 设置状态
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult setStatus(User entity) {
        entity.setUpdateUser(ShiroUtils.getUserId());
        entity.setUpdateTime(DateUtils.now());
        boolean result = this.updateById(entity);
        if (!result) {
            return JsonResult.error();
        }
        return JsonResult.success();
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    @Override
    public JsonResult getUserInfo() {
        Integer userId = ShiroUtils.getUserId();
        User user = userMapper.selectById(userId);

        // 拷贝属性
        UserInfoVo userInfoVo = new UserInfoVo();
        BeanUtils.copyProperties(user, userInfoVo);
        // 会员头像
        if (StringUtils.isNotNull(user.getAvatar())) {
            userInfoVo.setAvatar(CommonUtils.getImageURL(user.getAvatar()));
        }

        // 获取角色列表
        List<Role> roleList = userRoleMapper.getRolesByUserId(userId);
        userInfoVo.setRoles(roleList);

        // 获取权限菜单
        List<Menu> menuList = menuService.getMenuList(userId);
        userInfoVo.setAuthorities(menuList);

        // 获取节点权限
        List<String> permissionList = menuService.getPermissionList(userId);
        userInfoVo.setPermissionList(permissionList);
        return JsonResult.success(userInfoVo);
    }

    /**
     * 修改密码
     *
     * @param updatePwdDto 参数
     * @return
     */
    @Override
    public JsonResult updatePwd(UpdatePwdDto updatePwdDto) {
        User user = userMapper.selectById(ShiroUtils.getUserId());
        if (user == null) {
            return JsonResult.error("用户信息不存在");
        }
        // 校验旧密码是否正确
        if (!CommonUtils.password(updatePwdDto.getOldPassword()).equals(user.getPassword())) {
            return JsonResult.error("原始密码不正确");
        }
        // 设置新密码
        user.setPassword(CommonUtils.password(updatePwdDto.getNewPassword()));
        Integer result = userMapper.updateById(user);
        if (result == 0) {
            return JsonResult.error("修改密码失败");
        }
        return JsonResult.success();
    }

    /**
     * 更新个人资料
     *
     * @param updateUserInfoDto 参数
     * @return
     */
    @Override
    public JsonResult updateUserInfo(UpdateUserInfoDto updateUserInfoDto) {
        // 用户ID验证
        if (StringUtils.isNull(ShiroUtils.getUserId()) || ShiroUtils.getUserId() <= 0) {
            return JsonResult.error("用户ID不能为空");
        }


        // 实例化用户对象
        User user = new User();
        BeanUtils.copyProperties(updateUserInfoDto, user);
        user.setId(ShiroUtils.getUserId());
        // 头像处理
        if (!StringUtils.isEmpty(updateUserInfoDto.getAvatar()) && updateUserInfoDto.getAvatar().contains(CommonConfig.imageURL)) {
            user.setAvatar(updateUserInfoDto.getAvatar().replaceAll(CommonConfig.imageURL, ""));
        }
        int result = userMapper.updateById(user);
        if (result == 0) {
            return JsonResult.error("更新失败");
        }
        return JsonResult.success("更新成功");
    }

    /**
     * 重置密码
     *
     * @param resetPwdDto 参数
     * @return
     */
    @Override
    public JsonResult resetPwd(ResetPwdDto resetPwdDto) {
        // 用户ID验证
        if (StringUtils.isNull(resetPwdDto.getId())) {
            return JsonResult.error("用户ID不能为空");
        }
        User user = userMapper.selectById(resetPwdDto.getId());
        if (StringUtils.isNull(user)) {
            return JsonResult.error("用户信息不存在");
        }
        // 设置新密码
        user.setPassword(CommonUtils.password("123456"));
        int reuslt = userMapper.updateById(user);
        if (reuslt == 0) {
            return JsonResult.error(null, "密码重置失败");
        }
        return JsonResult.success(null, "密码重置成功");
    }
}
