/**
 *
 */
package com.hentica.app.module.common.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.hentica.app.lib.util.NetHelper;
import com.hentica.app.module.common.base.BaseFragment;
import com.hentica.app.widget.view.TitleView;
import com.fiveixlg.app.customer.R;

import java.lang.reflect.InvocationTargetException;

/**
 * 通用Web内容显示：<br>
 *
 * 参数: <br />
 * intent.putExtra(CommonWebFragment.INTENT_EXTRA_TITLE, "服务协议"); // 界面标题 <br />
 * intent.putExtra(CommonWebFragment.INTENT_EXTRA_URL, "http://www.baidu.com/");
 * //网址 <br />
 *
 * @author BlueCoder
 *
 */
public class CommonWebFragment extends BaseFragment {

	/** 标题 */
	public static final String INTENT_EXTRA_TITLE = "INTENT_EXTRA_TITLE";

	/** 具体url */
	public static final String INTENT_EXTRA_URL = "INTENT_EXTRA_URL";

	/** 图文详情 */
	protected WebView mRichDetailWebView;
	/** 加载web失败提示 */
	private View mWapLoadFailedView;
	private TextView m_tvWapLoadFailedTip;

	/** 内容的网址 */
	protected String m_strDetailUrl;

	/** 内容的网址 */
	private String m_strTitle = "";

	protected boolean m_IsLoadResource = false;

	@Override
	public int getLayoutId() {
		return R.layout.common_web_fragment;
	}

	@Override
	protected boolean hasTitleView() {
		return true;
	}

	@Override
	protected TitleView initTitleView() {
		// TODO
		return getViews(R.id.common_title);
	}

	protected void onFirstShowed() {
		// 显示“图文详情”
		// 从详情数据结构读URL
		if (m_strDetailUrl != null && !m_strDetailUrl.isEmpty()) {

			// new Thread(runnable).start();
//			NetHelper.checkWebStatusCode(m_strDetailUrl, new NetHelper.CheckUrlStatusListener() {
//
//				@Override
//				public void onSuccess(int status_code) {
//					if (status_code == 200) {
//						mRichDetailWebView.loadUrl(m_strDetailUrl); // "http://192.167.1.84"
//					} else {
//						showFailedView(status_code);
//					}
//				}
//
//				@Override
//				public void onFailure() {
//					showFailedView(-1);
//				}
//			});
            // 不判断网络，直接请求网址
            mRichDetailWebView.loadUrl(m_strDetailUrl);
		} else {
			showFailedView(501);
		}
	}

	protected void loadUrl(String url){
		m_strDetailUrl = url;
		onFirstShowed();
	}


	/** 显示网页加载错误提示 */
	protected void showFailedView(int status_code) {

		mRichDetailWebView.setVisibility(View.GONE);
		mWapLoadFailedView.setVisibility(View.VISIBLE);
		switch (status_code) {
		case 404:
			m_tvWapLoadFailedTip.setText("正在建设中...");
			break;
		case 501:
			m_tvWapLoadFailedTip.setText("请检查URL是否正确！");
			break;
		case 500:
		case 502:
			m_tvWapLoadFailedTip.setText("请与 Web服务器的管理员联系");
			break;

		default:
			m_tvWapLoadFailedTip.setText("请确认网址是否正确，或检查是否为联网状态！");
			break;
		}
	}

	@Override
	protected void initData() {

		m_IsLoadResource = false;

		Intent intent = this.getIntent();

		if (intent != null) {
			m_strTitle = intent.getStringExtra(INTENT_EXTRA_TITLE);
			m_strDetailUrl = intent.getStringExtra(INTENT_EXTRA_URL);
		}
	}

