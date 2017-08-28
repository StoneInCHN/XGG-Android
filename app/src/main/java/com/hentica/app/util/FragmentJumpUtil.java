package com.hentica.app.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.inner.GeoPoint;
import com.baidu.mapapi.utils.route.BaiduMapRoutePlan;
import com.baidu.mapapi.utils.route.RouteParaOption;
import com.google.gson.Gson;
import com.hentica.app.framework.activity.FrameActivity;
import com.hentica.app.framework.data.ApplicationData;
import com.hentica.app.framework.data.Constants;
import com.hentica.app.framework.fragment.UsualFragment;
import com.hentica.app.module.common.ui.CommonWebFragment;
import com.hentica.app.module.common.ui.ImageGallery;
import com.hentica.app.module.entity.index.IndexBusinessDetailData;
import com.hentica.app.module.entity.index.IndexNotifyListData;
import com.hentica.app.module.entity.mine.ResMineOrderListData;
import com.hentica.app.module.entity.mine.shop.ResShopInfo;
import com.hentica.app.module.entity.mine.shop.ResShopOrderItem;
import com.hentica.app.module.index.IndexAllCommentFragment;
import com.hentica.app.module.index.IndexBusinessDetailFragment;
import com.hentica.app.module.index.IndexBusinessListFragment;
import com.hentica.app.module.index.IndexNotifyDetailFragment;
import com.hentica.app.module.index.IndexPayFragment;
import com.hentica.app.module.index.IndexPayingFragment;
import com.hentica.app.module.index.IndexScanFragment;
import com.hentica.app.module.index.TlPayWebFragment;
import com.hentica.app.module.mine.ui.MineChangeLoginPasswordFragment;
import com.hentica.app.module.mine.ui.MineChangePayPasswordFragment;
import com.hentica.app.module.mine.ui.MineEvaluateDetailFragment;
import com.hentica.app.module.mine.ui.MineFillEvaluateActivity;
import com.hentica.app.module.mine.ui.MineTextContentFragment;
import com.hentica.app.module.mine.ui.MineTransferFragment;
import com.hentica.app.module.mine.ui.MineWechatBindFragment;
import com.hentica.app.module.mine.ui.bank.BankCardAddFragment;
import com.hentica.app.module.mine.ui.bank.BankCardInfoFragment;
import com.hentica.app.module.mine.ui.bank.BankCardMobileVerifyFragment;
import com.hentica.app.module.mine.ui.shop.MineLoactionFragment;
import com.hentica.app.module.mine.ui.shop.MineOrderDetailFragment;
import com.hentica.app.module.mine.ui.shop.MineOrderManagerMainFragment;
import com.hentica.app.module.mine.ui.shop.MinePaymentDetailFragment;
import com.hentica.app.module.mine.ui.shop.MineReceiveQrCodeFragment;
import com.hentica.app.module.mine.ui.shop.MineRecordOrderFragment;
import com.hentica.app.module.mine.ui.shop.MineReplyEvaluateFragment;
import com.hentica.app.module.mine.ui.shop.MineSettleFailureFragment;
import com.hentica.app.module.mine.ui.shop.MineShopCartFragment;
import com.hentica.app.module.mine.ui.shop.MineShopDetailActivity;
import com.hentica.app.module.mine.ui.shop.MineShopLocationFragment;
import com.hentica.app.module.mine.ui.shop.MineShopPaymentFragment;
import com.hentica.app.module.mine.ui.shop.ShopBusinessCountFragment;
import com.hentica.app.module.mine.ui.shop.ShopCustomerCountFragment;
import com.hentica.app.module.mine.ui.textcontent.HelpFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * 用于维护所有带参数的界面跳转
 *
 * @author mili
 * @createTime 2016-6-4 下午3:54:44
 */
public class FragmentJumpUtil {

    /**
     * 尝试跳转到登录界面，若跳转到登录界面了，返回true
     *
     * @param from
     * @return
     */
    public static boolean tryToLogin(UsualFragment from) {

        if (!ApplicationData.getInstance().isLogin()) {

            toLoginFragment(from);
            return true;
        }
        return false;
    }

