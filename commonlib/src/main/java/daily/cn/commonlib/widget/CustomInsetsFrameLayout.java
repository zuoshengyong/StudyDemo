package daily.cn.commonlib.widget;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public final class CustomInsetsFrameLayout extends FrameLayout {
	private int[] mInsets = new int[4];

	public CustomInsetsFrameLayout(Context context) {
		super(context);
	}

	public CustomInsetsFrameLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CustomInsetsFrameLayout(Context context, AttributeSet attrs,
                                   int defStyle) {
		super(context, attrs, defStyle);
	}

	public final int[] getInsets() {
		return mInsets;
	}

	@Override
	protected final boolean fitSystemWindows(Rect insets) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			mInsets[0] = insets.left;
			mInsets[1] = insets.top;
			mInsets[2] = insets.right;

			insets.left = 0;
			insets.top = 0;
			insets.right = 0;
		}

		return super.fitSystemWindows(insets);
	}
}