	@Override
	protected void initView() {

		AQuery query = new AQuery(getView());

		// Wap内容
		mRichDetailWebView = query.id(R.id.common_web_webview).getWebView();
		//
		mWapLoadFailedView = query.id(R.id.include_failed_view).getView();
		mWapLoadFailedView.setVisibility(View.GONE);
		//
		m_tvWapLoadFailedTip = query.id(R.id.common_emty_view_text).getTextView();
		m_tvWapLoadFailedTip.setText("正在建设中...");

		query.id(R.id.common_title_left_btn_back).visibility(View.VISIBLE);
		getTitleView().setTitleText(m_strTitle);

		// 配置webview
		initWebView();
	}

	/** 配置webview */
	private void initWebView(){
		mRichDetailWebView.clearCache(true);
		mRichDetailWebView.reload();

		//DOM
		mRichDetailWebView.getSettings().setDomStorageEnabled(true);
		//如果打不开网页设置一下两个
		mRichDetailWebView.getSettings().setAppCacheEnabled(true);
		mRichDetailWebView.getSettings().setAllowFileAccess(true);
		mRichDetailWebView.getSettings().setAllowContentAccess(true);
		//设置  Application Caches 缓存目录
		mRichDetailWebView.getSettings().setDatabaseEnabled(true);
		mRichDetailWebView.getSettings().setSavePassword(true);
		mRichDetailWebView.getSettings().setSaveFormData(true);
		//设置数据库缓存路径
		//设置缓冲大小，我设的是8M
		mRichDetailWebView.getSettings().setAppCacheMaxSize(1024*1024*8);
		mRichDetailWebView.getSettings().setLoadsImagesAutomatically(true);
		//允许JavaScript
		mRichDetailWebView.getSettings().setJavaScriptEnabled(true);
		try {
			mRichDetailWebView.getSettings().getClass().getMethod("setPageCacheCapacity", Integer.TYPE).invoke(mRichDetailWebView.getSettings(), 5);
			mRichDetailWebView.getSettings().setSupportMultipleWindows(true);
			mRichDetailWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		if (Build.VERSION.SDK_INT>= 16) {
			mRichDetailWebView.getSettings().setAllowFileAccessFromFileURLs(true);
			mRichDetailWebView.getSettings().setAllowUniversalAccessFromFileURLs(true);
		}

		//添加事件
		mRichDetailWebView.getSettings().setBuiltInZoomControls(true);
		//去掉缩放按钮，就是加减号
		mRichDetailWebView.getSettings().setDisplayZoomControls(false);
		//适应分辨率
		mRichDetailWebView.getSettings().setUseWideViewPort(true);
		//设置缓存模式
		mRichDetailWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
	}

	@Override
	protected void setEvent() {

		mRichDetailWebView.setWebChromeClient(new WebChromeClient() {

			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				super.onProgressChanged(view, newProgress);

				// 若加载完成
				if (newProgress == 100) {

					// 取消加载框
					dismissLoadingDialog();
				}
			}
		});
		mRichDetailWebView.setWebViewClient(new WebViewClient() {

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {

				super.onPageStarted(view, url, favicon);
//				m_IsLoadResource = false;
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);

				if (!m_IsLoadResource) {
					view.stopLoading();

					showFailedView(-1);
				}
			}

			@Override
			public void onLoadResource(WebView view, String url) {

				m_IsLoadResource = true;
				super.onLoadResource(view, url);
			}

			@Override
			public void onReceivedError(WebView view, int errorCode, String description,
					String failingUrl) {

				view.stopLoading();

				if (!m_IsLoadResource) {
					view.stopLoading();

					showFailedView(-1);
				}

				super.onReceivedError(view, errorCode, description, failingUrl);
			}
		});

		final AQuery query = new AQuery(getView());

		// ///////////////////////////////////////////////////////////////////////////////////
		// 返回按钮
		query.id(R.id.common_title_left_btn_back).clicked(new OnClickListener() {

			@Override
			public void onClick(View v) {

				finish();
			}
		});
	}

	@Override
	public void onStart() {
		super.onStart();
		loadUrl();
	}

	protected void loadUrl(){
		loadUrl(m_strDetailUrl);
	}
}
