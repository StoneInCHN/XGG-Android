package com.hentica.app.module.mine.ui.statistics;

import android.widget.TextView;

import com.hentica.app.framework.fragment.ptr.PtrPresenter;
import com.hentica.app.module.entity.mine.ResMineScore;
import com.hentica.app.module.login.business.LoginModel;
import com.hentica.app.module.login.data.UserLoginData;
import com.hentica.app.module.mine.presenter.statistics.ScorePtrPresenter;
import com.hentica.app.module.mine.ui.adapter.StatisticsScoreAdapter;
import com.hentica.app.module.mine.view.statistics.ScorePtrView;
import com.hentica.app.util.PriceUtil;
import com.hentica.app.util.event.DataEvent;
import com.hentica.app.widget.view.TitleView;
import com.hentica.appbase.famework.adapter.QuickAdapter;
import com.fiveixlg.app.customer.R;


/**
 * 积分界面
 *
 * @author
 * @createTime 2017-03-23 下午15:13:27
 */
public class MineScoreFragment extends MineStatisticsListFragment<ResMineScore> implements ScorePtrView{

    @Override
    public int getLayoutId() {
        return R.layout.mine_integral_fragment;
    }

    @Override
    protected QuickAdapter getListAdapter() {
        return new StatisticsScoreAdapter();
    }

    @Override
    protected PtrPresenter getPtrPresenter() {
        return new ScorePtrPresenter(this);
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
        title.setTitleText("积分");
    }

    @Override
    protected void initView() {
        super.initView();
        setViewVisiable(false, R.id.mine_statistics_tv_tip);
        refreshUI();
    }

    private void refreshUI(){
        TextView mTvTotal = getViews(getStatisticsItemLeft());//累计积分
        TextView mTvTotalValue = getViews(getStatisticsItemLeftValue());
        TextView mTvCurrent = getViews(getStatisticsItemRight());//当前积分
        TextView mTvCurrentValue = getViews(getStatisticsItemRightValue());
        mTvTotal.setText("累计积分");
        mTvCurrent.setText("当前积分");
        UserLoginData userData = LoginModel.getInstance().getUserLogin();
        mTvTotalValue.setText(PriceUtil.format4Decimal(userData == null ? 0 : userData.getTotalScore()));
        mTvCurrentValue.setText(PriceUtil.format4Decimal(userData == null ? 0 : userData.getCurScore()));
    }


    @Override
    public void onEvent(DataEvent.OnLoginEvent event) {
        super.onEvent(event);
        refreshUI();
    }

    @Override
    public void setParseScore(String score) {
        setViewText(String.format("注：满%s自动转换为1乐心。", score), R.id.mine_statistics_tv_tip);
        setViewVisiable(true, R.id.mine_statistics_tv_tip);
    }
}
