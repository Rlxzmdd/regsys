package have.somuch.regsys.generator.service.impl;

import have.somuch.regsys.generator.entity.GenTableColumn;
import have.somuch.regsys.generator.mapper.GenTableColumnMapper;
import have.somuch.regsys.generator.service.IGenTableColumnService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 代码生成业务表字段 服务实现类
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-11-06
 */
@Service
public class GenTableColumnServiceImpl extends ServiceImpl<GenTableColumnMapper, GenTableColumn> implements IGenTableColumnService {

    @Autowired
    private GenTableColumnMapper genTableColumnMapper;

    /**
     * 获取表字段信息
     *
     * @param tableId 表ID
     * @return
     */
    @Override
    public List<GenTableColumn> selectGenTableColumnListByTableId(Integer tableId) {
        return genTableColumnMapper.selectGenTableColumnListByTableId(tableId);
    }

}
