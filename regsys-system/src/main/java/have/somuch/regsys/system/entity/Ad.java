package have.somuch.regsys.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import have.somuch.regsys.common.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * <p>
 * 广告管理表
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-11-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_ad")
public class Ad extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 广告标题
     */
    private String title;

    /**
     * 广告位ID
     */
    private Integer adSortId;

    /**
     * 广告图片
     */
    private String cover;

    /**
     * 广告格式：1图片 2文字 3视频 4推荐
     */
    private Integer type;

    /**
     * 广告描述
     */
    private String description;

    /**
     * 广告内容
     */
    private String content;

    /**
     * 广告链接
     */
    private String url;

    /**
     * 广告宽度
     */
    private Integer width;

    /**
     * 广告高度
     */
    private Integer height;

    /**
     * 开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;

    /**
     * 结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;

    /**
     * 广告点击次数
     */
    private Integer viewNum;

    /**
     * 状态：1在用 2停用
     */
    private Integer status;

    /**
     * 排序
     */
    private Integer sort;


}
