package com.hentica.app.module.index;

import android.content.Context;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.hentica.app.module.common.base.BaseFragment;
import com.hentica.app.module.entity.SearchHisData;
import com.hentica.app.util.FragmentJumpUtil;
import com.hentica.app.util.ParseUtil;
import com.hentica.app.util.StorageUtil;
import com.hentica.app.util.StringUtil;
import com.hentica.app.util.ViewUtil;
import com.hentica.app.util.event.UiEvent;
import com.hentica.app.widget.dialog.SelfAlertDialog;
import com.hentica.app.widget.view.TitleView;
import com.hentica.app.widget.view.ptrview.CustomPtrListView;
import com.hentica.appbase.famework.adapter.QuickAdapter;
import com.fiveixlg.app.customer.R;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;

/**
 * 搜索历史界面
 *
 * @author
 * @createTime 2017-03-28 下午14:23:27
 */
public class IndexSearchHisFragment extends BaseFragment {

    @BindView(R.id.title_status_bar_view)
    View mStatusBar;
    @BindView(R.id.index_search_his_back_btn)
    ImageButton mBackBtn;
    @BindView(R.id.index_search_his_search_edit)
    EditText mSearchEdit;
    @BindView(R.id.index_search_his_title)
    TextView mSearchTitle;
    @BindView(R.id.index_search_his_list)
    CustomPtrListView mSearchHisListView;
    @BindView(R.id.index_search_his_clear_btn)
    TextView mClearBtn;

    private SearchAdapter mSearchAdapter;

    /** 当前要搜索的内容 */
    private SearchHisData mCurrSearchData = new SearchHisData();

    /**
     * 历史记录条数
     */
    private int mHistorySize = 10;

    @Override
    public int getLayoutId() {
        return R.layout.index_search_his_fragment;
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
    protected void initData() {
        mSearchAdapter = new SearchAdapter();
    }

    @Override
    protected void initView() {
        mSearchHisListView.setAdapter(mSearchAdapter);
        ViewUtil.setKeepStatusBar(getView(),mStatusBar,getContext(),true);
        refreshHistoryList();
    }

    @Override
    protected void setEvent() {

        // 返回按钮，被点击
        mBackBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 键盘搜索按钮，被点击
        mSearchEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // 先隐藏键盘
                    ((InputMethodManager) mSearchEdit.getContext()
                            .getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    // 记录此次搜索，跳转到搜索结果界面
                    String keyWords = mSearchEdit.getText().toString();
                    if(!TextUtils.isEmpty(keyWords)){
                        // 封装搜索数据
                        fillSearchData();
                        // 保存搜索记录
                        addHistory(mCurrSearchData);
                        // 跳转到搜索结果界面
                        jumpToSearchResult(keyWords);
                        EventBus.getDefault().post(new UiEvent.JumpToSearchResultEvent());
                        finish();
                    }else{
                        showToast("请输入搜索关键字！");
                    }
                    return true;
                }
                return false;
            }
        });

        // 清除搜索历史
        mClearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelfAlertDialog dialog = new SelfAlertDialog();
                dialog.setText("确定要删除搜索记录吗？");
                dialog.setSureClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 清除搜索历史
                        clearSearchHistoryList();
                        // 刷新历史记录列表
                        refreshHistoryList();
                    }
                });
                dialog.show(getChildFragmentManager(),"search");
            }
        });

        // 历史记录被点击
        mSearchHisListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SearchHisData data = (SearchHisData) view.getTag();
                if(data != null){
                    // 保存搜索记录
                    addHistory(data);
                    // 跳转到搜索结果
                    jumpToSearchResult(data.getKeyWords());
                    EventBus.getDefault().post(new UiEvent.JumpToSearchResultEvent());
                    finish();
                }
            }
        });
    }

    /** 刷新界面 */
    private void refreshHistoryList(){
        List<SearchHisData> historyDataList = getSearchListData();
        mSearchAdapter.setDatas(historyDataList);
        // 是否有历史记录
        boolean hasHistoryData = historyDataList.size() > 0;
        mSearchTitle.setVisibility(hasHistoryData ? View.VISIBLE : View.GONE);
        mClearBtn.setVisibility(hasHistoryData ? View.VISIBLE : View.GONE);
    }

    /** 封装搜索数据 */
    private void fillSearchData(){
        String keyWords = mSearchEdit.getText().toString();
        mCurrSearchData.setKeyWords(keyWords);
        mCurrSearchData.setDate(StringUtil.getNowDateStr());
    }

    /**
     * 添加一条历史记录
     */
    private void addHistory(SearchHisData data) {

        // 深复制
        data = ParseUtil.parseObject(ParseUtil.toJsonString(data), SearchHisData.class);

        // 添加到第一条
        List<SearchHisData> historyDatas = mSearchAdapter.getDatas();
        historyDatas.add(0, data);

        // 去掉重复数据
        List<SearchHisData> noRepeatdatas = new ArrayList<SearchHisData>();
        Set<String> historyTags = new HashSet<String>();
        for (SearchHisData historyData : historyDatas) {

            String tag = this.getHistoryTag(historyData);
            if (!historyTags.contains(tag)) {

                historyTags.add(tag);
                noRepeatdatas.add(historyData);
            }
        }

        // 移除多余记录
        while (noRepeatdatas.size() > mHistorySize) {
            noRepeatdatas.remove(noRepeatdatas.size() - 1);
        }
        saveSearchListData(noRepeatdatas);

        // 添加到显示
        mSearchAdapter.setDatas(noRepeatdatas);
    }

    /**
     * 取得搜索内容唯一tag
     */
    private String getHistoryTag(SearchHisData data) {

        if (data != null) {

            // 算法不是很准确，但是够用了
            return String.format("__%s__%s__", data.getKeyWords(),data.getDate());
        }
        return "";
    }

    /** 搜索列表适配器 */
    private class SearchAdapter extends QuickAdapter<SearchHisData>{

        @Override
        protected int getLayoutRes(int type) {
            return R.layout.index_search_his_item;
        }

        @Override
        protected void fillView(int position, View convertView, ViewGroup parent, SearchHisData data) {
            AQuery query = new AQuery(convertView);
            query.id(R.id.index_search_his_key_word).text(data.getKeyWords());
            query.id(R.id.index_search_his_date).text(data.getDate());
            convertView.setTag(data);
        }
    }


    /** 获取搜索历史 */
    protected List<SearchHisData> getSearchListData(){
        return StorageUtil.getSearchHistoryDatas();
    }

    /** 保存搜索历史 */
    protected void saveSearchListData(List<SearchHisData> datas){
        StorageUtil.saveSearchHouseHistory(datas);
    }

    /** 清除搜索历史 */
    protected void clearSearchHistoryList(){
        StorageUtil.saveSearchHouseHistory(null);
    }

    /** 跳转到搜索结果界面 */
    protected void jumpToSearchResult(String keyWords){
        // 跳转到搜索结果界面
        FragmentJumpUtil.toBusinessList(getUsualFragment(),keyWords,"");
    }

}
