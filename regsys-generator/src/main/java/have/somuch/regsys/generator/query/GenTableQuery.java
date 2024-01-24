package have.somuch.regsys.generator.query;

import lombok.Data;

/**
 * 表生成查询条件
 */
@Data
public class GenTableQuery {

    /**
     * 页码
     */
    private Integer page;

    /**
     * 每页数
     */
    private Integer limit;

    /**
     * 表名
     */
    private String tableName;

    /**
     * 表描述
     */
    private String tableComment;

}
