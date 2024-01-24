package have.somuch.regsys.generator.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import have.somuch.regsys.generator.entity.GenTable;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import have.somuch.regsys.generator.query.GenTableQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 代码生成业务表 Mapper 接口
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-11-06
 */
public interface GenTableMapper extends BaseMapper<GenTable> {

    /**
     * 获取业务表列表
     *
     * @param page  分页信息
     * @param param 参数
     * @return
     */
    IPage<GenTable> selectGenTableList(IPage<GenTable> page, @Param("param") GenTableQuery param);

    /**
     * 获取数据库表
     *
     * @param page  分页信息
     * @param param 查询条件
     * @return
     */
    IPage<GenTable> selectDbTableList(IPage<GenTable> page, @Param("param") GenTableQuery param);

    /**
     * 根据表明获取数据库列表
     *
     * @param tableNames 数据库名
     * @return
     */
    List<GenTable> selectDbTableListByNames(String[] tableNames);

    /**
     * 插入数据表
     *
     * @param genTable 待生成数据表
     * @return
     */
    int insertGenTable(GenTable genTable);

    /**
     * 根据表ID获取表信息
     *
     * @param tableId 表ID
     * @return
     */
    GenTable selectGenTableById(Integer tableId);

    /**
     * 修改业务表信息
     *
     * @param genTable 业务表
     * @return
     */
    int updateGenTable(GenTable genTable);


    /**
     * 根据表名查询业务表
     *
     * @param tableName 表名
     * @return
     */
    GenTable selectGenTableByName(String tableName);

}
