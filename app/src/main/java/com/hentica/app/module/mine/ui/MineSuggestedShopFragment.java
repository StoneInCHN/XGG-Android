package com.hentica.app.module.mine.ui;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.fiveixlg.app.customer.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.hentica.app.framework.fragment.CommonPtrFragment;
import com.hentica.app.framework.fragment.ptr.PtrPresenter;
import com.hentica.app.framework.fragment.ptr.PtrView;
import com.hentica.app.module.entity.mine.ResSaleSuggestShopListData;
import com.hentica.app.module.entity.type.ApplyStatus;
import com.hentica.app.module.login.business.LoginModel;
import com.hentica.app.module.login.data.UserLoginData;
import com.hentica.app.module.mine.presenter.SalerSuggestShopPtrPresenter;
import com.hentica.app.module.mine.ui.adapter.SuggestShopAdapter;
import com.hentica.app.module.mine.ui.shop.MineSettleCheckingFragment;
import com.hentica.app.module.mine.ui.shop.MineSettledBusinessActivity;
import com.hentica.app.util.FragmentJumpUtil;
import com.hentica.app.widget.view.TitleView;
import com.hentica.appbase.famework.adapter.QuickAdapter;

import java.util.List;

/**
 * 推荐商家界面
 * Created by Snow on 2017/5/23.
 */

public class MineSuggestedShopFragment extends CommonPtrFragment<ResSaleSuggestShopListData> implements PtrView<ResSaleSuggestShopListData>{

    private PtrPresenter mPtrPresenter;

    @Override
    protected String getFragmentTitle() {
        return "推荐商家";
    }

    @Override
    public QuickAdapter createAdapter() {
        return new SuggestShopAdapter();
    }

    @Override
    protected void configTitleValues(TitleView title) {
        super.configTitleValues(title);
        UserLoginData login = LoginModel.getInstance().getUserLogin();
        TextView rightBtn = title.getRightTextBtn();
        if(!login.getIsSalesmanApply()){//没有权限
            return;
        }
        rightBtn.setText("新增");
        rightBtn.setVisibility(View.VISIBLE);
        rightBtn.setTextColor(getResources().getColor(R.color.text_white));
        rightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //新增
                //跳转新增商家
                startActivity(new Intent(getContext(), MineSettledBusinessActivity.class));
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        mPtrPresenter = new SalerSuggestShopPtrPresenter(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ResSaleSuggestShopListData itemData = getItem(position - 1);
        String applyStatus = itemData.getSellerApplication().getApplyStatus();
        if(ApplyStatus.AUDIT_WAITING.equals(applyStatus)){
            //待审核
            startFrameActivity(MineSettleCheckingFragment.class);
        }else if(ApplyStatus.AUDIT_FAILED.equals(applyStatus)){
            //未通过
            FragmentJumpUtil.toSettleFailureFragment(getUsualFragment(),
                    itemData.getSellerApplication().getId()+"",
                    itemData.getSellerApplication().getNotes(),
                    itemData.getSellerApplication().getContactCellPhone());
        }else{
            return;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mPtrPresenter.onRefresh();
    }

    @Override
    protected void setEvent() {
        super.setEvent();
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        mPtrPresenter.onRefresh();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        mPtrPresenter.onLoadMore();
    }

    @Override
    public void setListDatas(List<ResSaleSuggestShopListData> datas) {
        super.setDatas(datas);
    }

    @Override
    public void addListDatas(List<ResSaleSuggestShopListData> datas) {
        super.addDatas(datas);
    }
}
