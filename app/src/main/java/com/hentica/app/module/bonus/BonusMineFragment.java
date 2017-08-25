package com.hentica.app.module.bonus;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.hentica.app.module.common.base.BaseFragment;
import com.hentica.app.module.common.listener.ListenerAdapter;
import com.hentica.app.module.common.listener.UsualDataBackListener;
import com.hentica.app.module.entity.bonus.BonusPeopleData;
import com.hentica.app.module.login.business.LoginModel;
import com.hentica.app.module.login.data.UserLoginData;
import com.hentica.app.util.PriceUtil;
import com.hentica.app.util.event.DataEvent;
import com.hentica.app.util.request.Request;
import com.hentica.app.widget.view.TitleView;
import com.litesuits.http.request.content.multi.BoundaryCreater;
import com.fiveixlg.app.customer.R;

import org.greenrobot.eventbus.Subscribe;

/**
 * 我的分红界面
 *
 * @author
 * @createTime 2017-03-23 下午15:13:27
 */
public class BonusMineFragment extends FrameLayout {

    private TextView mDateTv;
    private TextView mBonusLefenTv;
    private TextView mConsumeTv;
    private TextView mMaxLefenTv;
    private TextView mScoreTv;
    private TextView mLexinTv;
    private TextView mLedouTv;
    private TextView mLefenTv;

    private Context mContext;
    private AQuery mQuery;

    private static float screenRatio = 0;

    public BonusMineFragment(Context context) {
        this(context, null);
    }

    public BonusMineFragment(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BonusMineFragment(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private void initView() {
        final View view = View.inflate(mContext, R.layout.bonus_mine_fragment, this);
        if (view != null) {
            mQuery = new AQuery(view);
            mDateTv = mQuery.id(R.id.bonus_mine_date).getTextView();
            mBonusLefenTv = mQuery.id(R.id.bonus_mine_bonus_lefen).getTextView();
            mConsumeTv = mQuery.id(R.id.bonus_mine_total_money).getTextView();
            mMaxLefenTv = mQuery.id(R.id.bonus_mine_max_bonus_lefen).getTextView();
            mScoreTv = mQuery.id(R.id.bonus_mine_score).getTextView();
            mLexinTv = mQuery.id(R.id.bonus_mine_lenxin).getTextView();
            mLedouTv = mQuery.id(R.id.bonus_mine_lendou).getTextView();
            mLefenTv = mQuery.id(R.id.bonus_mine_lenfen).getTextView();
            ViewTreeObserver vbo = view.getViewTreeObserver();
            vbo.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    view.getViewTreeObserver().removeOnPreDrawListener(this);
                    screenRatio = (float)view.getMeasuredHeight() / BonusConstans.BACKGROUND_HEIGHT;//计算缩放比例
                    resetViewSize();
                    return true;
                }
            });
        }
    }

    /**
     * 重置界面大小
     */
    private void resetViewSize(){
        //表格背景
        View tableLayout = findViewById(R.id.bonus_mine_table_layout);
        FrameLayout.LayoutParams tableLayoutLp = (LayoutParams) tableLayout.getLayoutParams();
        tableLayoutLp.height = (int) (screenRatio * BonusConstans.TABLE_BACKGROUND_HEIGHT);
        tableLayoutLp.setMargins(0, (int) (screenRatio * BonusConstans.MINE_TABLE_BG_MARGIN_TOP), 0, 0);

        //表格
        View table = findViewById(R.id.bonus_mine_table);
        FrameLayout.LayoutParams tableLp = (LayoutParams) table.getLayoutParams();
        tableLp.height = (int) (screenRatio * BonusConstans.MINE_TABLE_HEIGHT);
        tableLp.setMargins(0, 0, 0, (int) (screenRatio * BonusConstans.MINE_TABLE_MARGIN_BOTTOM));

        //表格标签
        View label = findViewById(R.id.bonus_mine_date);
        FrameLayout.LayoutParams labelLp = (LayoutParams) label.getLayoutParams();
        labelLp.height = (int) (screenRatio * BonusConstans.TABLE_LABEL_HEIGHT);
        labelLp.setMargins(0, (int) (screenRatio * BonusConstans.TABLE_LABEL_MARGIN_TOP), 0, 0);

        //统计信息
        View statistics = findViewById(R.id.bonus_mine_statistics_layout);
        FrameLayout.LayoutParams statisticsLp = (LayoutParams) statistics.getLayoutParams();
        statisticsLp.setMargins(0, 0, 0, (int) (screenRatio * BonusConstans.MINE_STATISTICS_MARGIN_BOTTOM));
    }

    private String getDealString(String text, String unit){
        return TextUtils.isEmpty(text) ? "0"+unit : text+unit;
    }

    /** 刷新UI */
    public void refreshUI(BonusPeopleData data){
        if(data != null){
            mDateTv.setText(data.getReportDate()+"统计");
            mBonusLefenTv.setText(getDoubleString(data.getBonusLeScore(),"元"));
            mConsumeTv.setText(getDoubleString(data.getConsumeTotalAmount(),"元"));
            mMaxLefenTv.setText(getDoubleString(data.getHighBonusLeScore(),"元"));
        }else {
            resetUI();
        }
        refreshAccount();
    }

    private String getDoubleString(String text, String unit){
        if(TextUtils.isEmpty(text)){
            return "暂未统计";
        }
        double value = 0;
        try{
            value = Double.parseDouble(text);
        } catch (NumberFormatException e){
            e.printStackTrace();
        }
        return PriceUtil.format(value)+unit;
    }

    /** 重置界面数据 */
    public void resetUI(){
        mDateTv.setText("暂未统计");
        mBonusLefenTv.setText("暂未统计");
        mConsumeTv.setText("暂未统计");
        mMaxLefenTv.setText("暂未统计");
    }

    /** 刷新账户信息 */
    public void refreshAccount(){
        UserLoginData loginData = LoginModel.getInstance().getUserLogin();
        if(loginData != null){
            mScoreTv.setText(PriceUtil.format(loginData.getCurScore()));
            mLexinTv.setText(loginData.getCurLeMind()+"");
            mLedouTv.setText(PriceUtil.format(loginData.getCurLeBean()));
            mLefenTv.setText(PriceUtil.format(loginData.getCurLeScore()));
        }
    }

}
