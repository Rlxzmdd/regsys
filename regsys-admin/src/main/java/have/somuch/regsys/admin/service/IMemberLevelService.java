package have.somuch.regsys.admin.service;

import have.somuch.regsys.admin.entity.MemberLevel;
import have.somuch.regsys.common.common.IBaseService;
import have.somuch.regsys.common.utils.JsonResult;

/**
 * <p>
 * 会员级别表 服务类
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-11-04
 */
public interface IMemberLevelService extends IBaseService<MemberLevel> {

    /**
     * 获取会员等级列表
     *
     * @return
     */
    JsonResult getMemberLevelList();

}