    /**
     * 尝试跳转到登录界面，若跳转到登录界面了，返回true
     *
     * @param activity
     * @return
     */
    public static boolean tryToLogin(FragmentActivity activity) {

        if (!ApplicationData.getInstance().isLogin()) {

            toLoginFragment(activity);
            return true;
        }
        return false;
    }

    /**
     * 跳转到登录界面
     *
     * @param from
     * @param isFirstEnter 是否是首次进入
     */
    public static void toLoginFragment(UsualFragment from, boolean isFirstEnter) {

        LoginJumpHelper.toLoginApp(from, isFirstEnter);
    }

    /**
     * 跳转到登录界面
     *
     * @param activity
     * @param isFirstEnter 是否是首次进入
     */
    public static void toLoginFragment(FragmentActivity activity, boolean isFirstEnter) {

        LoginJumpHelper.toLoginApp(activity, isFirstEnter);
    }

    /**
     * 跳转到登录界面
     *
     * @param from
     */
    public static void toLoginFragment(UsualFragment from) {

        LoginJumpHelper.toLoginApp(from);
    }

    /**
     * 跳转到登录界面
     *
     * @param activity
     */
    public static void toLoginFragment(FragmentActivity activity) {

        LoginJumpHelper.toLoginApp(activity);
    }

    /***
     * 跳转到第三方登录绑定手机界面
     *
     * @param from
     * @param loginType    第三方登录类型
     * @param thirdAccount 第三方账号
     */
    public static void toLoginThirdBindPhone(UsualFragment from, String loginType,
                                             String thirdAccount) {

        // TODO
//        Intent intent = new Intent();
//        intent.putExtra("thridAccount", thirdAccount);
//        intent.putExtra("type", loginType);
//        from.startFrameActivity(ThirdPartLoginFragment.class, intent);
    }

    /**
     * 跳转到web界面
     */
    public static void toWeb(UsualFragment from, String title, String url) {

        Intent intent = new Intent();
        intent.putExtra(CommonWebFragment.INTENT_EXTRA_TITLE, title);
        intent.putExtra(CommonWebFragment.INTENT_EXTRA_URL, url);

        from.startFrameActivity(CommonWebFragment.class, intent);
    }

    /**
     * 跳转到拨号界面
     *
     * @param from
     * @param phone 电话
     */
    public static void toCalling(UsualFragment from, String phone) {
        toDial(from.getContext(), phone);
    }

    /**
     * 跳转拨号界面
     *
     * @param context
     * @param phone
     */
    public static void toDial(Context context, String phone) {
        Uri uri = Uri.parse("tel:" + phone);
        Intent intent = new Intent(Intent.ACTION_DIAL, uri);
        context.startActivity(intent);
    }

    /**
     * 跳转大力预览界面，单张图片
     *
     * @param from
     * @param url
     */
    public static void toImageGallery(UsualFragment from, String url) {
        List<String> images = new ArrayList<>();
        images.add(url);
        toImageGallery(from, images, 0);
    }

    /**
     * 跳转到大图预览界面
     *
     * @param from
     * @param images   要显示的图片
     * @param position 默认位置
     */
    public static void toImageGallery(UsualFragment from, List<String> images, int position) {
        ArrayList<String> images2 = new ArrayList<String>();
        images2.addAll(images);
        Intent intent = new Intent();
        intent.putStringArrayListExtra("images", images2);
        intent.putExtra("pos", position);
        from.startFrameActivity(ImageGallery.class, intent);
    }

    /**
     * 跳转到大图预览界面
     *
     * @param activity
     * @param images   要显示的图片
     * @param position 默认位置
     */
    public static void toImageGallery(Activity activity, List<String> images, int position) {
        ArrayList<String> images2 = new ArrayList<String>();
        images2.addAll(images);
        Intent intent = new Intent();
        intent.putStringArrayListExtra("images", images2);
        intent.putExtra("pos", position);
        toFragment(activity, ImageGallery.class, intent);
    }

    /**
     * 跳转到商家详情
     *
     * @param from
     * @param sellerId
     */
    public static void toBusinessDetail(UsualFragment from, String sellerId) {
        Intent intent = new Intent();
        intent.putExtra(IndexBusinessDetailFragment.SELLERID, sellerId);
        from.startFrameActivity(IndexBusinessDetailFragment.class, intent);
    }

