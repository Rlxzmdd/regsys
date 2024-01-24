package have.somuch.regsys.admin.service.impl;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import have.somuch.regsys.admin.entity.Member;
import have.somuch.regsys.admin.mapper.MemberMapper;
import have.somuch.regsys.admin.query.MemberQuery;
import have.somuch.regsys.admin.service.IMemberService;
import have.somuch.regsys.admin.vo.member.MemberListVo;
import have.somuch.regsys.common.common.BaseQuery;
import have.somuch.regsys.common.common.BaseServiceImpl;
import have.somuch.regsys.common.config.CommonConfig;
import have.somuch.regsys.common.utils.CommonUtils;
import have.somuch.regsys.common.utils.JsonResult;
import have.somuch.regsys.common.utils.StringUtils;
import have.somuch.regsys.system.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-11-04
 */
@Service
public class MemberServiceImpl extends BaseServiceImpl<MemberMapper, Member> implements IMemberService {

    @Autowired
    private MemberMapper memberMapper;

    /**
     * 获取会员等级
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        MemberQuery memberQuery = (MemberQuery) query;
        // 查询条件
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        // 会员账号
        if (!StringUtils.isEmpty(memberQuery.getUsername())) {
            queryWrapper.like("username", memberQuery.getUsername());
        }
        // 会员性别
        if (!StringUtils.isNull(memberQuery.getGender())) {
            queryWrapper.eq("gender", memberQuery.getGender());
        }
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByDesc("id");

        // 查询分页数据
        IPage<Member> page = new Page<>(memberQuery.getPage(), memberQuery.getLimit());
        IPage<Member> pageData = memberMapper.selectPage(page, queryWrapper);
        pageData.convert(x -> {
            MemberListVo memberListVo = Convert.convert(MemberListVo.class, x);
            // 头像
            if (!StringUtils.isEmpty(x.getAvatar())) {
                memberListVo.setAvatar(CommonUtils.getImageURL(x.getAvatar()));
            }
            // 城市数据处理
            if (StringUtils.isNotNull(x.getProvinceCode()) &&
                    StringUtils.isNotNull(x.getCityCode()) &&
                    StringUtils.isNotNull(x.getDistrictCode())) {
                // 初始化数组
                String[] strings = new String[3];
                strings[0] = x.getProvinceCode();
                strings[1] = x.getCityCode();
                strings[2] = x.getDistrictCode();
                memberListVo.setCity(strings);
            }
            return memberListVo;
        });
        return JsonResult.success(pageData);
    }

    /**
     * 添加或编辑用户
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult edit(Member entity) {
        // 头像处理
        if (!StringUtils.isEmpty(entity.getAvatar()) && entity.getAvatar().contains(CommonConfig.imageURL)) {
            entity.setAvatar(entity.getAvatar().replaceAll(CommonConfig.imageURL, ""));
        }
        // 用户密码MD5加密
        if (StringUtils.isNotEmpty(entity.getPassword())) {
            entity.setPassword(CommonUtils.password(entity.getPassword()));
        } else {
            entity.setPassword(null);
        }
        // 省市区处理
        if (entity.getCity().length != 3) {
            return JsonResult.error("请选择省市区");
        }
        // 省份
        entity.setProvinceCode(entity.getCity()[0]);
        // 城市
        entity.setCityCode(entity.getCity()[1]);
        // 省份
        entity.setDistrictCode(entity.getCity()[2]);
        if (StringUtils.isNotNull(entity.getId()) && entity.getId() > 0) {
            entity.setUpdateUser(ShiroUtils.getUserId());
        } else {
            entity.setCreateUser(ShiroUtils.getUserId());
        }
        return super.edit(entity);
    }

    /**
     * 设置状态
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult setStatus(Member entity) {
        Member member = new Member();
        member.setId(entity.getId());
        member.setStatus(entity.getStatus());
        return super.setStatus(member);
    }

}
