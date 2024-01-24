package have.somuch.regsys.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import have.somuch.regsys.common.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * <p>
 * 文章管理表
 * </p>
 *
 * @author 鲲鹏
 * @since 2021-10-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_article")
public class Article extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章封面
     */
    private String cover;

    /**
     * 站点ID
     */
    private Integer itemId;

    /**
     * 栏目ID
     */
    private Integer cateId;

    /**
     * 文章导读
     */
    private String intro;

    /**
     * 图集序列化
     */
    private String imgs;

    /**
     * 图片列表
     */
    @TableField(exist = false)
    private String[] imgsList;

    /**
     * 文章内容
     */
    private String content;

    /**
     * 状态：1正常 2停用
     */
    private Integer status;

    /**
     * 浏览量
     */
    private Integer browse;

    /**
     * 排序号
     */
    private Integer sort;

}