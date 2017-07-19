package com.kxf.atwosimple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.webkit.DownloadListener;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.beijing.dianyingjie.VideoEnabledWebChromeClient;
import com.beijing.dianyingjie.VideoEnabledWebView;

/**
 * @author kxf
 * @ClassName: MainActivity
 * @Description: [主页]
 * @date 2016-3-24 下午4:39:17
 * @since JDK 1.6
 */
public class MainActivity extends Activity implements OnRefreshListener,
        OnClickListener {
    private RelativeLayout parent;
    private FrameLayout videoLayout;
    private VideoEnabledWebView webView;
    Properties properties;
    List<HashMap<String, String>> hashMaps;
    private TextView textView1, textView2, textView3, textView4, texTitle,
            textView5;
    private Button imageView1, imageView2, imageView3, imageView4, imageView5;
    private SwipeRefreshLayout swipeRefreshLayout;
    SharedPreferences mySharedPreferences;
    private String tabName, tabImg, tabUrl, tabEnglish, tabEngUrl;
    private List<String> list_name, list_url, list_img, list_english,
            list_engurl;
    private LinearLayout shoyeLinear, richengLinear, shipinLiner, tupianLiner,
            selct_liner, titl_line;
    private int isEnZh = 0;
    private VideoEnabledWebChromeClient webChromeClient;

    @SuppressLint("InlinedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mySharedPreferences = getSharedPreferences("kxf-zjy",
                Activity.MODE_PRIVATE);
        list_name = new ArrayList<String>();
        list_url = new ArrayList<String>();
        list_img = new ArrayList<String>();
        list_engurl = new ArrayList<String>();
        list_english = new ArrayList<String>();
        for (int i = 0; i < 5; i++) {
            tabName = mySharedPreferences.getString("onename" + i, "");
            list_name.add(tabName);

            tabImg = mySharedPreferences.getString("oneimg" + i, "");
            list_img.add(tabImg);

            tabUrl = mySharedPreferences.getString("oneurl" + i, "");
            list_url.add(tabUrl);

            tabEnglish = mySharedPreferences.getString("oneenglish" + i, "");
            list_english.add(tabEnglish);

            tabEngUrl = mySharedPreferences.getString("oneengUrl" + i, "");
            list_engurl.add(tabEngUrl);
        }
        setContentView(R.layout.activity_main);
        properties = MyProperUtil.getProperties(getApplicationContext());
        webView = (VideoEnabledWebView) findViewById(R.id.main_content);
        parent = (RelativeLayout) findViewById(R.id.content_frame);
        videoLayout = (FrameLayout) findViewById(R.id.videoLayout);
        hashMaps = new ArrayList<HashMap<String, String>>();
        textView1 = (TextView) findViewById(R.id.main_txtone);
        textView2 = (TextView) findViewById(R.id.main_txttwo);
        textView3 = (TextView) findViewById(R.id.main_txtthre);
        textView4 = (TextView) findViewById(R.id.main_txtfour);
        texTitle = (TextView) findViewById(R.id.textTitle);
        textView5 = (TextView) findViewById(R.id.main_txtfive);
        imageView1 = (Button) findViewById(R.id.main_imgone);
        imageView2 = (Button) findViewById(R.id.main_imgtwo);
        imageView3 = (Button) findViewById(R.id.main_imgthre);
        imageView4 = (Button) findViewById(R.id.main_imgfour);
        imageView5 = (Button) findViewById(R.id.main_imgfive);
        shoyeLinear = (LinearLayout) findViewById(R.id.bottom_shouye);
        richengLinear = (LinearLayout) findViewById(R.id.bottom_richeng);
        shipinLiner = (LinearLayout) findViewById(R.id.bottom_shipin);
        tupianLiner = (LinearLayout) findViewById(R.id.bottom_tupian);
        titl_line = (LinearLayout) findViewById(R.id.main_top);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.main_swipe);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light);

        // showUrl("file:///android_asset/androidtohtml.html");
        webChromeClient = new VideoEnabledWebChromeClient(this, parent, videoLayout);
