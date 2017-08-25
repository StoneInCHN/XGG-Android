package com.hentica.app.module.mine.ui.shop;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.hentica.app.framework.activity.BaseCompatActivity;
import com.hentica.app.framework.data.ApplicationData;
import com.hentica.app.framework.data.Constants;
import com.hentica.app.framework.fragment.UsualFragment;
import com.hentica.app.module.db.ConfigModel;
import com.hentica.app.module.entity.mine.shop.Photo;
import com.hentica.app.module.entity.mine.shop.ResShopInfo;
import com.hentica.app.module.entity.type.FeaturedService;
import com.hentica.app.module.mine.model.ShopModel;
import com.hentica.app.module.mine.presenter.shop.DownPhotoPresenter;
import com.hentica.app.module.mine.presenter.shop.DownPhotoPresenterImpl;
import com.hentica.app.module.mine.presenter.shop.ShopInfoModifyPresenter;
import com.hentica.app.module.mine.presenter.shop.ShopInfoModifyPresenterImpl;
import com.hentica.app.module.mine.presenter.shop.ShopInfoPresenter;
import com.hentica.app.module.mine.presenter.shop.ShopInfoPresenterImpl;
import com.hentica.app.module.mine.ui.adapter.PhotoAdapter;
import com.hentica.app.module.mine.view.shop.ShopInfoModifyView;
import com.hentica.app.module.mine.view.shop.ShopInfoView;
import com.hentica.app.util.FragmentJumpUtil;
import com.hentica.app.util.IntentUtil;
import com.hentica.app.util.TextWatcherAdapter;
import com.hentica.app.util.TimeUtils;
import com.hentica.app.util.baidumap.GeoCodeUtils;
import com.hentica.app.util.event.DataEvent;
import com.hentica.app.util.region.Region;
import com.hentica.app.widget.dialog.SelfAlertDialogHelper;
import com.hentica.app.widget.photo.MakePhotoDialog2;
import com.hentica.app.widget.photo.MakePhotoListener;
import com.hentica.app.widget.view.TitleView;
import com.hentica.app.widget.view.lineview.LineViewEdit;
import com.hentica.app.widget.view.lineview.LineViewHelper;
import com.hentica.app.widget.wheel.TakeAddrWheelDialog;
import com.hentica.app.widget.wheel.TakeHMTimeHelper;
import com.hentica.appbase.famework.util.ListUtils;
import com.fiveixlg.app.customer.R;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 店铺信息界面
 *
 * @author
 * @createTime 2017-03-23 下午15:13:27
 */
