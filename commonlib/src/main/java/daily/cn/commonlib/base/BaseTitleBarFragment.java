package daily.cn.commonlib.base;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import daily.cn.commonlib.DensityUtil;
import daily.cn.commonlib.R;
import daily.cn.commonlib.widget.CustomInsetsFrameLayout;

/**
 * 基础类，主要是定义了titlebar
 * <p>
 * 内容项：
 * 1.顶部通用titlebar
 * 2.右上角的pop menu菜单项
 * <p>
 *
 */
public abstract class BaseTitleBarFragment extends BaseFragment {

    /**
     * 帶有titleBar 的主視圖區域
     */
    protected View mBaseFragmentView;

    /**
     * 不含titleBar的FrameLayout，承載變化的內容
     */
    private CustomInsetsFrameLayout mContentContainer;

    /**
     * 顶部导航栏
     */
    private View mTitleBar;

    /**
     * 顶部导航栏标题控件
     */
    private TextView mTitleTextView;

    /**
     * 左侧回退按钮
     */
    private View mBackButton;

    /**
     * 右侧菜单按钮
     */
    private View mMenuButton;

    /**
     * 右侧扩展区按钮
     */
    private LinearLayout mExtendsMenuLayout;//菜单扩展区域

    /**
     * 右上角的pop menu相关
     */
    private PopupWindow mPopupWindow;
    private ListView mPopupListView;

    /**
     * 主要是创建titlebar
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View progressView = super.onCreateView(inflater, container, savedInstanceState);

        mBaseFragmentView = inflater.inflate(getLayoutResId(), container, false);
        mContentContainer = (CustomInsetsFrameLayout) mBaseFragmentView.findViewById(R.id.base_content_container);
        mTitleBar = mBaseFragmentView.findViewById(R.id.titlebar_container);
        mTitleTextView = (TextView) mBaseFragmentView.findViewById(R.id.host_titlebar_title);
        mBackButton = mBaseFragmentView.findViewById(R.id.host_titlebar_back);
        mMenuButton = mBaseFragmentView.findViewById(R.id.host_titlebar_menu);
        mExtendsMenuLayout = (LinearLayout) mBaseFragmentView.findViewById(R.id.host_titlebar_extends_menu_layout);

        //进度条
        mContentContainer.addView(progressView);

        //左上返回键
        mBackButton.setOnClickListener(mOnClickListener);
        //右上菜单栏
        mMenuButton.setOnClickListener(mOnPopMenuClickListener);
        return mBaseFragmentView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //点击菜单栏按钮绘制或释放pop菜单
        getMenuButton().setOnClickListener(mOnPopMenuClickListener);
        initBelowPopupWindow();
    }

    //===========================================================titlebar begin=======================================================================

    /**
     * 获取titlebar控件 layout
     *
     * @return
     */
    protected int getLayoutResId() {
        return R.layout.host_new_fragment_base;
    }

    /**
     * 获取titlebar控件
     *
     * @return
     */
    public View getTitleBar() {
        return mTitleBar;
    }

    /**
     * 设置后退按钮是否可视
     *
     * @param enable
     */
    public void setBackButtonEnable(boolean enable) {
        mBackButton.setVisibility(enable ? View.VISIBLE : View.GONE);
    }

    /**
     * 设置菜单按钮是否可视
     *
     * @param enable
     */
    public void setMenuButtonEnable(boolean enable) {
        mMenuButton.setVisibility(enable ? View.VISIBLE : View.GONE);
    }

    /**
     * 获取回退按钮View
     *
     * @return
     */
    public View getBackButton() {
        return mBackButton;
    }

    /**
     * 获取菜单按钮View
     *
     * @return
     */
    public View getMenuButton() {
        return mMenuButton;
    }

    /**
     * 获取扩展区域View
     *
     * @return
     */
    public View getExtendsMenuLayout() {
        return mExtendsMenuLayout;
    }

    /**
     * 设置回退的图片
     *
     * @param resId
     */
    public void setBackButtonImg(int resId) {
        ((ImageView) mBackButton).setImageResource(resId);
    }

    /**
     * 设置菜单按钮的图片
     *
     * @param resId
     */
    public void setMenuButtonImg(int resId) {
        ((ImageView) mMenuButton).setImageResource(resId);
    }

    /**
     * 设置标题
     *
     * @param resId
     */
    public void setTitle(int resId) {
        if (mTitleTextView != null) {
            mTitleTextView.setText(resId);
        }
    }

    public void setTitle(CharSequence title) {
        if (mTitleTextView != null) {
            mTitleTextView.setText(title);
        }
    }

    /***
     * 设置HTML格式的标题
     * @param str
     */
    public void setTitleHtml(String str) {
        if (mTitleTextView != null) {
            mTitleTextView.setText(Html.fromHtml(str));
        }
    }

    /**
     * 获取标题TextView
     *
     * @return
     */
    public TextView getTitleTv() {
        if (mTitleTextView != null) {
            return mTitleTextView;
        }
        return null;
    }

    /**
     * 點擊回退的方法，待考究
     */
    protected View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.host_titlebar_back) {
                getActivity().onBackPressed();
            }
        }
    };

    //===========================================================titlebar end=========================================================================


    //===========================================================右上角pop menu开始=====================================================================

    /**
     * 获取下拉菜单控件
     *
     * @return 返回ListView控件
     */
    public ListView getPopupListView() {
        return mPopupListView;
    }

    /**
     * 初始化下拉菜单
     */
    private void initBelowPopupWindow() {

    }

    /**
     * 返回菜单显示的内容，由子类覆盖实现
     *
     * @return
     */

    /**
     * 菜单适配器
     */


    protected void dismissPopupWindow() {
        if (mPopupWindow.isShowing())
            mPopupWindow.dismiss();
    }

    /**
     * 右上角按钮添加点击事件
     */
    protected View.OnClickListener mOnPopMenuClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int i = v.getId();
            if (i == R.id.host_titlebar_menu) {
                if (mPopupWindow != null) {
                    if (!mPopupWindow.isShowing()) {
                        mPopupWindow.showAsDropDown(v, DensityUtil.dip2px(getActivity(),-10.0f), DensityUtil.dip2px(getActivity(),-5.0f));
                    } else {
                        mPopupWindow.dismiss();
                    }
                }
            } else {
            }
        }
    };

    //===========================================================右上角pop menu end===================================================================
}
