package daily.cn.commonlib.http.util;


import daily.cn.commonlib.http.subscribers.BaseSubscriber;
import daily.cn.commonlib.http.subscribers.OnErrorListener;

/**
 * author :zuoshengyong
 * e-mail: partric23@gmail.com
 * time: 2018/03/05
 */
public interface ProgressCancelListener {
    void onCancelProgress();
    BaseSubscriber setOnErrorListener(OnErrorListener listener);
}
