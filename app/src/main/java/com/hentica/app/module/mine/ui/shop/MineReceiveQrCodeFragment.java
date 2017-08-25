package com.hentica.app.module.mine.ui.shop;

import android.content.Intent;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hentica.app.framework.data.ApplicationData;
import com.hentica.app.module.common.base.BaseFragment;
import com.hentica.app.module.entity.mine.shop.ResShopInfo;
import com.hentica.app.module.entity.mine.shop.ResShopQrCode;
import com.hentica.app.module.mine.presenter.QrCodePresenter;
import com.hentica.app.module.mine.presenter.ShopQrCodePresenterImpl;
import com.hentica.app.module.mine.view.QrCodeView;
import com.hentica.app.util.IntentUtil;
import com.hentica.app.widget.view.TitleView;
import com.fiveixlg.app.customer.R;

/**
 * 收款二维码界面
 *
 * @author
 * @createTime 2017-03-23 下午15:13:27
 */
public class MineReceiveQrCodeFragment extends BaseFragment implements QrCodeView<ResShopQrCode> {

    private QrCodePresenter mQrPresenter;

    public static final String DATA_SHOP_INFO = "ResShopInfo";

    private ResShopInfo mShopInfo;

    @Override
    public int getLayoutId() {
        return R.layout.mine_receive_qr_code_fragment;
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
    protected void handleIntentData(Intent intent) {
        super.handleIntentData(intent);
        mShopInfo = new IntentUtil(intent).getObject(DATA_SHOP_INFO, ResShopInfo.class);
    }

    @Override
    protected void initData() {
        mQrPresenter = new ShopQrCodePresenterImpl(this, String.valueOf(mShopInfo.getId()));
        mQrPresenter.getQrCode();
    }

    @Override
    protected void initView() {
//        setViewText(mShopInfo.getName(), R.id.tv_name);
//        setViewText(mShopInfo.getAddress(), R.id.tv_address);
//        //显示logo
//        Glide.with(getActivity())
//                .load(ApplicationData.getInstance().getImageUrl(mShopInfo.getStorePictureUrl()))
//                .override(400, 400)
//                .into((ImageView) getViews(R.id.img_logo));
    }

    @Override
    protected void setEvent() {

    }

    @Override
    public void setQrCode(byte[] datas) {
        //显示二维码
        Glide.with(getActivity()).load(datas).into((ImageView) getViews(R.id.img_qr_code));
    }

    @Override
    public void setData(ResShopQrCode data) {
        setViewText(data.getName(), R.id.tv_name);
        setViewText(data.getArea().getFullName(), R.id.tv_address);
        //显示logo
        Glide.with(getActivity())
                .load(ApplicationData.getInstance().getImageUrl(data.getStorePictureUrl()))
                .override(400, 400)
                .into((ImageView) getViews(R.id.img_logo));
    }
}
