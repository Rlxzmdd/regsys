package have.somuch.regsys.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import have.somuch.regsys.common.common.BaseQuery;
import have.somuch.regsys.common.common.BaseServiceImpl;
import have.somuch.regsys.common.config.UploadFileConfig;
import have.somuch.regsys.system.entity.Level;
import have.somuch.regsys.system.mapper.LevelMapper;
import have.somuch.regsys.system.query.LevelQuery;
import have.somuch.regsys.system.service.ILevelService;
import have.somuch.regsys.system.utils.ShiroUtils;
import have.somuch.regsys.system.vo.level.LevelInfoVo;
import have.somuch.regsys.common.utils.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 职级表 服务实现类
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-11-02
 */
@Service
public class LevelServiceImpl extends BaseServiceImpl<LevelMapper, Level> implements ILevelService {

    @Autowired
    private LevelMapper levelMapper;

    /**
     * 获取职级列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        LevelQuery levelQuery = (LevelQuery) query;
        // 查询条件
        QueryWrapper<Level> queryWrapper = new QueryWrapper<>();
        // 职级名称
        if (!StringUtils.isEmpty(levelQuery.getName())) {
            queryWrapper.like("name", levelQuery.getName());
        }
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByAsc("sort");

        // 查询分页数据
        IPage<Level> page = new Page<>(levelQuery.getPage(), levelQuery.getLimit());
        IPage<Level> pageData = levelMapper.selectPage(page, queryWrapper);
        return JsonResult.success(pageData);
    }

    /**
     * 添加或编辑
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult edit(Level entity) {
        if (StringUtils.isNotNull(entity.getId()) && entity.getId() > 0) {
            entity.setUpdateUser(ShiroUtils.getUserId());
        } else {
            entity.setCreateUser(ShiroUtils.getUserId());
        }
        return super.edit(entity);
    }

    /**
     * 获取职级列表
     *
     * @return
     */
    @Override
    public JsonResult getLevelList() {
        QueryWrapper<Level> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", 1);
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByAsc("sort");
        List<Level> levelList = list(queryWrapper);
        return JsonResult.success(levelList);
    }

    /**
     * 导入Excel数据
     *
     * @param request 网络请求
     * @param name    目录名称
     * @return
     */
    @Override
    public JsonResult importExcel(HttpServletRequest request, String name) {
        // 上传文件
        UploadUtils uploadUtils = new UploadUtils();
        uploadUtils.setDirName("files");
        Map<String, Object> result = uploadUtils.uploadFile(request, name);
        List<String> imageList = (List<String>) result.get("image");

        // 文件路径
        String filePath = UploadFileConfig.uploadFolder + imageList.get(imageList.size() - 1);
        // 读取文件
        List<Object> rows = ExcelUtil.readMoreThan1000RowBySheet(filePath, null);
        if (CollectionUtils.isEmpty(rows)) {
            return JsonResult.error("文件读取失败");
        }
        int totalNum = 0;
        for (int i = 1; i < rows.size(); i++) {
            // 排除第一行
            String info = rows.get(i).toString();
            if (info.length() <= 2) {
                continue;
            }

            info = info.substring(1, info.length() - 1);
            String[] cloumns = info.split(",\\s+");
            if (cloumns.length != 3) {
                continue;
            }

            // 插入数据
            Level level = new Level();
            level.setName(cloumns[0]);
            level.setStatus(cloumns[1].equals("正常") ? 1 : 2);
            level.setSort(StringUtils.isNull(cloumns[2]) ? 0 : Integer.valueOf(cloumns[2]));
            level.setCreateUser(ShiroUtils.getUserId());
            level.setCreateTime(DateUtils.now());
            int count = levelMapper.insert(level);
            if (count == 1) {
                totalNum++;
            }
        }
        return JsonResult.success(null, String.format("本次共导入数据【%s】条", totalNum));
    }

    /**
     * 导出Excel
     *
     * @param levelQuery 查询条件
     * @return
     */
    @Override
    public List<LevelInfoVo> exportExcel(LevelQuery levelQuery) {
        // 查询条件
        QueryWrapper<Level> queryWrapper = new QueryWrapper<>();
        // 职级名称
        if (!StringUtils.isEmpty(levelQuery.getName())) {
            queryWrapper.like("name", levelQuery.getName());
        }
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByAsc("sort");

        // 查询分页数据
        List<Level> levelList = levelMapper.selectList(queryWrapper);
        List<LevelInfoVo> levelInfoVoList = new ArrayList<>();
        if (!levelList.isEmpty()) {
            levelList.forEach(item -> {
                LevelInfoVo levelInfoVo = new LevelInfoVo();
                BeanUtils.copyProperties(item, levelInfoVo);
                levelInfoVoList.add(levelInfoVo);
            });
        }
        return levelInfoVoList;
    }
}
