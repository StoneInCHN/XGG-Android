package com.hentica.app.module.mine.ui.statistics;

import android.annotation.SuppressLint;
import android.view.View;

import com.fiveixlg.app.customer.R;
import com.hentica.app.framework.fragment.ptr.PtrPresenter;
import com.hentica.app.module.entity.mine.ResMineLeFen;
import com.hentica.app.module.mine.presenter.statistics.LefenPtrPresenter;
import com.hentica.app.module.mine.ui.adapter.StatisticsLefenAdapter;
import com.hentica.appbase.famework.adapter.QuickAdapter;

/**
 * Created by Snow on 2017/7/4.
 */

@SuppressLint("ValidFragment")
public class MineLefenStatisticsFragment extends MineStatisticsListFragment<ResMineLeFen> {

    private String mType;

    /**
     *
     * @param type 请参考{@linkplain com.hentica.app.module.entity.type.LeScoreType}
     */
    public MineLefenStatisticsFragment(String type) {
        mType = type;
    }

    @Override
    protected QuickAdapter<ResMineLeFen> getListAdapter() {
        return new StatisticsLefenAdapter();
    }

    @Override
    protected PtrPresenter getPtrPresenter() {
        return new LefenPtrPresenter(this, mType);
    }

    @Override
    protected void initView() {
        super.initView();
        getViews(R.id.layout_statistics_top).setVisibility(View.GONE);
        getViews(R.id.divider_1).setVisibility(View.GONE);
    }
}
