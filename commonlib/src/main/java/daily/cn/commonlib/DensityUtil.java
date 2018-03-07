package daily.cn.commonlib;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * <pre>
 * author :zuoshengyong
 * e-mail: partric23@gmail.com
 * time: 2018/03/06
 * desc:尺寸转换
 * </pre>
 */
public class DensityUtil {
	
	public static int dip2px(Context context, float dpValue) {
		if(context==null)
			throw new IllegalArgumentException("context should not be null");
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	public static int px2dip(Context context, float pxValue) {
		if(context==null)
			throw new IllegalArgumentException("context should not be null");
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/** 获取手机的密度*/
	public static float getDensity(Context context) {
		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		return dm.density;
	}
}
