package have.somuch.regsys.system.service;

import have.somuch.regsys.common.common.IBaseService;
import have.somuch.regsys.system.entity.UserRole;

/**
 * <p>
 * 人员角色表 服务类
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-10-30
 */
public interface IUserRoleService extends IBaseService<UserRole> {

    /**
     * 插入用户角色关系数据
     *
     * @param userId  用户ID
     * @param roleIds 角色ID
     */
    void insertUserRole(Integer userId, Integer[] roleIds);

    /**
     * 根据用户ID删除用户角色关系数据
     *
     * @param userId 用户ID
     */
    void deleteUserRole(Integer userId);

}
