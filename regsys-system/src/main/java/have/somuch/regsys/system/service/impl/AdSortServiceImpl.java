package have.somuch.regsys.system.service.impl;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import have.somuch.regsys.common.common.BaseQuery;
import have.somuch.regsys.common.common.BaseServiceImpl;
import have.somuch.regsys.common.utils.JsonResult;
import have.somuch.regsys.common.utils.StringUtils;
import have.somuch.regsys.system.entity.AdSort;
import have.somuch.regsys.system.mapper.AdSortMapper;
import have.somuch.regsys.system.query.AdSortQuery;
import have.somuch.regsys.system.service.IAdSortService;
import have.somuch.regsys.system.service.IItemCateService;
import have.somuch.regsys.system.utils.ShiroUtils;
import have.somuch.regsys.system.vo.adsort.AdSortListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 广告位管理表 服务实现类
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-11-07
 */
@Service
public class AdSortServiceImpl extends BaseServiceImpl<AdSortMapper, AdSort> implements IAdSortService {

    @Autowired
    private AdSortMapper adSortMapper;
    @Autowired
    private IItemCateService itemCateService;

    /**
     * 获取广告位列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        AdSortQuery adSortQuery = (AdSortQuery) query;
        // 查询条件
        QueryWrapper<AdSort> queryWrapper = new QueryWrapper<>();
        // 广告位名称
        if (!StringUtils.isEmpty(adSortQuery.getName())) {
            queryWrapper.like("name", adSortQuery.getName());
        }
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByAsc("sort");

        // 查询分页数据
        IPage<AdSort> page = new Page<>(adSortQuery.getPage(), adSortQuery.getLimit());
        IPage<AdSort> pageData = adSortMapper.selectPage(page, queryWrapper);
        pageData.convert(x -> {
            AdSortListVo adSortListVo = Convert.convert(AdSortListVo.class, x);
            // 栏目名称
            if (x.getCateId() > 0) {
                String cateName = itemCateService.getCateName(x.getCateId(), ">>");
                adSortListVo.setCateName(cateName);
            }
            return adSortListVo;
        });
        return JsonResult.success(pageData);
    }

    /**
     * 添加或编辑
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult edit(AdSort entity) {
        if (StringUtils.isNotNull(entity.getId()) && entity.getId() > 0) {
            entity.setUpdateUser(ShiroUtils.getUserId());
        } else {
            entity.setCreateUser(ShiroUtils.getUserId());
        }
        return super.edit(entity);
    }

    /**
     * 获取广告位列表
     *
     * @return
     */
    @Override
    public JsonResult getAdSortList() {
        QueryWrapper<AdSort> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByAsc("sort");
        return JsonResult.success(list(queryWrapper));
    }
}
