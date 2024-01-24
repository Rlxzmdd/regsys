package have.somuch.regsys.system.vo.article;

import lombok.Data;

/**
 * <p>
 * 文章管理表列表Vo
 * </p>
 *
 * @author 鲲鹏
 * @since 2021-10-11
 */
@Data
public class ArticleListVo {

    /**
     * 文章管理表ID
     */
    private Integer id;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 首张图片编号
     */
    private String cover;

    /**
     * 站点ID
     */
    private Integer itemId;

    /**
     * 站点名称
     */
    private String itemName;

    /**
     * 栏目ID
     */
    private Integer cateId;

    /**
     * 栏目列表
     */
    private String cateName;

    /**
     * 文章导读
     */
    private String intro;

    /**
     * 图集
     */
    private String imgs;

    /**
     * 图片列表
     */
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
     * 状态描述
     */
    private String statusName;

    /**
     * 浏览量
     */
    private Integer browse;

    /**
     * 排序号
     */
    private Integer sort;

    /**
     * 添加人
     */
    private Integer createUser;

    /**
     * 添加时间
     */
    private Integer createTime;

    /**
     * 更新人
     */
    private Integer updateUser;

    /**
     * 更新时间
     */
    private Integer updateTime;

    /**
     * 有效标记
     */
    private Integer mark;

}