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
import have.somuch.regsys.system.entity.Item;
import have.somuch.regsys.system.mapper.ItemMapper;
import have.somuch.regsys.system.query.ItemQuery;
import have.somuch.regsys.system.service.IItemService;
import have.somuch.regsys.system.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 站点配置表 服务实现类
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-11-07
 */
@Service
public class ItemServiceImpl extends BaseServiceImpl<ItemMapper, Item> implements IItemService {

    @Autowired
    private ItemMapper itemMapper;

    /**
     * 获取站点列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        ItemQuery itemQuery = (ItemQuery) query;
        // 查询条件
        QueryWrapper<Item> queryWrapper = new QueryWrapper<>();
        // 站点名称
        if (!StringUtils.isEmpty(itemQuery.getName())) {
            queryWrapper.like("name", itemQuery.getName());
        }
        // 站点类型
        if (StringUtils.isNotNull(itemQuery.getType())) {
            queryWrapper.eq("type", itemQuery.getType());
        }
        // 站点状态
        if (StringUtils.isNotNull(itemQuery.getStatus())) {
            queryWrapper.eq("status", itemQuery.getStatus());
        }
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByAsc("sort");

        // 查询分页数据
        IPage<Item> page = new Page<>(itemQuery.getPage(), itemQuery.getLimit());
        IPage<Item> pageData = itemMapper.selectPage(page, queryWrapper);
        pageData.convert(x -> {
            // 站点图片
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
    public JsonResult edit(Item entity) {
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
     * 获取站点列表
     *
     * @return
     */
    @Override
    public List<Item> getItemList() {
        QueryWrapper<Item> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", 1);
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByAsc("sort");
        return list(queryWrapper);
    }

    /**
     * 设置状态
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult setStatus(Item entity) {
        Item item = new Item();
        item.setId(entity.getId());
        item.setStatus(entity.getStatus());
        return super.setStatus(item);
    }

}
