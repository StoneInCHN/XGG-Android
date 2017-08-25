package com.hentica.app.module.mine.ui.bank;

import android.content.Intent;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.fiveixlg.app.customer.R;
import com.hentica.app.framework.activity.BaseCompatActivity;
import com.hentica.app.lib.net.NetData;
import com.hentica.app.module.listener.Callback;
import com.hentica.app.module.mine.presenter.RealNameVerificationPresenter;
import com.hentica.app.module.mine.presenter.RealNameVerificationPresenterImpl;
import com.hentica.app.module.mine.ui.bank.BankCardAddFragment;
import com.hentica.app.module.mine.ui.textcontent.VerificationDescribtionFragment;
import com.hentica.app.module.mine.view.RealNameVerificationView;
import com.hentica.app.util.FragmentJumpUtil;
import com.hentica.app.util.event.DataEvent;
import com.hentica.app.widget.dialog.SelfAlertDialogHelper;
import com.hentica.app.widget.photo.MakePhotoDialog2;
import com.hentica.app.widget.photo.MakePhotoListener;
import com.hentica.app.widget.view.TitleView;
import com.hentica.app.widget.view.lineview.LineViewEdit;
import com.hentica.app.widget.view.lineview.LineViewHelper;
import com.hentica.appbase.famework.util.IdcardValidator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;


/**
 * Created by Snow on 2017/5/23.
 */

public class RealNameVerificationActivity extends BaseCompatActivity implements RealNameVerificationView{
    private String mIdCardPhoto1 ="";//身份证正面照片
    private String mIdCardPhoto2 ="";//身份证反面照片

    private RealNameVerificationPresenter mVerificationPresenter;

    @Override
    public int getLayoutId() {
        return R.layout.mine_realname_varification_fragment;
    }

    @Override
    protected void initData() {
        super.initData();
        mVerificationPresenter = new RealNameVerificationPresenterImpl(this);
    }

    @Override
    protected void initView() {
        super.initView();
        TitleView title = getViews(R.id.common_title);
        title.setOnLeftImageBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(getRealName()) || !TextUtils.isEmpty(getIdCardNumber()) ||
                    !TextUtils.isEmpty(mIdCardPhoto1) || !TextUtils.isEmpty(mIdCardPhoto2)){
                    //显示提示框
                    SelfAlertDialogHelper.showDialog(getSupportFragmentManager(),
                            getString(R.string.tip_real_name_verification_3),
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    finish();
                                }
                            });

                }else {
                    finish();
                }
            }
        });
        title.getRightTextBtn().setTextColor(getResources().getColor(R.color.text_white));
        title.setOnRightTextBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentJumpUtil.toFragment(getActivity(), VerificationDescribtionFragment.class);
            }
        });
        LineViewEdit lnvIdNumber = getViews(R.id.verification_lnv_number);
        lnvIdNumber.getContentTextView().setFilters(new InputFilter[]{new InputFilter.LengthFilter(18)});
    }

    @Override
    protected void setEvent() {
        super.setEvent();
        setViewClickEvent(R.id.img_idcard_1, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(mIdCardPhoto1)){
                    //已拍照，点击无效
                    return;
                }else{
                    //拍照
                    toTakePhoto1();
                }
            }
        });
        setViewClickEvent(R.id.img_idcard_2, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(mIdCardPhoto2)){
                    //已拍照，点击无效
                    return;
                }else{
                    //拍照
                    toTakePhoto2();
                }
            }
        });
        //删除按钮
        setViewClickEvent(R.id.img_idcard_delete_1, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIdCardPhoto1 = "";
                ImageView img = getViews(R.id.img_idcard_1);
                img.setImageResource(0);
                setViewVisiable(true, R.id.tv_idcard_tip_1);
                setViewVisiable(false, R.id.img_idcard_delete_1);
            }
        });
        //删除按钮
        setViewClickEvent(R.id.img_idcard_delete_2, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIdCardPhoto2 = "";
                ImageView img = getViews(R.id.img_idcard_2);
                img.setImageResource(0);
                setViewVisiable(true, R.id.tv_idcard_tip_2);
                setViewVisiable(false, R.id.img_idcard_delete_2);
            }
        });
        //认证
        setViewClickEvent(R.id.btn_submit, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idCardNumber = getIdCardNumber();
                if(!(new IdcardValidator().isValidatedAllIdcard(idCardNumber))){
                    showToast("身份证号码输入有误！");
                    return;
                }
                mVerificationPresenter.verification(getRealName(), getIdCardNumber(), mIdCardPhoto1, mIdCardPhoto2);
            }
        });
    }

    /**
     * 拍微信证正面
     */
    private void toTakePhoto1(){
        takeIdCardPhoto(new Callback<String>() {
            @Override
            public void callback(boolean isSuccess, String data) {
                if(isSuccess){
                    mIdCardPhoto1 = data;
                    showPhoto(mIdCardPhoto1, (ImageView) getViews(R.id.img_idcard_1));
                    setViewVisiable(false, R.id.tv_idcard_tip_1);
                    setViewVisiable(true, R.id.img_idcard_delete_1);
                }
            }
            @Override
            public void onFailed(NetData result) {}
        });
    }

    /**
     * 拍微信证反面
     */
    private void toTakePhoto2(){
        takeIdCardPhoto(new Callback<String>() {
            @Override
            public void callback(boolean isSuccess, String data) {
                if(isSuccess){
                    mIdCardPhoto2 = data;
                    showPhoto(mIdCardPhoto2, (ImageView) getViews(R.id.img_idcard_2));
                    setViewVisiable(false, R.id.tv_idcard_tip_2);
                    setViewVisiable(true, R.id.img_idcard_delete_2);
                }
            }
            @Override
            public void onFailed(NetData result) {}
        });
    }


    private void takeIdCardPhoto(final Callback<String> l){
        takePhoto(new MakePhotoListener() {
            @Override
            public void onComplete(List<String> imgFilePaths) {
                if(imgFilePaths != null && !imgFilePaths.isEmpty()){
                    l.callback(true, imgFilePaths.get(0));
                }
            }
        });
    }

    private void takePhoto(MakePhotoListener l){
        MakePhotoDialog2 dialog2 = new MakePhotoDialog2();
        dialog2.setCompressConfig(200 * 1024, 1024, 1024);
        dialog2.setOnMakePhotoListener(l);
        dialog2.show(getSupportFragmentManager(), dialog2.getClass().getSimpleName());
    }

    /**
     * 显示图片
     * @param path
     * @param img
     */
    private void showPhoto(String path, ImageView img){
        Glide.with(getActivity())
                .load(path)
                .into(img);
    }

    /**
     * 获取真实姓名
     * return
     */
    private String getRealName(){
        return LineViewHelper.getValue(mQuery, R.id.verification_lnv_name);
    }

    /**
     * 获取微信证号
     * @return
     */
    private String getIdCardNumber(){
        return LineViewHelper.getValue(mQuery, R.id.verification_lnv_number);
    }


    @Override
    public void verificationSuccess(String name) {
        Intent intent = new Intent();
        intent.putExtra(BankCardAddFragment.DATA_REAL_NAME, name);
        intent.putExtra(BankCardAddFragment.DATA_ID_CARD, getIdCardNumber());
        intent.putExtra(BankCardAddFragment.DATA_IS_DEFAULT, true);
        FragmentJumpUtil.toFragment(this, BankCardAddFragment.class, intent);
    }


    @Subscribe
    public void onEvent(DataEvent.OnBankCardAddSuccess event){
        finish();
    }
}
