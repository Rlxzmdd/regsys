package have.somuch.regsys.system.service;

import have.somuch.regsys.common.common.IBaseService;
import have.somuch.regsys.system.entity.ItemCate;

import java.util.List;

/**
 * <p>
 * 栏目管理表 服务类
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-11-07
 */
public interface IItemCateService extends IBaseService<ItemCate> {

    /**
     * 获取栏目名称
     *
     * @param cateId    栏目ID
     * @param delimiter 分隔符
     * @return
     */
    String getCateName(Integer cateId, String delimiter);

    /**
     * 获取栏目列表
     *
     * @return
     */
    List<ItemCate> getCateList();

}
