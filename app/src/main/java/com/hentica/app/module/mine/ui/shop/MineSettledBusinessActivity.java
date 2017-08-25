package com.hentica.app.module.mine.ui.shop;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
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
import com.hentica.app.framework.activity.BaseCompatActivity;
import com.hentica.app.framework.data.ApplicationData;
import com.hentica.app.framework.data.Constants;
import com.hentica.app.framework.fragment.UsualFragment;
import com.hentica.app.lib.util.TextGetter;
import com.hentica.app.module.db.ConfigModel;
import com.hentica.app.module.entity.mine.shop.ResShopCategory;
import com.hentica.app.module.mine.presenter.shop.SalesAddShopPresenter;
import com.hentica.app.module.mine.presenter.shop.SalesAddShopPresenterImpl;
import com.hentica.app.module.mine.presenter.shop.ShopCategoryPresenterImpl;
import com.hentica.app.module.mine.ui.adapter.PhotoAdapter;
import com.hentica.app.module.mine.view.shop.SalesAddShopView;
import com.hentica.app.module.mine.view.shop.ShopCategoryView;
import com.hentica.app.module.mine.view.shop.ShopSettleView;
import com.hentica.app.util.BitmapCompress;
import com.hentica.app.util.FragmentJumpUtil;
import com.hentica.app.util.TextWatcherAdapter;
import com.hentica.app.util.baidumap.GeoCodeUtils;
import com.hentica.app.util.baidumap.LocationUtils;
import com.hentica.app.util.event.DataEvent;
import com.hentica.app.util.region.Region;
import com.hentica.app.widget.dialog.SelfAlertDialog;
import com.hentica.app.widget.dialog.SelfAlertDialogHelper;
import com.hentica.app.widget.dialog.WheelDialogHelper;
import com.hentica.app.widget.photo.MakePhotoDialog2;
import com.hentica.app.widget.photo.MakePhotoListener;
import com.hentica.app.widget.view.TitleView;
import com.hentica.app.widget.view.lineview.LineViewEdit;
import com.hentica.app.widget.view.lineview.LineViewHelper;
import com.hentica.app.widget.view.lineview.LineViewText;
import com.hentica.app.widget.wheel.TakeAddrWheelDialog;
import com.hentica.appbase.famework.util.ListUtils;
import com.fiveixlg.app.customer.R;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 入驻商户界面
 *
 * @author
 * @createTime 2017-03-23 下午15:13:27
 */
