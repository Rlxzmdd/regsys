package have.somuch.regsys.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import have.somuch.regsys.common.common.BaseQuery;
import have.somuch.regsys.common.common.BaseServiceImpl;
import have.somuch.regsys.common.config.CommonConfig;
import have.somuch.regsys.common.utils.CommonUtils;
import have.somuch.regsys.common.utils.JsonResult;
import have.somuch.regsys.common.utils.StringUtils;
import have.somuch.regsys.system.entity.Item;
import have.somuch.regsys.system.entity.ItemCate;
import have.somuch.regsys.system.mapper.ItemCateMapper;
import have.somuch.regsys.system.mapper.ItemMapper;
import have.somuch.regsys.system.query.ItemCateQuery;
import have.somuch.regsys.system.service.IItemCateService;
import have.somuch.regsys.system.utils.ShiroUtils;
import have.somuch.regsys.system.vo.itemcate.ItemCateListVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 栏目管理表 服务实现类
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-11-07
 */
@Service
public class ItemCateServiceImpl extends BaseServiceImpl<ItemCateMapper, ItemCate> implements IItemCateService {

    @Autowired
    private ItemCateMapper itemCateMapper;
    @Autowired
    private ItemMapper itemMapper;

    /**
     * 获取栏目列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        ItemCateQuery itemCateQuery = (ItemCateQuery) query;
        // 查询条件
        QueryWrapper<ItemCate> queryWrapper = new QueryWrapper<>();
        // 栏目名称
        if (!StringUtils.isEmpty(itemCateQuery.getName())) {
            queryWrapper.like("name", itemCateQuery.getName());
        }
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByAsc("sort");

        // 查询分页数据
        List<ItemCate> pageData = itemCateMapper.selectList(queryWrapper);
        List<ItemCateListVo> itemCateListVoList = new ArrayList<>();
        pageData.forEach(x -> {
            ItemCateListVo itemCateListVo = new ItemCateListVo();
            BeanUtils.copyProperties(x, itemCateListVo);
            // 封面
            itemCateListVo.setCover(CommonUtils.getImageURL(x.getCover()));
            // 所属站点
            if (StringUtils.isNotNull(x.getItemId())) {
                Item item = itemMapper.selectById(x.getItemId());
                if (item != null) {
                    itemCateListVo.setItemName(item.getName());
                }
            }
            itemCateListVoList.add(itemCateListVo);
        });
        return JsonResult.success(itemCateListVoList);
    }

    /**
     * 添加或编辑栏目
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult edit(ItemCate entity) {
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

    /**
     * 获取栏目名称
     *
     * @param cateId    栏目ID
     * @param delimiter 分隔符
     * @return
     */
    @Override
    public String getCateName(Integer cateId, String delimiter) {
        List<String> nameList = new ArrayList<>();
        while (cateId > 0) {
            ItemCate cateInfo = itemCateMapper.selectById(cateId);
            if (cateInfo != null) {
                nameList.add(cateInfo.getName());
                cateId = cateInfo.getPid();
            } else {
                cateId = 0;
            }
        }
        // 使用集合工具实现数组翻转
        Collections.reverse(nameList);
        return org.apache.commons.lang3.StringUtils.join(nameList.toArray(), delimiter);
    }

    /**
     * 获取栏目列表
     *
     * @return
     */
    @Override
    public List<ItemCate> getCateList() {
        QueryWrapper<ItemCate> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", 1);
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByAsc("sort");
        List<ItemCate> itemCateList = list(queryWrapper);
        return itemCateList;
    }
}
