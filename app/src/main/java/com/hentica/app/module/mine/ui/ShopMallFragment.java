package com.hentica.app.module.mine.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.fiveixlg.app.customer.R;
import com.hentica.app.framework.data.ApplicationData;
import com.hentica.app.module.common.base.BaseFragment;
import com.hentica.app.module.login.business.LoginModel;
import com.hentica.app.module.login.data.UserLoginData;
import com.hentica.app.util.event.DataEvent;

import org.greenrobot.eventbus.Subscribe;

import java.lang.reflect.InvocationTargetException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 商城
 */

public class ShopMallFragment extends BaseFragment {
    @BindView(R.id.webView)
    WebView webView;
    Unbinder unbinder;
    @BindView(R.id.layout_top)
    LinearLayout layoutTop;

    /**
     * 登录事件
     */
    @Subscribe
    public void onEvent(DataEvent.OnLoginEvent event) {
        webView.loadUrl(handleUrl());
    }

    @Subscribe
    public void loadUrl(DataEvent.OnLoadWebView event) {
        if (webView != null && event != null)
            webView.loadUrl(handleUrl());
    }

    private String handleUrl() {
        UserLoginData userLoginData = LoginModel.getInstance().getUserLogin();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(ApplicationData.getInstance().getMshopMallUrl()).append("?userid=")
                .append(userLoginData.getId()).append("&token=").append(ApplicationData.getInstance().getToken())
                .append("&mobile=").append(userLoginData.getCellPhoneNum());
        String m_strDetailUrl = stringBuffer.toString();
        Log.e("m_strDetailUrl", m_strDetailUrl);
        return m_strDetailUrl;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_shop_mall;
    }

    @Override
    protected void initData() {
        layoutTop.setPadding(0, getStatusBarHeight(getActivity()), 0, 0);
        initWebView();
        if (ApplicationData.getInstance().isLogin())
            webView.loadUrl(handleUrl());
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void setEvent() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * 配置webview
     */
    private void initWebView() {
        webView.clearCache(true);
        webView.reload();

        //DOM
        webView.getSettings().setDomStorageEnabled(true);
        //如果打不开网页设置一下两个
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAllowContentAccess(true);
        //设置  Application Caches 缓存目录
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setSavePassword(true);
        webView.getSettings().setSaveFormData(true);
        //设置数据库缓存路径
        //设置缓冲大小，我设的是8M
        webView.getSettings().setAppCacheMaxSize(1024 * 1024 * 8);
        webView.getSettings().setLoadsImagesAutomatically(true);
        //允许JavaScript
        webView.getSettings().setJavaScriptEnabled(true);
        try {
            webView.getSettings().getClass().getMethod("setPageCacheCapacity", Integer.TYPE).invoke(webView.getSettings(), 5);
            webView.getSettings().setSupportMultipleWindows(true);
            webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT >= 16) {
            webView.getSettings().setAllowFileAccessFromFileURLs(true);
            webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        }

        //添加事件
        webView.getSettings().setBuiltInZoomControls(true);
        //去掉缩放按钮，就是加减号
        webView.getSettings().setDisplayZoomControls(false);
        //适应分辨率
        webView.getSettings().setUseWideViewPort(true);
        //设置缓存模式
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(final WebView view, String url) {
                // 获取上下文, H5PayDemoActivity为当前页面
                final Activity context = getActivity();

                // ------  对alipays:相关的scheme处理 -------
                if (url.startsWith("alipays:") || url.startsWith("alipay")) {
                    try {
                        context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
                    } catch (Exception e) {
                        new AlertDialog.Builder(context)
                                .setMessage("未检测到支付宝客户端，请安装后重试。")
                                .setPositiveButton("立即安装", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Uri alipayUrl = Uri.parse("https://d.alipay.com");
                                        context.startActivity(new Intent("android.intent.action.VIEW", alipayUrl));
                                    }
                                }).setNegativeButton("取消", null).show();
                    }
                    return true;
                }
                // ------- 处理结束 -------

                if (!(url.startsWith("http") || url.startsWith("https"))) {
                    return true;
                }
                view.loadUrl(url);
                return true;
            }
        });
    }

    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