    /**
     * 跳转到支付界面
     *
     * @param from
     * @param data
     */
    public static void toPayFragment(UsualFragment from, IndexBusinessDetailData data) {
        Intent intent = new Intent();
        String json = ParseUtil.toJsonString(data);
        intent.putExtra(IndexPayFragment.BusinessData, json);
        from.startFrameActivity(IndexPayFragment.class, intent);
    }

    /**
     * 跳转到支付界面
     *
     * @param from
     * @param latitude
     * @param longitude
     * @param sellerId
     */
    public static void toPayFragment(UsualFragment from, double latitude, double longitude, String sellerId) {
        Intent intent = new Intent();
        intent.putExtra(IndexPayFragment.LATITUDE, latitude);
        intent.putExtra(IndexPayFragment.LONGITUDE, longitude);
        intent.putExtra(IndexPayFragment.SELLERID, sellerId);
        from.startFrameActivity(IndexPayFragment.class, intent);
    }

    public static void toBusinessList(UsualFragment from, String keyWords, String category) {
        Intent intent = new Intent();
        intent.putExtra(IndexBusinessListFragment.KEY_WORDS, keyWords);
        intent.putExtra(IndexBusinessListFragment.CATEGORY, category);
        from.startFrameActivity(IndexBusinessListFragment.class, intent);
    }

    /**
     * 跳转商家订单管理界面
     *
     * @param from
     * @param shopInfo
     */
    public static void toShopOrders(UsualFragment from, ResShopInfo shopInfo) {
        Intent intent = new Intent();
        intent.putExtra(MineOrderManagerMainFragment.DATA_SHOP_INFO, ParseUtil.toJsonString(shopInfo));
        from.startFrameActivity(MineOrderManagerMainFragment.class, intent);
    }


    /**
     * 跳转商家订单管理界面
     *
     * @param from
     * @param shopInfo
     */
    public static void toShopRecordOrders(UsualFragment from, ResShopInfo shopInfo) {
        Intent intent = new Intent();
        intent.putExtra(MineOrderManagerMainFragment.DATA_SHOP_INFO, ParseUtil.toJsonString(shopInfo));
        intent.putExtra(MineOrderManagerMainFragment.PAGE_NUMBER, 1);
        from.startFrameActivity(MineOrderManagerMainFragment.class, intent);
    }

    /**
     * 跳转收款二维码界面
     *
     * @param from
     * @param shopInfo
     */
    public static void toShopQrCodeFragment(UsualFragment from, ResShopInfo shopInfo) {
        Intent intent = new Intent();
        intent.putExtra(MineReceiveQrCodeFragment.DATA_SHOP_INFO, ParseUtil.toJsonString(shopInfo));
        from.startFrameActivity(MineReceiveQrCodeFragment.class, intent);
    }

    /**
     * 跳转商家详情界面
     *
     * @param from
     * @param shopInfo
     */
    public static void toShopDetailFragment(UsualFragment from, ResShopInfo shopInfo) {
        Intent intent = new Intent();
//        intent.putExtra(MineShopDetailFragment.DATA_SHOP_INFO, ParseUtil.toJsonString(shopInfo));
//        from.startFrameActivity(MineShopDetailFragment.class,intent);
        intent.putExtra(MineShopDetailActivity.DATA_SHOP_INFO, ParseUtil.toJsonString(shopInfo));
        intent.setClass(from.getActivity(), MineShopDetailActivity.class);
        from.startActivity(intent);
    }

    /**
     * 跳转到订单详情界面
     *
     * @param from
     * @param order1
     * @param order2
     */
    public static void toOrderDetail(UsualFragment from, ResMineOrderListData order1, ResShopOrderItem order2) {
        Intent intent = new Intent();
        intent.putExtra(MineOrderDetailFragment.USERORDERDATA, ParseUtil.toJsonString(order1));
        intent.putExtra(MineOrderDetailFragment.SHOPORDERDATA, ParseUtil.toJsonString(order2));
        from.startFrameActivity(MineOrderDetailFragment.class, intent);
    }

