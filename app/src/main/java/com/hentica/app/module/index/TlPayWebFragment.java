package com.hentica.app.module.index;

import android.content.Intent;
import android.graphics.Bitmap;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;

import com.fiveixlg.app.customer.R;
import com.hentica.app.framework.data.Constants;
import com.hentica.app.module.common.ui.CommonWebFragment;
import com.hentica.app.util.IntentUtil;
import com.hentica.app.util.WebHost;
import com.hentica.app.widget.view.TitleView;

import butterknife.BindView;

/**
 * Created by YangChen on 2017/5/31 10:08.
 * E-mail:656762935@qq.com
 */

public class TlPayWebFragment extends CommonWebFragment {

    @BindView(R.id.common_title)
    TitleView mTitleView;
    @BindView(R.id.common_web_webview_back_btn)
    ImageButton mBackBtn;
    private WebHost webHost;
    /**
     * 支付成功跳转url
     */
    public static final String INTENT_PICKUP_URL = "INTENT_PICKUP_URL";

    private String mPickUpUrl;

    @Override
    protected void handleIntentData(Intent intent) {
        IntentUtil intentUtil = new IntentUtil(intent);
        mPickUpUrl = intentUtil.getString(INTENT_PICKUP_URL);
    }

    @Override
    protected void initView() {
        super.initView();
        // 隐藏返回按钮
//        mTitleView.getLeftImageBtn().setVisibility(View.GONE);
        //        if (mPickUpUrl == null || TextUtils.isEmpty(mPickUpUrl)) {
        webHost = new WebHost(getActivity());
        mRichDetailWebView.addJavascriptInterface(webHost, "Android");
        webHost.setPaySuccessCallBack(new WebHost.PaySuccessCallBack() {
            @Override
            public void onClick() {
                // 支付成功
                getActivity().setResult(Constants.ACTIVITY_RESULT_CODE_TL_PAY);
                finish();
            }
        });
//        }
    }

    /**
     * 屏蔽系统返回功能
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void setEvent() {
        super.setEvent();
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
                if (mRichDetailWebView.canGoBack()) {
                    mBackBtn.setVisibility(View.GONE);
                } else {
                    mBackBtn.setVisibility(View.VISIBLE);
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

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.contains("baidu.com")) {
                    // 支付成功
                    getActivity().setResult(Constants.ACTIVITY_RESULT_CODE_TL_PAY);
                    finish();
                    return false;
                } else {
                    return super.shouldOverrideUrlLoading(view, url);
                }
            }
        });
    }
}
