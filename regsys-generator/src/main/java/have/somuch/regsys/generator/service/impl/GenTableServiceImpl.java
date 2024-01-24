package have.somuch.regsys.generator.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import have.somuch.regsys.common.exception.CustomException;
import have.somuch.regsys.common.utils.DateUtils;
import have.somuch.regsys.common.utils.JsonResult;
import have.somuch.regsys.common.utils.StringUtils;
import have.somuch.regsys.generator.constant.GenConstants;
import have.somuch.regsys.generator.entity.GenTable;
import have.somuch.regsys.generator.entity.GenTableColumn;
import have.somuch.regsys.generator.mapper.GenTableColumnMapper;
import have.somuch.regsys.generator.mapper.GenTableMapper;
import have.somuch.regsys.generator.query.GenTableQuery;
import have.somuch.regsys.generator.service.IGenTableService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import have.somuch.regsys.generator.utils.CodeGenerateUtils;
import have.somuch.regsys.generator.utils.GenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 代码生成业务表 服务实现类
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-11-06
 */
@Service
public class GenTableServiceImpl extends ServiceImpl<GenTableMapper, GenTable> implements IGenTableService {

    @Autowired
    private GenTableMapper genTableMapper;

    @Autowired
    private GenTableColumnMapper genTableColumnMapper;

    @Autowired
    private CodeGenerateUtils codeGenerateUtils;

    /**
     * 获取业务表列表
     *
     * @param genTableQuery 查询条件
     * @return
     */
    @Override
    public JsonResult getList(GenTableQuery genTableQuery) {
        IPage<GenTable> page = new Page<>(genTableQuery.getPage(), genTableQuery.getLimit());
        IPage<GenTable> pageData = genTableMapper.selectGenTableList(page, genTableQuery);
        return JsonResult.success(pageData, "操作成功");
    }

    /**
     * 获取数据库表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public IPage<GenTable> genDbTableList(GenTableQuery query) {
        IPage<GenTable> page = new Page<>(query.getPage(), query.getLimit());
        return genTableMapper.selectDbTableList(page, query);
    }

    /**
     * 查询据库列表
     *
     * @param tableNames 表数组
     * @return
     */
    @Override
    public List<GenTable> selectDbTableListByNames(String[] tableNames) {
        return genTableMapper.selectDbTableListByNames(tableNames);
    }

    /**
     * 导入表结构
     *
     * @param tableList 导入表列表
     */
    @Transactional
    @Override
    public void importGenTable(List<GenTable> tableList) {
        String operName = "内部人员";//SecurityUtils.getUsername();
        for (GenTable table : tableList) {
            try {
                String tableName = table.getTableName();
                GenUtils.initTable(table, operName);
                int row = genTableMapper.insertGenTable(table);
                if (row > 0) {
                    // 保存列信息
                    List<GenTableColumn> genTableColumns = genTableColumnMapper.selectDbTableColumnsByName(tableName);
                    for (GenTableColumn column : genTableColumns) {
                        GenUtils.initColumnField(column, table);
                        genTableColumnMapper.insertGenTableColumn(column);
                    }
                }
            } catch (Exception e) {
                log.error("表名 " + table.getTableName() + " 导入失败：", e);
            }
        }
    }

    /**
     * 根据表ID获取表信息
     *
     * @param tableId 表ID
     * @return
     */
    @Override
    public GenTable selectGenTableById(Integer tableId) {
        GenTable genTable = genTableMapper.selectGenTableById(tableId);
        setTableFromOptions(genTable);
        return genTable;
    }

    /**
     * 设置代码生成其他选项
     *
     * @param genTable 待生成表
     */
    public void setTableFromOptions(GenTable genTable) {
        JSONObject paramsObj = JSONObject.parseObject(genTable.getOptions());
        if (StringUtils.isNotNull(paramsObj)) {
            String treeCode = paramsObj.getString(GenConstants.TREE_CODE);
            String treeParentCode = paramsObj.getString(GenConstants.TREE_PARENT_CODE);
            String treeName = paramsObj.getString(GenConstants.TREE_NAME);
            genTable.setTreeCode(treeCode);
            genTable.setTreeParentCode(treeParentCode);
            genTable.setTreeName(treeName);
        }
    }

    /**
     * 验证编辑信息
     *
     * @param genTable 生成表
     */
    @Override
    public void validateEdit(GenTable genTable) {
        if (GenConstants.TPL_TREE.equals(genTable.getTplCategory())) {
            String options = "{}";//JSON.toJSONString(genTable.getParams());
            JSONObject paramsObj = JSONObject.parseObject(options);
            if (StringUtils.isEmpty(paramsObj.getString(GenConstants.TREE_CODE))) {
                throw new CustomException("树编码字段不能为空");
            } else if (StringUtils.isEmpty(paramsObj.getString(GenConstants.TREE_PARENT_CODE))) {
                throw new CustomException("树父编码字段不能为空");
            } else if (StringUtils.isEmpty(paramsObj.getString(GenConstants.TREE_NAME))) {
                throw new CustomException("树名称字段不能为空");
            }
        }
    }

    /**
     * 编辑业务表生成信息
     *
     * @param genTable 业务表
     */
    @Override
    public void updateGenTable(GenTable genTable) {
        String options = "{}";//JSON.toJSONString(genTable.getParams());
        genTable.setOptions(options);
        int row = genTableMapper.updateGenTable(genTable);
        if (row > 0) {
            for (GenTableColumn cenTableColumn : genTable.getColumns()) {
                genTableColumnMapper.updateGenTableColumn(cenTableColumn);
            }
        }
    }

    /**
     * 生成数据表
     *
     * @param tableNames 数据表
     * @return
     */
    @Override
    public JsonResult generatorCode(String[] tableNames) {
        Integer totalNum = 0;
        for (String tableName : tableNames) {
            // 查询表信息
            GenTable tableInfo = genTableMapper.selectGenTableByName(tableName);
            try {
                // 生成文件
                codeGenerateUtils.generateFile(tableInfo.getTableName(), tableInfo.getTableComment());
                totalNum++;
            } catch (Exception e) {

            }
        }
        return JsonResult.success(String.format("本地共生成【%s】个模块", totalNum));
    }

    /**
     * 删除业务表
     *
     * @param ids 业务表ID
     * @return
     */
    @Override
    public JsonResult delete(Integer[] ids) {
        Integer totalNum = 0;
        for (Integer id : ids) {
            GenTable entity = genTableMapper.selectById(id);
            if (entity == null) {
                return JsonResult.error("业务表不存在");
            }
            entity.setUpdateUser(0);
            entity.setUpdateTime(DateUtils.now());
            entity.setMark(0);
            boolean result = updateById(entity);
            if (result) {
                totalNum++;
            }
        }
        if (totalNum != ids.length) {
            return JsonResult.error("删除失败");
        }
        return JsonResult.success("删除成功");
    }
}