    /**
     * 跳转到订单详情界面
     *
     * @param from
     * @param orderId
     * @param encourageAmount
     */
    public static void toOrderDetail(UsualFragment from, String orderId, double encourageAmount) {
        Intent intent = new Intent();
        intent.putExtra(MineOrderDetailFragment.ORDER_ID, orderId);
        intent.putExtra(MineOrderDetailFragment.ENCOURAGE_AMOUNT, encourageAmount);
        from.startFrameActivity(MineOrderDetailFragment.class, intent);
    }

    /**
     * 跳转到立即评价界面
     *
     * @param from
     * @param data
     */
    public static void toFillEvaluate(UsualFragment from, ResMineOrderListData data) {
        Intent intent = new Intent();
        intent.putExtra(MineFillEvaluateActivity.ORDERINFO, ParseUtil.toJsonString(data));
//        from.startFrameActivity(MineFillEvaluateFragment.class,intent);
        intent.setClass(from.getContext(), MineFillEvaluateActivity.class);
        from.startActivity(intent);
    }


    /**
     * 跳转回复评价界面
     *
     * @param from
     * @param orderId
     */
    public static void toReplyEvaluate(UsualFragment from, long orderId) {
        Intent intent = new Intent();
        intent.putExtra(MineReplyEvaluateFragment.DATA_ORDER_ID, orderId);
        from.startFrameActivity(MineReplyEvaluateFragment.class, intent);
    }

    /**
     * 跳转到评论详情
     *
     * @param from
     * @param data
     */
    public static void toEvaluateDetail(UsualFragment from, ResMineOrderListData data) {
        ResMineOrderListData.SellerBean seller = data.getSeller();
        if (seller == null) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(MineEvaluateDetailFragment.DATA_ORDER_ID, data.getId());
        intent.putExtra(MineEvaluateDetailFragment.DATA_SHOP_PHOTO_URL, seller.getStorePictureUrl());
        intent.putExtra(MineEvaluateDetailFragment.DATA_SHOP_NAME, seller.getName());
        intent.putExtra(MineEvaluateDetailFragment.DATA_SHOP_ADDRESS, seller.getAddress());
        intent.putExtra(MineEvaluateDetailFragment.ORDERINFO, ParseUtil.toJsonString(data));
        from.startFrameActivity(MineEvaluateDetailFragment.class, intent);
    }

    /**
     * 跳转到评论详情
     *
     * @param activity
     * @param data
     */
    public static void toEvaluateDetail(Activity activity, ResMineOrderListData data) {
        ResMineOrderListData.SellerBean seller = data.getSeller();
        if (seller == null) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(MineEvaluateDetailFragment.DATA_ORDER_ID, data.getId());
        intent.putExtra(MineEvaluateDetailFragment.DATA_SHOP_PHOTO_URL, seller.getStorePictureUrl());
        intent.putExtra(MineEvaluateDetailFragment.DATA_SHOP_NAME, seller.getName());
        intent.putExtra(MineEvaluateDetailFragment.DATA_SHOP_ADDRESS, seller.getAddress());
        intent.putExtra(MineEvaluateDetailFragment.ORDERINFO, ParseUtil.toJsonString(data));
        toFragment(activity, MineEvaluateDetailFragment.class, intent);
    }

    /**
     * 跳转到评论详情
     *
     * @param from
     * @param data
     */
    public static void toEvaluateDetail(UsualFragment from, long orderId, ResShopInfo data) {
        if (data == null) {
            return;
        }
        toEvaluateDetail(from, orderId, data.getStorePictureUrl(), data.getName(), data.getAddress());
    }

    /**
     * 跳转到评论详情
     *
     * @param from
     */
    public static void toEvaluateDetail(UsualFragment from, long orderId, String shopPhotoUrl, String shopName, String shopaddress) {
        Intent intent = new Intent();
        intent.putExtra(MineEvaluateDetailFragment.DATA_ORDER_ID, orderId);
        intent.putExtra(MineEvaluateDetailFragment.DATA_SHOP_PHOTO_URL, shopPhotoUrl);
        intent.putExtra(MineEvaluateDetailFragment.DATA_SHOP_NAME, shopName);
        intent.putExtra(MineEvaluateDetailFragment.DATA_SHOP_ADDRESS, shopaddress);
        from.startFrameActivity(MineEvaluateDetailFragment.class, intent);
    }


