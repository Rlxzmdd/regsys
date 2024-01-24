package have.somuch.regsys.common.common;

import lombok.Data;

/**
 * 查询对象基类
 */
@Data
public class BaseQuery {
    /**
     * 页码
     */
    private Integer page;

    /**
     * 每页数
     */
    private Integer limit;
}
