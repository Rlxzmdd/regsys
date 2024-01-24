package have.somuch.regsys.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import have.somuch.regsys.system.entity.Role;

import java.util.List;

/**
 * <p>
 * 系统角色表 Mapper 接口
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-10-31
 */
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 根据用户ID获取角色信息
     *
     * @param userId 用户ID
     * @return
     */
    List<Role> getRolesByUserId(Integer userId);

}
