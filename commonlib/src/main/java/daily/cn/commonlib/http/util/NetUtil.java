package daily.cn.commonlib.http.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import retrofit2.HttpException;


/**
 * author :zuoshengyong
 * e-mail: partric23@gmail.com
 * time: 2018/03/05
 */
public class NetUtil {
    public synchronized static boolean isNetError(Throwable e, Context context) {
        return e instanceof HttpException || e instanceof SocketTimeoutException || e instanceof ConnectException || !isNetAvailable(context);
    }

    public synchronized static boolean isNetAvailable(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connMgr.getActiveNetworkInfo();
        return netInfo != null && netInfo.isAvailable();
    }
}