//        showUrl("file:///android_asset/androidtohtml.html");
        setData();
        showWeb(list_url.get(0));
//		showWeb("http://www.bjiff.com/movie/");

    }

    public void setData() {
        textView1.setText(list_name.get(0));
        textView2.setText(list_name.get(1));
        textView3.setText(list_name.get(2));
        textView4.setText(list_name.get(3));
        textView5.setText(list_name.get(4));
    }

    /**
     * 英文
     */
    public void textChangeEnglish() {
        texTitle.setText("  The 7th BJIFF");
        webView.loadUrl("http://www.bjiff.com/app_en/");
        textView1.setText(list_english.get(0));
        textView2.setText(list_english.get(1));
        textView3.setText(list_english.get(2));
        textView4.setText(list_english.get(3));
        textView5.setText(list_english.get(4));
    }

    public void mainOne(View view) {
        clearTabImag();
        imageView1.setBackgroundResource(R.drawable.shou_ye);
        if (isEnZh == 0) {
            showWeb(list_url.get(0));
        }
        if (isEnZh == 1) {
            showWeb(list_engurl.get(0));
        }
    }

    public void mainTwo(View view) {
        clearTabImag();
        imageView2.setBackgroundResource(R.drawable.ri_cheng);
        if (isEnZh == 0)
            showWeb(list_url.get(1));
        if (isEnZh == 1)
            showWeb(list_engurl.get(1));
    }

    public void mainThre(View view) {
        clearTabImag();
        imageView3.setBackgroundResource(R.drawable.video_);
        if (isEnZh == 0)
            showWeb(list_url.get(2));
        if (isEnZh == 1)
            showWeb(list_engurl.get(2));
    }

    public void mainFour(View view) {
        clearTabImag();
        imageView4.setBackgroundResource(R.drawable.photo_);
        if (isEnZh == 0)
            showWeb(list_url.get(3));
        if (isEnZh == 1)
            showWeb(list_engurl.get(3));
    }

    public void mainFive(View view) {
        clearTabImag();
        imageView5.setBackgroundResource(R.drawable.user_on);
        if (isEnZh == 0)
            showWeb(list_url.get(4));
        if (isEnZh == 1)
            showWeb(list_engurl.get(4));
    }

    /**
     * 中文
     */
    public void textChangeChinese() {

        texTitle.setText("第七届北京电影节");
        webView.loadUrl("http://www.bjiff.com/shoujiapp/");
        setData();
    }

    Handler handler = new Handler() {

        public void handleMessage(android.os.Message msg) {

            switch (msg.what) {
                case 19:
                    textChangeEnglish();
                    break;
                case 13:
                    String str = webView.getUrl();
                    // webView.loadUrl("");
                    webView.loadUrl(str);
                    swipeRefreshLayout.setRefreshing(false);
                    break;
                case 21:
                    textChangeChinese();
                    break;
            }

        }

        ;
    };



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();// 返回前一个页面
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onRefresh() {
        new Thread() {
            @Override
            public void run() {
                handler.sendEmptyMessage(13);
            }
        }.start();

    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bottom_shouye:

                clearTabImag();
                imageView1.setBackgroundResource(R.drawable.shou_ye);
                if (isEnZh == 0) {

                    showWeb(list_url.get(0));
                }
                if (isEnZh == 1) {
                    showWeb(list_engurl.get(0));
                }
                break;
            case R.id.bottom_richeng:

                clearTabImag();
                imageView2.setBackgroundResource(R.drawable.ri_cheng);
                if (isEnZh == 0) {
                    showWeb(list_url.get(1));
                }
                if (isEnZh == 1) {
                    showWeb(list_engurl.get(1));
                }
                break;
            case R.id.bottom_shipin:
                clearTabImag();
                imageView3.setBackgroundResource(R.drawable.video_);

                if (isEnZh == 0) {
                    showWeb(list_url.get(2));
                }
                if (isEnZh == 1) {
                    Log.i("kxf", list_engurl.get(2));
                    showWeb(list_engurl.get(2));
                }
                break;
            case R.id.bottom_tupian:

                clearTabImag();
                imageView4.setBackgroundResource(R.drawable.photo_);
                if (isEnZh == 0) {
                    showWeb(list_url.get(3));
                }
                if (isEnZh == 1) {
                    Log.i("kxf", list_engurl.get(3));
                    showWeb(list_engurl.get(3));
                }
                break;
            case R.id.select_engwen:
                isEnZh = 1;
                handler.sendEmptyMessage(19);
                hideDialogForLoading();
                break;
            case R.id.select_zhongwen:
                isEnZh = 0;
                handler.sendEmptyMessage(21);
                hideDialogForLoading();
                break;
            case R.id.main_selct_liner:
                break;
        }
    }

    public void kxfSelctLua(View view) {
        creatDialog();
    }

    public void clearTabImag() {
        imageView1.setBackgroundResource(R.drawable.shouye);
        imageView2.setBackgroundResource(R.drawable.richeng);
        imageView3.setBackgroundResource(R.drawable.video);
        imageView4.setBackgroundResource(R.drawable.photos);
        imageView5.setBackgroundResource(R.drawable.user);

    }


    private String str_en = "http://www.bjiff.com/app_en/";
    @SuppressLint("SetJavaScriptEnabled")
    private void showWeb(String str) {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDisplayZoomControls(false);// 设定缩放控件隐藏加大缩小
        webSettings.setAllowFileAccess(true);
        webSettings.setBuiltInZoomControls(true);
        webView.requestFocus();
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.loadUrl(str);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("http:") || url.startsWith("https:")) {
                    return false;
                }
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                // OtherUtilsKxf.hideDialogForLoading();
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                String str = list_url.get(0);

                if (url.equals(str) || url.equals(str_en)
                        || url.equals(list_english.get(0))) {
                    Log.i("kxf", "onPageStarted  true  :" + url);
                    titl_line.setVisibility(View.VISIBLE);
                } else {
                    titl_line.setVisibility(View.GONE);

                }

                String strFor;
                for (int i = 0; i < 4; i++) {
                    strFor = list_url.get(i);
                    if (strFor.equals(url)) {
                        swipeRefreshLayout.setEnabled(true);
                        break;
                    } else {
                        swipeRefreshLayout.setEnabled(false);

                    }
                }

            }

            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                Toast.makeText(MainActivity.this, "暂时不支持下载", Toast.LENGTH_SHORT).show();
                webView.goBack();

            }

        });
        webView.setWebChromeClient(webChromeClient);

        webView.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        webView.setDownloadListener(new DownloadListener() {

            @Override
            public void onDownloadStart(String url, String userAgent,
                                        String contentDisposition, String mimetype,
                                        long contentLength) {

                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        }); // 在前面加入下载监听器

    }

    /**
     * 加载数据对话框
     */

    private Dialog mLoadingDialog;

    public void creatDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.selct_language,
                null);
        TextView dialog_zhong = (TextView) view
                .findViewById(R.id.select_zhongwen);
        TextView dialog_eng = (TextView) view.findViewById(R.id.select_engwen);
        dialog_eng.setOnClickListener(this);
        dialog_zhong.setOnClickListener(this);
        // mLoadingDialog.setCancelable(false);
        mLoadingDialog = new Dialog(this, R.style.loading_dialog_style);
        mLoadingDialog.setCancelable(true);
        mLoadingDialog.setCanceledOnTouchOutside(false);
        mLoadingDialog.setContentView(view, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        mLoadingDialog.show();

    }

    /**
     * 关闭加载对话框
     */
    public void hideDialogForLoading() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.cancel();
        }
    }
    @Override
    protected void onPause() {
        webView.reload();
        super.onPause();
    }
}
