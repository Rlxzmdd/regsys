package have.somuch.regsys.common.utils;

import cn.hutool.core.codec.Base64;
import have.somuch.regsys.common.config.UploadFileConfig;
//import sun.misc.BASE64Decoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Base64Utils {

    /**
     * 保存Base64图片至本地
     *
     * @param baseImg Base64字符串
     * @param name    目录名称
     * @return
     */
    public static String saveImg(String baseImg, String name) {
        // todo debug
        //定义一个正则表达式的筛选规则，为了获取图片的类型
        String rgex = "data:image/(.*?);base64";
        String fileExt = getSubUtilSimple(baseImg, rgex);
        //去除base64图片的前缀
        baseImg = baseImg.replaceFirst("data:(.+?);base64,", "");
        byte[] b;
        byte[] bs;
        OutputStream os = null;
        // 格式化并获取当前日期（用来命名）
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String nowDate = format.format(new Date());
        //把图片转换成二进制
        b = Base64.decode(baseImg.replaceAll(" ", "+"));
        //生成路径
        String path = UploadFileConfig.uploadFolder + "\\images\\" + name + "\\";

        // 创建二级目录(格式：年月日)
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String ymd = sdf.format(new Date());
        path += ymd + "/";
        File dirFile = new File(path);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }

        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String fileName = df.format(new Date()) + new Random().nextInt(1000) + "." + fileExt;
        File imageFile = new File(path + "/" + fileName);
//        BASE64Decoder d = new BASE64Decoder();
        // 保存
        try {
//            bs = d.decodeBuffer(Base64.encode(b));
            os = new FileOutputStream(imageFile);
//            os.write(bs);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.getLocalizedMessage();
                }
            }
        }
        return "/images/" + name + "/" + ymd + "/" + fileName;
    }

    /**
     * 获取文件后缀
     *
     * @param baseImg base64字符串
     * @param rgex
     * @return
     */
    public static String getSubUtilSimple(String baseImg, String rgex) {
        Pattern pattern = Pattern.compile(rgex);
        Matcher m = pattern.matcher(baseImg);
        while (m.find()) {
            return m.group(1);
        }
        return "";
    }

}
