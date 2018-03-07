package daily.cn.studydemo.business.main;

import android.content.Context;

import java.util.Map;

import daily.cn.commonlib.ToastUtil;
import daily.cn.commonlib.http.RxFragment;
import daily.cn.commonlib.http.model.BaseModel;
import daily.cn.commonlib.http.result.HttpListResult;
import daily.cn.commonlib.http.result.HttpResult;
import daily.cn.commonlib.http.subscribers.BaseSubscriber;
import daily.cn.commonlib.http.subscribers.OnErrorListener;
import daily.cn.commonlib.http.subscribers.SubscriberOnNextListener;
import daily.cn.commonlib.http.util.BaseServiceUtil;
import daily.cn.commonlib.http.util.NetUtil;
import daily.cn.studydemo.http.ApiConstants;
import daily.cn.studydemo.http.MainService;
import io.reactivex.Observable;

/**
 * <pre>
 * author :zuoshengyong
 * e-mail: partric23@gmail.com
 * time: 2018/03/06
 * </pre>
 */

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View view;
    private Context mContext;
    private RxFragment rxFragment;

    public MainPresenter(RxFragment rxFragment, MainContract.View view) {
        if(rxFragment==null||view==null){
            throw new IllegalArgumentException("rxFragment or view should not be null");
        }
        this.view = view;
        this.rxFragment = rxFragment;
        this.mContext = rxFragment.getContext();
    }
    @Override
    public void loadData(Map<String, String> params) {
        if(!NetUtil.isNetAvailable(mContext)){
            ToastUtil.showToast(mContext,"网络不可用，请检查~");
            return;
        }
        BaseSubscriber<HttpResult<HttpListResult<MainEntity>>> subscriber = new BaseSubscriber<>(new SubscriberOnNextListener<HttpResult<HttpListResult<MainEntity>>>() {
            @Override
            public void onNext(HttpResult<HttpListResult<MainEntity>> result) {
                if(result!=null){
                    view.update2LoadData(result);
                }
            }
        }, new OnErrorListener() {
            @Override
            public void onError() {
                view.onFailure();
            }
        },mContext);
        Observable observable = BaseServiceUtil.createService(MainService.class).getMainList(ApiConstants.GET_MAIN_LIST_API,params);
        BaseModel.sendRequest(rxFragment, observable, subscriber);
    }

    /**
     * 清除对外部对象的引用，防止内存泄露。
     */
    public void recycle() {
        this.view = null;
    }
}
