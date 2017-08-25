package com.hentica.app.module.index;


import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import com.baidu.location.BDLocation;
import com.hentica.app.lib.util.TextGetter;
import com.hentica.app.module.common.base.BaseFragment;
import com.hentica.app.module.common.listener.ListenerAdapter;
import com.hentica.app.module.common.listener.UsualDataBackListener;
import com.hentica.app.module.db.ConfigModel;
import com.hentica.app.module.entity.index.IndexCityData;
import com.hentica.app.module.index.adapter.SlideAdapter;
import com.hentica.app.module.index.view.CityHeaderView;
import com.hentica.app.util.StorageUtil;
import com.hentica.app.util.baidumap.LocationUtils;
import com.hentica.app.util.event.DataEvent;
import com.hentica.app.util.region.Region;
import com.hentica.app.util.request.Request;
import com.hentica.app.widget.CharacterHeadHelper2;
import com.hentica.app.widget.view.SideBar;
import com.hentica.app.widget.view.TitleView;
import com.hentica.app.widget.view.ptrview.CustomPtrListView;
import com.fiveixlg.app.customer.R;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;

/**
 * 选择城市界面
 *
 * @author
 * @createTime 2017-03-23 下午15:13:27
 */
public class IndexSelectCityFragment extends BaseFragment {

    @BindView(R.id.common_title)
    TitleView mTitleView;
    @BindView(R.id.index_select_city_search_edit)
    EditText mSearchEdit;
    @BindView(R.id.index_select_city_list)
    CustomPtrListView mCityLv;
    @BindView(R.id.index_select_city_slide_bar)
    SideBar mSlideBar;

    /**
     * 城市列表适配器
     */
    private SlideAdapter mCityAdapter;
    /**
     * 排序列表辅助工具
     */
    private CharacterHeadHelper2<Region> mHeadrHelper;
    /**
     * 当前保存城市
     */
    private Region mSelectCity;
    /**
     * 列表头
     */
    private CityHeaderView mCityHeaderView;
    /**
     * 定位
     */
    private BDLocation mLocation;

    @Override
    public int getLayoutId() {
        return R.layout.index_select_city_fragment;
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
        mCityHeaderView = new CityHeaderView(getContext(), getUsualFragment());
        mSelectCity = StorageUtil.getSelectedRegion();
        mCityHeaderView.setSelectedCityId(getSelectedCityId());
        // 初始化排序辅助工具
        initHeaderHelper();
        // 异步获取所有城市数据
        toGetAllDatas();
        mLocation = LocationUtils.getInstance().getLocation();
        if (mLocation != null && mLocation.getLatitude() != 4.9E-324 && mLocation.getLongitude() != 4.9E-324) {
            mCityHeaderView.setLoaction(mLocation.getAddress().city);
            mCityHeaderView.HideNotipTv(true);
        }

        mCityAdapter = new SlideAdapter(mHeadrHelper);
    }

    /**
     * 保存当前定位城市
     */
    private void saveLocateCity(String cityName) {
        // 从数据库中查找城市信息
        Region region = ConfigModel.getInstace().getCityRegionByName(cityName);
        // 保存城市信息
        StorageUtil.saveSelectedRegion(region);
    }


    @Override
    protected void initView() {
        mCityLv.getRefreshableView().addHeaderView(mCityHeaderView);
        mCityLv.setAdapter(mCityAdapter);
        List<Region> sortedCitys = mHeadrHelper.getSortedDatas();
        mCityAdapter.setDatas(sortedCitys);
        // 请求热门城市
        requestHotCitys();
    }

    @Override
    protected void setEvent() {
        // 搜索监听
        mSearchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                textChange(s);
            }

