package have.somuch.regsys.system.service;

import have.somuch.regsys.common.common.IBaseService;
import have.somuch.regsys.common.utils.JsonResult;
import have.somuch.regsys.system.entity.Notice;

/**
 * <p>
 * 通知公告表 服务类
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-11-07
 */
public interface INoticeService extends IBaseService<Notice> {

    /**
     * 设置是否置顶
     *
     * @param entity 实体对象
     * @return
     */
    JsonResult setIsTop(Notice entity);

}