public class MineSettledBusinessActivity extends BaseCompatActivity implements ShopSettleView, ShopCategoryView ,
    OnGetGeoCoderResultListener, OnGetSuggestionResultListener, SalesAddShopView{

    public static final String DATE_APPLY_ID = "applyId";
    public static final String DATE_MOBILE = "mobile";

    private String mShopLogoPath;
    private String mShopLicensePath;
    private PhotoAdapter mEnvAdapter; //环境照片apdater
    private PhotoAdapter mCommitmentAdapter; //环境照片apdater

    private String applyId;
    private String mMobile;
    private final int mDescLength = 500;

    private SalesAddShopPresenter mSettlePresenter;

    private ShopCategoryPresenterImpl mCategoryPresenter;
    /**
     * 店铺行业类型数据
     */
    private List<ResShopCategory> mShopCategoryDatas;
    /**
     * 选中行业
     */
    private ResShopCategory mSelectedCategory;
    /**
     * 店铺折扣数据
     */
    private List<String> mShopDiscountDatas;
    /**
     * 选中店铺折扣数据
     */
    private String mSelectedDiscount;

    //所在地
    private Region mRegionPro;//省
    private Region mRegionCity;//市
    private Region mRegionDis;//区
    //百度地图定位
    private BDLocation mBDLocation;

    private double latitude = 1000;//经度
    private double longitude = 1000;//纬度
    private GeoCodeUtils codeUtils;

    private SuggestionSearch mSuggestSearch;

    private String mBindMemberMsg = "";

    private boolean isParseLocation = false;

    @Override
    public int getLayoutId() {
        return R.layout.mine_settled_business_fragment;
    }

    /**
     * 判断是否已修改数据
     * @return
     */
    private boolean hasChanged(){
        if(!TextUtils.isEmpty(getLogoPath()) ||
                !TextUtils.isEmpty(getPhone()) ||
                !TextUtils.isEmpty(getShopName()) ||
                !TextUtils.isEmpty(getCategoryId()) ||
                !TextUtils.isEmpty(getDiscount()) ||
                !TextUtils.isEmpty(getShopPhone()) ||
                !TextUtils.isEmpty(getAreaId()) ||
                !TextUtils.isEmpty(getAddress()) ||
                !TextUtils.isEmpty(getLicenseNumber()) ||
                !TextUtils.isEmpty(getLicensePhotoPath()) ||
                !ListUtils.isEmpty(getShopEnvPhotosPath()) ||
                !ListUtils.isEmpty(getCommitmentPhotosPath()) ||
                !TextUtils.isEmpty(getShopDescripte())
                ){
            return true;
        }
        return false;
    }



    @Override
    protected void initData() {
        super.initData();
        applyId = getIntent().getStringExtra(DATE_APPLY_ID);
        mMobile = getIntent().getStringExtra(DATE_MOBILE);

        codeUtils = GeoCodeUtils.newInstance(this);
        mSettlePresenter = new SalesAddShopPresenterImpl(this);
        mCategoryPresenter = new ShopCategoryPresenterImpl(this);
        mCategoryPresenter.getGategory();
        mShopDiscountDatas = getShopDiscountDatas();
        if (LocationUtils.getInstance() == null || LocationUtils.getInstance().getLocation() == null) {
            LocationUtils.newInstance(this, new LocationUtils.LocationCallBack() {
                @Override
                public boolean callBack(BDLocation location) {
                    mBDLocation = location;
                    return true;
                }
            });
        }else{
            mBDLocation = LocationUtils.getInstance().getLocation();
        }
        mSuggestSearch = SuggestionSearch.newInstance();
        mSuggestSearch.setOnGetSuggestionResultListener(this);
    }

    private List<String> getShopDiscountDatas() {
        List<String> result = new ArrayList<>();
        for (int i = 19; i >= 0; i--) {
            result.add(String.format("%.1f", i * 0.1f + 8));
        }
        return result;
    }

    @Override
    protected void initView() {
        super.initView();
        TitleView title = getViews(R.id.common_title);
        title.getLeftImageBtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hasChanged()){
                    SelfAlertDialogHelper.showDialog(getSupportFragmentManager(), getString(R.string.alert_dialog_tips), new View.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            finish();
                        }
                    });
                }else{
                    finish();
                }
            }
        });
        // 承诺书照片
        TextView tvTvCommitmentTitle = getViews(R.id.commitment_photos, R.id.tv_photos_title);
        tvTvCommitmentTitle.setText("商家承诺书和业务员承诺书(至少2张)");
        GridView mCommitmentImgs = getViews(R.id.commitment_photos, R.id.photos_list);
        mCommitmentAdapter = new PhotoAdapter(this, mCommitmentImgs);
        mCommitmentAdapter.setMaxCount(4);
        mCommitmentImgs.setAdapter(mCommitmentAdapter);

        // 环境照片
        TextView tvTvEnvTitle = getViews(R.id.environment_photos, R.id.tv_photos_title);
        tvTvEnvTitle.setText("环境照片");
        GridView mShopEnvImgs = getViews(R.id.environment_photos, R.id.photos_list);//环境图片
        mEnvAdapter = new PhotoAdapter(this, mShopEnvImgs);
        mEnvAdapter.setMaxCount(4);
        mShopEnvImgs.setAdapter(mEnvAdapter);
        setShopDescriptionLength(0, mDescLength);
        //电话号码
        LineViewEdit lineViewPhone = getViews(R.id.shop_edt_phone);
        lineViewPhone.getContentTextView().setInputType(InputType.TYPE_CLASS_PHONE);
        if(!TextUtils.isEmpty(mMobile)){
            lineViewPhone.getContentTextView().setText(mMobile);
            lineViewPhone.setEnabled(false);
            lineViewPhone.setVisibility(View.GONE);

            LineViewText lineViewMobile = getViews(R.id.shop_tv_phone);
            lineViewMobile.setText(mMobile);
            lineViewMobile.setVisibility(View.VISIBLE);
        }
        LineViewEdit lineViewShopPhone = getViews(R.id.shop_edt_shop_phone);
        lineViewShopPhone.getContentTextView().setInputType(InputType.TYPE_CLASS_PHONE);
    }

    @Override
    protected void setEvent() {
        super.setEvent();
        setShopLogonEvent();
        setShopLicenseEvent();
        getViews(R.id.shop_btn_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(mBindMemberMsg)) {
                    mSettlePresenter.toSettle();
                }else{
                    showToast(mBindMemberMsg);
                }
            }
        });
        //监听简介内容长度
        final EditText edtDiscription = getViews(R.id.shop_description_tv_content);
        edtDiscription.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                setShopDescriptionLength(s.length(), mDescLength);
            }
        });
        //选择行业类型
        getViews(R.id.shop_lnv_category).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectCategory();
            }
        });
        //选择店铺折扣
        getViews(R.id.shop_lnv_discount).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDiscount();
            }
        });
        //选择所在地
        getViews(R.id.shop_lnv_location).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectLocation();
            }
        });
        //详细地址
        LineViewEdit lnvLocation = getViews(R.id.shop_location_lnv_address);
        lnvLocation.getContentTextView().addTextChangedListener(new TextWatcherAdapter(){
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                //，解析地址
                toParseLocation();
            }
        });
        // 2017/4/8 定位
        setViewClickEvent(R.id.shop_btn_location, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentJumpUtil.toLocationFragmentForResult(getActivity(), getShopName(), 1000, 1000);
                setActivityResultListener(new UsualFragment.OnActivityResultListener() {
                    @Override
                    public void onActivityResult(int requestCode, int resultCode, Intent data) {
                        if(resultCode == Activity.RESULT_OK && data != null){
                            latitude = data.getDoubleExtra(MineShopLocationFragment.RESULT_DATA_LATITUDE, latitude);
                            longitude = data.getDoubleExtra(MineShopLocationFragment.RESULT_DATA_LONGITUDE, longitude);
                            String cityName = data.getStringExtra(MineShopLocationFragment.RESULT_DATA_CITY);//获取定位城市名称
                            String districtName = data.getStringExtra(MineShopLocationFragment.RESULT_DATA_DISTRICT);//获取定位区县名称
                            // 2017/4/14 根据城市、区县，到数据库中查找相应数据
                            toFindRegion(cityName, districtName);
                            //显示地址信息
                            LineViewHelper.setValue(mQuery, R.id.shop_location_lnv_address ,data.getStringExtra(MineShopLocationFragment.RESULT_DATA_ADDRESS));
                        }
                    }
                });
            }
        });
        //手机号
        if(TextUtils.isEmpty(mMobile)) {
            final LineViewEdit lineViewPhone = getViews(R.id.shop_edt_phone);
            final EditText phoneEdt = lineViewPhone.getContentTextView();
            phoneEdt.setImeOptions(EditorInfo.IME_ACTION_DONE);
            phoneEdt.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
            phoneEdt.setInputType(EditorInfo.TYPE_CLASS_PHONE);
            phoneEdt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        mSettlePresenter.checkPhone(phoneEdt.getText().toString());
                        phoneEdt.setFocusable(false);
                        phoneEdt.setFocusableInTouchMode(false);
                    } else {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(phoneEdt, 0);
                    }
                }
            });
            phoneEdt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    phoneEdt.setFocusable(true);
                    phoneEdt.setFocusableInTouchMode(true);
                    phoneEdt.requestFocus();
                }
            });
        }
    }

    /**
     * 设置显示店铺简介内容长度
     */
    private void setShopDescriptionLength(int length, int max) {
        setViewText(String.format("%d/%d", length, max), R.id.shop_description_tv_length);
    }

    /**
     * 设置logo事件
     */
    private void setShopLogonEvent() {
        getViews(R.id.shop_img_logo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toTakePhoto(new MakePhotoListener() {
                    @Override
                    public void onComplete(List<String> imgFilePaths) {
                        if (!ListUtils.isEmpty(imgFilePaths)) {
                            //保存照片
                            mShopLogoPath = compressPhoto(imgFilePaths.get(0));
                            //显示照片
                            Glide.with(MineSettledBusinessActivity.this).load(new File(mShopLogoPath))
                                    .into((ImageView) getViews(R.id.shop_img_logo));
                            setViewVisiable(false, R.id.shop_tv_logo_tip);
                        }
                    }
                });
            }
        });
    }

    private String compressPhoto(String filePath){
        String outfile  = ApplicationData.getInstance().getTempPhotoDir() + new Date().getTime() + ".jpg";
        BitmapCompress.compress(filePath, outfile);
        return outfile;
    }

    /**
     * 拍照
     */
    private void toTakePhoto(MakePhotoListener l) {
        MakePhotoDialog2 dialog = new MakePhotoDialog2();
        dialog.setCompressConfig(200 * 1024, 1024, 1024);
        dialog.setOnMakePhotoListener(l);
        dialog.show(getSupportFragmentManager(), dialog.getClass().getSimpleName());
    }

    /**
     * 设置营业执照图片事件
     */
    private void setShopLicenseEvent() {
        getViews(R.id.shop_img_license).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toTakePhoto(new MakePhotoListener() {
                    @Override
                    public void onComplete(List<String> imgFilePaths) {
                        if (!ListUtils.isEmpty(imgFilePaths)) {
                            //保存照片
                            mShopLicensePath = compressPhoto(imgFilePaths.get(0));
                            //显示照片
                            Glide.with(MineSettledBusinessActivity.this).load(new File(mShopLicensePath))
                                    .into((ImageView) getViews(R.id.shop_img_license));
                        }
                    }
                });
            }
        });
    }

    /**
     * 选择行业分类
     */
    private void selectCategory() {
        WheelDialogHelper<ResShopCategory> helper = new WheelDialogHelper(getSupportFragmentManager());
        helper.setTextGetter(new TextGetter<ResShopCategory>() {
            @Override
            public String getText(ResShopCategory obj) {
                return obj.getCategoryName();
            }
        });
        helper.setDatas(mShopCategoryDatas);
        helper.setListener(new WheelDialogHelper.OnWheelSelectedListener<ResShopCategory>() {
            @Override
            public void onSelected(int index, String showText, ResShopCategory data) {
                mSelectedCategory = data;
            }
        });
        LineViewText lnv = getViews(R.id.shop_lnv_category);
        helper.setEventView(lnv, lnv.getContentTextView());
        lnv.performClick();
    }

    /**
     * 选择行业折扣
     */
    private void selectDiscount() {
        WheelDialogHelper<String> helper = new WheelDialogHelper(getSupportFragmentManager());
        helper.setTextGetter(new TextGetter<String>() {
            @Override
            public String getText(String obj) {
                return obj + "折";
            }
        });
        helper.setDatas(mShopDiscountDatas);
        helper.setListener(new WheelDialogHelper.OnWheelSelectedListener<String>() {
            @Override
            public void onSelected(int index, String showText, String data) {
                mSelectedDiscount = data;
            }
        });
        LineViewText lnv = getViews(R.id.shop_lnv_discount);
        helper.setEventView(lnv, lnv.getContentTextView());
        lnv.performClick();
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
        codeUtils.getGeoCode(options);
    }

    /**
     * 解析地址
     */
    private void toParseLocation() {
        //解析地址数据
        toParseLocation(getOptionCity(), getOptionAddress(getInputAddress()));
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

    /** 获取推荐地址 */
    private void getSuggestAddress(){
        SuggestionSearchOption option = new SuggestionSearchOption();
        option.city(getOptionCity());
        option.keyword(getInputAddress());
        mSuggestSearch.requestSuggestion(option);
    }

    /**
     * 查找地区数据
     * @param cityName 城市名称
     * @param districtName 区县名称
     */
    private void toFindRegion(String cityName, String districtName){
        //查找城市
        Region city = null;
        Region district = null;
        Region province = null;
        city = toFindCityByName(cityName);
        if(city != null){
            //查找区县
            district = ConfigModel.getInstace().findDistrictWithCity(city.getId(), districtName);
            //查找省份
            String parentId = city.getParent();
            if(!TextUtils.isEmpty(parentId)){
                province = ConfigModel.getInstace().getRegionById(parentId);
            }
            if(province == null){
                mRegionPro = city;
                mRegionCity = district;
                mRegionDis = null;
            }else{
                mRegionPro = province;
                mRegionCity = city;
                mRegionDis = district;
            }
            LineViewHelper.setValue(mQuery, R.id.shop_lnv_location , district.getFull_name());
        }
    }

    /**
     * 根据名称查找城市
     * @param name
     * @return
     */
    private Region toFindCityByName(String name){
        List<Region> region = ConfigModel.getInstace().queryCitysLike(name);//查找城市
        if(!ListUtils.isEmpty(region)){
            return region.get(0);
        }
        return null;
    }

    @Override
    public String getLogoPath() {
        return mShopLogoPath;
    }

    @Override
    public String getPhone() {
        return LineViewHelper.getValue(mQuery, R.id.shop_edt_phone);
    }

    @Override
    public String getShopName() {
        return LineViewHelper.getValue(mQuery, R.id.shop_edt_shop_name);
    }

    @Override
    public String getCategoryId() {
        return mSelectedCategory != null ? String.valueOf(mSelectedCategory.getId()) : null;
    }

    @Override
    public String getDiscount() {
        return mSelectedDiscount;
    }

    @Override
    public String getShopPhone() {
        return LineViewHelper.getValue(mQuery, R.id.shop_edt_shop_phone);
    }

    @Override
    public String getAreaId() {
        if (mRegionDis != null) {
            return mRegionDis.getId();
        }
        if (mRegionCity != null) {
            return mRegionCity.getId();
        }
        if (mRegionPro != null) {
            return mRegionPro.getId();
        }
        return null;
    }

    private String getInputAddress(){
        return LineViewHelper.getValue(mQuery, R.id.shop_location_lnv_address);
    }

    @Override
    public String getAddress() {
        String area = LineViewHelper.getValue(mQuery, R.id.shop_lnv_location);
        return area + getInputAddress();
    }

    @Override
    public String getLicenseNumber() {
        return LineViewHelper.getValue(mQuery, R.id.shop_edt_shop_license_number);
    }

    @Override
    public String getLicensePhotoPath() {
        return mShopLicensePath;
    }

    @Override
    public List<String> getShopEnvPhotosPath() {
        return mEnvAdapter.getImages();
    }

    @Override
    public List<String> getCommitmentPhotosPath() {
        return mCommitmentAdapter.getImages();
    }

    @Override
    public String getShopDescripte() {
        return mQuery.id(R.id.shop_description_tv_content).getText().toString();
    }

    @Override
    public String getLatitude() {
        if(latitude != 1000){
            return String.valueOf(latitude);
        }
        if (mBDLocation != null) {
            return String.valueOf(mBDLocation.getLatitude());
        }
        return String.valueOf(latitude);
    }

    @Override
    public String getLongitude() {
        if(longitude != 1000){
            return String.valueOf(longitude);
        }
        if (mBDLocation != null) {
            return String.valueOf(mBDLocation.getLongitude());
        }
        return String.valueOf(longitude);
    }

    @Override
    public String getApplyId() {
        return applyId;
    }

    @Override
    public boolean inParseLocation() {
        return isParseLocation;
    }

    @Override
    public void onSuccess() {
        //跳转入驻审核中界面，结束当前申请界面
        FragmentJumpUtil.toFragment(this, MineSettleCheckingFragment.class);
        EventBus.getDefault().post(new DataEvent.OnToUpdataUserProfileEvent());
        finish();
    }

    @Override
    public void setCategory(List<ResShopCategory> datas) {
        mShopCategoryDatas = datas;
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
        latitude = result.getLocation().latitude;
        longitude = result.getLocation().longitude;
        Log.d(TAG, "onGetReverseGeoCodeResult: \n latitude=" + latitude + "\nlongitude=" + longitude);
    }

    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {

    }

    @Override
    public void onDestroy() {
        if(codeUtils != null){
            codeUtils.destory();
            codeUtils = null;
        }
        if(mSuggestSearch != null){
            mSuggestSearch.destroy();
            mSuggestSearch = null;
        }
        super.onDestroy();
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
        if(latitude != point.latitude || longitude != point.longitude){
            latitude = point.latitude;
            longitude = point.longitude;
        }
    }

    @Override
    public void bindMember(String msg) {
        mBindMemberMsg = "";
        if(TextUtils.isEmpty(mBindMemberMsg)){
            return;
        }
        SelfAlertDialog dialog = new SelfAlertDialog();
        dialog.setText(msg);
        dialog.show(getSupportFragmentManager(), dialog.getClass().getSimpleName());
    }

    @Override
    public void cantBindMember(String msg) {
        mBindMemberMsg = msg;
        showToast(msg);
    }
}
