package daily.cn.commonlib;

import android.app.Application;

import daily.cn.commonlib.http.util.RqbTrustManager;

/**
 * <pre>
 * author :zuoshengyong
 * e-mail: partric23@gmail.com
 * time: 2018/03/05
 * </pre>
 */

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RqbTrustManager.init(this);
    }
}
