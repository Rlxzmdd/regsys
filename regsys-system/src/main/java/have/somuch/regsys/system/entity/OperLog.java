package have.somuch.regsys.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import have.somuch.regsys.common.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 操作日志记录
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-10-31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_oper_log")
public class OperLog extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 日志标题
     */
    private String title;

    /**
     * 业务类型（0其它 1新增 2修改 3删除）
     */
    private Integer logType;

    /**
     * 操作方法
     */
    private String operMethod;

    /**
     * 请求方式
     */
    private String requestMethod;

    /**
     * 操作类型：0其他 1后台用户 2WAP用户
     */
    private Integer operType;

    /**
     * 操作人员
     */
    private String operName;

    /**
     * 请求URL
     */
    private String operUrl;

    /**
     * 主机地址
     */
    private String operIp;

    /**
     * 操作地点
     */
    private String operLocation;

    /**
     * 请求参数
     */
    private String operParam;

    /**
     * 返回参数
     */
    private String jsonResult;

    /**
     * 操作状态（0正常 1异常）
     */
    private Integer status;

    /**
     * 备注
     */
    private String note;


}
