package have.somuch.regsys.api.common.utils;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class HttpClientUtil {

    /**
     * 作为客户端发送HTTP POST请求
     *
     * @param url      目标接口
     * @param map      表单参数
     * @param encoding 字符编码
     * @return response result
     */
    public static String sendPostDataByMap(String url, Map<String, String> map, String encoding) throws ClientProtocolException, IOException {
        String result = "";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");
        httpPost.setHeader(HttpHeaders.USER_AGENT, "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

        // POST 参数
        List<NameValuePair> params = new ArrayList<>();
        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params, encoding));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        CloseableHttpResponse response = null;

        try {
            response = httpClient.execute(httpPost);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (response != null && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            result = EntityUtils.toString(response.getEntity(), "utf-8");
            response.close();
        }

        return result;
    }

    /**
     * 作为客户端发起HTTP GET请求
     *
     * @param url      目标接口
     * @param encoding 字符编码
     * @return response result
     */
    public Map<String, Object> sendGetData(String url, String encoding) {
        String result = "";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result = EntityUtils.toString(response.getEntity(), encoding);
                response.close();
            }
        } catch (IOException e) {
            log.info("Http client component get request failed");
        }
        Gson gson = new Gson();
        return gson.fromJson(result, HashMap.class);
    }

}
