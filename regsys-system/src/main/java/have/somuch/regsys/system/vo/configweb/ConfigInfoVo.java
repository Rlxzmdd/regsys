package have.somuch.regsys.system.vo.configweb;

import lombok.Data;

import java.util.List;

/**
 * <p>
 * 网站配置表单Vo
 * </p>
 *
 * @author 鲲鹏
 * @since 2021-03-27
 */
@Data
public class ConfigInfoVo {

    /**
     * 配置ID
     */
    private Integer configId;

    /**
     * 配置名称
     */
    private String configName;

    /**
     * 配置数据列表
     */
    private List<ConfigDataInfoVo> dataList;

}
