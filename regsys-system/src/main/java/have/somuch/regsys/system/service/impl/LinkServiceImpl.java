package have.somuch.regsys.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import have.somuch.regsys.common.common.BaseQuery;
import have.somuch.regsys.common.common.BaseServiceImpl;
import have.somuch.regsys.common.config.CommonConfig;
import have.somuch.regsys.common.utils.CommonUtils;
import have.somuch.regsys.common.utils.JsonResult;
import have.somuch.regsys.common.utils.StringUtils;
import have.somuch.regsys.system.entity.Link;
import have.somuch.regsys.system.mapper.LinkMapper;
import have.somuch.regsys.system.query.LinkQuery;
import have.somuch.regsys.system.service.ILinkService;
import have.somuch.regsys.system.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 友链管理表 服务实现类
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-11-07
 */
@Service
public class LinkServiceImpl extends BaseServiceImpl<LinkMapper, Link> implements ILinkService {

    @Autowired
    private LinkMapper linkMapper;

    /**
     * 获取友链列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        LinkQuery linkQuery = (LinkQuery) query;
        // 查询条件
        QueryWrapper<Link> queryWrapper = new QueryWrapper<>();
        // 友链名称
        if (!StringUtils.isEmpty(linkQuery.getName())) {
            queryWrapper.like("name", linkQuery.getName());
        }
        // 友链类型
        if (StringUtils.isNotNull(linkQuery.getType())) {
            queryWrapper.eq("type", linkQuery.getType());
        }
        // 使用平台
        if (StringUtils.isNotNull(linkQuery.getPlatform())) {
            queryWrapper.eq("platform", linkQuery.getPlatform());
        }
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByAsc("sort");

        // 查询分页数据
        IPage<Link> page = new Page<>(linkQuery.getPage(), linkQuery.getLimit());
        IPage<Link> pageData = linkMapper.selectPage(page, queryWrapper);
        pageData.convert(x -> {
            // 友链图片
            if (!StringUtils.isEmpty(x.getImage())) {
                x.setImage(CommonUtils.getImageURL(x.getImage()));
            }
            return x;
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
    public JsonResult edit(Link entity) {
        if (StringUtils.isNotNull(entity.getId()) && entity.getId() > 0) {
            entity.setUpdateUser(ShiroUtils.getUserId());
        } else {
            entity.setCreateUser(ShiroUtils.getUserId());
        }
        // 图片处理
        if (!StringUtils.isEmpty(entity.getImage()) && entity.getImage().contains(CommonConfig.imageURL)) {
            entity.setImage(entity.getImage().replaceAll(CommonConfig.imageURL, ""));
        }
        return super.edit(entity);
    }

    /**
     * 设置状态
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult setStatus(Link entity) {
        Link link = new Link();
        link.setId(entity.getId());
        link.setStatus(entity.getStatus());
        return super.setStatus(link);
    }
}
