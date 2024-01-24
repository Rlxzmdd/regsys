package have.somuch.regsys.admin.service;

import have.somuch.regsys.common.utils.JsonResult;
import have.somuch.regsys.admin.entity.Example;
import have.somuch.regsys.common.common.IBaseService;

/**
 * <p>
 * 演示案例一 服务类
 * </p>
 *
 * @author 鲲鹏
 * @since 2024-01-24
 */
public interface IExampleService extends IBaseService<Example> {

    /**
     * 设置是否VIP
     *
     * @param entity 实体对象
     * @return
     */
    JsonResult setIsVip(Example entity);


}