package have.somuch.regsys.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import have.somuch.regsys.common.utils.JsonResult;
import have.somuch.regsys.system.dto.ResetPwdDto;
import have.somuch.regsys.system.dto.UpdatePwdDto;
import have.somuch.regsys.system.dto.UpdateUserInfoDto;
import have.somuch.regsys.system.entity.User;
import have.somuch.regsys.system.query.UserQuery;

/**
 * <p>
 * 后台用户管理表 服务类
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-10-30
 */
public interface IUserService extends IService<User> {

    /**
     * 根据查询条件获取数据列表
     *
     * @param userQuery 查询条件
     * @return
     */
    JsonResult getList(UserQuery userQuery);

    /**
     * 获取用户详情
     *
     * @param userId 用户ID
     * @return
     */
    JsonResult info(Integer userId);

    /**
     * 根据实体对象添加、编辑记录
     *
     * @param entity 实体对象
     * @return
     */
    JsonResult edit(User entity);

    /**
     * 根据ID删除记录
     *
     * @param ids 记录ID
     * @return
     */
    JsonResult deleteByIds(Integer[] ids);

    /**
     * 设置状态
     *
     * @param entity 实体对象
     * @return
     */
    JsonResult setStatus(User entity);

    /**
     * 获取用户信息
     *
     * @return
     */
    JsonResult getUserInfo();

    /**
     * 修改密码
     *
     * @param updatePwdDto 参数
     * @return
     */
    JsonResult updatePwd(UpdatePwdDto updatePwdDto);

    /**
     * 更新个人资料
     *
     * @param updateUserInfoDto 参数
     * @return
     */
    JsonResult updateUserInfo(UpdateUserInfoDto updateUserInfoDto);

    /**
     * 重置密码
     *
     * @param resetPwdDto 参数
     * @return
     */
    JsonResult resetPwd(ResetPwdDto resetPwdDto);

}
