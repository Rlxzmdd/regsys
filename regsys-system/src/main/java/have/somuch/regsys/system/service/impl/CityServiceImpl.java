package have.somuch.regsys.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import have.somuch.regsys.common.common.BaseQuery;
import have.somuch.regsys.common.common.BaseServiceImpl;
import have.somuch.regsys.common.utils.JsonResult;
import have.somuch.regsys.common.utils.StringUtils;
import have.somuch.regsys.system.entity.City;
import have.somuch.regsys.system.mapper.CityMapper;
import have.somuch.regsys.system.query.CityQuery;
import have.somuch.regsys.system.service.ICityService;
import have.somuch.regsys.system.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 高德城市表 服务实现类
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-11-03
 */
@Service
public class CityServiceImpl extends BaseServiceImpl<CityMapper, City> implements ICityService {

    @Autowired
    private CityMapper cityMapper;

    /**
     * 获取城市列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        CityQuery cityQuery = (CityQuery) query;
        // 查询条件
        QueryWrapper<City> queryWrapper = new QueryWrapper<>();
        // 父级ID
        if (StringUtils.isNull(cityQuery.getPid())) {
            queryWrapper.eq("pid", 0);
        } else {
            queryWrapper.eq("pid", cityQuery.getPid());
        }
        // 城市名称
        if (!StringUtils.isEmpty(cityQuery.getName())) {
            queryWrapper.like("name", cityQuery.getName());
        }
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByAsc("sort");

        // 查询分页数据
        List<City> cityList = cityMapper.selectList(queryWrapper);
        cityList.forEach(item -> {
            if (item.getLevel() <= 2) {
                item.setHasChildren(true);
            }
        });
        return JsonResult.success(cityList);
    }

    /**
     * 添加或编辑
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult edit(City entity) {
        if (StringUtils.isNotNull(entity.getId()) && entity.getId() > 0) {
            entity.setUpdateUser(ShiroUtils.getUserId());
        } else {
            entity.setCreateUser(ShiroUtils.getUserId());
        }
        return super.edit(entity);
    }
}
