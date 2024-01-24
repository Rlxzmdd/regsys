package have.somuch.regsys.system.service;

import have.somuch.regsys.common.common.IBaseService;
import have.somuch.regsys.system.entity.OperLog;

/**
 * <p>
 * 操作日志记录 服务类
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-10-31
 */
public interface IOperLogService extends IBaseService<OperLog> {

    /**
     * 创建系统操作日志
     *
     * @param operLog 操作日志对象
     */
    public void insertOperlog(OperLog operLog);

}
