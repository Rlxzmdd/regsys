package have.somuch.regsys.system.vo.configweb;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站配置项Vo
 * </p>
 *
 * @author 鲲鹏
 * @since 2021-03-27
 */
@Data
public class ConfigDataInfoVo {

    /**
     * 配置ID
     */
    private Integer id;

    /**
     * 配置标题
     */
    private String title;

    /**
     * 配置编码
     */
    private String code;

    /**
     * 配置值
     */
    private String value;

    /**
     * 配置值列表
     */
    private List<String> valueList;

    /**
     * 配置项
     */
    private String options;

    /**
     * 配置ID
     */
    private Integer configId;

    /**
     * 配置类型
     */
    private String type;

    /**
     * 状态：1正常 2停用
     */
    private Integer status;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 配置说明
     */
    private String note;

    /**
     * 配置项解析参数
     */
    private Map<Integer, String> param;

}
