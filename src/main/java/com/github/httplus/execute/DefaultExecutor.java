package com.github.httplus.execute;

import com.github.httplus.Configuration;
import org.apache.http.Header;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

import static com.github.httplus.common.Constants.REQUEST_METHOD_GET;
import static com.github.httplus.common.Constants.REQUEST_METHOD_POST;

/**
 * 默认的请求执行器
 */
public class DefaultExecutor implements Executor {

    private Configuration configuration;

    public DefaultExecutor(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public Response execute() {
        CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().build();
        String url = configuration.getUrl();
        String method = configuration.getMethod();
        try {
            if (REQUEST_METHOD_GET.equals(method)) {
                HttpGet httpGet = new HttpGet(url);
                return handleClosableResponse(closeableHttpClient.execute(httpGet));
            } else if (REQUEST_METHOD_POST.equals(method)) {
                HttpPost httpPost = new HttpPost(url);
                httpPost.setEntity(new StringEntity(configuration.getBody()));
                return handleClosableResponse(closeableHttpClient.execute(httpPost));
            } else {
                throw new RuntimeException("暂不支持该请求方式");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        } finally {
            try {
                if (closeableHttpClient != null) {
                    closeableHttpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 处理响应
     *
     * @param closeableHttpResponse 响应信息
     * @return 面向用户的响应信息
     * @throws IOException 读取响应信息流可能产生的IO异常
     */
    private Response handleClosableResponse(CloseableHttpResponse closeableHttpResponse) throws IOException {
        Response response = new Response();
        try {
            StatusLine statusLine = closeableHttpResponse.getStatusLine();
            response.setCode(statusLine.getStatusCode());
            response.setBody(EntityUtils.toString(closeableHttpResponse.getEntity()));
            for (Header header : closeableHttpResponse.getAllHeaders()) {
                response.addHeader(header.getName(), header.getValue());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (closeableHttpResponse != null) {
                closeableHttpResponse.close();
            }
        }
        return response;
    }
}
