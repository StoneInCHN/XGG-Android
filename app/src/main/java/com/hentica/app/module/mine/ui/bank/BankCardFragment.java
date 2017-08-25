package com.hentica.app.module.mine.ui.bank;

import android.content.Intent;
import android.view.View;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.fiveixlg.app.customer.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.hentica.app.framework.fragment.ptr.PtrPresenter;
import com.hentica.app.framework.fragment.ptr.PtrView;
import com.hentica.app.module.common.base.BaseFragment;
import com.hentica.app.module.entity.mine.ResBankListData;
import com.hentica.app.module.entity.mine.ResIdCardData;
import com.hentica.app.module.mine.presenter.bank.BankCardPresenter;
import com.hentica.app.module.mine.presenter.bank.BankCardPresenterImpl;
import com.hentica.app.module.mine.presenter.bank.BankCardListPresenter;
import com.hentica.app.module.mine.ui.adapter.BankCardAdapter;
import com.hentica.app.module.mine.ui.intf.OnDeleteViewClickListener;
import com.hentica.app.module.mine.view.BankCardView;
import com.hentica.app.util.FragmentJumpUtil;
import com.hentica.app.widget.dialog.SelfAlertDialogHelper;
import com.hentica.app.widget.view.TitleView;
import com.hentica.appbase.famework.adapter.QuickChooseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 *  我的银行卡界面
 * Created by Snow on 2017/5/27 0027.
 */

public class BankCardFragment extends BaseFragment implements PtrView<ResBankListData>, BankCardView {

    private PtrPresenter mPresneter;
    private PullToRefreshScrollView mScrollView;
    private ListView mListView;
    private BankCardAdapter mCardAdapter;
    private BankCardPresenter mBankCardPresenter;
    private ResIdCardData mIdCardData;

    @Override
    protected boolean hasTitleView() {
        return true;
    }

    @Override
    protected TitleView initTitleView() {
        return getViews(R.id.common_title);
    }

    @Override
    protected void configTitleValues(TitleView title) {
        super.configTitleValues(title);
        TextView tvRight = title.getRightTextBtn();
        tvRight.setTextColor(getResources().getColor(R.color.text_white));
        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //添加银行卡
                addBankCard();
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.mine_bank_card_list_fragment;
    }

    @Override
    protected void initData() {
        mPresneter = new BankCardListPresenter(this);
        mCardAdapter = new BankCardAdapter();
        mBankCardPresenter = new BankCardPresenterImpl(this);
    }

    @Override
    protected void initView() {
        mScrollView = getViews(R.id.scroll_view);
        mListView = getViews(R.id.bank_card_list);
        mListView.setAdapter(mCardAdapter);
    }

    @Override
    protected void setEvent() {
        //删除按钮事件
        mCardAdapter.setDeleteClickLisntener(new OnDeleteViewClickListener<ResBankListData>() {
            @Override
            public void onDeleteClick(View view, int pos, final ResBankListData data) {
                SelfAlertDialogHelper.showDialog(getFragmentManager(), "确定要删除此账号吗？删除后不可恢复！",
                        new View.OnClickListener(){
                            @Override
                            public void onClick(View v) {
                                //删除银行卡
                                mBankCardPresenter.deleteCard(String.valueOf(data.getId()));
                            }
                        });
            }
        });
        mCardAdapter.setOnItemClickListener(new QuickChooseAdapter.OnItemClickListener<ResBankListData>() {
            @Override
            public void onItemClick(View view, long position, ResBankListData data) {
                if(data.isIsDefault()){
                    return;
                }
                //设置默认
                mBankCardPresenter.setDefault(String.valueOf(data.getId()));
            }
        });
        //下拉刷新，上拉加载事件
        mScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                mPresneter.onRefresh();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                mPresneter.onLoadMore();
            }
        });
        //添加银行卡按钮事件
        setViewClickEvent(R.id.lnv_btn_add_card, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBankCard();
            }
        });
    }

    @Override
    public int getListSize() {
        return mCardAdapter.getCount();
    }

    @Override
    public void setListDatas(List<ResBankListData> datas) {
        mScrollView.onRefreshComplete();
        mCardAdapter.setDatas(datas);
        ResBankListData defualtData = getDefualtData(datas);
        if(defualtData != null){
            List<ResBankListData> defaultDatas = new ArrayList<>();
            defaultDatas.add(defualtData);
            mCardAdapter.setDefaultSelectedDatas(defaultDatas);
        }
    }

    private ResBankListData getDefualtData(List<ResBankListData> datas){
        ResBankListData result = null;
        if(datas != null && !datas.isEmpty()){
            for(ResBankListData child : datas){
                if(child.isIsDefault()){
                    result = child;
                    break;
                }
            }
        }
        return result;
    }

    private void addBankCard(){
        if(mIdCardData == null){
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(BankCardAddFragment.DATA_IS_DEFAULT, getListSize() == 0);
        intent.putExtra(BankCardAddFragment.DATA_REAL_NAME, mIdCardData.getRealName());
       intent.putExtra(BankCardAddFragment.DATA_ID_CARD, mIdCardData.getIdCardNo());
       intent.putExtra(BankCardAddFragment.DATA_IS_DEFAULT, mCardAdapter.getCount() == 0);
        FragmentJumpUtil.toBankCardAddFragment(getUsualFragment(), intent);
    }

    @Override
    public void addListDatas(List<ResBankListData> datas) {
        mScrollView.onRefreshComplete();
        mCardAdapter.addAll(datas);
        ResBankListData defualtData = getDefualtData(datas);
        if(defualtData != null){
            List<ResBankListData> defaultDatas = new ArrayList<>();
            defaultDatas.add(defualtData);
            mCardAdapter.setDefaultSelectedDatas(defaultDatas);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresneter.onRefresh();
        mBankCardPresenter.getIdCard();
    }

    @Override
    public void setIdCardData(ResIdCardData data) {
        mIdCardData = data;
    }

    @Override
    public void deleteSuccess() {
        mPresneter.onRefresh();
    }

    @Override
    public void setDefaultSuccess() {
        mPresneter.onRefresh();
    }
}
