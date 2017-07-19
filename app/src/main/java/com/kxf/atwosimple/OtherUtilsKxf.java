package com.kxf.atwosimple;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

/**
 * 
 * @author kenneth孔
 * 
 * @2015-8-12
 * 
 */
public class OtherUtilsKxf {

	public static boolean isNetworkConnected(Context context) {
		ConnectivityManager connectMgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		// 移动网络
		NetworkInfo mobNetInfo = connectMgr
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		// wifi网络
		NetworkInfo wifiNetInfo = connectMgr
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (!mobNetInfo.isConnected() && !wifiNetInfo.isConnected()) {
			return false;
		} else {
			return true;
		}

	}
	
	/**
	 * post XUTILS
	 */
	public void getConByXUTILS() {
		RequestParams params = new RequestParams();
		params.addBodyParameter("str1", "str1");
		HttpUtils httpUtils = new HttpUtils();
		httpUtils.send(HttpMethod.POST, "url", new RequestCallBack<String>() {
			@Override
			public void onFailure(HttpException arg0, String arg1) {
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				// String backRes = arg0.result;
			}
		});
	}

	public static void dismissProgress(Context context, Dialog progressDialog) {
		progressDialog.dismiss();
	}

	/** 加载数据对话框 */
	private static Dialog mLoadingDialog;

	/**
	 * 显示加载对话框
	 * 
	 * @param context
	 *            上下文
	 * @param msg
	 *            对话框显示内容
	 * @param cancelable
	 *            对话框是否可以取消
	 */
	public static void showDialogForLoading(Activity context, String msg,
			boolean cancelable) {
		View view = LayoutInflater.from(context).inflate(
				R.layout.layout_loading_dialog, null);

		TextView loadingText = (TextView) view
				.findViewById(R.id.id_tv_loading_dialog_text);
		loadingText.setText(msg);
		// mLoadingDialog.setCancelable(false);
		mLoadingDialog = new Dialog(context, R.style.loading_dialog_style);
		mLoadingDialog.setCancelable(cancelable);
		mLoadingDialog.setCanceledOnTouchOutside(false);
		mLoadingDialog.setContentView(view, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT));
		mLoadingDialog.show();
	}

	public static int px2sp(Context context, float pxValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (pxValue / fontScale + 0.5f);
	}

	public static int dp2px(Context context, int dp) {
		DisplayMetrics displayMetrics = context.getResources()
				.getDisplayMetrics();
		return (int) ((dp * displayMetrics.density) + 0.5);
	}

	/**
	 * 关闭加载对话框
	 */
	public static void hideDialogForLoading() {
		if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
			mLoadingDialog.cancel();
		}
	}

}
