package com.hentica.app.module.mine.ui.statistics;

import android.view.View;
import android.widget.TextView;

import com.hentica.app.framework.fragment.ptr.PtrPresenter;
import com.hentica.app.module.entity.mine.ResMineLeXin;
import com.hentica.app.module.login.business.LoginModel;
import com.hentica.app.module.login.data.UserLoginData;
import com.hentica.app.module.mine.presenter.statistics.LexinPtrPresenter;
import com.hentica.app.module.mine.ui.adapter.StatisticsLexinAdapter;
import com.hentica.app.util.FragmentJumpUtil;
import com.hentica.app.util.PriceUtil;
import com.hentica.app.util.event.DataEvent;
import com.hentica.app.widget.view.TitleView;
import com.hentica.appbase.famework.adapter.QuickAdapter;
import com.fiveixlg.app.customer.R;

/**
 * 乐心界面
 *
 * @author
 * @createTime 2017-03-23 下午15:13:27
 */
public class MineLexinFragment extends MineStatisticsListFragment<ResMineLeXin> {

    @Override
    public int getLayoutId() {
        return R.layout.mine_lexin_fragment;
    }

    @Override
    protected QuickAdapter getListAdapter() {
        return new StatisticsLexinAdapter();
    }

    @Override
    protected PtrPresenter getPtrPresenter() {
        return new LexinPtrPresenter(this);
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
    protected void configTitleValues(TitleView title) {
        super.configTitleValues(title);
        title.setTitleText("乐心");
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
        mTvTotal.setText("累计分红乐心");
        mTvCurrent.setText("当前乐心");
        UserLoginData userData = LoginModel.getInstance().getUserLogin();
        mTvTotalValue.setText(userData == null ? "0" : String.valueOf(userData.getTotalLeMind()));
        mTvCurrentValue.setText(userData == null ? "0" : String.valueOf(userData.getCurLeMind()));
    }

    @Override
    protected void setEvent() {
        super.setEvent();
        setViewClickEvent(R.id.lexin_btn_transfer, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentJumpUtil.toTransferFragment(getUsualFragment(), 1);
            }
        });
    }

    @Override
    public void onEvent(DataEvent.OnLoginEvent event) {
        super.onEvent(event);
        refreshUI();
    }

    @Override
    public void onStart() {
        super.onStart();
        refreshUI();
    }
}
