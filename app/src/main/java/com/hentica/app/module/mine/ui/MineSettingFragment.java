package com.hentica.app.module.mine.ui;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.fiveixlg.app.customer.R;
import com.hentica.app.framework.data.ApplicationData;
import com.hentica.app.lib.util.PhoneInfo;
import com.hentica.app.module.common.base.BaseFragment;
import com.hentica.app.module.entity.ResAppUpdateData;
import com.hentica.app.module.mine.presenter.MineSettingPresenter;
import com.hentica.app.module.mine.presenter.MineSettingPresenterImpl;
import com.hentica.app.module.mine.view.MineSettingView;
import com.hentica.app.module.update.AppUpdateModel;
import com.hentica.app.module.update.UpdateAppView;
import com.hentica.app.util.CheckUpdateUtil;
import com.hentica.app.util.StorageUtil;
import com.hentica.app.widget.dialog.SelfAlertDialog;
import com.hentica.app.widget.view.TitleView;

/**
 * 设置界面
 *
 * @author
 * @createTime 2017-03-23 下午15:13:27
 */
public class MineSettingFragment extends BaseFragment implements MineSettingView, UpdateAppView<ResAppUpdateData> {

    private MineSettingPresenter mSettingPresenter;

    @Override
    public int getLayoutId() {
        return R.layout.mine_setting_fragment;
    }

    @Override
    protected boolean hasTitleView() {
        return true;
    }

    @Override
    protected TitleView initTitleView() {
        return getViews(R.id.common_title);
    }

    @Override
    protected void initData() {
        mSettingPresenter = new MineSettingPresenterImpl(this);
        mUpdateModel = AppUpdateModel.getInstance();
        mUpdateModel.setCheckUpdateView(this, true);
    }

    @Override
    protected void initView() {
        refreshUI();
    }

    private AppUpdateModel mUpdateModel;

    private void refreshUI() {
        setViewText(mSettingPresenter.getCacheFilesSize(), R.id.setting_tv_cache_size);
        //显示版本信息
        setViewText(PhoneInfo.getAndroidVersionname(), R.id.setting_tv_version_name);
    }

    @Override
    protected void setEvent() {
        //清除缓存按钮事件
        setViewClickEvent(R.id.setting_layout_clear, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearCache();
            }
        });
        //更新按钮
        setViewClickEvent(R.id.setting_layout_update, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUpdateModel.check();
            }
        });
        if (ApplicationData.getInstance().isLogin()) {
            getViews(R.id.setting_layout_jpush).setVisibility(View.VISIBLE);
        } else {
            getViews(R.id.setting_layout_jpush).setVisibility(View.GONE);
        }
        //设置极光推送状态
        if (null==StorageUtil.getLastLoginInfo()||StorageUtil.getLastLoginInfo().isPushMsg()) {
            ((CheckBox) getViews(R.id.setting_switch)).setChecked(true);
        } else {
            ((CheckBox) getViews(R.id.setting_switch)).setChecked(false);
        }
        ((CheckBox) getViews(R.id.setting_switch)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(final CompoundButton compoundButton, boolean b) {
                setPushToggle(compoundButton, b);
            }
        });
    }

    /**
     * 设置推送开关
     *
     * @param compoundButton
     * @param b
     */
    private void setPushToggle(final CompoundButton compoundButton, boolean b) {
        if (!b) {
            SelfAlertDialog dialog = new SelfAlertDialog();
            dialog.setText("确定要关闭推送吗？");
            dialog.setSureText("确认");
            dialog.setCancelText("取消");
            dialog.setSureClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSettingPresenter.setJpushToggle(false);
                    compoundButton.setChecked(false);
                }
            });
            dialog.setCancelClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    compoundButton.setChecked(true);
                }
            });
            dialog.setCancelable(false);
            dialog.show(getChildFragmentManager(), "dialog");
        } else {
            mSettingPresenter.setJpushToggle(true);
        }
    }

    /**
     * 清空缓存
     */
    private void clearCache() {
        SelfAlertDialog dialog = new SelfAlertDialog();
        dialog.setText("确定要清除缓存？");
        dialog.setSureText("确认");
        dialog.setCancelText("取消");
        dialog.setSureClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSettingPresenter.clearCacheFiles();
            }
        });
        dialog.show(getChildFragmentManager(), "dialog");
    }

    @Override
    public void cacheClearSuccess() {
        refreshUI();
    }

    @Override
    public void setUpdateCheckData(ResAppUpdateData data) {
        if (data == null) {
            showToast("检测升级信息异常！");
            return;
        }
        CheckUpdateUtil.getInstance(getContext()).checkUpdateApp(this, data, true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUpdateModel.destory();
        mUpdateModel = null;
    }
}