    public static void toAllEvaluate(UsualFragment from, String sellerId, float rating, String score) {
        Intent intent = new Intent();
        intent.putExtra(IndexAllCommentFragment.SELLERID, sellerId);
        intent.putExtra(IndexAllCommentFragment.RATING, rating);
        intent.putExtra(IndexAllCommentFragment.SCORE, score);
        from.startFrameActivity(IndexAllCommentFragment.class, intent);
    }

    /**
     * 跳转到消息详情
     *
     * @param from
     * @param notify
     */
    public static void toNotifyDetail(UsualFragment from, IndexNotifyListData notify) {
        Intent intent = new Intent();
        intent.putExtra(IndexNotifyDetailFragment.NOTIFYDATA, ParseUtil.toJsonString(notify));
        from.startFrameActivity(IndexNotifyDetailFragment.class, intent);
    }

    /**
     * 跳转定位界面
     *
     * @param from
     * @param latitude
     * @param longitude
     */
    public static void toLocationFragmentForResult(UsualFragment from, String name, double latitude, double longitude) {
        Intent intent = new Intent();
        intent.putExtra(MineShopLocationFragment.DATA_NAME, name);
        intent.putExtra(MineShopLocationFragment.DATA_LATITUDE, latitude);
        intent.putExtra(MineShopLocationFragment.DATA_LONGITUDE, longitude);
        from.startFrameActivityForResult(MineShopLocationFragment.class, intent, MineShopLocationFragment.REQUEST_CODE);
    }

    /**
     * 跳转定位界面
     *
     * @param activity
     * @param latitude
     * @param longitude
     */
    public static void toLocationFragmentForResult(Activity activity, String name, double latitude, double longitude) {
        Intent intent = new Intent();
        intent.putExtra(MineShopLocationFragment.DATA_NAME, name);
        intent.putExtra(MineShopLocationFragment.DATA_LATITUDE, latitude);
        intent.putExtra(MineShopLocationFragment.DATA_LONGITUDE, longitude);
        toFragmentForResult(activity, MineShopLocationFragment.class, intent, MineShopLocationFragment.REQUEST_CODE);
    }

    public static void toFragment(Activity activity, Class<? extends UsualFragment> clazz) {
        toFragment(activity, clazz, null);
    }

    public static void toFragment(Activity activity, Class<? extends UsualFragment> clazz, Intent intent) {
        if (intent == null) {
            intent = new Intent();
        }
        intent.setClass(activity, FrameActivity.class);
        intent.putExtra("jumpTo", clazz.getName());
        activity.startActivity(intent);
    }

    public static void toFragmentForResult(Activity activity, Class<? extends UsualFragment> clazz, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(activity, FrameActivity.class);
        intent.putExtra("jumpTo", clazz.getName());
        activity.startActivityForResult(intent, requestCode);
    }

