package com.hentica.app.module.bonus;

import android.content.Context;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.excelsecu.zxing.util.LogUtil;
import com.hentica.app.module.entity.bonus.BonusNationData;
import com.hentica.app.util.LogUtils;
import com.fiveixlg.app.customer.R;
import com.hentica.app.util.PriceUtil;

/**
 * 全国分红界面
 *
 * @author
 * @createTime 2017-03-23 下午15:13:27
 */
public class BonusCountryFragment extends FrameLayout {

    private static final String TAG = BonusCountryFragment.class.getSimpleName();

    private TextView mDateTv1;
    private TextView mNationTotalMoneyTv;
    private TextView mNationTotalPeopleTv;
    private TextView mNationGoodBusinessTv;
    private TextView mNationGoodMoneyTv;
    private TextView mDateTv2;
    private TextView mTotalLexinTv;
    private TextView mTotalConsumeTv;
    private TextView mTotalLefenTv;
    private TextView mGoodMoneyTv;

    private Context mContext;
    private AQuery mQuery;

    private float screenRatio = 0;//屏幕竖起方向绽放比例 HEIGHT / 界面高度

    public BonusCountryFragment(Context context) {
        this(context, null);
    }

    public BonusCountryFragment(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BonusCountryFragment(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }


    private void initView() {
        final View view = View.inflate(mContext, R.layout.bonus_country_fragment, this);
        if (view != null) {
            mQuery = new AQuery(view);
            mDateTv1 = mQuery.id(R.id.bonus_country_date1).getTextView();
            mNationTotalMoneyTv = mQuery.id(R.id.bonus_country_total_money).getTextView();
            mNationTotalPeopleTv = mQuery.id(R.id.bonus_country_total_people).getTextView();
            mNationGoodBusinessTv = mQuery.id(R.id.bonus_country_good_business).getTextView();
            mNationGoodMoneyTv = mQuery.id(R.id.bonus_country_total_good_money).getTextView();
            mDateTv2 = mQuery.id(R.id.bonus_country_date2).getTextView();
            mTotalLexinTv = mQuery.id(R.id.bonus_country_total_bonus_lexin).getTextView();
            mTotalConsumeTv = mQuery.id(R.id.bonus_country_total_consume).getTextView();
            mTotalLefenTv = mQuery.id(R.id.bonus_country_bonus_lefen).getTextView();
            mGoodMoneyTv = mQuery.id(R.id.bonus_country_good_money).getTextView();
            ViewTreeObserver vbo = view.getViewTreeObserver();
            vbo.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    LogUtil.i("onPreDraw");
                    view.getViewTreeObserver().removeOnPreDrawListener(this);
                    LogUtils.i("width : " + view.getWidth() + " \theight : " + view.getHeight());
                    LogUtils.i("measureWidth : " + view.getMeasuredWidth() + " \tmeasureHeight : " + view.getMeasuredHeight());
                    DisplayMetrics dm = getResources().getDisplayMetrics();
                    LogUtils.i("screenWidth : " + dm.widthPixels + " \tscreenHeight : " + dm.heightPixels);
                    int viewHeight = view.getMeasuredHeight();//界面高度
                    //计算缩放比例
                    screenRatio = (float)viewHeight / BonusConstans.BACKGROUND_HEIGHT ;
                    resetViewSize();
                    return true;
                }
            });
        }
    }

    /**
     * 重置控件大小
     */
    private void resetViewSize(){
        //重置表格1
        setTableSize1(R.id.bonus_country_date1, R.id.bonus_country_table_one);
        //重置表格2
        setTableSize2( R.id.bonus_country_date2, R.id.bonus_country_table_two);
    }

    private void setTableSize1(@IdRes int tableLabel, @IdRes int table){
        //背景
        View tab1 = findViewById(R.id.bonus_country_info_one);
        LinearLayout.LayoutParams tab1Lp = (LinearLayout.LayoutParams) tab1.getLayoutParams();
        tab1Lp.height = (int) (screenRatio * BonusConstans.TABLE_BACKGROUND_HEIGHT);
        tab1Lp.setMargins(0, (int) (screenRatio * BonusConstans.COUNTRY_BACKGROUND_DIVDER_TOP_1), 0, 0);

        //设置标签1
        View label1 = findViewById(tableLabel);
        FrameLayout.LayoutParams label1Lp = (LayoutParams) label1.getLayoutParams();
        label1Lp.setMargins(0, (int) (screenRatio * BonusConstans.TABLE_LABEL_MARGIN_TOP), 0, 0);
        label1Lp.height = (int) (screenRatio * BonusConstans.TABLE_LABEL_HEIGHT);

        //设置表格1
        View table1 = findViewById(table);
        FrameLayout.LayoutParams table1Lp = (LayoutParams) table1.getLayoutParams();
        table1Lp.setMargins(0, 0, 0, (int) (screenRatio * BonusConstans.MINE_TABLE_MARGIN_BOTTOM));
        table1Lp.height = (int) (screenRatio * BonusConstans.MINE_TABLE_HEIGHT);
    }

    private void setTableSize2(@IdRes int tableLabel, @IdRes int table){

        //背景2
        View tab2 = findViewById(R.id.bonus_country_info_two);
        LinearLayout.LayoutParams tab2Lp = (LinearLayout.LayoutParams) tab2.getLayoutParams();
        tab2Lp.height = (int) (screenRatio * BonusConstans.TABLE_BACKGROUND_HEIGHT);
        tab2Lp.setMargins(0, (int) (screenRatio * BonusConstans.COUNTRY_BACKGROUND_DIVDER_TOP_2), 0, 0);

        //设置标签2
        View label1 = findViewById(tableLabel);
        FrameLayout.LayoutParams label1Lp = (LayoutParams) label1.getLayoutParams();
        label1Lp.setMargins(0, (int) (screenRatio * BonusConstans.TABLE_LABEL_MARGIN_TOP), 0, 0);
        label1Lp.height = (int) (screenRatio * BonusConstans.TABLE_LABEL_HEIGHT);
        //设置表格2
        View table1 = findViewById(table);
        FrameLayout.LayoutParams table1Lp = (LayoutParams) table1.getLayoutParams();
        table1Lp.setMargins(0, 0, 0, (int) (screenRatio * BonusConstans.TABLE_BORDER));
        table1Lp.height = (int) (screenRatio * BonusConstans.TABLE_HEIGHT);
    }

    private String getDealString(String text, String unit){
        return TextUtils.isEmpty(text) ? "暂未统计" : text+unit;
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

    /** 刷新ui */
    public void refreshUI(BonusNationData data){
        if(data != null){
            mDateTv1.setText(String.format("截止%s统计",data.getReportDate()));
            mNationTotalMoneyTv.setText(getDoubleString(data.getConsumeTotalAmount(),"元"));
            mNationTotalPeopleTv.setText(data.getConsumePeopleNum()+"人");
            mNationGoodBusinessTv.setText(getDealString(data.getSellerNum(),"家"));
//            mNationGoodMoneyTv.setText(getDoubleString(data.getTotalBonus(),"元"));
            mDateTv2.setText(data.getReportDate()+"统计");
//            mTotalLexinTv.setText(getDealString(data.getLeMindByDay(),""));
            mTotalConsumeTv.setText(getDoubleString(data.getConsumeByDay(),"元"));
            mTotalLefenTv.setText(getDoubleString(data.getCalValue(),"元"));//当日乐心价值
//            mGoodMoneyTv.setText(getDealString(data.getBonusLeScoreByDay(),"元"));
        } else {
            mDateTv1.setText("暂未统计");
            mDateTv2.setText("暂未统计");
        }
    }


}
