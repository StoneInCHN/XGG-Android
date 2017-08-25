package com.hentica.app.module.mine.ui.shop;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.hentica.app.framework.data.ApplicationData;
import com.hentica.app.module.common.base.BaseFragment;
import com.hentica.app.module.entity.mine.shop.ResShopInfo;
import com.hentica.app.module.mine.model.ShopModel;
import com.hentica.app.module.mine.presenter.shop.ShopInfoPresenter;
import com.hentica.app.module.mine.presenter.shop.ShopInfoPresenterImpl;
import com.hentica.app.module.mine.ui.MineWithdrawalsFragment;
import com.hentica.app.module.mine.ui.bank.BankCardAddFragment;
import com.hentica.app.module.mine.view.shop.ShopInfoView;
import com.hentica.app.util.FragmentJumpUtil;
import com.hentica.app.util.HtmlBuilder;
import com.hentica.app.util.PriceUtil;
import com.hentica.app.util.VerifyHelper;
import com.hentica.app.util.event.DataEvent;
import com.hentica.app.widget.dialog.SelfAlertDialog;
import com.hentica.app.widget.dialog.SelfAlertDialogHelper;
import com.hentica.app.widget.view.TitleView;
import com.fiveixlg.app.customer.R;

import org.greenrobot.eventbus.Subscribe;

/**
 * 我的店铺界面
 *
 * @author
 * @createTime 2017-03-23 下午15:13:27
 */
public class MineShopInfoFragment extends BaseFragment implements ShopInfoView {

    private PullToRefreshScrollView mPtrScrollView;

    private ShopInfoPresenter mShopInfoPresenter;
    private ResShopInfo mShopInfo;
    private ShopModel mShopModel;

    private boolean isVerifyTip = false;//标记认证提示是否已显示

    @Override
    protected boolean hasTitleView() {
        return true;
    }

    @Override
    protected TitleView initTitleView() {
        return getViews(R.id.common_title);
    }

    @Override
    public int getLayoutId() {
        return R.layout.mine_shop_fragment;
    }


    @Override
    protected void initData() {
        mShopInfoPresenter = new ShopInfoPresenterImpl(this);
        mShopInfoPresenter.getShopInfo();
        mShopModel = ShopModel.getInstance();
    }

    @Override
    protected void initView() {
        setViewVisiable(false, R.id.shop_layout_detail);
        mPtrScrollView = getViews(R.id.shop_ptr_scrollview);
    }

