package have.somuch.regsys.common.common;

import com.baomidou.mybatisplus.extension.service.IService;
import have.somuch.regsys.common.utils.JsonResult;

import java.io.Serializable;
import java.util.List;


public interface IBaseService<T> extends IService<T> {

    /**
     * 根据查询条件获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    JsonResult getList(BaseQuery query);

    /**
     * 根据ID获取记录信息
     *
     * @param id 记录ID
     * @return
     */
    JsonResult info(Integer id);

    /**
     * 根据ID获取记录信息
     *
     * @param id 记录ID
     * @return
     */

    Object getInfo(Serializable id);

    /**
     * 根据实体对象添加记录
     *
     * @param entity 实体对象
     * @return
     */
    JsonResult add(T entity);

    /**
     * 根据实体对象更新记录
     *
     * @param entity 实体对象
     * @return
     */
    JsonResult update(T entity);

    /**
     * 根据实体对象添加、编辑记录
     *
     * @param entity 实体对象
     * @return
     */
    JsonResult edit(T entity);

    /**
     * 删除记录
     *
     * @param entity 实体对象
     * @return
     */
    JsonResult delete(T entity);

    /**
     * 根据ID删除记录
     *
     * @param id 记录ID
     * @return
     */
    JsonResult deleteById(Integer id);

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
    JsonResult setStatus(T entity);

    /**
     * 导出Excel
     *
     * @return
     */
    List<T> exportExcel();

}
