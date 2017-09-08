package com.hentica.app.module.home.ui;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioGroup;

import com.fiveixlg.app.customer.R;
import com.hentica.app.framework.fragment.UsualFragment;
import com.hentica.app.module.bonus.BonusMainFragment;
import com.hentica.app.module.common.base.BaseFragment;
import com.hentica.app.module.entity.ResAppUpdateData;
import com.hentica.app.module.index.IndexMainFragment;
import com.hentica.app.module.mine.ui.MineMainFragment;
import com.hentica.app.module.update.AppUpdateModel;
import com.hentica.app.module.update.UpdateAppView;
import com.hentica.app.util.CheckUpdateUtil;
import com.hentica.app.util.FragmentFlipHelper;
import com.hentica.app.util.FragmentJumpUtil;
import com.hentica.app.util.LogUtils;
import com.hentica.app.util.PermissionUtils;
import com.hentica.app.util.StatusBarUtil;
import com.hentica.app.util.event.DataEvent;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;

/**
 * Created by kezhong.
 * E-Mail:396926020@qq.com
 * on 2016/10/21 11:46
 */

public class HomeMainFragment extends BaseFragment implements UpdateAppView<ResAppUpdateData> {

    @BindView(R.id.main_viewPager)
    ViewPager mPager;

    @BindView(R.id.main_bottom_radio_group)
    RadioGroup mRgTabs;

    FragmentFlipHelper mFragmentFlipHelper;

    private boolean finish = false;

    private AppUpdateModel appUpdateModel;

    /**
     * 标志是否从分红跳转登录界面
     */
    private boolean mIsToLoginFromBonus;

    private CheckUpdateUtil mUpdateUtil;

    private ResAppUpdateData mUpdateData;

    private boolean isUpdateForcue = false;
    private boolean isFirstCheck = true;

    @Override
    public int getLayoutId() {
        return R.layout.home_main_fragment;
    }

    @Override
    protected void initData() {
        appUpdateModel = AppUpdateModel.getInstance();
        appUpdateModel.setCheckUpdateView(this, false);
        mUpdateUtil = CheckUpdateUtil.getInstance(getContext());
    }

    @Override
    protected void initView() {
        mFragmentFlipHelper = new FragmentFlipHelper(getUsualFragment(), R.id.main_viewPager, R.id.main_bottom_radio_group, 0, 0);
        mFragmentFlipHelper.addFragmentInfo(new FragmentFlipHelper.FragmentInfo(new IndexMainFragment(), R.id.main_bottom_tab_index, "index"));
        mFragmentFlipHelper.addFragmentInfo(new FragmentFlipHelper.FragmentInfo(new BonusMainFragment(), R.id.main_bottom_tab_bonus, "bonus"));
        mFragmentFlipHelper.addFragmentInfo(new FragmentFlipHelper.FragmentInfo(new MineMainFragment(), R.id.main_bottom_tab_mine, "mine"));
        mFragmentFlipHelper.createFragments();

        StatusBarUtil.setTranslucentStatus(getActivity(), true);
    }

    @Override
    protected void setEvent() {
        mFragmentFlipHelper.setOnBeforeChangeListener(new FragmentFlipHelper.OnBeforeChangeListener() {
            @Override
            public boolean beforeChanged(int checkedId) {
                if (checkedId == R.id.main_bottom_tab_bonus) {
                    int index = mFragmentFlipHelper.getCurrIndex();
                    // 尝试跳转登录界面
                    if (FragmentJumpUtil.tryToLogin(getUsualFragment())) {
                        mFragmentFlipHelper.setCurrIndex(index);
                        mIsToLoginFromBonus = true;
                        return true;
                    }
                    return false;
                }
                return false;
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        if (appUpdateModel != null) appUpdateModel.check();
    }

    private int getFragmentIndex(Class<? extends UsualFragment> clzz) {
        int result = -1;
        List<FragmentFlipHelper.FragmentInfo> fragmentInfos = mFragmentFlipHelper.getAllChildFragmentInfos();
        for (int i = 0; i < fragmentInfos.size(); i++) {
            FragmentFlipHelper.FragmentInfo info = fragmentInfos.get(i);
            if (info.mFragment.getClass() == clzz) {
                result = i;
                break;
            }
        }
        return result;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mUpdateUtil != null) {
            if (mUpdateUtil.onKeyDown(keyCode, event)) {
                return true;
            }
        }
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            return doubleClickToFinish();
        }
        return super.onKeyDown(keyCode, event);
    }

    private boolean doubleClickToFinish() {
        if (finish) {
            finish();
        } else {
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    finish = false;
                }
            }, 10 * 1000);
            finish = true;
            showToast("再次点击退出应用！");
        }
        return true;
    }

    @Override
    public void setUpdateCheckData(ResAppUpdateData data) {
        mUpdateData = data;
        if (mUpdateData == null) {
            isFirstCheck = false;
            return;
        }
        if (mUpdateUtil != null) {
            if (isFirstCheck || mUpdateData.isIsForced()) {
                checkFilePermissioinUpdate();
            }
        }
        isFirstCheck = false;
    }

    /**
     * 检查文件限权
     */
    private void checkFilePermissioinUpdate() {
        PermissionUtils.mPermissionSetCancelListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getUserVisibleHint()) {
                    finish();
                }
            }
        };
        PermissionUtils.requestPermission(this, PermissionUtils.CODE_WRITE_EXTERNAL_STORAGE, getPermissionGrant());
    }

    private PermissionUtils.PermissionGrant getPermissionGrant() {
        return new PermissionUtils.PermissionGrant() {
            @Override
            public void onPermissionGranted(int requestCode) {
                switch (requestCode) {
                    case PermissionUtils.CODE_WRITE_EXTERNAL_STORAGE:
                        mUpdateUtil.checkUpdateApp(getUsualFragment(), mUpdateData, false);
                        break;
                }
            }
        };
    }

    /**
     * 登录事件
     */
    @Subscribe
    public void onEvent(DataEvent.OnLoginEvent event) {
        if (mIsToLoginFromBonus) {
            // 跳转到分红界面
            mFragmentFlipHelper.setCurrIndex(1);
            mIsToLoginFromBonus = false;
        }
    }

    /**
     * 支付成功
     */
    @Subscribe
    public void onEvent(DataEvent.OnPaySuccess event) {
        // 切换到我的
        mFragmentFlipHelper.setCurrIndex(2);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        LogUtils.i("BaseFragment", "onRequestPermissionsResult : requestCode=" + requestCode);
        for (FragmentFlipHelper.FragmentInfo fragmentInfo : mFragmentFlipHelper.getAllChildFragmentInfos()) {
            if (fragmentInfo.mFragment != null) {
                fragmentInfo.mFragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
        PermissionUtils.requestPermissionsResult(getUsualFragment(), requestCode, permissions, grantResults, getPermissionGrant());
    }

}
