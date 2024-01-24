package have.somuch.regsys.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import have.somuch.regsys.common.common.BaseServiceImpl;
import have.somuch.regsys.common.utils.StringUtils;
import have.somuch.regsys.system.entity.UserRole;
import have.somuch.regsys.system.mapper.UserRoleMapper;
import have.somuch.regsys.system.service.IUserRoleService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 人员角色表 服务实现类
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-10-30
 */
@Service
public class UserRoleServiceImpl extends BaseServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

    /**
     * 插入用户角色关系数据
     *
     * @param userId  用户ID
     * @param roleIds 角色ID
     */
    @Override
    public void insertUserRole(Integer userId, Integer[] roleIds) {
        if (StringUtils.isNotNull(roleIds)) {
            List<UserRole> userRoleList = new ArrayList<>();
            for (Integer roleId : roleIds) {
                UserRole userRole = new UserRole();
                userRole.setUserId(userId);
                userRole.setRoleId(roleId);
                userRoleList.add(userRole);
            }
            if (userRoleList.size() > 0) {
                this.saveBatch(userRoleList);
            }
        }
    }

    /**
     * 根据用户ID删除用户角色数据
     *
     * @param userId 用户ID
     */
    @Override
    public void deleteUserRole(Integer userId) {
        QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        remove(queryWrapper);
    }

}
