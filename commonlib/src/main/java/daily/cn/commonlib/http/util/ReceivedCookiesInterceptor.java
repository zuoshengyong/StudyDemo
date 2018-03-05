package daily.cn.commonlib.http.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * author :zuoshengyong
 * e-mail: partric23@gmail.com
 * time: 2018/03/05
 */
public class ReceivedCookiesInterceptor implements Interceptor {
    private Context context;

    public ReceivedCookiesInterceptor(Context context) {
        super();
        this.context = context;

    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Response originalResponse = chain.proceed(chain.request());
        //这里获取请求返回的cookie
        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            List<String> cookies = originalResponse.headers("set-cookie");
            String cookie = receiveCookie(cookies);
            Log.e("TAG","save Cookie="+cookie);
            saveCookie(chain.request().url().toString(),originalResponse.request().url().host(),cookie);
        }
        

        return originalResponse;
    }


    private String receiveCookie(List<String> cookies) {
        Log.e("TAG","item cookie="+cookies.toString());
        StringBuilder sb = new StringBuilder();
        for (String cookie : cookies) {
            String[] arr = cookie.split(";");
            sb.append(arr[0]);
            Log.e("TAG","item cookie="+cookie);
        }
//        int last = sb.lastIndexOf(";");
//        if (sb.length() - 1 == last) {
//            sb.deleteCharAt(last);
//        }
        Log.i("TAG","receiveCookie="+sb.toString());
        return sb.toString();
    }
    private String encodeCookie(List<String> cookies) {
        StringBuilder sb = new StringBuilder();
        Set<String> set=new HashSet<>();
        for (String cookie : cookies) {
            String[] arr = cookie.split(";");
            for (String s : arr) {
                if(set.contains(s))continue;
                set.add(s);

            }
        }

        Iterator<String> ite = set.iterator();
        while (ite.hasNext()) {
            String cookie = ite.next();
            sb.append(cookie).append(";");
        }

        int last = sb.lastIndexOf(";");
        if (sb.length() - 1 == last) {
            sb.deleteCharAt(last);
        }
        Log.i("TAG","encodeCookie="+sb.toString());
        return sb.toString();
    }



    private void saveCookie(String url, String domain, String cookies) {
        SharedPreferences sp = context.getSharedPreferences("CookiePrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if (TextUtils.isEmpty(url)) {
            throw new NullPointerException("url is null.");
        }else{
            editor.putString(url, cookies);
        }

        if (!TextUtils.isEmpty(domain)) {
            editor.putString(domain, cookies);
        }

        editor.apply();

    }
}