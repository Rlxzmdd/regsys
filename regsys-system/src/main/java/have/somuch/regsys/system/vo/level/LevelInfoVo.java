package have.somuch.regsys.system.vo.level;

import com.fasterxml.jackson.annotation.JsonFormat;
import have.somuch.regsys.common.annotation.Excel;
import have.somuch.regsys.common.annotation.Excel.ColumnType;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 职级Vo
 */
@Data
public class LevelInfoVo {

    /**
     * 职级ID
     */
    @Excel(name = "职级ID", cellType = ColumnType.NUMERIC)
    private Integer id;

    /**
     * 职级名称
     */
    @Excel(name = "职级名称")
    private String name;

    /**
     * 状态：1正常 2停用
     */
    @Excel(name = "职级状态", readConverterExp = "1=正常,2=排口")
    private Integer status;

    /**
     * 显示顺序
     */
    @Excel(name = "显示顺序", cellType = ColumnType.NUMERIC)
    private Integer sort;

    /**
     * 添加人
     */
    private Integer createUser;

    /**
     * 创建时间
     */
    @Excel(name = "创建时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 更新人
     */
    private Integer updateUser;

    /**
     * 更新时间
     */
    @Excel(name = "更新时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    /**
     * 有效标识
     */
    private Integer mark;

}
