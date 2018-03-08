package daily.cn.commonlib.http.util;

import android.text.TextUtils;

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
public class TestCommonInterceptor implements Interceptor {

    public static final String FAMILY_APP_ID = "";
    public static final String FAMILY_KEY = "";

    public static volatile String loginType;
    public static volatile String uid;
    public static volatile String auth;

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
            Logger.getLogger().d("response:%s", sbJson.toString());
        } catch (Exception e) {
            e.printStackTrace();
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
        String app_id;
        String app_key;
        String requestUrl = request.url().toString();
        Request.Builder requestBuilder;
        if ("POST".equals(request.method())) {
            RequestBody requestBody = rebuildBody(request);
            requestBuilder = request.newBuilder().method(request.method(), requestBody);
            paramsPostStr = getParamContent(requestBody);
            Logger.getLogger().i("url:%s,paramsPostStr:%s", requestUrl, "{" + paramsPostStr + "}");
        } else {
            requestBuilder = request.newBuilder().method(request.method(), request.body());
        }
        app_id = FAMILY_APP_ID;
        app_key = FAMILY_KEY;
        String SIGN = Coder.encodeMD5(app_id + app_key).toUpperCase();
        if (SIGN == null || TextUtils.isEmpty(SIGN)) {
            throw new IllegalArgumentException("SIGN should not be null,please check your appid and appkey");
        }
        requestBuilder.addHeader("appid", app_id);
        requestBuilder.addHeader("sign", SIGN);
        Logger.getLogger().i("Header,appid:%s,appkey:%s,sign:%s", app_id, app_key, SIGN);
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

    private RequestBody rebuildBody(Request request) {
        RequestBody originalRequestBody = request.body();
        if (originalRequestBody instanceof FormBody) {
            FormBody.Builder builder = new FormBody.Builder();
            FormBody requestBody = (FormBody) request.body();
            int fieldSize = requestBody == null ? 0 : requestBody.size();
            for (int i = 0; i < fieldSize; i++) {
                builder.add(requestBody.name(i), requestBody.value(i));
            }
            builder.add("login_type", TextUtils.isEmpty(uid) ?"2":loginType)
                    .add("uid", TextUtils.isEmpty(uid) ? "c4c95d40209f4704b9dd01f109bd58ef" : uid)
                    .add("auth", TextUtils.isEmpty(auth) ? "473f98cb13064452a3911076b38f8fdc" : auth)
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
            multipartBodybuilder.addFormDataPart("login_type", "2")
                    .addFormDataPart("uid", TextUtils.isEmpty(uid) ? "24aa18f8b03a4f258616e670d387f5ee" : uid)
                    .addFormDataPart("auth", TextUtils.isEmpty(auth) ? "3ded451e3b0b4641b5f5a124a1e7ae7c" : auth)
                    .addFormDataPart("version_code", "76");
            return multipartBodybuilder.build();
        } else {
            return originalRequestBody;
        }
    }
}
