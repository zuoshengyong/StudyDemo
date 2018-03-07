package daily.cn.commonlib.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import daily.cn.commonlib.CommonConstants;

/**
 * <pre>
 * author :zuoshengyong
 * e-mail: partric23@gmail.com
 * time: 2018/03/06
 * </pre>
 */

public abstract class BaseFragment extends ProgressFragment {
    /**
     * 内容主视图
     */
    protected View mContentView;
    /**
     * 加载视图
     * 将加载主视图工作放到这个里面
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View progressView = super.onCreateView(inflater, container, savedInstanceState);
        mContentView = createContentView(inflater, savedInstanceState);
        return progressView;
    }

    /**
     * 完成视图填充工作
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mContentView != null){
            setContentView(mContentView);
        }
    }

    /**
     * 設置主內容區域的顯示layout，由子類自定義實現
     * @param inflater
     * @param paramBundle
     * @return
     */
    protected abstract View createContentView(LayoutInflater inflater, Bundle paramBundle);



    /**
     * 根据一个新的fragment启动一个新的页面
     *
     * 关于模块ID的判断
     * 1.关于包名格式和模块名的规则
     * @param paramBundle
     * @param fragmentClass
     */
    protected void startModuleActivity(Bundle paramBundle, String fragmentClass){
        Intent localIntent = new Intent();
        localIntent.setClass(getActivity(),BaseRootActivity.class);
        localIntent.putExtra(CommonConstants.Module.FRAGMENT_CLASS, fragmentClass);
        localIntent.putExtras(paramBundle);
        startActivity(localIntent);
    }

    /**
     * 根据一个新的fragment启动一个新的页面 需要返回值的
     * @param requestCode
     * @param paramBundle
     * @param fragmentClass
     */
    protected void startModuleActivityForResult(int requestCode, Bundle paramBundle, String fragmentClass){
        Intent localIntent = new Intent();
        localIntent.setClass(getActivity(),BaseRootActivity.class);
        localIntent.putExtra(CommonConstants.Module.FRAGMENT_CLASS, fragmentClass);
        localIntent.putExtras(paramBundle);
        startActivityForResult(localIntent, requestCode);
    }
}
