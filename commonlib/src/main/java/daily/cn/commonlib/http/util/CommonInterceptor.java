package daily.cn.commonlib.http.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

import daily.cn.commonlib.Logger;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * author :zuoshengyong
 * e-mail: partric23@gmail.com
 * time: 2018/03/05
 */
public class CommonInterceptor implements Interceptor {

    @Override
    public synchronized Response intercept(Chain chain) throws IOException {
        Request request = rebuildRequest(chain.request());
        Response response = chain.proceed(request);
        // 输出返回结果
        try {
            Charset charset;
            charset = Charset.forName("UTF-8");
            ResponseBody responseBody = response.peekBody(Long.MAX_VALUE);
            Reader jsonReader = new InputStreamReader(responseBody.byteStream(), charset);
            BufferedReader reader = new BufferedReader(jsonReader);
            StringBuilder sbJson = new StringBuilder();
            String line = reader.readLine();
            do {
                sbJson.append(line);
                line = reader.readLine();
            } while (line != null);
            Logger.getLogger().d("original,response:%s", sbJson.toString());
        } catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger().e("errorMsg:%s", e.getMessage());
        }
        return response;
    }


    public static byte[] toByteArray(RequestBody body) throws IOException {
        Buffer buffer = new Buffer();
        body.writeTo(buffer);
        InputStream inputStream = buffer.inputStream();
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] bufferWrite = new byte[4096];
        int n = 0;
        while (-1 != (n = inputStream.read(bufferWrite))) {
            output.write(bufferWrite, 0, n);
        }
        return output.toByteArray();
    }

    private Request rebuildRequest(Request request) throws IOException {
        String paramsPostStr = "";
        String requestUrl = request.url().toString();
        Request.Builder requestBuilder;
        if ("POST".equals(request.method())) {
            RequestBody requestBody = rebuildBody(request);
            requestBuilder = request.newBuilder().method(request.method(), requestBody);
            paramsPostStr = getParamContent(requestBody);
            Logger.getLogger().d("url:%s,paramsPostStr:%s", requestUrl, "{" + paramsPostStr + "}");
        } else {
            requestBuilder = request.newBuilder().method(request.method(), request.body());
        }
        requestBuilder.addHeader("cookie", "cookie");
        requestBuilder.addHeader("sign", "SIGN");
        return requestBuilder.build();
    }


    /**
     * 获取请求参数
     */
    private String getParamContent(RequestBody body) throws IOException {
        Buffer buffer = new Buffer();
        body.writeTo(buffer);
        return buffer.readUtf8();
    }

    /**
     * 添加通用参数
     */
    private RequestBody rebuildBody(Request request) {
        RequestBody originalRequestBody = request.body();
        if (originalRequestBody instanceof FormBody) {
            FormBody.Builder builder = new FormBody.Builder();
            FormBody requestBody = (FormBody) request.body();
            int fieldSize = requestBody == null ? 0 : requestBody.size();
            for (int i = 0; i < fieldSize; i++) {
                builder.add(requestBody.name(i), requestBody.value(i));
            }
            builder.add("uid", "eec5f2b1f9da482fbf0cbd66d55cab32")
                    .add("auth", "9c301e76dcba4c1d89c4067b2300166a")
                    .add("version_code", "76");
            return builder.build();
        } else if (originalRequestBody instanceof MultipartBody) {
            MultipartBody requestBody = (MultipartBody) request.body();
            if (requestBody == null) {
                return null;
            }
            MultipartBody.Builder multipartBodybuilder = new MultipartBody.Builder();
            for (int i = 0; i < requestBody.size(); i++) {
                multipartBodybuilder.addPart(requestBody.part(i));
            }
            multipartBodybuilder.addFormDataPart("login_type", "loginType")
                    .addFormDataPart("uid", "db72e8fe6b304d4ea4d100e48c892618")
                    .addFormDataPart("auth", "dcc07f27535747eba7ffc61344616037")
                    .addFormDataPart("version_code", "76");
            return multipartBodybuilder.build();
        } else {
            return originalRequestBody;
        }
    }
}
