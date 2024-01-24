package have.somuch.regsys.system.service;

import have.somuch.regsys.common.common.IBaseService;
import have.somuch.regsys.system.entity.Menu;

import java.util.List;

/**
 * <p>
 * 系统菜单表 服务类
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-10-30
 */
public interface IMenuService extends IBaseService<Menu> {

    /**
     * 获取导航菜单
     *
     * @param userId 用户ID
     * @return
     */
    List<Menu> getMenuList(Integer userId);

    /**
     * 获取所有菜单列表
     *
     * @return
     */
    List<Menu> getMenuAll();

    /**
     * 获取节点权限
     *
     * @param userId 用户ID
     * @return
     */
    List<String> getPermissionList(Integer userId);

}
