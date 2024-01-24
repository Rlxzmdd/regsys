package have.somuch.regsys.generator.service;

import have.somuch.regsys.generator.entity.GenTableColumn;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 代码生成业务表字段 服务类
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-11-06
 */
public interface IGenTableColumnService extends IService<GenTableColumn> {

    /**
     * 查询表字段信息
     *
     * @param tableId 表ID
     * @return
     */
    List<GenTableColumn> selectGenTableColumnListByTableId(Integer tableId);

}