    public static void toFragmentForResult(Activity activity, Class<? extends UsualFragment> clazz, Intent intent, int requestCode) {
        if (intent == null) {
            intent = new Intent();
        }
        intent.setClass(activity, FrameActivity.class);
        intent.putExtra("jumpTo", clazz.getName());
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 修改登录密码
     *
     * @param from
     */
    public static void toUpdateLoginPwdForResult(UsualFragment from) {
        from.startFrameActivityForResult(MineChangeLoginPasswordFragment.class, MineChangeLoginPasswordFragment.REQUEST_CODE);
    }

    /**
     * 修改登录密码
     */
    public static void toUpdateLoginPwdForResult(Activity activity) {
        toFragmentForResult(activity, MineChangeLoginPasswordFragment.class, MineChangeLoginPasswordFragment.REQUEST_CODE);
    }

    /**
     * 修改支付密码
     *
     * @param from
     */
    public static void toUpdatePayPwdForResult(UsualFragment from) {
        from.startFrameActivityForResult(MineChangePayPasswordFragment.class, MineChangePayPasswordFragment.REQUEST_CODE);
    }

    /**
     * 修改支付密码
     */
    public static void toUpdatePayPwdForResult(Activity activity) {
        toFragmentForResult(activity, MineChangePayPasswordFragment.class, MineChangePayPasswordFragment.REQUEST_CODE);
    }

    /**
     * 跳转到商家地址界面
     *
     * @param from
     * @param latitude
     * @param longitude
     * @param data
     */
    public static void toBusinessLocation(UsualFragment from, double latitude, double longitude, IndexBusinessDetailData data) {
        Intent intent = new Intent();
        intent.putExtra(MineLoactionFragment.LATITUDE, latitude);
        intent.putExtra(MineLoactionFragment.LONGITUDE, longitude);
        intent.putExtra(MineLoactionFragment.BUSINESSDATA, ParseUtil.toJsonString(data));
        from.startFrameActivity(MineLoactionFragment.class, intent);
    }

    /**
     * 跳转导航应用程序
     *
     * @param from
     * @param longitude
     * @param latitude
     * @param type      type in {1(百度地图), 2(高德地图)}
     */
    public static void startNavigationApp(UsualFragment from, double longitude, double latitude, int type) {
        switch (type) {
            case 1:
                toBaiduMap(from, longitude, latitude);
                break;
            case 2:
                toGaoDeMap(from, longitude, latitude);
                break;
        }

    }

    /**
     * 跳转百度导航
     *
     * @param from
     * @param longitude
     * @param latitude
     */
    private static void toBaiduMap(UsualFragment from, double longitude, double latitude) {
        if (AppUtils.isAvilible(from.getContext(), "com.baidu.BaiduMap")) {
            //安装百度地图
            GeoPoint ptEnd = new GeoPoint(latitude, longitude);
            RouteParaOption para = new RouteParaOption()
                    .startName("我的位置")
                    .endPoint(new LatLng(latitude, longitude));
            BaiduMapRoutePlan.openBaiduMapDrivingRoute(para, from.getContext());
        } else {
            //未安装百度地图
            from.showToast("请安装百度地图！");
        }

    }

    /**
     * 跳转高德地图导航
     *
     * @param from
     * @param longitude
     * @param latitude
     */
    private static void toGaoDeMap(UsualFragment from, double longitude, double latitude) {
        // 跳转高德地图导航
        MapDistance.Gps gps = MapDistance.bd09_To_Gcj02(latitude, longitude);
        if (AppUtils.isAvilible(from.getContext(), "com.autonavi.minimap")) {
            //安装了高德地图,调起高德地图
            goToNaviActivity(from, "menli", "", gps.getWgLon() + "", gps.getWgLat() + "", "0", "2");
        } else {
            //未安装高德地图
            from.showToast("请安装高德地图！");
        }
    }

    /**
     * 启动高德App进行导航
     *
     * @param sourceApplication 必填 第三方调用应用名称。如 amap
     * @param poiname           非必填 POI 名称
     * @param lat               必填 纬度
     * @param lon               必填 经度
     * @param dev               必填 是否偏移(0:lat 和 lon 是已经加密后的,不需要国测加密; 1:需要国测加密)
     * @param style             必填 导航方式(0 速度快; 1 费用少; 2 路程短; 3 不走高速；4 躲避拥堵；5 不走高速且避免收费；6 不走高速且躲避拥堵；7 躲避收费和拥堵；8 不走高速躲避收费和拥堵))
     */
    public static void goToNaviActivity(UsualFragment from, String sourceApplication, String poiname, String lat, String lon, String dev, String style) {
        StringBuffer stringBuffer = new StringBuffer("androidamap://navi?sourceApplication=")
                .append(sourceApplication);
        if (!TextUtils.isEmpty(poiname)) {
            stringBuffer.append("&poiname=").append(poiname);
        }
        stringBuffer.append("&lat=").append(lat)
                .append("&lon=").append(lon)
                .append("&dev=").append(dev)
                .append("&style=").append(style);

        Intent intent = new Intent("android.intent.action.VIEW", android.net.Uri.parse(stringBuffer.toString()));
        intent.setPackage("com.autonavi.minimap");
        from.startActivity(intent);
    }


    /**
     * 跳转消息详情
     *
     * @param from
     * @param id
     * @param title
     */
    public static void toHelpDetailFragment(UsualFragment from, int id, String title) {
        Intent intent = new Intent();
        intent.putExtra(HelpFragment.DATA_HELP_ID, id);
        intent.putExtra(HelpFragment.DATA_HELP_TITLE, title);
        from.startFrameActivity(HelpFragment.class, intent);
    }

    /**
     * 跳转到扫描二维码界面
     *
     * @param from
     * @param latitude
     * @param longitude
     */
    public static void toScan(UsualFragment from, double latitude, double longitude) {
        Intent intent = new Intent();
        intent.putExtra(IndexScanFragment.LATITUDE, latitude);
        intent.putExtra(IndexScanFragment.LONGITUDE, longitude);
        from.startFrameActivity(IndexScanFragment.class, intent);
    }

    /**
     * 跳转微信绑定界面
     *
     * @param from
     * @param nickName
     */
    public static void toWechatBindFragment(UsualFragment from, String nickName) {
        Intent intent = new Intent();
        intent.putExtra(MineWechatBindFragment.DATA_NICK_NAME, nickName);
        from.startFrameActivity(MineWechatBindFragment.class, intent);
    }

    /**
     * 跳转微信绑定界面
     *
     * @param nickName
     */
    public static void toWechatBindFragment(Activity activity, String nickName) {
        Intent intent = new Intent();
        intent.putExtra(MineWechatBindFragment.DATA_NICK_NAME, nickName);
        toFragment(activity, MineWechatBindFragment.class, intent);
    }

    /**
     * 跳转审核失败界面
     *
     * @param from
     * @param applyId
     */
    public static void toSettleFailureFragment(UsualFragment from, String applyId, String reason, String mobile) {
        Intent intent = new Intent();
        intent.putExtra(MineSettleFailureFragment.DATA_APPLY_ID, applyId);
        intent.putExtra(MineSettleFailureFragment.DATA_REASON, reason);
        intent.putExtra(MineSettleFailureFragment.DATA_MOBILE, mobile);
        from.startFrameActivity(MineSettleFailureFragment.class, intent);
    }

    /**
     * 跳转购物车
     *
     * @param from
     * @param sellerId
     */
    public static void toShopCartFragment(UsualFragment from, long sellerId, String sellerName) {
        Intent intent = new Intent();
        intent.putExtra(MineShopCartFragment.SELLER_ID, sellerId);
        intent.putExtra(MineShopCartFragment.SELLER_NAME, sellerName);
        from.startFrameActivity(MineShopCartFragment.class, intent);
    }

    /**
     * 跳转订单管理界面
     *
     * @param from
     * @param info
     */
    public static void toShopRecordOrderFragment(UsualFragment from, ResShopInfo info) {
        Intent intent = new Intent();
        intent.putExtra(MineRecordOrderFragment.DATA_SHOP_INFO, new Gson().toJson(info));
        from.startFrameActivity(MineRecordOrderFragment.class, intent);
    }

    /**
     * 跳转录单支付界面
     *
     * @param from
     * @param orderSn
     */
    public static void toRecordOrderPayFragment(UsualFragment from, String orderSn, String sellerId, String sellerName, String amount) {
        Intent intent = new Intent();
        intent.putExtra(MineShopPaymentFragment.DATA_ORDER_SN, orderSn);
        intent.putExtra(MineShopPaymentFragment.DATA_SELLER_ID, sellerId);
        intent.putExtra(MineShopPaymentFragment.DATA_SELLER_NAME, sellerName);
        intent.putExtra(MineShopPaymentFragment.DATA_AMOUNT, amount);
        from.startFrameActivity(MineShopPaymentFragment.class, intent);
    }

    /**
     * 跳转银行卡详情界面
     *
     * @param from
     * @param intent
     */
    public static void toBankCardInfoFragment(UsualFragment from, Intent intent) {
        if (intent == null) {
            intent = new Intent();
        }
        from.startFrameActivity(BankCardInfoFragment.class, intent);
    }

    /**
     * 跳转验证手机号界面
     *
     * @param from
     * @param intent
     */
    public static void toBankCardVerifyMobileFragment(UsualFragment from, Intent intent) {
        if (intent == null) {
            intent = new Intent();
        }
        from.startFrameActivity(BankCardMobileVerifyFragment.class, intent);
    }

    public static void toPaymentDetail(UsualFragment from, String paymentId) {
        Intent intent = new Intent();
        intent.putExtra(MinePaymentDetailFragment.PAYMENT_ID, paymentId);
        from.startFrameActivity(MinePaymentDetailFragment.class, intent);
    }

    /**
     * 跳转添加银行卡界面
     *
     * @param from
     * @param intent
     */
    public static void toBankCardAddFragment(UsualFragment from, Intent intent) {
        if (intent == null) {
            intent = new Intent();
        }
        from.startFrameActivity(BankCardAddFragment.class, intent);
    }

    public static void toTlPayWeb(UsualFragment from, String title, String baseUrl, String pickUpUrl) {
        Intent intent = new Intent();
        intent.putExtra(TlPayWebFragment.INTENT_EXTRA_TITLE, title);
        intent.putExtra(TlPayWebFragment.INTENT_EXTRA_URL, baseUrl);
        intent.putExtra(TlPayWebFragment.INTENT_PICKUP_URL, pickUpUrl);
        from.startFrameActivityForResult(TlPayWebFragment.class, intent, Constants.ACTIVITY_REQUEST_CODE_TL_PAY);
    }

    /**
     * 跳转文本内容界面
     *
     * @param from
     * @param title     标题
     * @param configKey 配置关键字 {@linkplain com.hentica.app.module.entity.ConfigKey ConfigKey}
     */
    public static void toTextContentFragment(UsualFragment from, String title, String configKey) {
        Intent intent = new Intent();
        intent.putExtra(MineTextContentFragment.DATA_TITLE, title);
        intent.putExtra(MineTextContentFragment.DATA_CONFIG_KEY, configKey);
        from.startFrameActivity(MineTextContentFragment.class, intent);
    }

    /**
     * 跳转到商家数界面
     *
     * @param from
     * @param areaId
     * @param isChildView
     */
    public static void toBusinessCount(UsualFragment from, String areaId, boolean isChildView) {
        Intent intent = new Intent();
        intent.putExtra(ShopBusinessCountFragment.AREA_ID, areaId);
        intent.putExtra(ShopBusinessCountFragment.IS_CHILD_VIEW, isChildView);
        from.startFrameActivity(ShopBusinessCountFragment.class, intent);
    }

    /**
     * 跳转到消费者数界面
     *
     * @param from
     * @param areaId
     * @param isChildView
     */
    public static void toCustomerCount(UsualFragment from, String areaId, boolean isChildView) {
        Intent intent = new Intent();
        intent.putExtra(ShopCustomerCountFragment.AREA_ID, areaId);
        intent.putExtra(ShopCustomerCountFragment.IS_CHILD_VIEW, isChildView);
        from.startFrameActivity(ShopCustomerCountFragment.class, intent);
    }


    /**
     * 跳转正在支付界面
     *
     * @param from
     * @param orderId
     * @param amount
     */
    public static void toPayingFragment(UsualFragment from, String orderId, double amount) {
        Intent intent = new Intent();
        intent.putExtra(IndexPayingFragment.JUMP_TAG, 1);
        intent.putExtra(IndexPayingFragment.ORDER_ID, orderId);
        intent.putExtra(IndexPayingFragment.ENCOURAGE_AMOUNT, amount);
        from.startFrameActivity(IndexPayingFragment.class, intent);
    }

    /**
     * 跳转正在支付界面
     *
     * @param from
     */
    public static void toRecordPayingFragment(UsualFragment from) {
        Intent intent = new Intent();
        intent.putExtra(IndexPayingFragment.JUMP_TAG, 2);
        from.startFrameActivity(IndexPayingFragment.class, intent);
    }

    /**
     * 跳转转账界面
     *
     * @param from
     * @param type 1:乐心，2：乐分，3：乐豆
     */
    public static void toTransferFragment(UsualFragment from, int type) {
        Intent intent = new Intent();
        String transferType = "";
        switch (type) {
            case 1:
                transferType = "LE_MIND";
                break;
            case 2:
                transferType = "LE_SCORE";
                break;
            case 3:
                transferType = "LE_BEAN";
                break;
            default:
                return;
        }
        intent.putExtra(MineTransferFragment.DATA_TRANSFER_TYPE, transferType);
        from.startFrameActivity(MineTransferFragment.class, intent);
    }

}
