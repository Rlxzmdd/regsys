package have.somuch.regsys.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import have.somuch.regsys.system.entity.Role;
import have.somuch.regsys.system.entity.UserRole;

import java.util.List;

/**
 * <p>
 * 人员角色表 Mapper 接口
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-10-30
 */
public interface UserRoleMapper extends BaseMapper<UserRole> {

    /**
     * 根据用户ID获取角色
     *
     * @param userId 用户ID
     * @return
     */
    List<Role> getRolesByUserId(Integer userId);

}
