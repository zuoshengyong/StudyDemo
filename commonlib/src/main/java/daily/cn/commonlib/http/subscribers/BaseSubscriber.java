package daily.cn.commonlib.http.subscribers;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.Toast;

import daily.cn.commonlib.Logger;
import daily.cn.commonlib.http.util.CancelRequestListener;
import daily.cn.commonlib.http.util.NetProgressDialog;
import daily.cn.commonlib.http.util.NetUtil;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

import static android.os.Looper.getMainLooper;


/**
 * author :zuoshengyong
 * e-mail: partric23@gmail.com
 * time: 2018/03/05
 */
public class BaseSubscriber<T> implements Observer<T>, CancelRequestListener {
    private Disposable disposable;
    private NetProgressDialog netProgressDialog;
    private SubscriberOnNextListener subscriberOnNextListener;
    private OnErrorListener onErrorListener;
    private boolean showProgress;
    private Context context;

    public BaseSubscriber(SubscriberOnNextListener subscriberOnNextListener, Context context) {
        this(subscriberOnNextListener, context, false);
    }
    public BaseSubscriber(SubscriberOnNextListener subscriberOnNextListener,OnErrorListener errorListener,Context context) {
        this(subscriberOnNextListener, errorListener,context, false,"");
    }
    public BaseSubscriber(SubscriberOnNextListener subscriberOnNextListener, Context context, boolean showProgress) {
        this(subscriberOnNextListener, context, showProgress, null);
    }

    public BaseSubscriber(SubscriberOnNextListener subscriberOnNextListener, Context context, boolean showProgress, String progressTip) {
        this(subscriberOnNextListener,null, context, showProgress, progressTip);
    }

    public BaseSubscriber(SubscriberOnNextListener subscriberOnNextListener, OnErrorListener errorListener, Context context, boolean showProgress, String progressTip) {
        this.subscriberOnNextListener = subscriberOnNextListener;
        this.context = context;
        this.showProgress = showProgress;
        this.onErrorListener = errorListener;
        showProgress(progressTip);
    }

    @Override
    public synchronized void onError(Throwable e) {
        dismissProgressDialog();
        Logger.getLogger().e("onError,errorMessage:%s,error:%s",e.getMessage(),e);
        if(!NetUtil.isNetAvailable(context)){
            showToast("网络不可用，请检查后再试！");
        }
        if (onErrorListener != null) {
            onErrorListener.onError(e);
            return;
        }
    }

    @Override
    public void onComplete() {
        dismissProgressDialog();
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        this.disposable = d;
    }

    /**
     * @param t 订阅处理结果
     */
    @SuppressWarnings("unchecked")
    @Override
    public synchronized void onNext(T t) {
        if (subscriberOnNextListener != null) {
            subscriberOnNextListener.onNext(t);
        }
    }

    @Override
    public void onCancelRequest() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
        dismissProgressDialog();
    }

    public BaseSubscriber setOnErrorListener(OnErrorListener listener) {
        this.onErrorListener = listener;
        return this;
    }

    private void showProgress(final String progressTip) {
        if (showProgress) {
            if (getMainLooper() != Looper.myLooper()) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        netProgressDialog = NetProgressDialog.getInstance(context, progressTip, BaseSubscriber.this);
                        netProgressDialog.show();
                    }
                });
            } else {
                netProgressDialog = NetProgressDialog.getInstance(context, progressTip, BaseSubscriber.this);
                netProgressDialog.show();
            }
        }
    }

    public void dismissProgressDialog() {
        if (netProgressDialog != null && netProgressDialog.isShowing()) {
            netProgressDialog.dismiss();
        }
    }

    public Context getContext() {
        return context;
    }

    private Handler handler = new Handler(Looper.getMainLooper());

    public void showToast(final String msg) {
        if (!TextUtils.isEmpty(msg)) {
            if (Looper.myLooper() == getMainLooper()) {
                runToast(msg);
            } else {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        runToast(msg);
                    }
                });
            }
        }
    }

    private void runToast(String msg) {
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.show();
    }
}
