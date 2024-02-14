package have.somuch.regsys.api.user.service.impl;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import have.somuch.regsys.api.common.constant.Constant;
import have.somuch.regsys.api.common.constant.LoginTypeEnum;
import have.somuch.regsys.api.common.constant.ResultCodeEnum;
import have.somuch.regsys.api.common.dto.WechatProgramIdentityDto;
import have.somuch.regsys.api.common.utils.JsonResultS;
import have.somuch.regsys.api.common.utils.JwtUtil;
import have.somuch.regsys.api.common.utils.WxUserRegisterUtil;
import have.somuch.regsys.api.shiro.token.NumberToken;
import have.somuch.regsys.api.shiro.token.StuExamToken;
import have.somuch.regsys.api.user.dto.WechatLoginDto;
import have.somuch.regsys.api.user.entity.UserStudent;
import have.somuch.regsys.api.user.entity.UserTeacher;
import have.somuch.regsys.api.user.entity.UserWechat;
import have.somuch.regsys.api.user.mapper.UserStudentMapper;
import have.somuch.regsys.api.user.mapper.UserTeacherMapper;
import have.somuch.regsys.api.user.mapper.UserWechatMapper;
import have.somuch.regsys.api.user.query.UserWechatQuery;
import have.somuch.regsys.api.user.service.IUserWechatService;
import have.somuch.regsys.api.user.vo.userwechat.UserWechatInfoVo;
import have.somuch.regsys.api.user.vo.userwechat.UserWechatListVo;
import have.somuch.regsys.common.common.BaseQuery;
import have.somuch.regsys.common.common.BaseServiceImpl;
import have.somuch.regsys.common.utils.DateUtils;
import have.somuch.regsys.common.utils.JsonResult;
import have.somuch.regsys.common.utils.StringUtils;
import have.somuch.regsys.system.utils.ShiroUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.HashMap;

/**
 * <p>
 * 用户微信表 服务类实现
 * </p>
 *
 * @author isZhous
 * @since 2024-01-25
 */
@Service
public class UserWechatServiceImpl extends BaseServiceImpl<UserWechatMapper, UserWechat> implements IUserWechatService {

    /*是否允许覆盖绑定 ，开发环境下允许*/
    @Value("${wechat.program.coverBind}")
    private boolean coverBind;

    @Autowired
    private UserWechatMapper wechatMapper;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private WxUserRegisterUtil wxUserRegisterUtil;

    @Autowired
    private UserStudentMapper studentMapper;

    @Autowired
    private UserTeacherMapper teacherMapper;

    @Autowired
    private UserWechatMapper userWechatMapper;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        UserWechatQuery userWechatQuery = (UserWechatQuery) query;
        // 查询条件
        QueryWrapper<UserWechat> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByDesc("id");

        // 获取数据列表
        IPage<UserWechat> page = new Page<>(userWechatQuery.getPage(), userWechatQuery.getLimit());
        IPage<UserWechat> pageData = userWechatMapper.selectPage(page, queryWrapper);
        pageData.convert(x -> {
            UserWechatListVo userWechatListVo = Convert.convert(UserWechatListVo.class, x);
            return userWechatListVo;
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
        UserWechat entity = (UserWechat) super.getInfo(id);
        // 返回视图Vo
        UserWechatInfoVo userWechatInfoVo = new UserWechatInfoVo();
        // 拷贝属性
        BeanUtils.copyProperties(entity, userWechatInfoVo);
        return userWechatInfoVo;
    }

    /**
     * 添加、更新记录
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult edit(UserWechat entity) {
        if (StringUtils.isNotNull(entity.getId()) && entity.getId() > 0) {
            entity.setUpdateUser(ShiroUtils.getUserId());
            entity.setUpdateTime(DateUtils.now());
        } else {
            entity.setCreateUser(ShiroUtils.getUserId());
            entity.setCreateTime(DateUtils.now());
        }
        return super.edit(entity);
    }

    /**
     * 删除记录
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult delete(UserWechat entity) {
        entity.setUpdateUser(1);
        entity.setUpdateTime(DateUtils.now());
        entity.setMark(0);
        return super.delete(entity);
    }

    @Override
    @Transactional
    /*使用事务保证，保证数据一致，中途出错将会回滚数据*/
    public JsonResultS login(String code, WechatLoginDto dto) {
        WechatProgramIdentityDto wxIdentity = wxUserRegisterUtil.requestCode2Session(code);
        if (wxIdentity == null) {
            return JsonResultS.error(ResultCodeEnum.USER_ERROR_A0402);
        }
        // 获取Subject
        Subject subject = SecurityUtils.getSubject();
        // 根据dto的type，创建对应的Token
        AuthenticationToken token;
        switch (dto.getType()) {
            case STU_NUMBER:
            case TCH_NUMBER:
                token = new NumberToken(dto);
                break;
            case EXAM_NUMBER:
                token = new StuExamToken(dto);
                break;
            default:
                return JsonResultS.error("未知的登录类型");
        }
        //进行登录，统一错误管理 --> GlobalExceptionHandler
        subject.login(token);
        // 登录成功，生成统一验证Token --> jwtUtil
        String type = Constant.TOKEN_USER_TYPE_STUDENT;
        if (dto.getType().equals(LoginTypeEnum.TCH_NUMBER)) {
            type = Constant.TOKEN_USER_TYPE_TEACHER;
        }
        // fixme 准考证登录似乎没有会number，需要复查
        String jwtToken = jwtUtil.sign(dto.getNumber(), type);
        return JsonResultS.success(new HashMap<String, String>() {{
            put("authorization", jwtToken);
        }});
    }

    @Override
    public JsonResultS obtain(String code) {
        WechatProgramIdentityDto dto = wxUserRegisterUtil.requestCode2Session(code);

        if (dto == null) {
            return JsonResultS.error(ResultCodeEnum.USER_ERROR_A0242);
        }
        QueryWrapper<UserWechat> wrapper = new QueryWrapper<>();
        wrapper.eq("open_id", dto.getOpenid());
        UserWechat wechat = wechatMapper.selectOne(wrapper);
        String token;
        // 微信用户未绑定情况
        if (wechat == null) {
            return JsonResultS.success(ResultCodeEnum.WECHAT_USER_NOT_BIND);
        }
        QueryWrapper<UserStudent> studentQueryWrapper = new QueryWrapper<>();
        studentQueryWrapper.eq("wx_id", wechat.getId());
        UserStudent student = studentMapper.selectOne(studentQueryWrapper);
        if (student == null) {
            QueryWrapper<UserTeacher> teacherQueryWrapper = new QueryWrapper<>();
            teacherQueryWrapper.eq("wx_id", wechat.getId());
            UserTeacher teacher = teacherMapper.selectOne(teacherQueryWrapper);
            if (teacher == null) {
                return JsonResultS.error(ResultCodeEnum.WECHAT_USER_NOT_BIND);
            }
            token = jwtUtil.sign(teacher.getTchNumber(), Constant.TOKEN_USER_TYPE_TEACHER);
        } else {
            token = jwtUtil.sign(student.getStuNumber(), Constant.TOKEN_USER_TYPE_STUDENT);
        }

        // 均未找到微信绑定信息
        if (token == null) {
            return JsonResultS.success(ResultCodeEnum.WECHAT_USER_NOT_BIND);
        }

        return JsonResultS.success(new HashMap<String, String>() {{
            put("authorization", token);
        }});
    }
}