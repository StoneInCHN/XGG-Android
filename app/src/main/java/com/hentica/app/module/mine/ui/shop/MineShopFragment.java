package com.hentica.app.module.mine.ui.shop;

import android.support.annotation.Nullable;

import com.hentica.app.framework.fragment.ptrscrollviewcontainer.AbsCommonPtrScrollViewContainer;
import com.hentica.app.framework.fragment.ptrscrollviewcontainer.DataLoadPresenter;
import com.hentica.app.module.entity.mine.ResUserProfile;
import com.hentica.app.module.entity.type.SellerStatus;
import com.hentica.app.module.mine.presenter.MineShopPresenterImpl;
import com.hentica.app.util.FragmentJumpUtil;
import com.hentica.app.util.event.DataEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * 我的店铺、包含2个状态时的界面，1店铺审核中，2审核失败
 *
 * @author
 * @createTime 2017-03-23 下午15:13:27
 */
public class MineShopFragment extends AbsCommonPtrScrollViewContainer<ResUserProfile> {

    @Override
    protected boolean hasTitleView() {
        return true;
    }

    @Override
    protected String getFragmentTitle() {
        return "入驻店铺";
    }

    @Override
    protected DataLoadPresenter<ResUserProfile> createDataLoadPresenter() {
        return new MineShopPresenterImpl(this);
    }

    @Override
    protected String[] loadDetailDataParams() {
        return null;
    }

    @Nullable
    @Override
    protected Class getSubFragmentClass(ResUserProfile data) {
        if(SellerStatus.AUDIT_WAITING.equals(data.getSellerStatus())){
            return MineSettleCheckingFragment2.class;//店铺审核界面
        }else if(SellerStatus.AUDIT_FAILED.equals(data.getSellerStatus())){
            return MineSettleFailureFragment2.class;//我的店铺界面
        }else if(SellerStatus.YES.equals(data.getSellerStatus())){
            startFrameActivity(MineShopInfoFragment.class);
            EventBus.getDefault().post(new DataEvent.OnToUpdataUserProfileEvent());
            finish();
            return null;
        }
        return null;
    }

}
