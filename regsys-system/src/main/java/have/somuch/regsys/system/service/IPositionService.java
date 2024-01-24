package have.somuch.regsys.system.service;

import have.somuch.regsys.common.common.IBaseService;
import have.somuch.regsys.common.utils.JsonResult;
import have.somuch.regsys.system.entity.Position;

/**
 * <p>
 * 岗位表 服务类
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-11-02
 */
public interface IPositionService extends IBaseService<Position> {

    /**
     * 获取岗位列表
     *
     * @return
     */
    JsonResult getPositionList();

}
