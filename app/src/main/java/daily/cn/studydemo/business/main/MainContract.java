package daily.cn.studydemo.business.main;

import java.util.Map;

import daily.cn.commonlib.http.result.HttpResult;
import daily.cn.commonlib.mvp.IContract;
import daily.cn.commonlib.mvp.IPresenter;
import daily.cn.commonlib.mvp.IView;

/**
 * <pre>
 * author :zuoshengyong
 * e-mail: partric23@gmail.com
 * time: 2018/03/06
 * </pre>
 */

public interface MainContract extends IContract {
    interface View<T> extends IView {
        void update2LoadData(HttpResult<T> result);
    }
    interface Presenter extends IPresenter {
        void loadData(Map<String,String> params);
    }
}
