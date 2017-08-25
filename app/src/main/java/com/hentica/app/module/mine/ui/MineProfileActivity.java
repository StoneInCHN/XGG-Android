package com.hentica.app.module.mine.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.hentica.app.framework.activity.BaseCompatActivity;
import com.hentica.app.framework.data.ApplicationData;
import com.hentica.app.framework.fragment.UsualFragment;
import com.hentica.app.module.db.ConfigModel;
import com.hentica.app.module.entity.mine.ResUserProfile;
import com.hentica.app.module.login.business.LoginModel;
import com.hentica.app.module.mine.presenter.MineProfilePresenterImpl;
import com.hentica.app.module.mine.presenter.MineWechatBindPresenter;
import com.hentica.app.module.mine.presenter.MineWechatBindPresenterImpl;
import com.hentica.app.module.mine.presenter.ModifyProfilePresenter;
import com.hentica.app.module.mine.presenter.ModifyProfilePresenterImpl;
import com.hentica.app.module.mine.view.MineWecahtBindView;
import com.hentica.app.module.mine.view.ModifyProfileView;
import com.hentica.app.util.FragmentJumpUtil;
import com.hentica.app.util.GlideUtil;
import com.hentica.app.util.UmengLoginUtil;
import com.hentica.app.util.ViewUtil;
import com.hentica.app.util.event.DataEvent;
import com.hentica.app.util.region.Region;
import com.hentica.app.widget.dialog.PayPasswordCheckView;
import com.hentica.app.widget.dialog.PayPasswordInputDialog;
import com.hentica.app.widget.dialog.PayPasswordInputPresenterImpl;
import com.hentica.app.widget.dialog.SelfAlertDialogHelper;
import com.hentica.app.widget.photo.MakePhotoDialog2;
import com.hentica.app.widget.photo.MakePhotoListener;
import com.hentica.app.widget.view.TitleView;
import com.hentica.app.widget.view.lineview.LineViewHelper;
import com.hentica.app.widget.view.lineview.LineViewText;
import com.hentica.app.widget.wheel.TakeAddrWheelDialog;
import com.hentica.appbase.famework.util.ListUtils;
import com.fiveixlg.app.customer.R;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.OnClick;

import static com.fiveixlg.app.customer.R.id.mine_profile_lnv_location;

/**
 * Created by Snow on 2017/5/13.
 */

public class MineProfileActivity extends BaseCompatActivity implements ModifyProfileView, MineWecahtBindView, PayPasswordCheckView {

    private UsualFragment.OnActivityResultListener mActivityResultListener;

    private ModifyProfilePresenter mModifyProfilePresenter;
    private MineProfilePresenterImpl mMineProfilePresenter;
    private MineWechatBindPresenter mBindPresenter;

    //所在地
    private Region mRegionPro;//省
    private Region mRegionCity;//市
    private Region mRegionDis;//区

    private ResUserProfile mProfileData;
    private String mStrWechatNickName;

    @Override
    protected int getLayoutId() {
        return R.layout.mine_profile_fragment;
    }

    @Override
    protected void initData(){
        super.initData();
        mModifyProfilePresenter = new ModifyProfilePresenterImpl(this);
        mMineProfilePresenter = new MineProfilePresenterImpl(this);
        mBindPresenter = new MineWechatBindPresenterImpl(this);
        //获取用户资料
        mMineProfilePresenter.getUserProfile();
    }

    @Override
    protected void initView(){
        super.initView();
        AQuery query = new AQuery(this);
        TitleView title = (TitleView) query.id(R.id.common_title).getView();
        title.setOnLeftImageBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ViewUtil.bindEditDelete(this, R.id.mine_profile_edt_name,0,R.drawable.rebate_login_delete);
    }

