package daily.cn.commonlib.http.util;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import daily.cn.commonlib.R;


/**
 * 进度对话框
 * author :zuoshengyong
 * e-mail: partric23@gmail.com
 * time: 2018/03/05
 */

public class NetProgressDialog extends Dialog {

    private CancelRequestListener cancelRequestListener;

    public synchronized static NetProgressDialog getInstance(Context context) {
        return getInstance(context, null);
    }

    public synchronized static NetProgressDialog getInstance(Context context, String tipMsg) {
        return getInstance(context, tipMsg, null);
    }

    public synchronized static NetProgressDialog getInstance(Context context, String tipMsg, CancelRequestListener listener) {
        return new NetProgressDialog(context, tipMsg, listener);
    }

    private NetProgressDialog(Context context, String tipMsg, CancelRequestListener listener) {
        super(context, R.style.actionSheetdialog);
        this.cancelRequestListener = listener;
        init(tipMsg);
    }

    private void init(String tipMsg) {
        setContentView(R.layout.layout_net_progress);
        if (!TextUtils.isEmpty(tipMsg)) {
            TextView tvTip = (TextView) findViewById(R.id.tv_tip);
            tvTip.setText(tipMsg);
            tvTip.setVisibility(View.GONE);
        }
//        Window window = getWindow();
//        WindowManager.LayoutParams params = window.getAttributes();
//        params.gravity = Gravity.CENTER;
//        params.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
//        params.dimAmount = 0.7f;
//        params.format = PixelFormat.TRANSLUCENT;

        // 触摸对话框以外的地方取消对话框
        setCanceledOnTouchOutside(false);
    }

    @Override
    public void show() {
        try {
            super.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dismiss() {
        // TODO Auto-generated method stub
        try {
            super.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        if (cancelRequestListener != null) {
            cancelRequestListener.onCancelRequest();
        }
        dismiss();
    }
}
