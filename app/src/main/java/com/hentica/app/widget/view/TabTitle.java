package com.hentica.app.widget.view;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fiveixlg.app.customer.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Snow.ZhK on 2016/12/25.
 */

public class TabTitle extends FrameLayout implements ViewPager.OnPageChangeListener{

    private View rootView;

    private TitleGetter mTitleGetter;

    private ImageView mImgIndicator;

    private LinearLayout mTitleLayout;

    //指引图片宽度
    private int mImgWidth;

    //选中的字体大小
    private float mSizeSelected = 40;

    //正常时字体大小
    private float mSizeNormal = 40;

    private int mColorSelected = Color.BLACK;

    private int mColorNormal = Color.BLACK;

    //默认选中索引号
    private int mIndexDefaultSelected = 0;

    //指引背景图片
    private @DrawableRes int mIndicatorBg = 0;

    private @DrawableRes int mTabDivBg = 0;

    ViewPager mViewPager;

    List<TextView> mTabs = new ArrayList<>(4);

    List<ImageView> mTabDivs = new ArrayList<>(3);

    private boolean hasTabs = false;

    private long mDivd = 0;

    public interface TitleGetter{
        String[] getTitles();
    }

    /** 设置标题 */
    public void setTitleGetter(TitleGetter getter){
        this.mTitleGetter = getter;
        refreshUI();
    }

    /**
     * 设置标题文字颜色
     * @param selectedColor 选中时的颜色
     * @param normalColor 正常颜色
     */
    public void setTitleTextColor(@ColorRes int selectedColor, @ColorRes int normalColor){
        mColorSelected = getResources().getColor(selectedColor);
        mColorNormal = getResources().getColor(normalColor);
        refreshUI();
    }

    /**
     * 设置标题文字大小
     * @param selectedSize 选中时的大小
     * @param normalSize 正常大小
     */
    public void setTitleSize(@DimenRes int selectedSize, @DimenRes int normalSize){
        mSizeSelected = getResources().getDimensionPixelSize(selectedSize);
        mSizeNormal = getResources().getDimensionPixelSize(normalSize);
        refreshUI();
    }

    /**
     * 设置标题指引背景图片
     * @param bgRes
     */
    public void setIndicatorBackground(@DrawableRes int bgRes){
        mIndicatorBg = bgRes;
        refreshUI();
    }

    public void setTitleTabDivBackground(@DrawableRes int bgRes){
        mTabDivBg = bgRes;
        refreshTabDiv();
    }

    /** 绑定ViewPager */
    public void bindViewPager(ViewPager viewPager){
        this.mViewPager = viewPager;
        if(mViewPager != null){
            mViewPager.addOnPageChangeListener(this);
            mViewPager.setCurrentItem(mIndexDefaultSelected);
        }
    }


    public void setDefaultIndex(int index){
        mIndexDefaultSelected = index;
//        mTitleClickListener.onTabClick(index);
        if(mViewPager != null){
            mViewPager.setCurrentItem(mIndexDefaultSelected);
        }
        refreshUI();
    }

    public TabTitle(Context context) {
        this(context, null);
    }

