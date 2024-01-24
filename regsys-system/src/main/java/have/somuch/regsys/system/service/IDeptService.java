package have.somuch.regsys.system.service;

import have.somuch.regsys.common.common.IBaseService;
import have.somuch.regsys.common.utils.JsonResult;
import have.somuch.regsys.system.entity.Dept;

/**
 * <p>
 * 部门表 服务类
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-11-03
 */
public interface IDeptService extends IBaseService<Dept> {

    /**
     * 获取部门列表
     *
     * @return
     */
    JsonResult getDeptList();

}
