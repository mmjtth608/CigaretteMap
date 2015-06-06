package com.tth.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;

import com.tb.schoolmap.R;
import com.tth.common.Utility;

public class LoadingDialog extends Dialog {

	private Context context;
	private String message = "正在加载,请稍后...";
	private LoadingView mLoadingView;

	public LoadingDialog(Context context, String message) {
		super(context, R.style.loadingdialog);
		this.context = context;
		if (!TextUtils.isEmpty(message))
			this.message = message;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.dialog_loading);
		mLoadingView = (LoadingView) findViewById(R.id.loadingView);
		mLoadingView.setLoadingText(message);
		Window dialogWindow = getWindow();
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		lp.width = (int) (Utility.getWindowSize(context).widthPixels * 1); // 高度设置为屏幕的0.6
		lp.height = (int) (Utility.getWindowSize(context).heightPixels * 1); // 高度设置为屏幕的0.6
		dialogWindow.setAttributes(lp);
	}

}