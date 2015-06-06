package com.tth.common;

import android.content.Context;
import android.util.DisplayMetrics;

public class Utility {

	/**
	 * dip px 转换
	 * 
	 * @param dipValue
	 * @return dip的换算： dip（value）=(int) (px（value）/1.5 + 0.5)
	 */
	public static int dip2px(Context mContext, float dipValue) {
		final float scale = mContext.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	public static DisplayMetrics getWindowSize(Context mContext) {
		DisplayMetrics d = mContext.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
		return d;
	}
}
