package have.somuch.regsys.common.service;

import have.somuch.regsys.common.utils.JsonResult;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 文件上传 服务类
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-10-30
 */
public interface IUploadService {

    /**
     * 上传图片
     *
     * @param request 网络请求
     * @param name    目录名
     * @return
     */
    JsonResult uploadImage(HttpServletRequest request, String name);

    /**
     * 上传文件
     *
     * @param request 网络请求
     * @param name    目录名
     * @return
     */
    JsonResult uploadFile(HttpServletRequest request, String name);

}
