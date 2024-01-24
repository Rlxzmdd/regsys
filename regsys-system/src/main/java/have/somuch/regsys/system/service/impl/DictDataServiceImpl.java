package have.somuch.regsys.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import have.somuch.regsys.common.common.BaseQuery;
import have.somuch.regsys.common.common.BaseServiceImpl;
import have.somuch.regsys.common.utils.JsonResult;
import have.somuch.regsys.common.utils.StringUtils;
import have.somuch.regsys.system.entity.DictData;
import have.somuch.regsys.system.mapper.DictDataMapper;
import have.somuch.regsys.system.query.DictDataQuery;
import have.somuch.regsys.system.service.IDictDataService;
import have.somuch.regsys.system.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 字典项管理表 服务实现类
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-11-01
 */
@Service
public class DictDataServiceImpl extends BaseServiceImpl<DictDataMapper, DictData> implements IDictDataService {

    @Autowired
    private DictDataMapper dictDataMapper;

    /**
     * 获取字典项列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        DictDataQuery dictQuery = (DictDataQuery) query;
        // 查询条件
        QueryWrapper<DictData> queryWrapper = new QueryWrapper<>();
        // 字典ID
        if (StringUtils.isNotNull(dictQuery.getDictId())) {
            queryWrapper.eq("dict_id", dictQuery.getDictId());
        }
        // 字典项名称
        if (!StringUtils.isEmpty(dictQuery.getName())) {
            queryWrapper.like("name", dictQuery.getName());
        }
        // 字典项值
        if (!StringUtils.isEmpty(dictQuery.getValue())) {
            queryWrapper.like("value", dictQuery.getValue());
        }
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByAsc("sort");

        // 查询分页数据
        IPage<DictData> page = new Page<>(dictQuery.getPage(), dictQuery.getLimit());
        IPage<DictData> pageData = dictDataMapper.selectPage(page, queryWrapper);
        return JsonResult.success(pageData);
    }

    /**
     * 添加或编辑
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult edit(DictData entity) {
        if (StringUtils.isNotNull(entity.getId()) && entity.getId() > 0) {
            entity.setUpdateUser(ShiroUtils.getUserId());
        } else {
            entity.setCreateUser(ShiroUtils.getUserId());
        }
        return super.edit(entity);
    }
}
