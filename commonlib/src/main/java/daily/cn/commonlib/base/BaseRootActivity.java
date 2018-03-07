package daily.cn.commonlib.base;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import daily.cn.commonlib.CommonConstants;
import daily.cn.commonlib.Logger;
import daily.cn.commonlib.R;

/**
 * <pre>
 * author :zuoshengyong
 * e-mail: partric23@gmail.com
 * time: 2018/03/06
 * </pre>
 */

public class BaseRootActivity extends BaseActivity {

    protected FrameLayout mViewRoot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        addModuleFragment();
    }

    private View getContentView(){
        this.mViewRoot = new FrameLayout(this);
        if(Build.VERSION.SDK_INT >= 14){
            this.mViewRoot.setFitsSystemWindows(true);
        }
        this.mViewRoot.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        this.mViewRoot.setId(R.id.host_module_root_view);
        return this.mViewRoot;
    }
    /**
     * 开启一个新的fragment
     */
    private void addModuleFragment(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        try {
            Fragment fragment = this.loadFragment(intent);
            if(fragment != null){
                fragment.setArguments(bundle);
                this.getSupportFragmentManager().beginTransaction().replace(R.id.host_module_root_view, fragment).commitAllowingStateLoss();
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 加载需要的Fragment类，子类如果通过插件加载，可以覆盖该方法
     * @return咯改革日
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public Fragment loadFragment(Intent intent) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        String fragmentClass = intent.getStringExtra(CommonConstants.Module.FRAGMENT_CLASS);
        if(TextUtils.isEmpty(fragmentClass)){
            Logger.getLogger().e("no fragment find from intent with param " + CommonConstants.Module.FRAGMENT_CLASS);
            return null;
        }
        Fragment fragment = (Fragment)getClassLoader().loadClass(fragmentClass).newInstance();
        return fragment;
    }
}
