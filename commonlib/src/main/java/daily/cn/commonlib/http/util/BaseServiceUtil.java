package daily.cn.commonlib.http.util;

import android.text.TextUtils;

import java.util.concurrent.TimeUnit;

import daily.cn.commonlib.BuildConfig;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * author :zuoshengyong
 * e-mail: partric23@gmail.com
 * time: 2018/03/05
 */
public class BaseServiceUtil {
    private static final int DEFAULT_TIMEOUT = 50;
    private static final CommonInterceptor interceptor = new CommonInterceptor();
    private static final OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder()
            .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
    // Retrofit要求baseUrl以 '/' 为结尾
    private static final Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create());

    public static synchronized <S> S createService(String baseUrl, Class<S> serviceClass) {
        return createService(serviceClass, baseUrl);
    }

    public static synchronized <S> S createService(Class<S> serviceClass) {
        return createService(serviceClass, null);
    }

    private static <S> S createService(Class<S> serviceClass, String baseUrl) {
        if (!TextUtils.isEmpty(baseUrl)) {
            retrofitBuilder.baseUrl(baseUrl);
        } else {
            retrofitBuilder.baseUrl(BuildConfig.BASE_URL);
        }
        clientBuilder.interceptors().clear();
        clientBuilder.interceptors().add(interceptor);

        // 设置证书
        try {
            clientBuilder.sslSocketFactory(RqbTrustManager.getInstance().getSSLSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
        OkHttpClient client = clientBuilder.build();
        Retrofit retrofit = retrofitBuilder.client(client).build();
        return retrofit.create(serviceClass);
    }
}