    @Override
    protected void setEvent() {
        //订单管理按钮
        getViews(R.id.mine_shop_opt_orders).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentJumpUtil.toShopOrders(getUsualFragment(), mShopInfo);
            }
        });
        //店铺信息按钮
        getViews(R.id.mine_shop_opt_shop_detail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentJumpUtil.toShopDetailFragment(getUsualFragment(), mShopInfo);
            }
        });
        //收款二维码按钮
        getViews(R.id.mine_shop_opt_shop_qr_code).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentJumpUtil.toShopQrCodeFragment(getUsualFragment(), mShopInfo);
            }
        });
        //店铺货款
        getViews(R.id.mine_shop_opt_payment_for_goods).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mShopInfo != null) {
                    if (!mShopInfo.isAuth()) {
                        VerifyHelper helper = VerifyHelper.newInstance();
                        helper.initData(MinePaymentListFragment.class, null);
                        helper.startFragment(getActivity(), mShopInfo.isAuth());
                    } else {
                        if (mShopInfo.isOwnBankCard()) {
                            startFrameActivity(MinePaymentListFragment.class);
                        } else {
                            Intent intent = new Intent();
                            intent.putExtra(BankCardAddFragment.DATA_IS_DEFAULT, true);
                            startFrameActivity(BankCardAddFragment.class, intent);
                        }
                    }

                }
            }
        });
        //店铺录单
        getViews(R.id.mine_shop_opt_record_order).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 2017/5/24 0024
                if (mShopInfo != null) {

                    Intent intent = new Intent();
                    intent.putExtra(MineRecordOrderFragment.DATA_SHOP_INFO, new Gson().toJson(mShopInfo));
                    VerifyHelper helper = VerifyHelper.newInstance();
                    helper.initData(MineRecordOrderFragment.class, intent);
                    helper.startFragment(getActivity(), mShopInfo.isAuth(), mShopInfo.isOwnBankCard());

//                    Intent intent = new Intent();
//                    intent.putExtra(MineRecordOrderFragment.DATA_SHOP_INFO, new Gson().toJson(mShopInfo));
//                    if (!mShopInfo.isAuth()) {
//                        VerifyHelper helper = VerifyHelper.newInstance();
//                        helper.initData(MineRecordOrderFragment.class, intent);
//                        helper.startFragment(getActivity(), mShopInfo.isAuth());
//                    } else {
//                        if (mShopInfo.isOwnBankCard()) {
//                            startFrameActivity(MineRecordOrderFragment.class, intent);
//                        } else {
//                            Intent intent1 = new Intent();
//                            intent.putExtra(BankCardAddFragment.DATA_IS_DEFAULT, true);
//                            startFrameActivity(BankCardAddFragment.class, intent);
//                        }
//                    }
                }
            }
        });
        //
        mPtrScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                mShopInfoPresenter.getShopInfo();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mShopInfoPresenter.getShopInfo();
    }

    private void refreshUI(ResShopInfo data) {
        if (data != null) {
            //显示logo图片
            ImageView imgLogo = getViews(R.id.shop_img_logo);
            Glide.with(getActivity())
                    .load(ApplicationData.getInstance().getImageUrl(data.getStorePictureUrl()))
                    .override(400, 400)
                    .skipMemoryCache(false)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .error(R.drawable.rebate_mine_shop)
                    .into(imgLogo);
            setViewText(data.getName(), R.id.shop_tv_name);//店铺名称
            setViewText(data.getAddress(), R.id.shop_tv_address);//店铺地址
            //收藏数量
            HtmlBuilder hBuilder = HtmlBuilder.newInstance();
            hBuilder.appendNormal("收藏").appendNextLine().appendRed(String.valueOf(data.getFavoriteNum()));
            TextView tvCollect = getViews(R.id.shop_tv_collect_count);
            tvCollect.setText(hBuilder.create());
            //折扣
            hBuilder = HtmlBuilder.newInstance();
            hBuilder.appendNormal("折扣(折)").appendNextLine().appendRed(String.format("%.1f", data.getDiscount()));
            TextView tvDiscount = getViews(R.id.shop_tv_discount);
            tvDiscount.setText(hBuilder.create());
            // 2017/7/13 显示总额度、
            hBuilder = HtmlBuilder.newInstance();
            hBuilder.appendNormal("总额度(元)").appendNextLine().appendRed(PriceUtil.format(data.getLimitAmountByDay()));
            TextView tvTotalLimit = getViews(R.id.shop_tv_total_limit);
            tvTotalLimit.setText(hBuilder.create());
            // 当前额度
            hBuilder = HtmlBuilder.newInstance();
            hBuilder.appendNormal("当前额度(元)").appendNextLine().appendRed(PriceUtil.format(data.getLimitAmountByDay() - data.getCurLimitAmountByDay()));
            TextView tvCurLimit = getViews(R.id.shop_tv_current_limit);
            tvCurLimit.setText(hBuilder.create());

            setViewVisiable(true, R.id.shop_layout_detail);//显示信息
            if (!data.isAuth()) {
                showVerifyAlertDialog();
            }
        } else {
            setViewVisiable(false, R.id.shop_layout_detail);
        }
    }

    private void showVerifyAlertDialog() {
        if (isVerifyTip) {
            return;
        }
        isVerifyTip = true;
        SelfAlertDialogHelper.showDialog(getFragmentManager(), "请您先实名认证并添加银行卡，否则会影响收益结算哦！",
                "马上认证", "暂不需要", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        VerifyHelper helper = VerifyHelper.newInstance();
                        helper.startFragment(getActivity(), false);
                    }
                });
    }

    @Override
    public void setShopInfoData(ResShopInfo data) {
        mShopInfo = data;
        mShopModel.saveShopInfo(mShopInfo);
        refreshUI(data);
    }

    @Override
    public void failure() {
        // TODO: 2017/4/6 暂时处理，获取数据出错，结束当前界面
        finish();
    }

    @Override
    public void dismissLoadingDialog() {
        super.dismissLoadingDialog();
        mPtrScrollView.onRefreshComplete();
    }

    @Subscribe
    public void onEvent(DataEvent.OnUpdateShopProfileEvent event) {
//        mShopInfoPresenter.getShopInfo();
    }
}
