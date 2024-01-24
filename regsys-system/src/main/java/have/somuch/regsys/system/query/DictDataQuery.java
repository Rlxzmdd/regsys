package have.somuch.regsys.system.query;

import have.somuch.regsys.common.common.BaseQuery;
import lombok.Data;

/**
 * 字典数据查询条件
 */
@Data
public class DictDataQuery extends BaseQuery {

    /**
     * 字典项名称
     */
    private String name;

    /**
     * 字典项值
     */
    private String value;

    /**
     * 字典ID
     */
    private Integer dictId;

}
