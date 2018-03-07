package daily.cn.commonlib.http.model;


import com.google.gson.Gson;

import daily.cn.commonlib.CommonConstants;
import daily.cn.commonlib.Logger;
import daily.cn.commonlib.http.RxActivity;
import daily.cn.commonlib.http.RxFragment;
import daily.cn.commonlib.http.result.HttpResult;
import daily.cn.commonlib.http.subscribers.BaseSubscriber;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;


/**
 * author :zuoshengyong
 * e-mail: partric23@gmail.com
 * time: 2018/03/05
 */

public class BaseModel {
    /**
     * @param subscriber 订阅者
     */
    @SuppressWarnings("unchecked")
    public static synchronized void sendRequest(final BaseSubscriber subscriber, Observable observable) {
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Predicate<Object>() {
                    @Override
                    public boolean test(@NonNull Object result) throws Exception {
                        return dispatchResult(result, subscriber);
                    }
                })
                .subscribeWith(subscriber);
    }

    /**
     * @param activity   用于与observable绑定,activity生命周期结束时,自动取消订阅
     * @param observable 被观察者
     * @param subscriber 订阅者
     */
    @SuppressWarnings("unchecked")
    public static synchronized void sendRequest(RxActivity activity, Observable observable, final BaseSubscriber subscriber) {
        observable.compose(activity.bindToLifecycle()) //防止内存泄漏,activity生命周期结束后取消订阅
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Predicate<HttpResult>() {
                    @Override
                    public boolean test(@NonNull HttpResult result) throws Exception {
                        return dispatchResult(result, subscriber);
                    }
                })
                .subscribe(subscriber);
    }

    /**
     * @param fragment   用于与observable绑定,fragment生命周期结束时,自动取消订阅
     * @param subscriber 订阅者
     */
    @SuppressWarnings("unchecked")
    public static synchronized void sendRequest(RxFragment fragment, Observable observable, final BaseSubscriber subscriber) {
        observable.compose(fragment.bindToLifecycle()) //防止内存泄漏,fragment生命周期结束后取消订阅
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).filter(new Predicate<HttpResult>() {
            @Override
            public boolean test(@NonNull HttpResult result) throws Exception {
                return dispatchResult(result, subscriber);
            }
        })

                .subscribe(subscriber);
    }

    private static synchronized boolean dispatchResult(Object response, final BaseSubscriber subscriber) {
        if (response == null) {
            return true;
        }
        HttpResult httpResult = null;
        if (response instanceof HttpResult) {
            httpResult = (HttpResult) response;
        }  else if (response instanceof ResponseBody) {
            try {
                ResponseBody responseBody = (ResponseBody) response;
                Gson gson = new Gson();
                String resultStr = responseBody.string();
                httpResult = gson.fromJson(resultStr, HttpResult.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (httpResult != null) {
            if (!CommonConstants.HttpCode.CODE_SUCCESS.equalsIgnoreCase(httpResult.getCode())) {
                Logger.getLogger().e("httpCode:%s,httpMessage:%s",httpResult.getCode(),httpResult.getMessage());
                if ("300".equalsIgnoreCase(httpResult.getCode()) || "201".equalsIgnoreCase(httpResult.getCode())) {
                    return true;
                }
                if("200001".equalsIgnoreCase(httpResult.getCode())){
                    return true;
                }
                subscriber.showToast(httpResult.getMessage());
            }
        }
        return true;
    }
}