    public TabTitle(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabTitle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView(){
        //引入标题布局
        rootView = View.inflate(getContext(), R.layout.common_tab_title, this);
        initIndicator();
    }

    //初始化指示标签
    private void initIndicator(){
        mImgIndicator = (ImageView) rootView.findViewById(R.id.tab_indicator);
        //设置指示背景
        mImgIndicator.setBackgroundResource(mIndicatorBg);
        final ViewGroup titleRoot = (ViewGroup) rootView.findViewById(R.id.tab_title_root);
        final ViewTreeObserver vto = titleRoot.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    titleRoot.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                //设置图片宽度
                if(mTitleGetter == null || mTitleGetter.getTitles() == null || mTitleGetter.getTitles().length == 0){
                    return;
                }
                ViewGroup.LayoutParams lp =  mImgIndicator.getLayoutParams();
                mImgWidth = titleRoot.getWidth() / mTitleGetter.getTitles().length;
                lp.width = mImgWidth;
                mImgIndicator.setLayoutParams(lp);
                mImgIndicator.setX(mIndexDefaultSelected * mImgWidth);
            }
        });
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if(mImgIndicator != null){
            mImgIndicator.setX((position + positionOffset )* mImgWidth );
        }
        //设置字体大小变化效果
        //注：以ViewPager当前currentItem=1为例
        //1：向右移动目标item=2，此时positionOffset是从0开始增加到1，position=1
        //2：向左移动目标item=0，此时positionOffset是从1开始减少到0，position=0;
        TextView mTab = (TextView) mTabs.get(position);
        int size = (int) (mSizeSelected - positionOffset * (mSizeSelected - mSizeNormal));
        if(mTab != null) {
            mTab.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        }
        if(position + 1 < mTabs.size()) {
            mTab = (TextView) mTabs.get(position + 1);
            size = (int) (mSizeNormal + positionOffset * (mSizeSelected - mSizeNormal));
            if (mTab != null) {
                mTab.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
            }
        }
    }

    @Override
    public void onPageSelected(int position) {
        TextView mTab = null;
        for(int i = 0, count = mTabs.size(); i < count ; i++){
            mTab = (TextView) mTabs.get(i);
            if( i == position){
                mTab.setTextColor(mColorSelected);
            }else{
                mTab.setTextColor(mColorNormal);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void refreshUI(){
        createTitleTabs();
        TextView mTab = null;
        for(int i = 0, count = mTabs.size(); i < count; i++){
                mTab = mTabs.get(i);
            if(i == mIndexDefaultSelected){
                mTab.setTextColor(mColorSelected);
                mTab.setTextSize(TypedValue.COMPLEX_UNIT_PX, mSizeSelected);
            }else{
                mTab.setTextColor(mColorNormal);
                mTab.setTextSize(TypedValue.COMPLEX_UNIT_PX, mSizeNormal);
            }
        }
        if(mIndicatorBg != 0){
            mImgIndicator.setBackgroundResource(mIndicatorBg);
        }
    }

    //创建标题
    private void createTitleTabs(){
        if(hasTabs){
            return;
        }
        mTitleLayout = (LinearLayout) rootView.findViewById(R.id.tab_title_layout);
        mTitleLayout.removeAllViews();
        if(mTitleGetter == null || mTitleGetter.getTitles() == null || mTitleGetter.getTitles().length == 0){
            return;
        }
        String[] titles = mTitleGetter.getTitles();
        for(int i = 0, count = titles.length; i < count; i++){
            final TextView tab = createTitleTab(titles[i], i);
            mTabs.add(tab);
            final int finalI = i;
            tab.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mViewPager != null){
                        mViewPager.setCurrentItem(mTabs.indexOf(tab));
                    }
                }
            });
            mTitleLayout.addView(tab);
            if( i < count - 1){
                ImageView div = createTabDiv();
                mTabDivs.add(div);
                mTitleLayout.addView(div);
            }
        }
        hasTabs = true;
    }

    //创建一个标题的标签
    private TextView createTitleTab(String title, int index){
        TextView tab = new TextView(getContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1);
        tab.setLayoutParams(lp);
        tab.setText(title);
        if(index == mIndexDefaultSelected){
            tab.setTextColor(mColorSelected);
            tab.setTextSize(TypedValue.COMPLEX_UNIT_PX, mSizeSelected);
        }else{
            tab.setTextColor(mColorNormal);
            tab.setTextSize(TypedValue.COMPLEX_UNIT_PX, mSizeNormal);
        }
        tab.setGravity(Gravity.CENTER);
        return tab;
    }

    //创建Tab的分隔线
    private ImageView createTabDiv(){
        ImageView div = new ImageView(getContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER_VERTICAL;
        div.setLayoutParams(lp);
        div.setBackgroundResource(mTabDivBg);
        return div;
    }

    public void setTitleText(String... titles){
        if(mTitleGetter == null ||mTitleGetter.getTitles() == null || mTitleGetter.getTitles().length == 0){
            return;
        }
        String[] oldTitls = mTitleGetter.getTitles();
        TextView mTab = null;
        for(int i = 0, count = Math.min(oldTitls.length, titles.length); i < count; i++){
            oldTitls[i] = titles[i];
            mTab = mTabs.get(i);
            mTab.setText(oldTitls[i]);
        }
    }

    private void refreshTabDiv(){
        for(ImageView tmp : mTabDivs){
            tmp.setBackgroundResource(mTabDivBg);
        }
    }
}
