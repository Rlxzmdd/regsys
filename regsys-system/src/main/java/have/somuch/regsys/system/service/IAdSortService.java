package have.somuch.regsys.system.service;

import have.somuch.regsys.common.common.IBaseService;
import have.somuch.regsys.common.utils.JsonResult;
import have.somuch.regsys.system.entity.AdSort;

/**
 * <p>
 * 广告位管理表 服务类
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-11-07
 */
public interface IAdSortService extends IBaseService<AdSort> {

    /**
     * 获取广告位列表
     *
     * @return
     */
    JsonResult getAdSortList();

}
