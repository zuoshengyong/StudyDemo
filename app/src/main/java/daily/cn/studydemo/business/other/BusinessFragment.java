package daily.cn.studydemo.business.other;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import daily.cn.commonlib.base.BaseTitleBarFragment;
import daily.cn.studydemo.R;

/**
 * <pre>
 * author :zuoshengyong
 * e-mail: partric23@gmail.com
 * time: 2018/03/07
 * </pre>
 */

public class BusinessFragment extends BaseTitleBarFragment {
    @Override
    protected View createContentView(LayoutInflater inflater, Bundle paramBundle) {
        mContentView=inflater.inflate(R.layout.fragment_business,null);
        return mContentView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setContentShown(true);
        setTitle("商务页面");
        initView();
    }

    private void initView() {
        TextView tv_business=mContentView.findViewById(R.id.tv_business);
        tv_business.setText("福克斯了的减肥了开始就打发时间砥砺奋进私搭乱建方式简单冯老师会计法圣诞节反倒是");
    }
}
