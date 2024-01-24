package have.somuch.regsys.system.service.impl;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import have.somuch.regsys.common.common.BaseQuery;
import have.somuch.regsys.common.common.BaseServiceImpl;
import have.somuch.regsys.common.config.CommonConfig;
import have.somuch.regsys.common.utils.CommonUtils;
import have.somuch.regsys.common.utils.DateUtils;
import have.somuch.regsys.common.utils.JsonResult;
import have.somuch.regsys.common.utils.StringUtils;
import have.somuch.regsys.system.constant.ArticleConstant;
import have.somuch.regsys.system.entity.Article;
import have.somuch.regsys.system.mapper.ArticleMapper;
import have.somuch.regsys.system.query.ArticleQuery;
import have.somuch.regsys.system.service.IArticleService;
import have.somuch.regsys.system.service.IItemCateService;
import have.somuch.regsys.system.utils.ShiroUtils;
import have.somuch.regsys.system.vo.article.ArticleInfoVo;
import have.somuch.regsys.system.vo.article.ArticleListVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 文章管理表 服务类实现
 * </p>
 *
 * @author 鲲鹏
 * @since 2021-10-11
 */
@Service
public class ArticleServiceImpl extends BaseServiceImpl<ArticleMapper, Article> implements IArticleService {

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private IItemCateService itemCateService;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        ArticleQuery articleQuery = (ArticleQuery) query;
        // 查询条件
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        // 文章标题
        if (!StringUtils.isEmpty(articleQuery.getTitle())) {
            queryWrapper.like("title", articleQuery.getTitle());
        }
        // 状态：1正常 2停用
        if (!StringUtils.isEmpty(articleQuery.getStatus())) {
            queryWrapper.eq("status", articleQuery.getStatus());
        }
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByDesc("id");

        // 获取数据列表
        IPage<Article> page = new Page<>(articleQuery.getPage(), articleQuery.getLimit());
        IPage<Article> pageData = articleMapper.selectPage(page, queryWrapper);
        pageData.convert(x -> {
            ArticleListVo articleListVo = Convert.convert(ArticleListVo.class, x);
            // 文章封面
            if (!StringUtils.isEmpty(articleListVo.getCover())) {
                articleListVo.setCover(CommonUtils.getImageURL(articleListVo.getCover()));
            }
            // 状态描述
            if (articleListVo.getStatus() != null && articleListVo.getStatus() > 0) {
                articleListVo.setStatusName(ArticleConstant.ARTICLE_STATUS_LIST.get(articleListVo.getStatus()));
            }
            // 所属栏目
            if (x.getCateId() > 0) {
                String cateName = itemCateService.getCateName(x.getCateId(), ">>");
                articleListVo.setCateName(cateName);
            }
            return articleListVo;
        });
        return JsonResult.success(pageData);
    }

    /**
     * 获取详情Vo
     *
     * @param id 记录ID
     * @return
     */
    @Override
    public Object getInfo(Serializable id) {
        Article entity = (Article) super.getInfo(id);
        // 返回视图Vo
        ArticleInfoVo articleInfoVo = new ArticleInfoVo();
        // 拷贝属性
        BeanUtils.copyProperties(entity, articleInfoVo);
        // 文章封面
        if (!StringUtils.isEmpty(articleInfoVo.getCover())) {
            articleInfoVo.setCover(CommonUtils.getImageURL(articleInfoVo.getCover()));
        }
        // 图集
        if (StringUtils.isNotEmpty(entity.getImgs())) {
            List<String> stringList = new ArrayList<>();
            String[] strings = entity.getImgs().split(",");
            for (String string : strings) {
                stringList.add(CommonUtils.getImageURL(string));
            }
            articleInfoVo.setImgsList(stringList.toArray(new String[stringList.size()]));
        }
        // 富文本图片
        List<String> stringList = CommonUtils.getImgStr(entity.getContent());
        if (stringList.size() > 0) {
            stringList.forEach(item -> {
                articleInfoVo.setContent(entity.getContent().replaceAll(item, CommonUtils.getImageURL(item)));
            });
        }
        return articleInfoVo;
    }

    /**
     * 添加、更新记录
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult edit(Article entity) {
        // 文章封面
        if (entity.getCover().contains(CommonConfig.imageURL)) {
            entity.setCover(entity.getCover().replaceAll(CommonConfig.imageURL, ""));
        }
        if (entity.getId() != null && entity.getId() > 0) {
            entity.setUpdateUser(ShiroUtils.getUserId());
        } else {
            entity.setCreateUser(ShiroUtils.getUserId());
        }
        // 处理图集
        if (StringUtils.isNotNull(entity.getImgsList()) && entity.getImgsList().length > 0) {
            List<String> stringList = new ArrayList<>();
            for (int i = 0; i < entity.getImgsList().length; i++) {
                stringList.add(entity.getImgsList()[i].replace(CommonConfig.imageURL, ""));
            }
            entity.setImgs(String.join(",", stringList));
        }
        // 处理富文本
        entity.setContent(entity.getContent().replaceAll(CommonConfig.imageURL, ""));
        return super.edit(entity);
    }

    /**
     * 删除记录
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult delete(Article entity) {
        entity.setUpdateUser(1);
        entity.setUpdateTime(DateUtils.now());
        entity.setMark(0);
        return super.delete(entity);
    }

    /**
     * 设置状态
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult setStatus(Article entity) {
        if (entity.getId() == null || entity.getId() <= 0) {
            return JsonResult.error("记录ID不能为空");
        }
        if (entity.getStatus() == null) {
            return JsonResult.error("记录状态不能为空");
        }
        return super.setStatus(entity);
    }

}