package have.somuch.regsys.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import have.somuch.regsys.common.common.BaseQuery;
import have.somuch.regsys.common.common.BaseServiceImpl;
import have.somuch.regsys.common.utils.JsonResult;
import have.somuch.regsys.common.utils.StringUtils;
import have.somuch.regsys.system.entity.Config;
import have.somuch.regsys.system.mapper.ConfigMapper;
import have.somuch.regsys.system.query.ConfigQuery;
import have.somuch.regsys.system.service.IConfigService;
import have.somuch.regsys.system.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 配置表 服务实现类
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-11-06
 */
@Service
public class ConfigServiceImpl extends BaseServiceImpl<ConfigMapper, Config> implements IConfigService {

    @Autowired
    private ConfigMapper configMapper;

    /**
     * 获取配置列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        ConfigQuery configQuery = (ConfigQuery) query;
        // 查询条件
        QueryWrapper<Config> queryWrapper = new QueryWrapper<>();
        // 配置名称
        if (!StringUtils.isEmpty(configQuery.getName())) {
            queryWrapper.like("name", configQuery.getName());
        }
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByAsc("sort");

        // 查询分页数据
        IPage<Config> page = new Page<>(configQuery.getPage(), configQuery.getLimit());
        IPage<Config> pageData = configMapper.selectPage(page, queryWrapper);
        return JsonResult.success(pageData);
    }

    /**
     * 添加或编辑
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult edit(Config entity) {
        if (StringUtils.isNotNull(entity.getId()) && entity.getId() > 0) {
            entity.setUpdateUser(ShiroUtils.getUserId());
        } else {
            entity.setCreateUser(ShiroUtils.getUserId());
        }
        return super.edit(entity);
    }
}
