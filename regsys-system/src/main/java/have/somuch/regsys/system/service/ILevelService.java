package have.somuch.regsys.system.service;

import have.somuch.regsys.common.common.IBaseService;
import have.somuch.regsys.common.utils.JsonResult;
import have.somuch.regsys.system.entity.Level;
import have.somuch.regsys.system.query.LevelQuery;
import have.somuch.regsys.system.vo.level.LevelInfoVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 职级表 服务类
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-11-02
 */
public interface ILevelService extends IBaseService<Level> {

    /**
     * 获取职级列表
     *
     * @return
     */
    JsonResult getLevelList();

    /**
     * 导入Excel数据
     *
     * @param request 网络请求
     * @param name    目录名称
     * @return
     */
    JsonResult importExcel(HttpServletRequest request, String name);

    /**
     * 导出Excel数据
     *
     * @param levelQuery 查询条件
     * @return
     */
    List<LevelInfoVo> exportExcel(LevelQuery levelQuery);

}
