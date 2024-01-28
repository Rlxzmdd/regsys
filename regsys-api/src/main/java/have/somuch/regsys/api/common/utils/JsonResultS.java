package have.somuch.regsys.api.common.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import have.somuch.regsys.api.common.ResultCodeEnum;
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

    /**
     * 成功
     */
    @JsonIgnore
    public static final ResultCodeEnum SUCCESS = ResultCodeEnum.SUCCESS;

    /**
     * 失败
     */
    @JsonIgnore
    public static final ResultCodeEnum ERROR = ResultCodeEnum.SYSTEM_ERROR_B0001;

    /**
     * 空对象
     */
    @JsonIgnore
    public static final Map<String, String> EMPTY = new HashMap<>();

    private String code;

    private String msg;

    private T data;

    public static <T> JsonResultS<T> success() {
        return jsonResult(null, SUCCESS.getCode(), "操作成功");
    }

    public static <T> JsonResultS<T> success(T data) {
        return jsonResult(data, SUCCESS.getCode(), "操作成功");
    }

    public static <T> JsonResultS<T> success(T data, String msg) {
        return jsonResult(data, SUCCESS.getCode(), msg);
    }

    public static <T> JsonResultS<T> success(ResultCodeEnum codeEnum) {
        return jsonResult(null, codeEnum.getCode(), codeEnum.getDescription());
    }

    public static <T> JsonResultS<T> error() {
        return jsonResult(null, ERROR.getCode(), "操作失败");
    }

    public static <T> JsonResultS<T> error(String msg) {
        return jsonResult(null, ERROR.getCode(), msg);
    }

    public static <T> JsonResultS<T> error(T data) {
        return jsonResult(data, ERROR.getCode(), "操作失败");
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
        ObjectMapper objectMapper = new ObjectMapper();
        ServletOutputStream stream = response.getOutputStream();
        response.setStatus(status);
        stream.write(objectMapper.writeValueAsString(resultS).getBytes(StandardCharsets.UTF_8));
    }

    private static <T> JsonResultS<T> jsonResult(T data, String code, String msg) {
        JsonResultS<T> result = new JsonResultS<>();
        result.setCode(code);
        result.setData(data);
        result.setMsg(msg);
        return result;
    }

}
