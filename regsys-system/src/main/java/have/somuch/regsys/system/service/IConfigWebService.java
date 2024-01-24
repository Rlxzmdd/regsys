package have.somuch.regsys.system.service;

import have.somuch.regsys.common.utils.JsonResult;

import java.util.Map;

/**
 * <p>
 * 网站配置 服务类
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-11-06
 */
public interface IConfigWebService {

    /**
     * 获取配置列表
     *
     * @return
     */
    JsonResult getList();

    /**
     * 保存配置信息
     *
     * @param info 表单信息
     * @return
     */
    JsonResult edit(Map<String, Object> info);

}