    @Override
    protected void setEvent() {
        super.setEvent();
        toSelectLocation();
        findViewById(R.id.mine_profile_btn_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mModifyProfilePresenter.toLogout();
            }
        });
        //点击头像栏
        findViewById(R.id.mine_layout_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MakePhotoDialog2 dialog = new MakePhotoDialog2();
                dialog.setCropConfig(1, 1, 300, 300);
                dialog.setOnMakePhotoListener(new MakePhotoListener() {
                    @Override
                    public void onComplete(List<String> imgFilePaths) {
                        if (!ListUtils.isEmpty(imgFilePaths)) {
                            String file = imgFilePaths.get(0);
                            ImageView img = (ImageView) findViewById(R.id.profile_img_icon);
                            GlideUtil.loadHeadIcon(getBaseContext(), file, img);//显示新头像
                            mModifyProfilePresenter.updateUserPhoto(file);
                        }
                    }
                });

                dialog.show(getSupportFragmentManager(), dialog.getClass().getSimpleName());
            }
        });
        final EditText mEdtName = (EditText) findViewById(R.id.mine_profile_edt_name);
        mEdtName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEdtName.setFocusable(true);
                mEdtName.setFocusableInTouchMode(true);
                mEdtName.requestFocus();
            }
        });
        mEdtName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!LoginModel.getInstance().isLogin()){
                    return;
                }
                if (!hasFocus) {
                    toUpdateNickName();
                    mEdtName.setFocusable(false);
                    mEdtName.setFocusableInTouchMode(false);
                }else{
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(mEdtName, 0);
                }

                // 获得焦点
                if(hasFocus && !TextUtils.isEmpty(mEdtName.getText()))
                    mEdtName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.rebate_login_delete, 0);
                else {
                    // 失去焦点
                    mEdtName.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                }
            }
        });
        //登录密码
        setViewClickEvent(R.id.mine_profile_lnv_login_pwd, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toUpdateLoginPwd();
            }
        });
        //支付密码
        setViewClickEvent(R.id.mine_profile_lnv_pay_pwd, new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                toUpdatePayPwd(new UsualFragment.OnActivityResultListener() {
                    @Override
                    public void onActivityResult(int requestCode, int resultCode, Intent data) {
                        if (resultCode == Activity.RESULT_OK) {
                            mMineProfilePresenter.getUserProfile();
                        }
                    }
                });
            }
        });
        //跳转微信绑定
        setViewClickEvent(R.id.mine_profile_lnv_bind_wechat, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPayPasswordIsSetted()) {
                    showPayPassworInputDialog();
                } else {
                    showToast("支付密码暂未设置！");
                    toUpdatePayPwdBindWechat();
                }
            }
        });
        //更换手机号
        setViewClickEvent(R.id.mine_profile_lnv_change_phone, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentJumpUtil.toFragment(getActivity(), MineChangePhoneFragment.class);
            }
        });
    }

    private void toUpdateNickName() {
        if (TextUtils.isEmpty(getNickName())) {
            //显示原昵称
            setViewText(mProfileData.getNickName(), R.id.mine_profile_edt_name);//显示用户昵称
        } else if (getNickName().equals(mProfileData.getNickName())) {
            return;
        } else {
            //保存新昵称
            mModifyProfilePresenter.updateUserName();
        }
    }

    protected void setViewClickEvent(@IdRes int id, View.OnClickListener l){
        View view = findViewById(id);
        if(view != null){
            view.setOnClickListener(l);
        }
    }

    /**
     * 选择城市
     */
    private void toSelectLocation() {
        //选择城市
        final LineViewText lnvSelectCity = (LineViewText) findViewById(R.id.mine_profile_lnv_location);
        lnvSelectCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TakeAddrWheelDialog dialog = new TakeAddrWheelDialog();
                dialog.setDefaultDatas(getRegionName(mRegionPro), getRegionName(mRegionCity), getRegionName(mRegionDis));
                dialog.setOnSelectedCompleteListener(new TakeAddrWheelDialog.OnSelectedComplete() {
                    @Override
                    public void selectedDatas(Region value1, Region value2, Region value3) {
                        mRegionPro = value1;
                        mRegionCity = value2;
                        mRegionDis = value3;
                        StringBuilder sBuilder = new StringBuilder();
                        if (mRegionDis != null) {
                            sBuilder.append(mRegionDis.getFull_name());
                        } else if (mRegionCity != null) {
                            sBuilder.append(mRegionCity.getFull_name());
                        } else if (mRegionPro != null) {
                            sBuilder.append(mRegionPro.getFull_name());
                        }
                        lnvSelectCity.setText(sBuilder.toString());
                        mModifyProfilePresenter.updateLocation();
                    }
                });
                dialog.show(getSupportFragmentManager(), dialog.getClass().getSimpleName());
            }
        });
    }

    /**
     * 获取地区名称
     */
    private String getRegionName(Region region) {
        if (region == null) {
            return "";
        }
        return region.getName();
    }

    /**
     * 设置登录密码
     */
    private void toUpdateLoginPwd() {
        FragmentJumpUtil.toUpdateLoginPwdForResult(this);
        setActivityResultListener(new UsualFragment.OnActivityResultListener() {
            @Override
            public void onActivityResult(int requestCode, int resultCode, Intent data) {
                if (resultCode == Activity.RESULT_OK) {
                    mMineProfilePresenter.getUserProfile();
                }
            }
        });
    }

    /**
     * 设置支付密码
     */
    private void toUpdatePayPwd(UsualFragment.OnActivityResultListener l) {
        FragmentJumpUtil.toUpdatePayPwdForResult(this);
        setActivityResultListener(l);
    }

    private void toUpdatePayPwdBindWechat(){
        toUpdatePayPwd(new UsualFragment.OnActivityResultListener() {
            @Override
            public void onActivityResult(int requestCode, int resultCode, Intent data) {
                if(resultCode == Activity.RESULT_OK){
                    toWechatBind();
                }
            }
        });
    }

    /** 检查支付密码是否设置 */
    private boolean checkPayPasswordIsSetted(){
        return mProfileData.isIsSetPayPwd();
    }

    /**
     * 显示输入密码框
     */
    private void showPayPassworInputDialog(){
        // 2017/4/10 检查支付密码
        final PayPasswordInputDialog dialog = new PayPasswordInputDialog();
        dialog.setPayPwdInputPresenter(new PayPasswordInputPresenterImpl(getUsualFragment(), this) {
            @Override
            public void setInputPassword(String payPwd) {
                super.setInputPassword(payPwd);
                dialog.dismiss();
            }
        });
        dialog.show(getSupportFragmentManager(), dialog.getClass().getSimpleName());
    }

    /**
     * 绑定微信
     */
    private void toWechatBind() {
        if (mProfileData.isIsBindWeChat() == true) {
            FragmentJumpUtil.toWechatBindFragment(this, mProfileData.getWechatNickName());
        } else {
            bindWechat();
        }
    }

    private void bindWechat() {
        getWeichatOpenId(new UmengLoginUtil.OnLoginCompleteListener2() {
            @Override
            public void onLoginSuccess(String uuid, String openId, String nickName) {
                mStrWechatNickName = nickName;
                mBindPresenter.toBindWechat(openId, nickName);
            }

            @Override
            public void onLoginSuccess(String account, String nickName) {
            }

            @Override
            public void onLoginFailed() {
            }
        });
    }

    /**
     * 获取微信授权信息
     *
     * @param l
     */
    private void getWeichatOpenId(UmengLoginUtil.OnLoginCompleteListener l) {
        UmengLoginUtil umengLoginUtil = new UmengLoginUtil(this);
        umengLoginUtil.loginWeixin(l);
    }

    @Override
    public void logoutSuccess() {
        LoginModel.getInstance().logout(true);
        finish();
    }

    @Override
    public void setCheckResult(boolean isCheckOut) {
        if(isCheckOut){
            toWechatBind();
        }else{
            SelfAlertDialogHelper.showDialog(
                    getSupportFragmentManager(), "支付密码错误，请重试。", "忘记密码", "重新输入",
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showToast("忘记密码");
                            toUpdatePayPwdBindWechat();
                        }
                    },
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showPayPassworInputDialog();
                        }
                    }
            );
        }
    }

    @Override
    public void bindSuccess() {
        FragmentJumpUtil.toWechatBindFragment(this, mStrWechatNickName);
        mMineProfilePresenter.getUserProfile();
    }

    @Override
    public void unBindSuccess() {

    }

    @Override
    public void onUpdateFailure() {
        refreshUI(mProfileData);
    }

    @Override
    public String getNickName() {
        return ((TextView) findViewById(R.id.mine_profile_edt_name)).getText().toString();
    }

    @Override
    public String getAreaId() {
        if (mRegionDis != null) {
            return mRegionDis.getId();
        }
        if (mRegionCity != null) {
            return mRegionCity.getId();
        }
        if (mRegionPro != null) {
            return mRegionPro.getId();
        }
        return null;
    }

    @Override
    public void loadProfileSuccess(ResUserProfile userData) {
        mProfileData = userData;
//        mMineProfilePresenter.getUserProfile();
        refreshUI(userData);
    }

    @Subscribe
    public void onEvent(DataEvent.OnToUpdataUserProfileEvent event) {
        mMineProfilePresenter.getUserProfile();
    }

    /**
     * 刷新界面
     */
    private void refreshUI(ResUserProfile profile) {
        if (profile == null) {
            return;
        }
        setViewVisiable(true, R.id.mine_profile_btn_logout);//显示退出登录按钮
        //显示用户资料
        //显示用户头像
        AQuery query = new AQuery(this);
        ImageView img = (ImageView) findViewById(R.id.profile_img_icon);
        GlideUtil.loadHeadIconDefault(this, ApplicationData.getInstance().getImageUrl(profile.getUserPhoto()), img, R.drawable.rebate_mine_head_bg_acquiescent);
        LineViewHelper.setValue(query, R.id.mine_profile_lnv_account, profile.getCellPhoneNum());//显示用户账号
        setViewText(profile.getNickName(), R.id.mine_profile_edt_name);//显示用户昵称
        LineViewHelper.setValue(query, R.id.mine_profile_lnv_login_pwd, profile.isIsSetLoginPwd() ? "已设置" : "暂未设置");//显示用户是否已设置登录密码
        LineViewHelper.setValue(query, R.id.mine_profile_lnv_pay_pwd, profile.isIsSetPayPwd() ? "已设置" : "暂未设置");//显示用户是否已设置支付密码
        LineViewHelper.setValue(query, R.id.mine_profile_lnv_bind_wechat, profile.isIsBindWeChat() ? "已设置" : "暂未设置");//显示用户是否已设置支付密码
        if (profile.getArea() != null) {
            //有所在地
            Region region = ConfigModel.getInstace().getRegionById(profile.getArea().getId() + "");
            if (region != null) {
                LineViewHelper.setValue(query, mine_profile_lnv_location, region.getFull_name());
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
