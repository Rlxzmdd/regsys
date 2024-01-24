package have.somuch.regsys.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import have.somuch.regsys.common.common.BaseQuery;
import have.somuch.regsys.common.common.BaseServiceImpl;
import have.somuch.regsys.common.config.CommonConfig;
import have.somuch.regsys.common.utils.CommonUtils;
import have.somuch.regsys.common.utils.JsonResult;
import have.somuch.regsys.common.utils.StringUtils;
import have.somuch.regsys.system.entity.Notice;
import have.somuch.regsys.system.mapper.NoticeMapper;
import have.somuch.regsys.system.query.NoticeQuery;
import have.somuch.regsys.system.service.INoticeService;
import have.somuch.regsys.system.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 通知公告表 服务实现类
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-11-07
 */
@Service
public class NoticeServiceImpl extends BaseServiceImpl<NoticeMapper, Notice> implements INoticeService {

    @Autowired
    private NoticeMapper noticeMapper;

    /**
     * 获取通知公告列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        NoticeQuery noticeQuery = (NoticeQuery) query;
        // 查询条件
        QueryWrapper<Notice> queryWrapper = new QueryWrapper<>();
        // 通知标题
        if (!StringUtils.isEmpty(noticeQuery.getTitle())) {
            queryWrapper.like("title", noticeQuery.getTitle());
        }
        // 通知状态
        if (StringUtils.isNotNull(noticeQuery.getStatus())) {
            queryWrapper.eq("status", noticeQuery.getStatus());
        }
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByDesc("id");

        // 查询分页数据
        IPage<Notice> page = new Page<>(noticeQuery.getPage(), noticeQuery.getLimit());
        IPage<Notice> pageData = noticeMapper.selectPage(page, queryWrapper);
        return JsonResult.success(pageData);
    }

    /**
     * 获取通知公告详情
     *
     * @param id 记录ID
     * @return
     */
    @Override
    public Object getInfo(Serializable id) {
        Notice info = (Notice) super.getInfo(id);
        List<String> stringList = CommonUtils.getImgStr(info.getContent());
        if (stringList.size() > 0) {
            stringList.forEach(item -> {
                info.setContent(info.getContent().replaceAll(item, CommonUtils.getImageURL(item)));
            });
        }
        return info;
    }

    /**
     * 添加或编辑
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult edit(Notice entity) {
        if (StringUtils.isNotNull(entity.getId()) && entity.getId() > 0) {
            entity.setUpdateUser(ShiroUtils.getUserId());
        } else {
            entity.setCreateUser(ShiroUtils.getUserId());
        }
        // 处理富文本信息
        entity.setContent(entity.getContent().replaceAll(CommonConfig.imageURL, ""));
        return super.edit(entity);
    }

    /**
     * 设置状态
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult setStatus(Notice entity) {
        Notice notice = new Notice();
        notice.setId(entity.getId());
        notice.setStatus(entity.getStatus());
        return super.setStatus(notice);
    }

    /**
     * 设置是否置顶
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult setIsTop(Notice entity) {
        Notice notice = new Notice();
        notice.setId(entity.getId());
        notice.setIsTop(entity.getIsTop());
        return super.setStatus(notice);
    }
}