            public void textChange(CharSequence c) {
                if (TextUtils.isEmpty(c)) {
                    // 获取全部城市
                    toGetAllDatas();
                } else {
                    // 搜索城市
                    queryCityByKey(c.toString());
                }
            }
        });

        // 城市列表被点击
        mCityLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Region selectedCity = (Region) view.getTag();
                // 保存城市信息到本地
                StorageUtil.saveSelectedRegion(selectedCity);
                // 发送选择城市通知
                EventBus.getDefault().post(new DataEvent.OnSelectedCityEvent());
                finish();
            }
        });

        mCityHeaderView.setLocateClicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLocation != null && mLocation.getLatitude() != 4.9E-324 && mLocation.getLongitude() != 4.9E-324) {
                    saveLocateCity(mLocation.getAddress().city);
                    // 发送选择城市通知
                    EventBus.getDefault().post(new DataEvent.OnSelectedCityEvent());
                    finish();
                } else {
                    // 重新获取百度定位
                    LocationUtils locate = LocationUtils.newInstance(getContext(), new LocationUtils.LocationCallBack() {
                        @Override
                        public boolean callBack(final BDLocation location) {
                            if (location != null && location.getLatitude() != 4.9E-324 && location.getLongitude() != 4.9E-324) {
                                mCityHeaderView.setLoaction(location.getAddress().city);
                                mCityHeaderView.HideNotipTv(true);
                            }
                            return true;
                        }
                    });
                    IndexSelectCityFragment.this.setRequestPermissionResultListener(locate);
                    locate.startPermission(getUsualFragment());
                }
            }
        });
    }

    // 初始化排序辅助工具
    private void initHeaderHelper() {
        mHeadrHelper = new CharacterHeadHelper2();
        // 绑定视图
        mHeadrHelper.bindViews(mCityLv.getRefreshableView(), mSlideBar, null);
        mHeadrHelper.setTextGetter(new TextGetter<Region>() {
            @Override
            public String getText(Region obj) {
                return obj.getName();
            }
        });

        mHeadrHelper.setLetterGetter(new CharacterHeadHelper2.LetterGetter<Region>() {
            @Override
            public char getLetter(Region obj) {
                String pyName = obj.getPy_name();
                // 返回拼音首字母大写
                return !TextUtils.isEmpty(pyName) ? pyName.toUpperCase().charAt(0) : '#';
            }

            @Override
            public String getPinyin(Region obj) {
                String pyName = obj.getPy_name();
                return !TextUtils.isEmpty(pyName) ? pyName : "";
            }
        });
    }

    /**
     * 获取选中城市id
     */
    private String getSelectedCityId() {
        return mSelectCity == null ? "" : mSelectCity.getId();
    }

    /**
     * 获取异步AsyncTask获取全部数据
     */
    private void toGetAllDatas() {

        showLoadingDialog();
        AsyncTask<String, String, List<Region>> task = new AsyncTask<String, String, List<Region>>() {

            @Override
            protected List<Region> doInBackground(String... params) {
                List<Region> result = getCitys();
                mHeadrHelper.setDatas(result);
                return mHeadrHelper.getSortedDatas();
            }

            @Override
            protected void onPostExecute(List<Region> ts) {
                super.onPostExecute(ts);
                mCityAdapter.setDatas(ts);
                dismissLoadingDialog();
            }
        };
        task.execute("");
    }

    /**
     * 异步搜索城市
     */
    private void queryCityByKey(final String key) {
        AsyncTask<String, String, List<Region>> task = new AsyncTask<String, String, List<Region>>() {

            @Override
            protected List<Region> doInBackground(String... params) {
                List<Region> result = ConfigModel.getInstace().queryCitysLike(key);
                mHeadrHelper.setDatas(result);
                return mHeadrHelper.getSortedDatas();
            }

            @Override
            protected void onPostExecute(List<Region> ts) {
                super.onPostExecute(ts);
                mCityAdapter.setDatas(ts);
            }
        };
        task.execute("searchCity");
    }

    /**
     * 请求热门城市
     */
    private void requestHotCitys() {
        Request.getAreaGetHotCity(
                ListenerAdapter.createArrayListener(IndexCityData.class, new UsualDataBackListener<List<IndexCityData>>(this) {
                    @Override
                    protected void onComplete(boolean isSuccess, List<IndexCityData> data) {
                        if (isSuccess) {
                            // 请求成功
                            mCityHeaderView.setHotCitys(data);
                        }
                    }
                }));
    }

    /**
     * 获取城市信息
     */
    private List<Region> getCitys() {
        return ConfigModel.getInstace().getAllCitys();
    }

}
