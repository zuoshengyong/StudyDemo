package daily.cn.studydemo.business.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import daily.cn.commonlib.CommonConstants;
import daily.cn.commonlib.ToastUtil;
import daily.cn.commonlib.base.BaseFragment;
import daily.cn.commonlib.http.result.HttpListResult;
import daily.cn.commonlib.http.result.HttpResult;
import daily.cn.studydemo.R;
import daily.cn.studydemo.business.other.BusinessFragment;

/**
 * <pre>
 * author :zuoshengyong
 * e-mail: partric23@gmail.com
 * time: 2018/03/06
 * </pre>
 */

public class MainFragment extends BaseFragment implements MainContract.View<HttpListResult<MainEntity>>{

    private TextView tv_content;

    private MainPresenter mPresenter;
    @Override
    protected View createContentView(LayoutInflater inflater, Bundle paramBundle) {
        mContentView=inflater.inflate(R.layout.fragment_main,null);
        return mContentView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setContentShown(false);
        initView();
        loadData();
    }

    private void initView() {
        tv_content=(TextView)mContentView.findViewById(R.id.tv_content);
        tv_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startModuleActivity(new Bundle(), BusinessFragment.class.getName());
            }
        });
    }

    private void loadData() {
        mPresenter=new MainPresenter(this,this);
        Map<String, String> params = new HashMap<>();
        params.put("pageNo", String.valueOf(1));
        params.put("pageSize", String.valueOf(10));
        mPresenter.loadData(params);
    }
    @Override
    public void onFailure() {
        ToastUtil.showToast(getActivity(),"网络请求失败");
    }

    @Override
    public void update2LoadData(HttpResult<HttpListResult<MainEntity>> result) {
        if(CommonConstants.HttpCode.CODE_SUCCESS.equalsIgnoreCase(result.getCode())){
            setContentShown(true);
            ToastUtil.showToast(getActivity(),"网络请求成功");
            tv_content.setText(result.toString());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.recycle();
    }
}
