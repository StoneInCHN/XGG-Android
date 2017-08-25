package com.hentica.app.module.mine.ui.statistics;

import android.view.View;
import android.widget.TextView;

import com.fiveixlg.app.customer.R;
import com.hentica.app.framework.fragment.ptr.PtrPresenter;
import com.hentica.app.module.entity.mine.ResMineLeDou;
import com.hentica.app.module.login.business.LoginModel;
import com.hentica.app.module.login.data.UserLoginData;
import com.hentica.app.module.mine.presenter.statistics.LedouPtrPresenter;
import com.hentica.app.module.mine.ui.adapter.StatisticsLedouAdapter;
import com.hentica.app.util.FragmentJumpUtil;
import com.hentica.app.util.PriceUtil;
import com.hentica.app.util.event.DataEvent;
import com.hentica.appbase.famework.adapter.QuickAdapter;

/**
 * 乐豆界面
 *
 * @author
 * @createTime 2017-03-23 下午15:13:27
 */
public class MineLedouFragment extends MineStatisticsListFragment<ResMineLeDou> {

    @Override
    public int getLayoutId() {
        return R.layout.mine_statistics_list_ledou_fragment;
    }

    @Override
    protected QuickAdapter getListAdapter() {
        return new StatisticsLedouAdapter();
    }

    @Override
    protected PtrPresenter getPtrPresenter() {
        return new LedouPtrPresenter(this);
    }

    @Override
    protected void initView() {
        super.initView();
        refreshUI();
    }

    private void refreshUI(){
        TextView mTvTotal = getViews(getStatisticsItemLeft());//累计积分
        TextView mTvTotalValue = getViews(getStatisticsItemLeftValue());
        TextView mTvCurrent = getViews(getStatisticsItemRight());//当前积分
        TextView mTvCurrentValue = getViews(getStatisticsItemRightValue());
        mTvTotal.setText("累计乐豆");
        mTvCurrent.setText("当前乐豆");
        UserLoginData userData = LoginModel.getInstance().getUserLogin();
        mTvTotalValue.setText(PriceUtil.format4Decimal(userData == null ? 0 : userData.getTotalLeBean()));
        mTvCurrentValue.setText(PriceUtil.format4Decimal(userData == null ? 0 : userData.getCurLeBean()));
    }

    @Override
    protected void setEvent() {
        super.setEvent();
        setViewClickEvent(R.id.ledou_btn_transfer, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentJumpUtil.toTransferFragment(getUsualFragment(), 3);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        refreshUI();
    }

    @Override
    public void onEvent(DataEvent.OnLoginEvent event) {
        super.onEvent(event);
        refreshUI();
    }
}
