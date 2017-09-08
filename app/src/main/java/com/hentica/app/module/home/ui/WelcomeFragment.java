package com.hentica.app.module.home.ui;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.androidquery.AQuery;
import com.fiveixlg.app.customer.R;
import com.hentica.app.framework.data.ApplicationData;
import com.hentica.app.framework.fragment.RequestPermissionResultListener;
import com.hentica.app.framework.fragment.UsualFragment;
import com.hentica.app.module.config.ConfigDataUtl;
import com.hentica.app.module.db.ConfigModel;
import com.hentica.app.module.listener.CallbackAdapter;
import com.hentica.app.util.PermissionHelper;
import com.hentica.app.util.PermissionUtils;
import com.hentica.app.util.StorageUtil;
import com.hentica.app.util.ViewUtil;
import com.hentica.app.util.rsa.RsaUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 欢迎界面
 *
 * @author mili
 * @createTime 2016-07-26 下午19:04:31
 */
public class WelcomeFragment extends UsualFragment {

    // 是否是第一次使用
    private boolean mIsFirstUse;

    // 牵引页集合
    private List<View> mWelcomImages;

    // 牵引页装载控件
    private ViewPager mWelcomViewPager;

    private AQuery mQuery;

    private PermissionHelper.PermissionGrant mPermissionGrant = new PermissionHelper.PermissionGrant() {
        @Override
        public void onPermissionGranted(int requestCode) {
            ConfigModel.getInstace().init(getContext());
        }
    };

    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.welcome_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // 初始化界面
        this.initData();
        this.initView();
        this.setEvent();
        // 一系列网络请求都可以在这里完成
        // 请求配置数据
        loadConfig();
    }

    /**
     * 初始化数据
     */
    private void initData() {
//        initDataBase();
        mQuery = new AQuery(getView());
        mIsFirstUse = isFirstUse();

        if(mWelcomImages == null){
            mWelcomImages = new ArrayList<View>();
        }
        //清空集合
        mWelcomImages.clear();
        //添加欢迎界面图片
        //第1次登录，或者未选择城市信息，显示引导界面
        if(mIsFirstUse || StorageUtil.getSelectedRegion() == null){
            mWelcomImages.add(getImageViewById(R.drawable.welcome_1));
            mWelcomImages.add(getImageViewById(R.drawable.welcome_2));
            mWelcomImages.add(getImageViewById(R.drawable.welcome_3));
        }else {
            mWelcomImages.add(getImageViewById(R.drawable.welcome));
            // 不是第一次使用，跳过牵引页，自动登录并跳转主界面
//        }
        }
    }

    private void initDataBase(){
        PermissionHelper.requestPermission(this, PermissionHelper.CODE_WRITE_EXTERNAL_STORAGE, mPermissionGrant);
        setRequestPermissionResultListener(new RequestPermissionResultListener() {
            @Override
            public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
                PermissionHelper.requestPermissionsResult(getUsualFragment(), requestCode, permissions, grantResults, mPermissionGrant);
            }
        });
    }

    /**
     * 初始化界面
     */
    private void initView() {
        View statusBar = mQuery.id(R.id.status_bar).getView();
        ViewUtil.setStatusBarVisible(getView(), statusBar, getContext(), true);

        mWelcomViewPager = (ViewPager)  mQuery.id(R.id.welcome_vp).getView();
        mWelcomViewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mWelcomImages.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(mWelcomImages.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(mWelcomImages.get(position),0);
                return mWelcomImages.get(position);
            }
        });

        PermissionUtils.requestPermission(this, PermissionUtils.CODE_WRITE_EXTERNAL_STORAGE, new PermissionUtils.PermissionGrant() {
            @Override
            public void onPermissionGranted(int requestCode) {

            }
        });
    }

    /**
     * 设置事件
     */
    private void setEvent() {
        //为最后一页添加点击事件
        View view = mWelcomImages.get(mWelcomImages.size() - 1 );
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toNext();
            }
        });

        // 最后一页滑动时需要跳转到主界面
        mWelcomViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // 滑动了最后一页
                if(position == (mWelcomImages.size() - 1)){
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //请求自动登录
                            toNext();
                        }
                    }, 1000);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 下一步操作
     * 1.保存启动信息
     * 2.判断是否选择城市
     * 3.是，跳转主界面，否，跳转选择城市界面
     */
    private void toNext(){
        saveAppLoginInfo();
        //跳转主界面
//        tryAutoLogin();
        tryToMain();
    }

    /** 保存app启动信息 */
    private void saveAppLoginInfo(){
        if(mIsFirstUse){
            StorageUtil.hasLogin();
        }
    }

    // 尝试跳转到主页
    private void tryToMain() {
        // 跳转到主界面
        startFrameActivity(HomeMainFragment.class);
        finish();
    }

    //根据图片id生成ImageView
    private View getImageViewById(int id){
        View view = View.inflate(getContext(), R.layout.welcome_item,null);
        AQuery query = new AQuery(view);
        ImageView iv = query.id(R.id.login_welcome_iv).getImageView();
        iv.setImageResource(id);
        return view;
    }

    /** 是否第一次安装 */
    private boolean isFirstUse(){
        return StorageUtil.isFirstLogin();
    }

    /** 自动登录 */
    private void tryAutoLogin(){
        // 设置为正在登录
        // TODO: 2017/3/9 自动登录
        ApplicationData.getInstance().setOnLogin(true);
//        new AutoLogin(getUsualFragment()).autoLogin(new CallbackAdapter<MResMemberUserLoginData>(){
//            @Override
//            public void callback(boolean isSuccess, MResMemberUserLoginData data) {
//                dismissLoadingDialog();
//                tryToMain();
//            }
//
//            @Override
//            public void onFailed(NetData result) {
//                super.onFailed(result);
//                dismissLoadingDialog();
//                tryToMain();
//                LoginModel.getInstance().logout(true);
//            }
//        });
    }

    /** 请求配置数据 */
    private void loadConfig(){
        ConfigDataUtl configDataUtl = ConfigDataUtl.getInstance();
        configDataUtl.getRsaPublicKey(new CallbackAdapter<String>(){
            @Override
            public void callback(boolean isSuccess, String data) {
                super.callback(isSuccess, data);
                if(isSuccess){
                    RsaUtils.getPublicKey(data);
                }
            }
        });
        configDataUtl.getServicePhone(new CallbackAdapter<String>(){
            @Override
            public void callback(boolean isSuccess, String data) {
                super.callback(isSuccess, data);
            }
        });
    }
}
