package have.somuch.regsys.api.common.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import have.somuch.regsys.api.common.constant.ResultCodeEnum;
import lombok.Data;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Data
public class JsonResultS<T> implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 1L;

    @JsonIgnore
    public static final ResultCodeEnum SUCCESS = ResultCodeEnum.SUCCESS;

    @JsonIgnore
    public static final ResultCodeEnum ERROR = ResultCodeEnum.SYSTEM_ERROR_B0001;

    @JsonIgnore
    public static final Map<String, String> EMPTY = new HashMap<>();

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static final String SUCCESS_MESSAGE = "操作成功";
    private static final String ERROR_MESSAGE = "操作失败";

    private String code;

    private String msg;

    private T data;

    public static <T> JsonResultS<T> success() {
        return jsonResult(null, SUCCESS.getCode(), SUCCESS_MESSAGE);
    }

    public static <T> JsonResultS<T> success(T data) {
        return jsonResult(data, SUCCESS.getCode(), SUCCESS_MESSAGE);
    }

    public static <T> JsonResultS<T> success(T data, String msg) {
        return jsonResult(data, SUCCESS.getCode(), msg);
    }

    public static <T> JsonResultS<T> success(ResultCodeEnum codeEnum) {
        return jsonResult(null, codeEnum.getCode(), codeEnum.getDescription());
    }

    public static <T> JsonResultS<T> error() {
        return jsonResult(null, ERROR.getCode(), ERROR_MESSAGE);
    }

    public static <T> JsonResultS<T> error(String msg) {
        return jsonResult(null, ERROR.getCode(), msg);
    }

    public static <T> JsonResultS<T> error(T data) {
        return jsonResult(data, ERROR.getCode(), ERROR_MESSAGE);
    }

    public static <T> JsonResultS<T> error(T data, String msg) {
        return jsonResult(data, ERROR.getCode(), msg);
    }

    public static <T> JsonResultS<T> error(String code, String msg) {
        return jsonResult(null, code, msg);
    }

    public static <T> JsonResultS<T> error(ResultCodeEnum codeEnum) {
        return jsonResult(null, codeEnum.getCode(), codeEnum.getDescription());
    }

    public static <T> JsonResultS<T> error(T data, ResultCodeEnum codeEnum) {
        return jsonResult(data, codeEnum.getCode(), codeEnum.getDescription());
    }

    /**
     * 覆盖lombok的Getter 方法
     *
     * @return
     */
    public Object getData() {
        if (this.data == null) {
            return new HashMap<>();
        }
        return data;
    }

    /**
     * 获取真实的Data对象
     *
     * @return
     */
    @JsonIgnore
    public T getRealData() {
        return data;
    }

    public static void writeToResponse(HttpServletResponse response, JsonResultS resultS, int status) throws Exception {
        ServletOutputStream stream = response.getOutputStream();
        response.setStatus(status);
        stream.write(OBJECT_MAPPER.writeValueAsString(resultS).getBytes(StandardCharsets.UTF_8));
    }

    private static <T> JsonResultS<T> jsonResult(T data, String code, String msg) {
        JsonResultS<T> result = new JsonResultS<>();
        result.setCode(code);
        result.setData(data);
        result.setMsg(msg);
        return result;
    }

}
