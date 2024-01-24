package have.somuch.regsys.admin.vo.example;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 演示案例一列表Vo
 * </p>
 *
 * @author 鲲鹏
 * @since 2024-01-24
 */
@Data
public class ExampleListVo {

    /**
    * 演示案例一ID
    */
    private Integer id;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 案例名称
     */
    private String name;

    /**
     * 类型：1京东 2淘宝 3拼多多 4唯品会
     */
    private Integer type;


    /**
    * 类型描述
    */
    private String typeName;

    /**
     * 是否VIP：1是 2否
     */
    private Integer isVip;


    /**
    * 是否VIP描述
    */
    private String isVipName;

    /**
     * 计划时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date planTime;

    /**
     * 状态：1正常 2停用
     */
    private Integer status;


    /**
    * 状态描述
    */
    private String statusName;

    /**
     * 显示顺序
     */
    private Integer sort;

    /**
     * 备注
     */
    private String note;

    /**
     * 添加人
     */
    private Integer createUser;

    /**
     * 添加时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新人
     */
    private Integer updateUser;

    /**
     * 更新时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 有效标记
     */
    private Integer mark;

}