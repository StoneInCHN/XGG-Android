package com.hentica.app.module.mine.ui;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hentica.app.framework.data.ApplicationData;
import com.hentica.app.module.common.base.BaseFragment;
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

import static com.fiveixlg.app.customer.R.id.mine_profile_lnv_location;


/**
 * 修改资料界面
 *
 * @author
 * @createTime 2017-03-23 下午15:13:27
 */
public class MineProfileFragment extends BaseFragment implements ModifyProfileView, MineWecahtBindView, PayPasswordCheckView {

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
    public int getLayoutId() {
        return R.layout.mine_profile_fragment;
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
        mModifyProfilePresenter = new ModifyProfilePresenterImpl(this);
        mMineProfilePresenter = new MineProfilePresenterImpl(this);
        mBindPresenter = new MineWechatBindPresenterImpl(this);
        //获取用户资料
        mMineProfilePresenter.getUserProfile();
    }

    @Override
    protected void initView() {
        ViewUtil.bindEditDelete(getView(),R.id.mine_profile_edt_name,0,R.drawable.rebate_login_delete);
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
        ImageView img = getViews(R.id.profile_img_icon);
        GlideUtil.loadHeadIconDefault(getContext(), ApplicationData.getInstance().getImageUrl(profile.getUserPhoto()), img, R.drawable.rebate_mine_moren);
        LineViewHelper.setValue(getView(), R.id.mine_profile_lnv_account, profile.getCellPhoneNum());//显示用户账号
        setViewText(profile.getNickName(), R.id.mine_profile_edt_name);//显示用户昵称
        LineViewHelper.setValue(getView(), R.id.mine_profile_lnv_login_pwd, profile.isIsSetLoginPwd() ? "已设置" : "暂未设置");//显示用户是否已设置登录密码
        LineViewHelper.setValue(getView(), R.id.mine_profile_lnv_pay_pwd, profile.isIsSetPayPwd() ? "已设置" : "暂未设置");//显示用户是否已设置支付密码
        LineViewHelper.setValue(getView(), R.id.mine_profile_lnv_bind_wechat, profile.isIsBindWeChat() ? "已设置" : "暂未设置");//显示用户是否已设置支付密码
        if (profile.getArea() != null) {
            //有所在地
            Region region = ConfigModel.getInstace().getRegionById(profile.getArea().getId() + "");
            if (region != null) {
                LineViewHelper.setValue(getView(), mine_profile_lnv_location, region.getFull_name());
            }
        }
    }

    @Override
    protected void setEvent() {
        toSelectLocation();
        getViews(R.id.mine_profile_btn_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mModifyProfilePresenter.toLogout();
            }
        });
        //点击头像栏
        getViews(R.id.mine_layout_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //显示拍照、相册对话框
//                MakePhotoDialog dialog = new MakePhotoDialog();
//                setRequestPermissionResultListener(dialog);
//                dialog.setParentFragment(getUsualFragment());
//                dialog.setCrop(true);
//                dialog.setOnMakePhotoListener(new MakePhotoDialog.OnMakePhotoListener() {
//                    @Override
//                    public void onComplete(List<String> imgFilePaths) {
//                        if (!ListUtils.isEmpty(imgFilePaths)) {
//                            String file = imgFilePaths.get(0);
//                            ImageView img = getViews(R.id.profile_img_icon);
//                            GlideUtil.loadHeadIcon(getContext(), file, img);//显示新头像
//                            mModifyProfilePresenter.updateUserPhoto(file);
//                        }
//                    }
//                });
//                dialog.show(getFragmentManager(), dialog.getClass().getSimpleName());

                MakePhotoDialog2 dialog = new MakePhotoDialog2();
                dialog.setCropConfig(1, 1, 300, 300);
                dialog.setOnMakePhotoListener(new MakePhotoListener() {
                    @Override
                    public void onComplete(List<String> imgFilePaths) {
                        if (!ListUtils.isEmpty(imgFilePaths)) {
                            String file = imgFilePaths.get(0);
                            ImageView img = getViews(R.id.profile_img_icon);
                            GlideUtil.loadHeadIcon(getContext(), file, img);//显示新头像
                            mModifyProfilePresenter.updateUserPhoto(file);
                        }
                    }
                });
                setRequestPermissionResultListener(dialog);
                dialog.show(getFragmentManager(), dialog.getClass().getSimpleName());
            }
        });
        final EditText mEdtName = getViews(R.id.mine_profile_edt_name);
        mEdtName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    toUpdateNickName();
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
                toUpdatePayPwd(new OnActivityResultListener() {
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
                if(checkPayPasswordIsSetted()){
                    showPayPassworInputDialog();
                }else{
                    showToast("支付密码暂未设置！");
                    toUpdatePayPwdBindWechat();
                }
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

    /**
     * 选择城市
     */
    private void toSelectLocation() {
        //选择城市
        final LineViewText lnvSelectCity = getViews(R.id.mine_profile_lnv_location);
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
                dialog.show(getFragmentManager(), dialog.getClass().getSimpleName());
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
        FragmentJumpUtil.toUpdateLoginPwdForResult(getUsualFragment());
        setResultListener(new OnActivityResultListener() {
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
    private void toUpdatePayPwd(OnActivityResultListener l) {
        FragmentJumpUtil.toUpdatePayPwdForResult(getUsualFragment());
        setResultListener(l);
    }

    private void toUpdatePayPwdBindWechat(){
        toUpdatePayPwd(new OnActivityResultListener() {
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
        dialog.setFromUsualFragment(this);
        dialog.setPayPwdInputPresenter(new PayPasswordInputPresenterImpl(getUsualFragment(), this) {
            @Override
            public void setInputPassword(String payPwd) {
                super.setInputPassword(payPwd);
                dialog.dismiss();
            }
        });
        dialog.show(getFragmentManager(), dialog.getClass().getSimpleName());
    }

    /**
     * 绑定微信
     */
    private void toWechatBind() {
        if (mProfileData.isIsBindWeChat() == true) {
            FragmentJumpUtil.toWechatBindFragment(getUsualFragment(), mProfileData.getWechatNickName());
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
        UmengLoginUtil umengLoginUtil = new UmengLoginUtil(getUsualFragment());
        umengLoginUtil.loginWeixin(l);
    }

    @Override
    public void loadProfileSuccess(ResUserProfile userData) {
        mProfileData = userData;
        refreshUI(userData);
    }

    @Override
    public void logoutSuccess() {
        LoginModel.getInstance().logout(true);
        finish();
    }

    @Override
    public void onUpdateFailure() {
        refreshUI(mProfileData);
    }

    @Override
    public String getNickName() {
        return ((TextView) getViews(R.id.mine_profile_edt_name)).getText().toString();
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

    @Subscribe
    public void onEvent(DataEvent.OnToUpdataUserProfileEvent event) {
        mMineProfilePresenter.getUserProfile();
    }

    @Override
    public void bindSuccess() {
        FragmentJumpUtil.toWechatBindFragment(getUsualFragment(), mStrWechatNickName);
        mMineProfilePresenter.getUserProfile();
    }

    @Override
    public void unBindSuccess() {

    }

    @Override
    public void setCheckResult(boolean isCheckOut) {
        if(isCheckOut){
            toWechatBind();
        }else{
            SelfAlertDialogHelper.showDialog(
                    getChildFragmentManager(), "支付密码错误，请重试。", "忘记密码", "重新输入",
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

}
