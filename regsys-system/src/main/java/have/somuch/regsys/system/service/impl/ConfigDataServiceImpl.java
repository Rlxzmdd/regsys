package have.somuch.regsys.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import have.somuch.regsys.common.common.BaseQuery;
import have.somuch.regsys.common.common.BaseServiceImpl;
import have.somuch.regsys.common.utils.CommonUtils;
import have.somuch.regsys.common.utils.JsonResult;
import have.somuch.regsys.common.utils.StringUtils;
import have.somuch.regsys.system.entity.ConfigData;
import have.somuch.regsys.system.mapper.ConfigDataMapper;
import have.somuch.regsys.system.query.ConfigDataQuery;
import have.somuch.regsys.system.service.IConfigDataService;
import have.somuch.regsys.system.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 配置表 服务实现类
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-11-06
 */
@Service
public class ConfigDataServiceImpl extends BaseServiceImpl<ConfigDataMapper, ConfigData> implements IConfigDataService {

    @Autowired
    private ConfigDataMapper configDataMapper;

    /**
     * 获取配置列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        ConfigDataQuery configQuery = (ConfigDataQuery) query;
        // 查询条件
        QueryWrapper<ConfigData> queryWrapper = new QueryWrapper<>();
        // 配置ID
        if (StringUtils.isNotNull(configQuery.getConfigId())) {
            queryWrapper.eq("config_id", configQuery.getConfigId());
        }
        // 配置标题
        if (!StringUtils.isEmpty(configQuery.getTitle())) {
            queryWrapper.like("title", configQuery.getTitle());
        }
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByAsc("sort");

        // 查询分页数据
        IPage<ConfigData> page = new Page<>(configQuery.getPage(), configQuery.getLimit());
        IPage<ConfigData> pageData = configDataMapper.selectPage(page, queryWrapper);
        pageData.convert(x -> {
            // 单图
            if (x.getType().equals("image")) {
                x.setValue(CommonUtils.getImageURL(x.getValue()));
            }
            // 多图
            if (x.getType().equals("images")) {
                String[] strings = x.getValue().split(",");
                List<String> stringList = new ArrayList<>();
                for (String string : strings) {
                    stringList.add(CommonUtils.getImageURL(string));
                }
                x.setValueList(stringList.toArray(new String[stringList.size()]));
            }
            return x;
        });
        return JsonResult.success(pageData);
    }

    /**
     * 设置状态
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult setStatus(ConfigData entity) {
        ConfigData config = new ConfigData();
        config.setId(entity.getId());
        config.setStatus(entity.getStatus());
        config.setUpdateUser(ShiroUtils.getUserId());
        return super.setStatus(config);
    }

}
