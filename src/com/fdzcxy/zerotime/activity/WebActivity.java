package com.fdzcxy.zerotime.activity;

import com.fdzcxy.zerotime.R;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebActivity extends BaseActivity {
	private WebView webview;
	/**
	 * 网页地址
	 */
	private static String hrefs;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_read_web);
		initView();
		initData();
	}

	/**
	 * 初始化视图
	 */
	private void initView() {
		webview = (WebView) findViewById(R.id.webview);
		// 设置WebView属性，能够执行Javascript脚本
		webview.getSettings().setJavaScriptEnabled(true);
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		// 加载需要显示的网页
		hrefs = this.getIntent().getStringExtra("weburl");
		webview.loadUrl(hrefs);
		// 设置Web视图
		webview.setWebViewClient(new MyWebViewClient());
		// 设置可以支持缩放
		webview.getSettings().setSupportZoom(true);
		// 设置出现缩放工具
		webview.getSettings().setBuiltInZoomControls(true);
	}

	/**
	 * 设置回退 覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			if(!webview.canGoBack()){
				webview.destroy();
				WebActivity.this.finish();
			}
			else
				webview.goBack(); // goBack()表示返回WebView的上一页面
			return true;

		}
		return false;
	}

	/**
	 * Web视图
	 * 
	 */
	private class MyWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}
	}
}
