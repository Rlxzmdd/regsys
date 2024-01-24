package have.somuch.regsys.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import have.somuch.regsys.common.common.BaseQuery;
import have.somuch.regsys.common.common.BaseServiceImpl;
import have.somuch.regsys.common.utils.JsonResult;
import have.somuch.regsys.common.utils.StringUtils;
import have.somuch.regsys.system.entity.Dict;
import have.somuch.regsys.system.mapper.DictMapper;
import have.somuch.regsys.system.query.DictQuery;
import have.somuch.regsys.system.service.IDictService;
import have.somuch.regsys.system.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 字典类型表 服务实现类
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-11-01
 */
@Service
public class DictServiceImpl extends BaseServiceImpl<DictMapper, Dict> implements IDictService {

    @Autowired
    private DictMapper dictMapper;

    /**
     * 获取字典列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        DictQuery dictQuery = (DictQuery) query;
        // 查询条件
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        // 字典名称
        if (!StringUtils.isEmpty(dictQuery.getName())) {
            queryWrapper.like("name", dictQuery.getName());
        }
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByAsc("sort");

        // 查询分页数据
        IPage<Dict> page = new Page<>(dictQuery.getPage(), dictQuery.getLimit());
        IPage<Dict> pageData = dictMapper.selectPage(page, queryWrapper);
        return JsonResult.success(pageData);
    }

    /**
     * 添加或编辑
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult edit(Dict entity) {
        if (StringUtils.isNotNull(entity.getId()) && entity.getId() > 0) {
            entity.setUpdateUser(ShiroUtils.getUserId());
        } else {
            entity.setCreateUser(ShiroUtils.getUserId());
        }
        return super.edit(entity);
    }
}
