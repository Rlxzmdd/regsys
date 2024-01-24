package have.somuch.regsys.generator.dto;

import lombok.Data;

/**
 * 一键生成Dto
 */
@Data
public class GenTableDto {

    /**
     * 业务表名称(多个使用逗号“,”分隔)
     */
    private String tableNames;

}
