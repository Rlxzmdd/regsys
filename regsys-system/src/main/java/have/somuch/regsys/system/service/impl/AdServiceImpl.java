package have.somuch.regsys.system.service.impl;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import have.somuch.regsys.common.common.BaseQuery;
import have.somuch.regsys.common.common.BaseServiceImpl;
import have.somuch.regsys.common.config.CommonConfig;
import have.somuch.regsys.common.utils.CommonUtils;
import have.somuch.regsys.common.utils.JsonResult;
import have.somuch.regsys.common.utils.StringUtils;
import have.somuch.regsys.system.entity.Ad;
import have.somuch.regsys.system.entity.AdSort;
import have.somuch.regsys.system.mapper.AdMapper;
import have.somuch.regsys.system.mapper.AdSortMapper;
import have.somuch.regsys.system.query.AdQuery;
import have.somuch.regsys.system.service.IAdService;
import have.somuch.regsys.system.utils.ShiroUtils;
import have.somuch.regsys.system.vo.ad.AdListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 广告管理表 服务实现类
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-11-07
 */
@Service
public class AdServiceImpl extends BaseServiceImpl<AdMapper, Ad> implements IAdService {

    @Autowired
    private AdMapper adMapper;
    @Autowired
    private AdSortMapper adSortMapper;

    /**
     * 获取广告列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        AdQuery adQuery = (AdQuery) query;
        // 查询条件
        QueryWrapper<Ad> queryWrapper = new QueryWrapper<>();
        // 广告标题
        if (!StringUtils.isEmpty(adQuery.getTitle())) {
            queryWrapper.like("title", adQuery.getTitle());
        }
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByAsc("sort");

        // 查询分页数据
        IPage<Ad> page = new Page<>(adQuery.getPage(), adQuery.getLimit());
        IPage<Ad> pageData = adMapper.selectPage(page, queryWrapper);
        pageData.convert(x -> {
            AdListVo adListVo = Convert.convert(AdListVo.class, x);
            // 封面
            if (!StringUtils.isEmpty(x.getCover())) {
                adListVo.setCover(CommonUtils.getImageURL(x.getCover()));
            }
            // 广告位描述
            AdSort adSort = adSortMapper.selectById(x.getAdSortId());
            if (adSort != null) {
                adListVo.setAdSortName(String.format("%s => %s", adSort.getDescription(), adSort.getLocId().toString()));
            }
            return adListVo;
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
    public JsonResult edit(Ad entity) {
        if (StringUtils.isNotNull(entity.getId()) && entity.getId() > 0) {
            entity.setUpdateUser(ShiroUtils.getUserId());
        } else {
            entity.setCreateUser(ShiroUtils.getUserId());
        }
        // 图片处理
        if (!StringUtils.isEmpty(entity.getCover()) && entity.getCover().contains(CommonConfig.imageURL)) {
            entity.setCover(entity.getCover().replaceAll(CommonConfig.imageURL, ""));
        }
        return super.edit(entity);
    }
}
