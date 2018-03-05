package daily.cn.commonlib;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * <pre>
 * author :zuoshengyong
 * e-mail: partric23@gmail.com
 * time: 2018/03/05
 * </pre>
 */
public class ToastUtil {

	private static Toast toast;

	public static void showToast(final Context mContext, final String msg) {
		showToast(mContext,msg, Toast.LENGTH_SHORT);
	}
	public static void showToast(final Context mContext, final String msg, final int duration) {
		if (!TextUtils.isEmpty(msg)&&mContext!=null) {
			if (Looper.myLooper() == mContext.getMainLooper()) {
				runToast(mContext,msg,duration);
			} else {
				new Handler().post(new Runnable() {
					@Override
					public void run() {
						runToast(mContext,msg,duration);
					}
				});
			}
		}
	}

	private static void runToast(Context mContext, String msg, final int duration) {
		if (toast == null) {
			toast = Toast.makeText(mContext, msg, duration<0? Toast.LENGTH_SHORT:duration);
		} else {
			toast.setText(msg);
			toast.setDuration(duration<=0? Toast.LENGTH_SHORT:duration);
		}
		toast.show();
	}
}
