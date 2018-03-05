package daily.cn.commonlib.http.subscribers;

import android.content.Context;

import daily.cn.commonlib.http.result.HttpResult;


/**
 * 上传文件订阅者
 * author :zuoshengyong
 * e-mail: partric23@gmail.com
 * time: 2018/03/05
 */

public abstract class UploadSubscriber<T> extends BaseSubscriber<T> {

    public UploadSubscriber(SubscriberOnNextListener subscriberOnNextListener, Context context) {
        super(subscriberOnNextListener, context, false);
    }

    public UploadSubscriber(SubscriberOnNextListener subscriberOnNextListener, Context context, boolean showProgress) {
        super(subscriberOnNextListener, context, showProgress, null);
    }

    public UploadSubscriber(SubscriberOnNextListener subscriberOnNextListener, Context context, boolean showProgress, String progressTip) {
        super(subscriberOnNextListener, null,context, showProgress, progressTip);
    }

    public UploadSubscriber(SubscriberOnNextListener subscriberOnNextListener , OnErrorListener errorListener, Context context, boolean showProgress, String progressTip) {
        super(subscriberOnNextListener, errorListener,context, showProgress, progressTip);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onNext(Object o) {
        if (o instanceof Integer) {
            onProgress((Integer) o);
        }

        if (o instanceof HttpResult) {
            HttpResult result = (HttpResult) o;
            super.onNext((T) result);
        }
    }

    public abstract void onProgress(Integer percent);
}
