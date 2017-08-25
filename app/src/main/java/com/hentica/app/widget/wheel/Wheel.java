package com.hentica.app.widget.wheel;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hentica.app.lib.util.TextGetter;
import com.hentica.appbase.famework.util.ListUtils;
import com.fiveixlg.app.customer.R;

import java.util.ArrayList;
import java.util.List;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;

/**
 * Wheel分两种情况使用<br >
 * <p>1：做为主项（不被其他Wheel联动）</p>
 *      a.调用{@linkplain #setWheelDatas(List)}设置数据源<br >
 *      b.调用{@linkplain #setDefaultData(String)}设置默认选择数据，可以不设置<br >
 *      c.调用{@linkplain #setTextGetter(TextGetter)}设置数据显示规则<br >
 *      d.调用{@linkplain Wheel#invalidate()}方法生效<br >
 * <p>2：做为被联动项</p>
 *      a.调用{@linkplain #setDefaultData(String)}设置默认选择数据，可以不设置<br >
 *      b.调用{@linkplain #setTextGetter(TextGetter)}设置数据显示规则，如果没有，会设置与主轮规则相同<br >
 *      c.主轮调用{@linkplain #setLinkedWheel(Wheel, DatasGetter)}将当前轮设置为联动<br >
 * 其他：<br >
 *     a.调用{@linkplain #getSelectedDatas()}获取当前选择数据<br >
 * Created by Snow on 2017/2/13.
 */

public class Wheel<T> extends LinearLayout implements OnWheelScrollListener, OnWheelChangedListener {

    private String TAG = Wheel.class.getSimpleName();

    private View mRootView;

    private WheelView mWheelView;

    private Wheel mLinkWheel;//级联的Wheel

    private ComWheelAdapter<T> mAdapter;

    private DatasGetter<T> mDatasGetter;

    private int selectedSize = 14;
    private int unSelectedSize = 12;
    private String mDefault;

    private List<T> mDatas = new ArrayList<>();

    private boolean isFirst = true;//标记是否是第一次：只有第一次会选择“默认项”，

    private boolean holdDefault = false;//是否保持默认项选择

    private TextGetter<T> mTextGetter;//

    public void setTextGetter(TextGetter<T> getter){
        this.mTextGetter = getter;
    }

    protected TextGetter getTextGetter(){
        return this.mTextGetter;
    }

    public void holdDefault(boolean hold){
        holdDefault = hold;
    }

    /**
     * 设置级联的子Wheel
     * @param linkWheel 级联的子Wheel
     * @param listener 联动监听
     */
    public void setLinkedWheel(Wheel linkWheel, DatasGetter<T> listener){
        this.mLinkWheel = linkWheel;
        this.mDatasGetter = listener;
        if(mLinkWheel != null && mLinkWheel.getTextGetter() == null){
            mLinkWheel.setTextGetter(mTextGetter);
        }
        changeLinkWheel();
    }

    public Wheel(Context context) {
        this(context, null);
    }

    public Wheel(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Wheel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData(context);
        initView(context);
    }

    private void initData(Context context){

    }

    private void initView(Context context){
        //加载布局
        mRootView = View.inflate(context, R.layout.common_view_single_wheel_view, this);
        mWheelView = (WheelView) mRootView.findViewById(R.id.common_view_single_wheel);
        mWheelView.addScrollingListener(this);
        mWheelView.addChangingListener(this);
    }

    @Override
    public void onScrollingStarted(WheelView wheel) {
        Log.i(TAG, "onScrollingStarted");
    }

    @Override
    public void onScrollingFinished(WheelView wheel) {
        Log.i(TAG, "getDatas");
        //如果有级联的Wheel
        changeLinkWheel();
        if(mTextGetter != null) {
            mDefault = mTextGetter.getText(getSelectedDatas());
        }
    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        Log.i(TAG, "onChanged");
        //设置滚轮滚过时字体大小切换
        setViewSize(newValue);
    }

    private void setViewSize(int current){
        if(mAdapter == null){
            return;
        }
        ArrayList<View> views =  mAdapter.getTestViews();
        CharSequence currentText  = mAdapter.getItemText(current);
        for(int i = 0, count = views.size(); i < count; i++){
            View tmpView = views.get(i);
            if(tmpView instanceof TextView){
                TextView textView= (TextView) tmpView;
                if(currentText.equals(textView.getText())){
                    textView.setTextSize(selectedSize);
                }else{
                    textView.setTextSize(unSelectedSize);
                }
            }
        }
    }

    /**
     * 改变联动WheelView
     */
    private void changeLinkWheel(){
        if(mLinkWheel != null && mDatasGetter != null){
            linkWheelDatas(mDatasGetter.getDatas(getSelectedDatas()));
        }
    }

    /**
     * 通知联动WheelView改变
     */
    public void noticiChangeLinkWheel(){
        changeLinkWheel();
    }

    public T getSelectedDatas(){
        int index = mWheelView.getCurrentItem();
        return mAdapter.getItem(index);
    }

    /**
     * 设置滚轮数据
     * @param datas
     */
    public void setWheelDatas(List<T> datas){
        mDatas.clear();
        if(!ListUtils.isEmpty(datas)){
            mDatas.addAll(datas);
        }
    }

    /**
     * 设置默认数据
     * @param defaultData
     */
    public void setDefaultData(String defaultData){
        this.mDefault = defaultData;
    }

    /**
     * 设置联动WheelView数据
     * @param datas
     */
    protected void linkWheelDatas(List<T> datas){
        if(mLinkWheel != null){
            mLinkWheel.setWheelDatas(datas);
            mLinkWheel.invalidate();
            mLinkWheel.changeLinkWheel();
        }
    }

    @Override
    public void invalidate() {
        super.invalidate();
        int index = 0;
        if(isFirst){
            index = getDefaultIndex(mDefault);
            isFirst = !isFirst;
        }else{
            if(holdDefault){
                index = getDefaultIndex(mDefault);
            }
        }
        mAdapter = new ComWheelAdapter<T>(getContext(), index);
        mAdapter.setTextGetter(mTextGetter);
        mAdapter.setDatas(mDatas);
        mWheelView.setViewAdapter(mAdapter);
        mWheelView.setCurrentItem(index);
    }

    /**
     * 获取默认值索引
     * @param mDefault
     * @return
     */
    private int getDefaultIndex(String mDefault){
        int result = 0;
        if(mDefault == null){
            return result;
        }
        String value = null;
        for(int i = 0, count = mDatas.size(); i < count; i++){
            if(mTextGetter != null){
                value = mTextGetter.getText(mDatas.get(i));
            }else{
                value = mDatas.get(i).toString();
            }
            if(mDefault.equals(value) || value.startsWith(mDefault)){
                result = i;
                break;
            }
        }
        return result;
    }

    protected int getCurrentIndex(){
        return mWheelView.getCurrentItem();
    }
}
