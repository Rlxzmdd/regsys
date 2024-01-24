package have.somuch.regsys.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import have.somuch.regsys.admin.entity.MemberLevel;
import have.somuch.regsys.admin.mapper.MemberLevelMapper;
import have.somuch.regsys.admin.query.MemberLevelQuery;
import have.somuch.regsys.admin.service.IMemberLevelService;
import have.somuch.regsys.common.common.BaseQuery;
import have.somuch.regsys.common.common.BaseServiceImpl;
import have.somuch.regsys.common.utils.JsonResult;
import have.somuch.regsys.common.utils.StringUtils;
import have.somuch.regsys.system.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员级别表 服务实现类
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-11-04
 */
@Service
public class MemberLevelServiceImpl extends BaseServiceImpl<MemberLevelMapper, MemberLevel> implements IMemberLevelService {

    @Autowired
    private MemberLevelMapper memberLevelMapper;

    /**
     * 获取会员等级列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        MemberLevelQuery memberLevelQuery = (MemberLevelQuery) query;
        // 查询条件
        QueryWrapper<MemberLevel> queryWrapper = new QueryWrapper<>();
        // 会员等级名称
        if (!StringUtils.isEmpty(memberLevelQuery.getName())) {
            queryWrapper.like("name", memberLevelQuery.getName());
        }
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByAsc("sort");

        // 查询分页数据
        IPage<MemberLevel> page = new Page<>(memberLevelQuery.getPage(), memberLevelQuery.getLimit());
        IPage<MemberLevel> pageData = memberLevelMapper.selectPage(page, queryWrapper);
        return JsonResult.success(pageData);
    }

    /**
     * 添加或编辑
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult edit(MemberLevel entity) {
        if (StringUtils.isNotNull(entity.getId()) && entity.getId() > 0) {
            entity.setUpdateUser(ShiroUtils.getUserId());
        } else {
            entity.setCreateUser(ShiroUtils.getUserId());
        }
        return super.edit(entity);
    }

    /**
     * 获取会员等级列表
     *
     * @return
     */
    @Override
    public JsonResult getMemberLevelList() {
        QueryWrapper<MemberLevel> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByAsc("sort");
        return JsonResult.success(list(queryWrapper));
    }
}
