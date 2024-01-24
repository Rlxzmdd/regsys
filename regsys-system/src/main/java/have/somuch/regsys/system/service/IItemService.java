package have.somuch.regsys.system.service;

import have.somuch.regsys.common.common.IBaseService;
import have.somuch.regsys.system.entity.Item;

import java.util.List;

/**
 * <p>
 * 站点配置表 服务类
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-11-07
 */
public interface IItemService extends IBaseService<Item> {

    /**
     * 获取站点列表
     *
     * @return
     */
    List<Item> getItemList();

}
