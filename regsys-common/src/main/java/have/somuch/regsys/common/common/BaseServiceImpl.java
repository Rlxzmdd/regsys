package have.somuch.regsys.common.common;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import have.somuch.regsys.common.config.CommonConfig;
import have.somuch.regsys.common.utils.DateUtils;
import have.somuch.regsys.common.utils.JsonResult;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.List;

public class BaseServiceImpl<M extends BaseMapper<T>, T extends BaseEntity> extends ServiceImpl<M, T> implements IBaseService<T> {

    /**
     * 根据查询条件获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        return null;
    }

    /**
     * 根据ID获取记录信息
     *
     * @param id 记录ID
     * @return
     */
    @Override
    public JsonResult info(Integer id) {
        if (id == null && id <= 0) {
            return JsonResult.error("记录ID不能为空");
        }
        Object result = this.getInfo(id);
        return JsonResult.success(result);
    }

    /**
     * 根据ID获取记录信息
     *
     * @param id 记录ID
     * @return
     */
    @Override
    public Object getInfo(Serializable id) {
        T entity = this.getById(id);
        return entity;
    }

    /**
     * 传入实体对象添加记录
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult add(T entity) {
        if (CommonConfig.appDebug) {
            return JsonResult.error("演示环境禁止操作");
        }
        entity.setCreateTime(DateUtils.now());
        entity.setMark(1);
        boolean result = this.save(entity);
        if (!result) {
            return JsonResult.error();
        }
        return JsonResult.success();
    }

    /**
     * 传入实体对象更新记录
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult update(T entity) {
        if (CommonConfig.appDebug) {
            return JsonResult.error("演示环境禁止操作");
        }
        entity.setUpdateTime(DateUtils.now());
        boolean result = this.updateById(entity);
        if (!result) {
            return JsonResult.error();
        }
        return JsonResult.success();
    }

    /**
     * 根据实体对象添加、编辑记录
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult edit(T entity) {
        if (CommonConfig.appDebug) {
            return JsonResult.error("演示环境禁止操作");
        }
        if (entity == null) {
            return JsonResult.error("实体对象不存在");
        }
        if (entity.getId() != null && entity.getId() > 0) {
            // 修改记录
            return this.update(entity);
        } else {
            // 新增记录
            return this.add(entity);
        }
    }

    /**
     * 删除记录
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult delete(T entity) {
        if (CommonConfig.appDebug) {
            return JsonResult.error("演示环境禁止操作");
        }
        entity.setUpdateTime(DateUtils.now());
        entity.setMark(0);
        boolean result = this.updateById(entity);
        if (!result) {
            return JsonResult.error();
        }
        return JsonResult.success("删除成功");
    }

    /**
     * 根据ID删除记录
     *
     * @param id 记录ID
     * @return
     */
    @Override
    public JsonResult deleteById(Integer id) {
        if (CommonConfig.appDebug) {
            return JsonResult.error("演示环境禁止操作");
        }
        if (StringUtils.isEmpty(id)) {
            return JsonResult.error("记录ID不能为空");
        }
        // 设置Mark=0
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.set("mark", 0);
        updateWrapper.eq("id", id);
        boolean result = update(updateWrapper);
        if (!result) {
            return JsonResult.error();
        }
        return JsonResult.success("删除成功");
    }

    /**
     * 根据ID删除记录
     *
     * @param ids 记录ID
     * @return
     */
    @Override
    public JsonResult deleteByIds(Integer[] ids) {
        if (CommonConfig.appDebug) {
            return JsonResult.error("演示环境禁止操作");
        }
        if (StringUtils.isEmpty(ids)) {
            return JsonResult.error("记录ID不能为空");
        }
//        String[] item = ids.split(",");
        // 设置Mark=0
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.set("mark", 0);
        updateWrapper.in("id", ids);
        boolean result = update(updateWrapper);
        if (!result) {
            return JsonResult.error();
        }
        return JsonResult.success("删除成功");
    }

    /**
     * 设置状态
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult setStatus(T entity) {
        if (CommonConfig.appDebug) {
            return JsonResult.error("演示环境禁止操作");
        }
        return this.update(entity);
    }

    /**
     * 导出Excel
     *
     * @return
     */
    @Override
    public List<T> exportExcel() {
        return null;
    }
}
