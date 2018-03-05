package daily.cn.commonlib.http.subscribers;

/**
 * author :zuoshengyong
 * e-mail: partric23@gmail.com
 * time: 2018/03/05
 */
public interface SubscriberOnNextListener<T> {
    void onNext(T t);
}
