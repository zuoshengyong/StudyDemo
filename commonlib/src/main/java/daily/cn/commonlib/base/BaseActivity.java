package daily.cn.commonlib.base;

import android.content.Context;
import android.os.Bundle;

import daily.cn.commonlib.http.RxActivity;

/**
 * <pre>
 * author :zuoshengyong
 * e-mail: partric23@gmail.com
 * time: 2018/03/05
 * </pre>
 */

public class BaseActivity extends RxActivity {

    protected Context mContext;
    protected RxActivity rxActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=this.getBaseContext();
        rxActivity=this;
    }
}
