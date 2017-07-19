package com.kxf.atwosimple;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


public class WelcomeActivity extends Activity {
	List<HashMap<String, String>> hashMaps;
	SharedPreferences mySharedPreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome_activity);
		mySharedPreferences = getSharedPreferences("kxf-zjy",
				Activity.MODE_PRIVATE);
		boolean isNet = OtherUtilsKxf.isNetworkConnected(this);
		if (!isNet) {
			Toast.makeText(this, "当前无网络连接", Toast.LENGTH_SHORT).show();
		} else {
			new Thread() {
				@Override
				public void run() {
					try {
						// getData();
						getTab();
						Thread.sleep(1200);
						Intent intent = new Intent(WelcomeActivity.this,
								MainActivity.class);
						startActivity(intent);
						finish();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}.start();
		}
	}

	/**
	 * 读取配置文件
	 * 
	 * @Title: getData
	 * @Description:
	 * @param --参数说明
	 * @return void 返回类型
	 * @throws-- JumpException
	 */

	public void getData() {
		try {
			properties = MyProperUtil.getProperties(getApplicationContext());
			String value = properties.getProperty("codeJson");
			JSONObject jsonObject = new JSONObject(value);
			JSONArray arrayOne = jsonObject.optJSONArray("json");
			Log.i("kxf", value + "");
			Editor editor = mySharedPreferences.edit();

			for (int i = 0; i < arrayOne.length(); i++) {
				JSONObject eachObject = (JSONObject) arrayOne.opt(i);
				String tName = eachObject.get("name").toString();
				String tUrl = eachObject.get("url").toString();
				String tEng = eachObject.get("english").toString();
				String tImg = eachObject.get("img").toString();
				editor.putString("onename" + i, tName);
				editor.putString("oneurl" + i, tUrl);
				editor.putString("oneenglish" + i, tEng);
				editor.putString("oneimg" + i, tImg);
			}
			editor.commit();
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	private String strUrl;
	Properties properties;

	public void getNetData() {
		HttpURLConnection httpURLConnection = null;
		try {
			URL url = new URL(strUrl);
			httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setRequestMethod("GET");
			httpURLConnection.setRequestProperty("Charset", "UTF-8");
			httpURLConnection.setRequestProperty("Content-Type",
					"text/xml; charset=UTF-8");
			httpURLConnection.setRequestProperty("MyProperty", "this is me!");
			if (httpURLConnection.getResponseCode() == 200) {
				InputStream is = httpURLConnection.getInputStream();
				StringBuffer out = new StringBuffer();
				InputStreamReader inread = new InputStreamReader(is, "utf-8");
				char[] b = new char[2048];
				for (int n; (n = inread.read(b)) != -1;) {
					out.append(new String(b, 0, n));
				}
				JSONObject jsonObject = new JSONObject(out.toString());
				Log.e("kxflog","-----------------"+jsonObject);
				JSONArray arrayOne = jsonObject.optJSONArray("bottom");
				Editor editor = mySharedPreferences.edit();
				for (int i = 0; i < arrayOne.length(); i++) {
					JSONObject eachObject = (JSONObject) arrayOne.opt(i);
					String tName = eachObject.get("name").toString();
					String tUrl = eachObject.get("url").toString();
					String tEng = eachObject.get("englishName").toString();
					String tUrl_E = eachObject.get("englishiUrl")
							.toString();
					String tImg = eachObject.get("img").toString();
					editor.putString("onename" + i, tName);
					editor.putString("oneurl" + i, tUrl);
					editor.putString("oneenglish" + i, tEng);
					editor.putString("oneimg" + i, tImg);
					editor.putString("oneengUrl" + i, tUrl_E);
				}
				editor.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * @Title: getTab
	 * @Description: [底部信息]
	 */
	public void getTab() {
		properties = MyProperUtil.getProperties(getApplicationContext());
		strUrl = properties.getProperty("serverUrl");
		 getNetData();
	}
}
