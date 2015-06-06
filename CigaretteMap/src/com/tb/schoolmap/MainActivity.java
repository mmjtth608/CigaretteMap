package com.tb.schoolmap;

import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.LatLng;
import com.tb.schoolmap.activity.BaseActivity;

public class MainActivity extends BaseActivity implements AMapLocationListener,
		Runnable {

	private MapView mapView;
	private AMap aMap;
	private LocationManagerProxy aMapLocManager = null;
	private AMapLocation aMapLocation;// 用于判断定位超时
	private Handler handler = new Handler();
	private int mapzoom = 15;
	private TextView tv_location;// 我的位置textview
	private boolean isFirst = true;// 是否第一次定位，是否需要居中

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mapView = (MapView) findViewById(R.id.map);
		tv_location = (TextView) findViewById(R.id.tv_location);
		mapView.onCreate(savedInstanceState);// 此方法必须重写
		init();
		startLocation();
	}

	OnClickListener tvlocationClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (aMapLocation != null) {
				aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
						new LatLng(aMapLocation.getLatitude(), aMapLocation
								.getLongitude()), mapzoom));
			} else {
				isFirst = true;
				setLoadingState(true, "正在定位");
				startLocation();
			}
		}
	};

	/**
	 * 初始化AMap对象
	 */
	private void init() {
		if (aMap == null) {
			aMap = mapView.getMap();
		}
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onResume() {
		super.onResume();
		mapView.onResume();
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onPause() {
		super.onPause();
		mapView.onPause();
		stopLocation();// 停止定位
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mapView.onSaveInstanceState(outState);
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mapView.onDestroy();
	}

	/**
	 * 销毁定位
	 */
	private void stopLocation() {
		if (aMapLocManager != null) {
			aMapLocManager.removeUpdates(this);
			aMapLocManager.destory();
		}
		aMapLocManager = null;
	}

	/**
	 * 开始定位
	 */
	private void startLocation() {
		aMapLocation = null;
		tv_location.setOnClickListener(null);
		if (aMapLocManager == null)
			aMapLocManager = LocationManagerProxy.getInstance(this);
		/*
		 * mAMapLocManager.setGpsEnable(false);//
		 * 1.0.2版本新增方法，设置true表示混合定位中包含gps定位，false表示纯网络定位，默认是true Location
		 * API定位采用GPS和网络混合定位方式
		 * ，第一个参数是定位provider，第二个参数时间最短是2000毫秒，第三个参数距离间隔单位是米，第四个参数是定位监听者
		 */
		aMapLocManager.requestLocationUpdates(
				LocationProviderProxy.AMapNetwork, 2000, 10, this);
		// handler.postDelayed(this, 12000);// 设置超过12秒还没有定位到就停止定位
	}

	/**
	 * 此方法已经废弃
	 */
	@Override
	public void onLocationChanged(Location location) {
	}

	@Override
	public void onProviderDisabled(String provider) {

	}

	@Override
	public void onProviderEnabled(String provider) {

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {

	}

	/**
	 * 混合定位回调函数
	 */
	@Override
	public void onLocationChanged(AMapLocation location) {
		if (location != null && !TextUtils.isEmpty(location.getAddress())) {
			this.aMapLocation = location;// 判断超时机制
			if (isFirst)
				aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
						location.getLatitude(), location.getLongitude()),
						mapzoom));
			tv_location.setText(location.getAddress());
			isFirst = false;
			setLoadingState(false, "正在定位");
			tv_location.setOnClickListener(tvlocationClickListener);
		} else {
			tv_location.setText("正在定位...");
			tv_location.setOnClickListener(null);
		}
	}

	@Override
	public void run() {
		if (aMapLocation == null) {
			showMessage("12秒内还没有定位成功，停止定位");
			tv_location.setText("12秒内还没有定位成功，停止定位");
			tv_location.setOnClickListener(tvlocationClickListener);
			stopLocation();// 销毁掉定位
		}
	}
}
