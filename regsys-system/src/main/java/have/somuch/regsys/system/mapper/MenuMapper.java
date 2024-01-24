package have.somuch.regsys.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import have.somuch.regsys.system.entity.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 系统菜单表 Mapper 接口
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-10-30
 */
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 根据用户ID获取权限列表
     *
     * @param userId 用户ID
     * @param pid    上级ID
     * @return
     */
    List<Menu> getPermissionsListByUserId(@Param("userId") Integer userId, @Param("pid") Integer pid);

    /**
     * 获取所有权限
     *
     * @return
     */
    List<Menu> getPermissionsAll();

    /**
     * 获取用户权限节点
     *
     * @param userId 用户ID
     * @return
     */
    List<String> getPermissionList(Integer userId);

}