public class MineShopDetailActivity extends BaseCompatActivity implements ShopInfoModifyView,
        ShopInfoView, DownPhotoView, OnGetGeoCoderResultListener, OnGetSuggestionResultListener{

    public static final String DATA_SHOP_INFO = "ResShopInfo";

    private ResShopInfo mShopInfo;

    private GridView mEnvPhotos;

    private PhotoAdapter mAdapter;

    private boolean isLogoModify = false;//标记logo是否修改

    private String mModifyLogoPath;//logo修改后的路径

    private ShopInfoModifyPresenter mModifyPresenter;//修改资料

    private ShopInfoPresenter mInfoPresenter;//获取店铺信息

    private PullToRefreshScrollView mPtrScrollView;

    private DownPhotoPresenter mDownPhotoPresenter;//下载图片

    private List<Photo> mDownPhotos;

    private GeoCodeUtils geoCodeUtils;

    //所在地
    private Region mRegionPro;//省
    private Region mRegionCity;//市
    private Region mRegionDis;//区

    private SuggestionSearch mSuggestSearch;

    private boolean isParseLocation = false;

    @Override
    public int getLayoutId() {
        return R.layout.mine_shop_detail_fragment;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 判断数据是否改变
     * @return true：数据改变，false：未改变
     */
    private boolean hasChanged(){
        //判断数据是否改变
        if(isLogoModify() || //修改logo
                !getAnverage().equals(String.format("%.1f", mShopInfo.getAvgPrice())) || //修改人均消费
                !getBusinessTime().equals(mShopInfo.getBusinessTime()) || //修改营业时间
                !getPhone().equals(mShopInfo.getStorePhone()) || //修改店铺电话
                !getName().equals(mShopInfo.getName()) || //修改店铺名称
                !getAddress().equals(mShopInfo.getAddress()) ||//修改地址
                hasServiceChanged() || //修改服务
                isEnvironmentPhotoModify() || //修改环境照片
                !getDescription().equals(mShopInfo.getDescription())//修改简介
                ){
            return true;
        }
        return false;
    }

    /**
     * 判断服务是否改变
     * @return
     */
    private boolean hasServiceChanged(){
        if(TextUtils.isEmpty(getService()) && TextUtils.isEmpty(mShopInfo.getFeaturedService())){
            return false;
        }
        return !getService().equals(mShopInfo.getFeaturedService());
    }

    @Override
    protected void initData() {
        super.initData();
        mShopInfo = new IntentUtil(getIntent()).getObject(DATA_SHOP_INFO, ResShopInfo.class);
        mModifyPresenter = new ShopInfoModifyPresenterImpl(this);
        mInfoPresenter = new ShopInfoPresenterImpl(this);
        mDownPhotoPresenter = new DownPhotoPresenterImpl(this, this);
        geoCodeUtils = GeoCodeUtils.newInstance(this);
        mSuggestSearch = SuggestionSearch.newInstance();
        mSuggestSearch.setOnGetSuggestionResultListener(this);
    }

    @Override
    protected void initView() {
        super.initView();
        TitleView title = (TitleView) findViewById(R.id.common_title);
        title.getLeftImageBtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!hasChanged()){
                    finish();
                }else{
                    //有修改
                    SelfAlertDialogHelper.showDialog(getSupportFragmentManager(), getString(R.string.alert_dialog_tips), new View.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            finish();
                        }
                    });
                }
            }
        });
        mPtrScrollView = getViews(R.id.shop_ptr_scrollview);
        mEnvPhotos = getViews(R.id.photos_list);
        mAdapter = new PhotoAdapter(this, mEnvPhotos);
        mAdapter.setMaxCount(4);
        mEnvPhotos.setAdapter(mAdapter);
        refreshUI(mShopInfo);
        //禁止修改地址
        getViews(R.id.shop_lnv_location).setEnabled(false);
        getViews(R.id.shop_location_lnv_address).setEnabled(false);
        getViews(R.id.shop_btn_location).setEnabled(false);
        getViews(R.id.shop_btn_location).setVisibility(View.GONE);
    }

    @Override
    protected void setEvent() {
        super.setEvent();
        mPtrScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                isLogoModify = false;
                mInfoPresenter.getShopInfo();
            }
        });
        //更换logo
        setViewOnClickListener(R.id.shop_layout_logo_item, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MakePhotoDialog2 dialog = new MakePhotoDialog2();
                dialog.setCompressConfig(200 * 1024, 1024, 1024);
                dialog.setOnMakePhotoListener(new MakePhotoListener() {
                    @Override
                    public void onComplete(List<String> imgFilePaths) {
                        if (!ListUtils.isEmpty(imgFilePaths)) {
                            showLogoPhoto(imgFilePaths.get(0));
                        }
                    }
                });
                dialog.show(getSupportFragmentManager(), dialog.getClass().getSimpleName());
            }
        });
        EditText edtContent = getViews(R.id.shop_description_tv_content);
        edtContent.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                setViewText(String.format("%d/500", s.length()), R.id.shop_description_tv_length);
            }
        });
        //提交修改
        setViewOnClickListener(R.id.shop_btn_submit, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mModifyPresenter.toModify();
            }
        });

        //定位
        setViewClickEvent(R.id.shop_btn_location, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentJumpUtil.toLocationFragmentForResult(MineShopDetailActivity.this,
                        mShopInfo.getName(), 1000, 1000);
                setActivityResultListener(new UsualFragment.OnActivityResultListener() {
                    @Override
                    public void onActivityResult(int requestCode, int resultCode, Intent data) {
                        if (resultCode == Activity.RESULT_OK && data != null) {
                            mShopInfo.setLatitude(data.getDoubleExtra(MineShopLocationFragment.RESULT_DATA_LATITUDE, mShopInfo.getLatitude()));
                            mShopInfo.setLongitude(data.getDoubleExtra(MineShopLocationFragment.RESULT_DATA_LONGITUDE, mShopInfo.getLongitude()));
                            String cityName = data.getStringExtra(MineShopLocationFragment.RESULT_DATA_CITY);//获取定位城市名称
                            String districtName = data.getStringExtra(MineShopLocationFragment.RESULT_DATA_DISTRICT);//获取定位区县名称
                            // 2017/4/14 根据城市、区县，到数据库中查找相应数据
                            toFindRegion(cityName, districtName);
                            //显示地址信息
                            LineViewHelper.setValue(mQuery, R.id.shop_location_lnv_address, data.getStringExtra(MineShopLocationFragment.RESULT_DATA_ADDRESS));
                        }
                    }
                });
            }
        });

        //地址修改完成后，根据地址获取经纬度
        LineViewEdit lnvLocation = getViews(R.id.shop_location_lnv_address);
        lnvLocation.getContentTextView().addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                //失去焦点，解析地址
                toParseLocation();
            }
        });

        //选择所在地
        getViews(R.id.shop_lnv_location).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectLocation();
            }
        });
    }

    private void showLogoPhoto(final String photo){
        final ImageView logo = getViews(R.id.shop_img_logo);
        Glide.with(this)
                .load(new File(photo)).asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                                            Log.d(TAG, "onResourceReady: ");
                        mModifyLogoPath = photo;
                        isLogoModify = true;
                        logo.setImageBitmap(resource);
                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        super.onLoadFailed(e, errorDrawable);
//                                            Log.d(TAG, "onLoadFailed: ");
                        showToast("图片获取失败");
                        if (TextUtils.isEmpty(mModifyLogoPath)) {
                            isLogoModify = false;
                        }
                    }
                });
    }


    /**
     * 解析地址
     * @param city 城市
     * @param address 地址
     */
    private void toParseLocation(String city, String address){
        GeoCodeOption options = new GeoCodeOption();
        //城市
        options.city(city);
        options.address(address);
        //解析地址数据
        isParseLocation = true;
        geoCodeUtils.getGeoCode(options);
    }

    /**
     * 解析地址
     */
    private void toParseLocation() {
        //解析地址数据
        toParseLocation(getOptionCity(), getOptionAddress(getInputAddress()));
    }


    /** 获取推荐地址 */
    private void getSuggestAddress(){
        SuggestionSearchOption option = new SuggestionSearchOption();
        option.city(getOptionCity());
        option.keyword(getInputAddress());
        mSuggestSearch.requestSuggestion(option);
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    /**
     * 选择营业开始时间
     */
    private void selectStartBusinessTime() {
        TextView tvStartTime = getViews(R.id.shop_tv_time_start);
        new TakeHMTimeHelper(getSupportFragmentManager(), tvStartTime.getText().toString())
                .bindView(tvStartTime, tvStartTime).show();
    }

    /**
     * 选择营业结束时间
     */
    private void selectEndBusinessTime() {
        TextView tvEndTime = getViews(R.id.shop_tv_time_end);
        new TakeHMTimeHelper(getSupportFragmentManager(), tvEndTime.getText().toString())
                .bindView(tvEndTime, tvEndTime).show();
    }

    /**
     * 选择城市
     */
    private void selectLocation() {
        //选择城市
        TakeAddrWheelDialog dialog = new TakeAddrWheelDialog();
        dialog.setDefaultDatas(getRegionName(mRegionPro), getRegionName(mRegionCity), getRegionName(mRegionDis));
        dialog.setOnSelectedCompleteListener(new TakeAddrWheelDialog.OnSelectedComplete() {
            @Override
            public void selectedDatas(Region value1, Region value2, Region value3) {
                mRegionPro = value1;
                mRegionCity = value2;
                mRegionDis = value3;
                StringBuilder sBuilder = new StringBuilder();
                if (mRegionDis != null) {
                    sBuilder.append(mRegionDis.getFull_name());
                } else if (mRegionCity != null) {
                    sBuilder.append(mRegionCity.getFull_name());
                } else if (mRegionPro != null) {
                    sBuilder.append(mRegionPro.getFull_name());
                }
                LineViewHelper.setValue(mQuery, R.id.shop_lnv_location, sBuilder.toString());
                //解析地址
                toParseLocation();
            }
        });
        dialog.show(getSupportFragmentManager(), dialog.getClass().getSimpleName());
    }

    /**
     * 获取地区名称
     */
    private String getRegionName(Region region) {
        if (region == null) {
            return "";
        }
        return region.getName();
    }

    /**
     * 查找地区数据
     *
     * @param cityName     城市名称
     * @param districtName 区县名称
     */
    private void toFindRegion(String cityName, String districtName) {
        //查找城市
        Region city = null;
        Region district = null;
        Region province = null;
        city = toFindCityByName(cityName);
        if (city != null) {
            //查找区县
            district = ConfigModel.getInstace().findDistrictWithCity(city.getId(), districtName);
            //查找省份
            String parentId = city.getParent();
            if (!TextUtils.isEmpty(parentId)) {
                province = ConfigModel.getInstace().getRegionById(parentId);
            }
            if (province == null) {
                mRegionPro = city;
                mRegionCity = district;
                mRegionDis = null;
            } else {
                mRegionPro = province;
                mRegionCity = city;
                mRegionDis = district;
            }
            LineViewHelper.setValue(mQuery, R.id.shop_lnv_location, district.getFull_name());
        }
    }

    /**
     * 根据名称查找城市
     *
     * @param name
     * @return
     */
    private Region toFindCityByName(String name) {
        List<Region> region = ConfigModel.getInstace().queryCitysLike(name);//查找城市
        if (!ListUtils.isEmpty(region)) {
            return region.get(0);
        }
        return null;
    }

    /**
     * 显示数据
     *
     * @param shopInfo
     */
    private void refreshUI(ResShopInfo shopInfo) {
        if (shopInfo != null) {
            //显示logo
            Glide.with(this)
                    .load(ApplicationData.getInstance().getImageUrl(shopInfo.getStorePictureUrl()))
                    .override(400, 400)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .error(R.drawable.rebate_mine_shop)
                    .into((ImageView) getViews(R.id.shop_img_logo));
            //折扣
            LineViewHelper.setValue(mQuery, R.id.shop_lnv_discount, String.format("%.1f折", mShopInfo.getDiscount()));
            LineViewHelper.setValue(mQuery, R.id.shop_lnv_average, String.format("%.1f", mShopInfo.getAvgPrice()));
            //显示 营业时段
            String businessTime = mShopInfo.getBusinessTime();
            if (TextUtils.isEmpty(businessTime)) {
                businessTime = "09:00-22:00";//默认时间
            }
            String[] businessTimes = TimeUtils.getTimes(businessTime);
            setViewText(businessTimes[0], R.id.shop_tv_time_start);//显示营业开始时间
            setViewText(businessTimes[1], R.id.shop_tv_time_end);//显示营业结束时间
            selectStartBusinessTime();//选择开始时间
            selectEndBusinessTime();//选择结束时间

            LineViewHelper.setValue(mQuery, R.id.shop_lnv_phone, mShopInfo.getStorePhone());//电话
            LineViewHelper.setValue(mQuery, R.id.shop_lnv_name, mShopInfo.getName());//店名
            //其他服务
            if (FeaturedService.ALL.equals(mShopInfo.getFeaturedService())) {
                //全部服务
                mQuery.id(R.id.mine_shop_service_ckb_parking).checked(true);
                mQuery.id(R.id.mine_shop_service_ckb_wifi).checked(true);
            } else if (FeaturedService.WIFI.equals(mShopInfo.getFeaturedService())) {
                //wifi
                mQuery.id(R.id.mine_shop_service_ckb_wifi).checked(true);
                mQuery.id(R.id.mine_shop_service_ckb_parking).checked(false);
            } else if (FeaturedService.FREE_PARKING.equals(mShopInfo.getFeaturedService())) {
                //免费停车
                mQuery.id(R.id.mine_shop_service_ckb_parking).checked(true);
                mQuery.id(R.id.mine_shop_service_ckb_wifi).checked(false);
            } else {
                mQuery.id(R.id.mine_shop_service_ckb_parking).checked(false);
                mQuery.id(R.id.mine_shop_service_ckb_wifi).checked(false);
            }
            List<String> envImages = wrapEnvImagesData(mShopInfo.getEnvImgs());
            mAdapter.setImages(envImages);//环境照片
            downEnvironmentPhotos(envImages);//下载环境照片
            //简介
            setViewText(mShopInfo.getDescription(), R.id.shop_description_tv_content);//简介
            // TODO: 2017/4/14
            // 根据area_id显示所在地区
            String area_id = shopInfo.getArea().getIdX();
            findShopRegionInfo(area_id);
            String address = mShopInfo.getAddress();
            Region region = ConfigModel.getInstace().getRegionById(area_id);
            if (region != null) {
                LineViewHelper.setValue(mQuery, R.id.shop_lnv_location, region.getFull_name());
                //从详细地址中截取地区信息
                address = address.replace(region.getFull_name(), "");
            }
            //地址
            LineViewHelper.setValue(mQuery, R.id.shop_location_lnv_address, address);//详细地址
        }
    }

    /**
     * 查找店铺地区信息
     *
     * @param id
     */
    private void findShopRegionInfo(String id) {
        ConfigModel model = ConfigModel.getInstace();
        mRegionDis = model.getRegionById(id);//查找区县
        mRegionCity = model.getRegionById(mRegionDis.getParent());//查找市
        if (!TextUtils.isEmpty(mRegionCity.getParent())) {
            mRegionPro = model.getRegionById(mRegionCity.getParent());//查找省
        } else {
            mRegionPro = mRegionCity;
            mRegionCity = mRegionDis;
            mRegionDis = null;
        }
    }

    /**
     * 下载环境照片
     *
     * @param images
     */
    private void downEnvironmentPhotos(List<String> images) {
        mDownPhotoPresenter.downImages(images);
    }

    /**
     * 处理环境照片url
     *
     * @param envImages
     * @return
     */
    private List<String> wrapEnvImagesData(List<String> envImages) {
        List<String> result = new ArrayList<>();
        if (!ListUtils.isEmpty(envImages)) {
            for (String env : envImages) {
                result.add(ApplicationData.getInstance().getImageUrl(env));
            }
        }
        return result;
    }

    @Override
    public void dismissLoadingDialog() {
        super.dismissLoadingDialog();
        mPtrScrollView.onRefreshComplete();
    }

    @Override
    public void modifySuccess() {
        showToast("操作成功");
        isLogoModify = false;
        EventBus.getDefault().post(new DataEvent.OnUpdateShopProfileEvent());
        mInfoPresenter.getShopInfo();
    }

    @Override
    public void modifyFailure() {
        isLogoModify = false;
        mInfoPresenter.getShopInfo();
    }

    @Override
    public String getSellerId() {
        return String.valueOf(mShopInfo.getId());
    }

    @Override
    public boolean isLogoModify() {
        return isLogoModify;
    }

    @Override
    public String getLogoPath() {
        return mModifyLogoPath;
    }

    @Override
    public String getAnverage() {
        return LineViewHelper.getValue(mQuery, R.id.shop_lnv_average);
    }

    @Override
    public String getBusinessTime() {
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append(mQuery.id(R.id.shop_tv_time_start).getText().toString())
                .append("-")
                .append(mQuery.id(R.id.shop_tv_time_end).getText().toString());
        return sBuilder.toString();
    }

    @Override
    public String getPhone() {
        return LineViewHelper.getValue(mQuery, R.id.shop_lnv_phone);
    }

    @Override
    public String getName() {
        return LineViewHelper.getValue(mQuery, R.id.shop_lnv_name);
    }


    private String getOptionCity(){
        if (mRegionPro != null && Constants.CONFIG_DB_IS_CITY.equals(mRegionPro.getIs_city())) {
            return mRegionPro.getName();
        }
        if(mRegionCity != null && Constants.CONFIG_DB_IS_CITY.equals(mRegionCity.getIs_city())) {
            return mRegionCity.getName();
        }
        return  "";
    }

    private String getOptionAddress(String address){
        return getAddress();
//        if(!TextUtils.isEmpty(address)) {
//            return address;
//        }
//        if(mRegionDis != null){
//            return mRegionDis.getName();
//        }
//        if(mRegionCity == null){
//            return "";
//        }
//        return mRegionCity.getName();
    }

    /** 获取输入地址 */
    private String getInputAddress(){
        return LineViewHelper.getValue(mQuery, R.id.shop_location_lnv_address);
    }

    @Override
    public String getAddress() {
//        String area = LineViewHelper.getValue(mQuery, R.id.shop_lnv_location);
//        return area + getInputAddress();
        return mShopInfo.getAddress();
    }

    @Override
    public String getService() {
        if (isWifiServiceChecked() && isParkingServiceChecked()) {
            return FeaturedService.ALL;
        }
        if (isWifiServiceChecked()) {
            return FeaturedService.WIFI;
        }
        if (isParkingServiceChecked()) {
            return FeaturedService.FREE_PARKING;
        }
        return "";
    }

    @Override
    public boolean isEnvironmentPhotoModify() {
        if (mAdapter.getImages().size() != mShopInfo.getEnvImgs().size()) {
            //照片数量不相同
            return true;
        } else {
            //相同，判断内容是否相同
            for (String oldUrl : mShopInfo.getEnvImgs()) {
                if (!mAdapter.getImages().contains(ApplicationData.getInstance().getImageUrl(oldUrl))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * wifi服务是否选中
     *
     * @return
     */
    private boolean isWifiServiceChecked() {
        return mQuery.id(R.id.mine_shop_service_ckb_wifi).isChecked();
    }

    /**
     * 停车服务是否选中
     *
     * @return
     */
    private boolean isParkingServiceChecked() {
        return mQuery.id(R.id.mine_shop_service_ckb_parking).isChecked();
    }

    @Override
    public List<String> getEnvironment() {
        List<String> mNewPhotos = mAdapter.getImages();//新照片
        List<String> result = new ArrayList<>();//
        List<String> mOldPhotos = wrapEnvImagesData(mShopInfo.getEnvImgs());//旧照片
        for (String newPhoto : mNewPhotos) {
            if (mOldPhotos.contains(newPhoto)) {
                //从下载数据中获取照片本地存储路径
                for (Photo photo : mDownPhotos) {
                    if (newPhoto.equals(photo.getUrl())) {
                        //判断url是否相同
                        result.add(photo.getFilePath());
                        break;
                    }
                }
            } else {
                result.add(newPhoto);
            }
        }
        return result;
    }

    @Override
    public String getDescription() {
        return mQuery.id(R.id.shop_description_tv_content).getText().toString();
    }

    @Override
    public String getLatitude() {
        return String.valueOf(mShopInfo.getLatitude());
    }

    @Override
    public String getLongitude() {
        return String.valueOf(mShopInfo.getLongitude());
    }

    @Override
    public String getAreaId() {

//        if (mRegionDis != null) {
//            return mRegionDis.getId();
//        }
//        if (mRegionCity != null) {
//            return mRegionCity.getId();
//        }
//        if (mRegionPro != null) {
//            return mRegionPro.getId();
//        }
        // 2017/7/11 需求变动地址不可修改
        return null;
    }

    @Override
    public boolean inParseLocation() {
        return isParseLocation;
    }

    @Override
    public void setShopInfoData(ResShopInfo data) {
        mShopInfo = data;
        ShopModel.getInstance().saveShopInfo(mShopInfo);
        refreshUI(mShopInfo);
    }

    @Override
    public void failure() {
        finish();
    }

    @Override
    public void photoDownloadComplete(List<Photo> photos) {
        mDownPhotos = photos;
    }

    @Override
    public void onGetGeoCodeResult(GeoCodeResult result) {
//        getSuggestAddress();
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            if(!TextUtils.isEmpty(getOptionCity())){
                toParseLocation(getOptionCity(), getOptionAddress(""));
            }else{
                isParseLocation = false;
            }
            return;
        }
        isParseLocation = false;
        LatLng latLng = result.getLocation();
        if (latLng != null) {
            mShopInfo.setLatitude(latLng.latitude);
            mShopInfo.setLongitude(latLng.longitude);
        }
    }

    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            return;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (geoCodeUtils != null) {
            geoCodeUtils.destory();
            geoCodeUtils = null;
        }
    }

    @Override
    public void onGetSuggestionResult(SuggestionResult result) {
        if(result == null || result.getAllSuggestions() == null){
            return;
        }
        LatLng point = result.getAllSuggestions().get(0).pt;
        if(point == null){
            return;
        }
        if((mShopInfo.getLatitude() != point.latitude || mShopInfo.getLongitude() != point.longitude)){
            mShopInfo.setLatitude(point.latitude);
            mShopInfo.setLongitude(point.longitude);
        }
//        LogUtils.i(TAG, "onGetSuggestionResult: " + result.getAllSuggestions().get(0));
    }
}
