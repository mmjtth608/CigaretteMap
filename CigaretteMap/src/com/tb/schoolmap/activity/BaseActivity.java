package com.tb.schoolmap.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.tb.schoolmap.BuildConfig;
import com.tb.schoolmap.R;
import com.tth.widget.LoadingDialog;

public abstract class BaseActivity extends SherlockFragmentActivity {

	protected static final String TAG = BaseActivity.class.getSimpleName();

	protected Context mContext;
	protected boolean isBackground = false;
	protected LoadingDialog mLoadingDialog = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = BaseActivity.this;
		mLoadingDialog = new LoadingDialog(this, null);
		if (getSupportActionBar() != null) {
			// getSupportActionBar().setDisplayHomeAsUpEnabled(true);
			getSupportActionBar().setDisplayHomeAsUpEnabled(false);
			// getSupportActionBar().setDisplayShowHomeEnabled(true);
			getSupportActionBar().setHomeButtonEnabled(true);

		}
		// getSupportActionBar().setDisplayUseLogoEnabled(true);
		showProgressDialog(false);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.top_bar, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.refresh_loading:
			setLoadingState(true);
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	public void setLoadingState(boolean refreshing) {
		if (refreshing) {
			mLoadingDialog.show();
		} else {
			mLoadingDialog.dismiss();
		}
	}

	public void setLoadingState(boolean refreshing, String message) {
		if (refreshing)
			mLoadingDialog = new LoadingDialog(this, message);
		setLoadingState(refreshing);
	}

	protected void setHomeAsBackImage() {
		// getSupportActionBar().setIcon(R.drawable.ic_menu_back);
	}

	protected void hideKeyboard(View view) {
		InputMethodManager imm = (InputMethodManager) mContext
				.getSystemService(Context.INPUT_METHOD_SERVICE);

		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}

	protected void showKeyboard(View view) {
		InputMethodManager imm = (InputMethodManager) mContext
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(view, 0);
	}

	/**
	 * 
	 * @param show
	 */
	public void showProgressDialog(final boolean show) {
		if (getSupportActionBar() == null)
			return;
		if (show) {
			setSupportProgressBarIndeterminateVisibility(true);
		} else {
			setSupportProgressBarIndeterminateVisibility(false);
		}
	}

	/**
	 * @param title
	 */
	protected void setCustomTitle(String title) {

		if (getSupportActionBar() != null) {
			getSupportActionBar().setTitle(title);
		}
	}

	/**
	 * 
	 * @param resid
	 */
	protected void setCustomTitle(int resid) {
		if (getSupportActionBar() != null) {
			getSupportActionBar().setTitle(resid);
		}
	}

	/**
	 * 
	 * @param message
	 */
	protected void showMessage(String message) {
		Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
	}

	protected void SystemOut(String message) {
		if (BuildConfig.DEBUG)
			System.out.println(message);
	}
}
