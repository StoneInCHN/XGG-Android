package com.hentica.app.util.request;

import com.google.gson.Gson;
import com.hentica.app.framework.AppApplication;
import com.hentica.app.framework.data.ApplicationData;
import com.hentica.app.lib.net.Post;
import com.hentica.app.module.entity.ReqShopCartDelete;
import com.hentica.app.module.entity.ReqShopCartOrder;
import com.hentica.app.util.ParseUtil;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 * 所有网络请求
 *
 * @author mili
 * @createTime 2016-10-02 上午10:01:00
 */
public class RequestBase {

    /**
     * 获取RSA公钥
     * /rebate-interface/endUser/rsa.jhtml
     * POST
     */
    public static void getEndUserRsa(Post.FullListener listener) {
        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(),
                "/rebate-interface/endUser/rsa.jhtml");

        // 参数
        List<Post.ParamNameValuePair> params = new ArrayList<>();
        // 去掉UserId和Session参数
        // 文件参数
        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
        Post post = new RebatePost(url, new JsonBodyCreater());//Json请求
        post.setParams(params);
        post.setFileParams(fileParams);
        post.setFullListener(listener);
        post.doPost();
    }

    /**
     * 1.7.2	获取验证码
     * /rebate-interface/endUser/getSmsCode.jhtml
     * POST
     * json
     *
     * @param cellPhoneNum 手机号
     * @param smsCodeType  短信验证码类型，注册或更换手机号REG,登录LOGIN,找回密码RESETPWD,修改登录密码UPDATELOGINPWD,修改支付密码UPDATEPAYPWD,转账TRANSFER
     */
    public static void getEndUserGetSmsCode(String cellPhoneNum, String smsCodeType, Post.FullListener listener) {
        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(),
                "/rebate-interface/endUser/getSmsCode.jhtml");

        // 参数
        List<Post.ParamNameValuePair> params = new ArrayList<>();
        params.add(new Post.ParamNameValuePair("cellPhoneNum", cellPhoneNum));
        params.add(new Post.ParamNameValuePair("smsCodeType", smsCodeType));
        // 去掉UserId和Session参数
        // 文件参数
        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
        Post post = new RebatePost(url, new JsonBodyCreater()); //Json请求
        post.setParams(params);
        post.setFileParams(fileParams);
        post.setFullListener(listener);
        post.doPost();
    }

    /**
     * 1.7.3	用户注册
     * /rebate-interface/endUser/reg.jhtml
     * POST
     * json
     *
     * @param cellPhoneNum      手机号
     * @param smsCode           短信验证码
     * @param password          密码（rsa加密）
     * @param password_confirm  确认密码（rsa加密）
     * @param recommenderMobile 推荐人手机号
     */
    public static void getEndUserReg(String cellPhoneNum,
                                     String smsCode,
                                     String password,
                                     String password_confirm,
                                     String recommenderMobile,
                                     Post.FullListener listener) {
        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(),
                "/rebate-interface/endUser/reg.jhtml");

        // 参数
        List<Post.ParamNameValuePair> params = new ArrayList<>();
        params.add(new Post.ParamNameValuePair("cellPhoneNum", cellPhoneNum));
        params.add(new Post.ParamNameValuePair("smsCode", smsCode));
        params.add(new Post.ParamNameValuePair("password", password));
        params.add(new Post.ParamNameValuePair("password_confirm", password_confirm));
        params.add(new Post.ParamNameValuePair("recommenderMobile", recommenderMobile));
        // 去掉UserId和Session参数
        // 文件参数
        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
        Post post = new RebatePost(url, new JsonBodyCreater());//Json请求
        post.setParams(params);
        post.setFileParams(fileParams);
        post.setFullListener(listener);
        post.doPost();
    }

    /**
     * 1.7.4	找回用户登录密码
     * /rebate-interface/endUser/resetPwd.jhtml
     * POST
     * json
     *
     * @param cellPhoneNum     手机号
     * @param password         密码（rsa加密）
     * @param password_confirm 确认密码（rsa加密）
     * @param smsCode          短信验证码
     */
    public static void getEndUserResetPwd(String cellPhoneNum,
                                          String password,
                                          String password_confirm,
                                          String smsCode,
                                          Post.FullListener listener) {
        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(),
                "/rebate-interface/endUser/resetPwd.jhtml");

        // 参数
        List<Post.ParamNameValuePair> params = new ArrayList<>();
        params.add(new Post.ParamNameValuePair("cellPhoneNum", cellPhoneNum));
        params.add(new Post.ParamNameValuePair("password", password));
        params.add(new Post.ParamNameValuePair("password_confirm", password_confirm));
        params.add(new Post.ParamNameValuePair("smsCode", smsCode));
        // 去掉UserId和Session参数
        // 文件参数
        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
        Post post = new RebatePost(url, new JsonBodyCreater());//Json请求
        post.setParams(params);
        post.setFileParams(fileParams);
        post.setFullListener(listener);
        post.doPost();
    }

    /**
     * 1.7.5	用户登录
     * /rebate-interface/endUser/login.jhtml
     * POST
     * json
     * 用户密码和短信验证码二选一必填
     *
     * @param cellPhoneNum 手机号
     * @param password     用户名密码（rsa加密）
     * @param smsCode      短信验证码
     */
    public static void getEndUserLogin(String cellPhoneNum,
                                       String password,
                                       String smsCode,
                                       Post.FullListener listener) {
        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(),
                "/rebate-interface/endUser/login.jhtml");

        // 参数
        List<Post.ParamNameValuePair> params = new ArrayList<>();
        params.add(new Post.ParamNameValuePair("cellPhoneNum", cellPhoneNum));
        params.add(new Post.ParamNameValuePair("password", password));
        params.add(new Post.ParamNameValuePair("smsCode", smsCode));
        // 去掉UserId和Session参数
        // 文件参数
        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
        Post post = new RebatePost(url, new JsonBodyCreater());//Json请求
        post.setParams(params);
        post.setFileParams(fileParams);
        post.setFullListener(listener);
        post.doPost();
    }

    /**
     * 1.7.6	退出登录
     * /rebate-interface/endUser/logout.jhtml
     * POST
     * json
     *
     * @param userId 用户id
     */
    public static void getEndUserLogout(String userId,
                                        Post.FullListener listener) {
        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(),
                "/rebate-interface/endUser/logout.jhtml");

        // 参数
        List<Post.ParamNameValuePair> params = new ArrayList<>();
        params.add(new Post.ParamNameValuePair("userId", userId));
        params.add(new Post.ParamNameValuePair("token", ApplicationData.getInstance().getToken()));
        // 去掉UserId和Session参数
        // 文件参数
        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
        Post post = new RebatePost(url, new JsonBodyCreater());//Json请求
        post.setParams(params);
        post.setFileParams(fileParams);
        post.setFullListener(listener);
        post.doPost();
    }

    /**
     * 1.7.7	修改用户头像
     * /rebate-interface/endUser/editUserPhoto.jhtml
     * POST
     * text/html
     *
     * @param userId 用户id
     * @param photo  用户头像二进制流文件
     */
    public static void getEndUserEditUserPhoto(String userId,
                                               String photo,
                                               Post.FullListener listener) {
        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(),
                "/rebate-interface/endUser/editUserPhoto.jhtml");

        // 参数
        List<Post.ParamNameValuePair> params = new ArrayList<>();
        params.add(new Post.ParamNameValuePair("userId", userId));
        params.add(new Post.ParamNameValuePair("token", ApplicationData.getInstance().getToken()));
        // 去掉UserId和Session参数
        // 文件参数
        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
        fileParams.add(new Post.ParamNameValuePair("photo", photo));
        Post post = new RebatePost(url, new TextBodyCreater());//Json请求
        post.setParams(params);
        post.setFileParams(fileParams);
        post.setFullListener(listener);
        post.doPost();
    }

    /**
     * 1.7.8	修改用户信息（不包括头像，密码）
     * /rebate-interface/endUser/editUserInfo.jhtml
     * POST
     * json
     *
     * @param userId   用户id
     * @param nickName 昵称
     * @param areaId   地区ID
     */
    public static void getEndUserEditUserInfo(String userId,
                                              String nickName,
                                              String areaId,
                                              Post.FullListener listener) {
        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(),
                "/rebate-interface/endUser/editUserInfo.jhtml");

        // 参数
        List<Post.ParamNameValuePair> params = new ArrayList<>();
        params.add(new Post.ParamNameValuePair("userId", userId));
        params.add(new Post.ParamNameValuePair("token", ApplicationData.getInstance().getToken()));
        params.add(new Post.ParamNameValuePair("nickName", nickName));
        params.add(new Post.ParamNameValuePair("areaId", areaId));
        // 文件参数
        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
        Post post = new RebatePost(url, new JsonBodyCreater());//Json请求
        post.setParams(params);
        post.setFileParams(fileParams);
        post.setFullListener(listener);
        post.doPost();
    }

    /**
     * 1.7.9	修改用户密码(包括登录密码和支付密码)
     * /rebate-interface/endUser/updatePwd.jhtml
     * POST
     * json
     *
     * @param userId           用户id
     * @param cellPhoneNum     用户手机号
     * @param password         密码(rsa加密)
     * @param password_confirm 确认密码(rsa加密)
     * @param smsCode          短信验证码
     * @param smsCodeType      验证码类型，修改登录密码UPDATELOGINPWD,修改支付密码UPDATEPAYPWD
     */
    public static void getEndUserUpdatePwd(String userId,
                                           String cellPhoneNum,
                                           String password,
                                           String password_confirm,
                                           String smsCode,
                                           String smsCodeType,
                                           Post.FullListener listener) {
        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(),
                "/rebate-interface/endUser/updatePwd.jhtml");

        // 参数
        List<Post.ParamNameValuePair> params = new ArrayList<>();
        params.add(new Post.ParamNameValuePair("userId", userId));
        params.add(new Post.ParamNameValuePair("token", ApplicationData.getInstance().getToken()));
        params.add(new Post.ParamNameValuePair("cellPhoneNum", cellPhoneNum));
        params.add(new Post.ParamNameValuePair("password", password));
        params.add(new Post.ParamNameValuePair("password_confirm", password_confirm));
        params.add(new Post.ParamNameValuePair("smsCode", smsCode));
        params.add(new Post.ParamNameValuePair("smsCodeType", smsCodeType));
        // 文件参数
        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
        Post post = new RebatePost(url, new JsonBodyCreater());//Json请求
        post.setParams(params);
        post.setFileParams(fileParams);
        post.setFullListener(listener);
        post.doPost();
    }

    /**
     * 1.7.10	变更用户注册手机号
     * /rebate-interface/endUser/changeRegMobile.jhtml
     * POST
     * json
     *
     * @param userId       用户id
     * @param smsCode      短信验证码
     * @param cellPhoneNum 用户手机号
     */
    public static void getEndUserChangeRegMobile(String userId,
                                                 String smsCode,
                                                 String cellPhoneNum,
                                                 Post.FullListener listener) {
        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(),
                "/rebate-interface/endUser/changeRegMobile.jhtml");

        // 参数
        List<Post.ParamNameValuePair> params = new ArrayList<>();
        params.add(new Post.ParamNameValuePair("userId", userId));
        params.add(new Post.ParamNameValuePair("token", ApplicationData.getInstance().getToken()));
        params.add(new Post.ParamNameValuePair("smsCode", smsCode));
        params.add(new Post.ParamNameValuePair("cellPhoneNum", cellPhoneNum));
        // 文件参数
        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
        Post post = new RebatePost(url, new JsonBodyCreater());//Json请求
        post.setParams(params);
        post.setFileParams(fileParams);
        post.setFullListener(listener);
        post.doPost();
    }

    /**
     * 1.7.11	获取用户信息
     * /rebate-interface/endUser/getUserInfo.jhtml
     * POST
     * json
     *
     * @param userId 用户id
     */
    public static void getEndUserGetUserInfo(String userId,
                                             Post.FullListener listener) {
        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(),
                "/rebate-interface/endUser/getUserInfo.jhtml");

        // 参数
        List<Post.ParamNameValuePair> params = new ArrayList<>();
        params.add(new Post.ParamNameValuePair("userId", userId));
        params.add(new Post.ParamNameValuePair("token", ApplicationData.getInstance().getToken()));
        // 文件参数
        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
        Post post = new RebatePost(url, new JsonBodyCreater());//Json请求
        post.setParams(params);
        post.setFileParams(fileParams);
        post.setFullListener(listener);
        post.doPost();
    }

    /**
     * 1.7.12	获取用户二维码信息
     * /rebate-interface/endUser/getQrCode.jhtml
     * POST
     * json
     *
     * @param userId 用户id
     */
    public static void getEndUserGetQrCode(String userId,
                                           Post.FullListener listener) {
        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(),
                "/rebate-interface/endUser/getQrCode.jhtml");

        // 参数
        List<Post.ParamNameValuePair> params = new ArrayList<>();
        params.add(new Post.ParamNameValuePair("userId", userId));
        params.add(new Post.ParamNameValuePair("token", ApplicationData.getInstance().getToken()));
        // 文件参数
        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
        Post post = new RebatePost(url, new JsonBodyCreater());//Json请求
        post.setParams(params);
        post.setFileParams(fileParams);
        post.setFullListener(listener);
        post.doPost();
    }

    /**
     * 1.7.13	获取用户推荐记录
     * /rebate-interface/endUser/getRecommendRec.jhtml
     * POST
     * json
     *
     * @param userId     用户id
     * @param pageNumber 分页：页数
     * @param pageSize   分页：每页大小
     */
    public static void getEndUserGetRecommendRec(String userId,
                                                 String pageNumber,
                                                 String pageSize,
                                                 Post.FullListener listener) {
        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(),
                "/rebate-interface/endUser/getRecommendRec.jhtml");

        // 参数
        List<Post.ParamNameValuePair> params = new ArrayList<>();
        params.add(new Post.ParamNameValuePair("userId", userId));
        params.add(new Post.ParamNameValuePair("token", ApplicationData.getInstance().getToken()));
        params.add(new Post.ParamNameValuePair("pageNumber", pageNumber));
        params.add(new Post.ParamNameValuePair("pageSize", pageSize));
        // 文件参数
        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
        Post post = new RebatePost(url, new JsonBodyCreater());//Json请求
        post.setParams(params);
        post.setFileParams(fileParams);
        post.setFullListener(listener);
        post.doPost();
    }

    /**
     * 1.7.14	用户积分记录
     * /rebate-interface/endUser/scoreRec.jhtml
     * POST
     * json
     *
     * @param userId     用户id
     * @param pageNumber 分页：页数
     * @param pageSize   分页：每页大小
     */
    public static void getEndUserScoreRec(String userId,
                                          String pageNumber,
                                          String pageSize,
                                          Post.FullListener listener) {
        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(),
                "/rebate-interface/endUser/scoreRec.jhtml");

        // 参数
        List<Post.ParamNameValuePair> params = new ArrayList<>();
        params.add(new Post.ParamNameValuePair("userId", userId));
        params.add(new Post.ParamNameValuePair("token", ApplicationData.getInstance().getToken()));
        params.add(new Post.ParamNameValuePair("pageNumber", pageNumber));
        params.add(new Post.ParamNameValuePair("pageSize", pageSize));
        // 文件参数
        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
        Post post = new RebatePost(url, new JsonBodyCreater());//Json请求
        post.setParams(params);
        post.setFileParams(fileParams);
        post.setFullListener(listener);
        post.doPost();
    }

    /**
     * 1.7.15	用户乐心记录
     * /rebate-interface/endUser/leMindRec.jhtml
     * POST
     * json
     *
     * @param userId     用户id
     * @param pageNumber 分页：页数
     * @param pageSize   分页：每页大小
     */
    public static void getEndUserLeMindRec(String userId,
                                           String pageNumber,
                                           String pageSize,
                                           Post.FullListener listener) {
        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(),
                "/rebate-interface/endUser/leMindRec.jhtml");

        // 参数
        List<Post.ParamNameValuePair> params = new ArrayList<>();
        params.add(new Post.ParamNameValuePair("userId", userId));
        params.add(new Post.ParamNameValuePair("token", ApplicationData.getInstance().getToken()));
        params.add(new Post.ParamNameValuePair("pageNumber", pageNumber));
        params.add(new Post.ParamNameValuePair("pageSize", pageSize));
        // 文件参数
        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
        Post post = new RebatePost(url, new JsonBodyCreater());//Json请求
        post.setParams(params);
        post.setFileParams(fileParams);
        post.setFullListener(listener);
        post.doPost();
    }

//    /**
//     * 1.7.16	用户乐分记录
//     * /rebate-interface/endUser/leScoreRec.jhtml
//     * POST
//     * json
//     *
//     * @param userId     用户i
//     * @param pageNumber 分页：页数
//     * @param pageSize   分页：每页大小
//     */
//    @Deprecated
//    public static void getEndUserLeScoreRec(String userId,
//                                            String pageNumber,
//                                            String pageSize,
//                                            Post.FullListener listener) {
//        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(),
//                "/rebate-interface/endUser/leScoreRec.jhtml");
//
//        // 参数
//        List<Post.ParamNameValuePair> params = new ArrayList<>();
//        params.add(new Post.ParamNameValuePair("userId", userId));
//        params.add(new Post.ParamNameValuePair("token", ApplicationData.getInstance().getToken()));
//        params.add(new Post.ParamNameValuePair("pageNumber", pageNumber));
//        params.add(new Post.ParamNameValuePair("pageSize", pageSize));
//        // 文件参数
//        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
//        Post post = new RebatePost(url, new JsonBodyCreater());//Json请求
//        post.setParams(params);
//        post.setFileParams(fileParams);
//        post.setFullListener(listener);
//        post.doPost();
//    }

    /**
     * 1.7.16	用户乐分记录
     * /rebate-interface/endUser/leScoreRec.jhtml
     * POST
     * json
     *
     * @param userId     用户i
     * @param userId     leScoreType 乐分类型、
     * @param pageNumber 分页：页数
     * @param pageSize   分页：每页大小
     */
    public static void getEndUserLeScoreRec(String userId,
                                            String leScoreType,
                                            String pageNumber,
                                            String pageSize,
                                            Post.FullListener listener) {
        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(),
                "/rebate-interface/endUser/leScoreRec.jhtml");

        // 参数
        List<Post.ParamNameValuePair> params = new ArrayList<>();
        params.add(new Post.ParamNameValuePair("userId", userId));
        params.add(new Post.ParamNameValuePair("token", ApplicationData.getInstance().getToken()));
        params.add(new Post.ParamNameValuePair("leScoreType", leScoreType));
        params.add(new Post.ParamNameValuePair("pageNumber", pageNumber));
        params.add(new Post.ParamNameValuePair("pageSize", pageSize));
        // 文件参数
        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
        Post post = new RebatePost(url, new JsonBodyCreater());//Json请求
        post.setParams(params);
        post.setFileParams(fileParams);
        post.setFullListener(listener);
        post.doPost();
    }

    /**
     * 1.7.17	用户乐豆记录
     * /rebate-interface/endUser/leBeanRec.jhtml
     * POST
     * json
     *
     * @param userId     用户id
     * @param pageNumber 分页：页数
     * @param pageSize   分页：每页大小
     */
    public static void getEndUserLeBeanRec(String userId,
                                           String pageNumber,
                                           String pageSize,
                                           Post.FullListener listener) {
        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(),
                "/rebate-interface/endUser/leBeanRec.jhtml");

        // 参数
        List<Post.ParamNameValuePair> params = new ArrayList<>();
        params.add(new Post.ParamNameValuePair("userId", userId));
        params.add(new Post.ParamNameValuePair("token", ApplicationData.getInstance().getToken()));
        params.add(new Post.ParamNameValuePair("pageNumber", pageNumber));
        params.add(new Post.ParamNameValuePair("pageSize", pageSize));
        // 文件参数
        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
        Post post = new RebatePost(url, new JsonBodyCreater());//Json请求
        post.setParams(params);
        post.setFileParams(fileParams);
        post.setFullListener(listener);
        post.doPost();
    }

    /**
     * 1.7.18	获取所在地区
     * /rebate-interface/area/selectArea.jhtml
     * POST
     * json
     *
     * @param userId   用户id
     * @param entityId 地区ID，entityId不传值时,查询所有顶级地区;传值时查询该ID的地区的下级城市
     */
    public static void getAreaSelectArea(String userId,
                                         String entityId,
                                         Post.FullListener listener) {
        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(),
                "/rebate-interface/area/selectArea.jhtml");

        // 参数
        List<Post.ParamNameValuePair> params = new ArrayList<>();
        params.add(new Post.ParamNameValuePair("userId", userId));
        params.add(new Post.ParamNameValuePair("token", ApplicationData.getInstance().getToken()));
        params.add(new Post.ParamNameValuePair("entityId", entityId));
        // 文件参数
        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
        Post post = new RebatePost(url, new JsonBodyCreater());//Json请求
        post.setParams(params);
        post.setFileParams(fileParams);
        post.setFullListener(listener);
        post.doPost();
    }


    /**
     * 1.7.19	业务员提交申请入驻商户
     * /rebate-interface/seller/apply.jhtml
     * POST
     * text/html
     * * @param userId           业务员用户ID
     *
     * @param applyId          入驻请求ID
     * @param sellerName       店铺名称
     * @param contactCellPhone 联系人手机号（商户用户app账号）
     * @param address          店铺详细地址
     * @param storePhone       店铺电话
     * @param licenseNum       营业执照号
     * @param note             店铺简介
     * @param latitude         纬度
     * @param longitude        经度
     * @param discount         折扣
     * @param areaId           地区ID
     * @param categoryId       行业类别ID
     * @param storePicture     店铺图片
     * @param licenseImg       营业执照图片
     * @param envImgs          环境图片
     */
    @Deprecated
    public static void getSellerApply(String userId,
                                      String applyId,
                                      String sellerName,
                                      String contactCellPhone,
                                      String address,
                                      String storePhone,
                                      String licenseNum,
                                      String note,
                                      String latitude,
                                      String longitude,
                                      String discount,
                                      String areaId,
                                      String categoryId,
                                      String storePicture,
                                      String licenseImg,
                                      List<String> envImgs,
                                      Post.FullListener listener) {
        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(),
                "/rebate-interface/seller/apply.jhtml");

        // 参数
        List<Post.ParamNameValuePair> params = new ArrayList<>();
        params.add(new Post.ParamNameValuePair("userId", userId));
        params.add(new Post.ParamNameValuePair("token", ApplicationData.getInstance().getToken()));
        params.add(new Post.ParamNameValuePair("applyId", applyId));
        params.add(new Post.ParamNameValuePair("sellerName", sellerName));
        params.add(new Post.ParamNameValuePair("contactCellPhone", contactCellPhone));
        params.add(new Post.ParamNameValuePair("address", address));
        params.add(new Post.ParamNameValuePair("storePhone", storePhone));
        params.add(new Post.ParamNameValuePair("licenseNum", licenseNum));
        params.add(new Post.ParamNameValuePair("note", note));
        params.add(new Post.ParamNameValuePair("latitude", latitude));
        params.add(new Post.ParamNameValuePair("longitude", longitude));
        params.add(new Post.ParamNameValuePair("discount", discount));
        params.add(new Post.ParamNameValuePair("areaId", areaId));
        params.add(new Post.ParamNameValuePair("categoryId", categoryId));
        // 文件参数
        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
        fileParams.add(new Post.ParamNameValuePair("storePicture", storePicture));
        fileParams.add(new Post.ParamNameValuePair("licenseImg", licenseImg));
        if (envImgs != null) {
            for (int i = 0; i < envImgs.size(); i++) {
                fileParams.add(new Post.ParamNameValuePair("envImgs", envImgs.get(i)));
            }
        }
        Post post = new RebatePost(url, new TextBodyCreater());//Json请求
        post.setParams(params);
        post.setFileParams(fileParams);
        post.setFullListener(listener);
        post.doPost();
    }

    /**
     * 1.7.19	业务员提交申请入驻商户
     * /rebate-interface/seller/apply.jhtml
     * POST
     * text/html
     * * @param userId           业务员用户ID
     *
     * @param applyId          入驻请求ID
     * @param sellerName       店铺名称
     * @param contactCellPhone 联系人手机号（商户用户app账号）
     * @param address          店铺详细地址
     * @param storePhone       店铺电话
     * @param licenseNum       营业执照号
     * @param note             店铺简介
     * @param latitude         纬度
     * @param longitude        经度
     * @param discount         折扣
     * @param areaId           地区ID
     * @param categoryId       行业类别ID
     * @param storePicture     店铺图片
     * @param licenseImg       营业执照图片
     * @param envImgs          环境图片
     * @param commitmentImgs   承诺书图片
     */
    public static void getSellerApply(String userId,
                                      String applyId,
                                      String sellerName,
                                      String contactCellPhone,
                                      String address,
                                      String storePhone,
                                      String licenseNum,
                                      String note,
                                      String latitude,
                                      String longitude,
                                      String discount,
                                      String areaId,
                                      String categoryId,
                                      String storePicture,
                                      String licenseImg,
                                      List<String> envImgs,
                                      List<String> commitmentImgs,
                                      Post.FullListener listener) {
        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(),
                "/rebate-interface/seller/apply.jhtml");

        // 参数
        List<Post.ParamNameValuePair> params = new ArrayList<>();
        params.add(new Post.ParamNameValuePair("userId", userId));
        params.add(new Post.ParamNameValuePair("token", ApplicationData.getInstance().getToken()));
        params.add(new Post.ParamNameValuePair("applyId", applyId));
        params.add(new Post.ParamNameValuePair("sellerName", sellerName));
        params.add(new Post.ParamNameValuePair("contactCellPhone", contactCellPhone));
        params.add(new Post.ParamNameValuePair("address", address));
        params.add(new Post.ParamNameValuePair("storePhone", storePhone));
        params.add(new Post.ParamNameValuePair("licenseNum", licenseNum));
        params.add(new Post.ParamNameValuePair("note", note));
        params.add(new Post.ParamNameValuePair("latitude", latitude));
        params.add(new Post.ParamNameValuePair("longitude", longitude));
        params.add(new Post.ParamNameValuePair("discount", discount));
        params.add(new Post.ParamNameValuePair("areaId", areaId));
        params.add(new Post.ParamNameValuePair("categoryId", categoryId));
        // 文件参数
        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
        fileParams.add(new Post.ParamNameValuePair("storePicture", storePicture));
        fileParams.add(new Post.ParamNameValuePair("licenseImg", licenseImg));
        if (envImgs != null) {
            for (int i = 0; i < envImgs.size(); i++) {
                fileParams.add(new Post.ParamNameValuePair("envImgs", envImgs.get(i)));
            }
        }
        if (commitmentImgs != null) {
            for (int i = 0; i < commitmentImgs.size(); i++) {
                fileParams.add(new Post.ParamNameValuePair("commitmentImgs", commitmentImgs.get(i)));
            }
        }
        Post post = new RebatePost(url, new TextBodyCreater());//Json请求
        post.setParams(params);
        post.setFileParams(fileParams);
        post.setFullListener(listener);
        post.doPost();
    }

    /**
     * 1.7.20	验证手机号是否已注册会员或成为商家：
     * /rebate-interface/endUser/verifyMobile.jhtml
     * POST
     * json
     *
     * @param userId       用户id
     * @param cellPhoneNum 待验证的手机号
     */
    public static void getEndUserVerifyMobile(String userId,
                                              String cellPhoneNum,
                                              Post.FullListener listener) {
        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(),
                "/rebate-interface/endUser/verifyMobile.jhtml");

        // 参数
        List<Post.ParamNameValuePair> params = new ArrayList<>();
        params.add(new Post.ParamNameValuePair("userId", userId));
        params.add(new Post.ParamNameValuePair("token", ApplicationData.getInstance().getToken()));
        params.add(new Post.ParamNameValuePair("cellPhoneNum", cellPhoneNum));
        // 文件参数
        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
        Post post = new RebatePost(url, new JsonBodyCreater());//Json请求
        post.setParams(params);
        post.setFileParams(fileParams);
        post.setFullListener(listener);
        post.doPost();
    }

    /**
     * 1.7.21	用户获取我的店铺信息
     * /rebate-interface/seller/getSellerInfo.jhtml
     * POST
     * json
     *
     * @param userId 用户id
     */
    public static void getSellerGetSellerInfo(String userId,
                                              Post.FullListener listener) {
        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(),
                "/rebate-interface/seller/getSellerInfo.jhtml");

        // 参数
        List<Post.ParamNameValuePair> params = new ArrayList<>();
        params.add(new Post.ParamNameValuePair("userId", userId));
        params.add(new Post.ParamNameValuePair("token", ApplicationData.getInstance().getToken()));
        // 文件参数
        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
        Post post = new RebatePost(url, new JsonBodyCreater());//Json请求
        post.setParams(params);
        post.setFileParams(fileParams);
        post.setFullListener(listener);
        post.doPost();
    }

    /**
     * 1.7.22	获取店铺二维码信息
     * /rebate-interface/seller/getQrCode.jhtml
     * POST
     * json
     *
     * @param userId   用户id
     * @param entityId 商户ID
     */
    public static void getSellerGetQrCode(String userId,
                                          String entityId,
                                          Post.FullListener listener) {
        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(),
                "/rebate-interface/seller/getQrCode.jhtml");

        // 参数
        List<Post.ParamNameValuePair> params = new ArrayList<>();
        params.add(new Post.ParamNameValuePair("userId", userId));
        params.add(new Post.ParamNameValuePair("token", ApplicationData.getInstance().getToken()));
        params.add(new Post.ParamNameValuePair("entityId", entityId));
        // 文件参数
        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
        Post post = new RebatePost(url, new JsonBodyCreater());//Json请求
        post.setParams(params);
        post.setFileParams(fileParams);
        post.setFullListener(listener);
        post.doPost();
    }

    /**
     * 1.7.23	获取店铺的行业类别
     * /rebate-interface/seller/getSellerCategory.jhtml
     * POST
     * json
     */
    public static void getSellerGetSellerCategory(Post.FullListener listener) {
        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(),
                "/rebate-interface/seller/getSellerCategory.jhtml");

        // 参数
        List<Post.ParamNameValuePair> params = new ArrayList<>();
        // 文件参数
        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
        Post post = new RebatePost(url, new JsonBodyCreater());//Json请求
        post.setParams(params);
        post.setFileParams(fileParams);
        post.setFullListener(listener);
        post.doPost();
    }

    /**
     * 1.7.24	修改完善商户信息
     * /rebate-interface/seller/editInfo.jhtml
     * POST
     * text/html
     * 如果storePicture，envImgs未修改，则不传值，envImgs只要有任意一张图片修改都要重新传整体list的值；其他字段无论修改与否，都要传到后台
     *
     * @param userId          用户id
     * @param sellerId        商家ID
     * @param sellerName      店铺名称
     * @param avgPrice        人均消费
     * @param businessTime    营业时段，“8:00-22:00”
     * @param address         店铺详细地址
     * @param featuredService featuredService
     * @param storePhone      店铺电话
     * @param note            店铺简介
     * @param latitude        纬度
     * @param longitude       经度
     * @param storePicture    店铺图片
     * @param envImgs         环境图片
     */
    public static void getSellerEditInfo(String userId,
                                         String sellerId,
                                         String sellerName,
                                         String avgPrice,
                                         String businessTime,
                                         String address,
                                         String featuredService,
                                         String storePhone,
                                         String note,
                                         String areaId,
                                         String latitude,
                                         String longitude,
                                         String storePicture,
                                         List<String> envImgs,
                                         Post.FullListener listener) {
        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(),
                "/rebate-interface/seller/editInfo.jhtml");

        // 参数
        List<Post.ParamNameValuePair> params = new ArrayList<>();
        params.add(new Post.ParamNameValuePair("userId", userId));
        params.add(new Post.ParamNameValuePair("token", ApplicationData.getInstance().getToken()));
        params.add(new Post.ParamNameValuePair("sellerId", sellerId));
        params.add(new Post.ParamNameValuePair("sellerName", sellerName));
        params.add(new Post.ParamNameValuePair("avgPrice", avgPrice));
        params.add(new Post.ParamNameValuePair("businessTime", businessTime));
        params.add(new Post.ParamNameValuePair("address", address));
        params.add(new Post.ParamNameValuePair("featuredService", featuredService));
        params.add(new Post.ParamNameValuePair("storePhone", storePhone));
        params.add(new Post.ParamNameValuePair("note", note));
        params.add(new Post.ParamNameValuePair("areaId", areaId));
        params.add(new Post.ParamNameValuePair("latitude", latitude));
        params.add(new Post.ParamNameValuePair("longitude", longitude));
        // 文件参数
        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
        fileParams.add(new Post.ParamNameValuePair("storePicture", storePicture));
        if (envImgs != null) {
            for (int i = 0; i < envImgs.size(); i++) {
                fileParams.add(new Post.ParamNameValuePair("envImgs", envImgs.get(i)));
            }
        }
        Post post = new RebatePost(url, new TextBodyCreater());//Json请求
        post.setParams(params);
        post.setFileParams(fileParams);
        post.setFullListener(listener);
        post.doPost();
    }

    /**
     * 1.7.25	获取商家订单列表
     * /rebate-interface/order/getOrderUnderSeller.jhtml
     * POST
     * json
     * 对于每条order记录，如果"status": "PAID"，表示用户已支付，订单待评价；
     * 如果"status": "FINISHED"，"evaluate": {"sellerReply": null},表示用户已评价，商家未回复；如果"status": "FINISHED"，"evaluate": {"sellerReply": "不为null"},表示商已回复用户评价
     *
     * @param userId     用户id
     * @param entityId   商家ID
     * @param pageNumber 分页：页数
     * @param pageSize   分页：每页大小
     */
    public static void getOrderGetOrderUnderSeller(String userId,
                                                   String entityId,
                                                   String pageNumber,
                                                   String pageSize,
                                                   Post.FullListener listener) {
        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(),
                "/rebate-interface/order/getOrderUnderSeller.jhtml");

        // 参数
        List<Post.ParamNameValuePair> params = new ArrayList<>();
        params.add(new Post.ParamNameValuePair("userId", userId));
        params.add(new Post.ParamNameValuePair("token", ApplicationData.getInstance().getToken()));
        params.add(new Post.ParamNameValuePair("entityId", entityId));
        params.add(new Post.ParamNameValuePair("pageNumber", pageNumber));
        params.add(new Post.ParamNameValuePair("pageSize", pageSize));
        // 文件参数
        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
        Post post = new RebatePost(url, new JsonBodyCreater());//Json请求
        post.setParams(params);
        post.setFileParams(fileParams);
        post.setFullListener(listener);
        post.doPost();
    }

    /**
     * 1.7.25	获取商家订单列表
     * /rebate-interface/order/getOrderUnderSeller.jhtml
     * POST
     * json
     * 对于每条order记录，如果"status": "PAID"，表示用户已支付，订单待评价；
     * 如果"status": "FINISHED"，"evaluate": {"sellerReply": null},表示用户已评价，商家未回复；如果"status": "FINISHED"，"evaluate": {"sellerReply": "不为null"},表示商已回复用户评价
     *
     * @param userId        用户id
     * @param entityId      商家ID
     * @param pageNumber    分页：页数
     * @param pageSize      分页：每页大小
     * @param isSallerOrder 是否是录单订单
     * @param orderStatus   订单状态 "UNPAID"未支付, "PAID"已支付待评价 , "FINISHED"评价后，已完成
     * @param isSallerOrder 是否是录单订单
     * @param isClearing    结算状态(结算中:false;已结算:true)
     */
    public static void getOrderGetOrderUnderSeller(String userId,
                                                   String entityId,
                                                   String pageNumber,
                                                   String pageSize,
                                                   boolean isSallerOrder,
                                                   String orderStatus,
                                                   String isClearing,
                                                   Post.FullListener listener) {
        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(),
                "/rebate-interface/order/getOrderUnderSeller.jhtml");

        // 参数
        List<Post.ParamNameValuePair> params = new ArrayList<>();
        params.add(new Post.ParamNameValuePair("userId", userId));
        params.add(new Post.ParamNameValuePair("token", ApplicationData.getInstance().getToken()));
        params.add(new Post.ParamNameValuePair("entityId", entityId));
        params.add(new Post.ParamNameValuePair("pageNumber", pageNumber));
        params.add(new Post.ParamNameValuePair("pageSize", pageSize));
        params.add(new Post.ParamNameValuePair("isSallerOrder", String.valueOf(isSallerOrder)));
        params.add(new Post.ParamNameValuePair("orderStatus", orderStatus));
        params.add(new Post.ParamNameValuePair("isClearing", isClearing));
        // 文件参数
        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
        Post post = new RebatePost(url, new JsonBodyCreater());//Json请求
        post.setParams(params);
        post.setFileParams(fileParams);
        post.setFullListener(listener);
        post.doPost();
    }


    /**
     * 1.7.26	删除未支付的录单订单
     * /rebate-interface/order/delSellerUnpaidOrder.jhtml
     * POST
     * json
     *
     * @param userId   用户id
     * @param entityId 订单ID
     */
    public static void getOrderDelSellerUnpaidOrder(String userId,
                                                    String entityId,
                                                    Post.FullListener listener) {
        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(),
                "/rebate-interface/order/delSellerUnpaidOrder.jhtml");

        // 参数
        List<Post.ParamNameValuePair> params = new ArrayList<>();
        params.add(new Post.ParamNameValuePair("userId", userId));
        params.add(new Post.ParamNameValuePair("token", ApplicationData.getInstance().getToken()));
        params.add(new Post.ParamNameValuePair("entityId", entityId));
        // 文件参数
        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
        Post post = new RebatePost(url, new JsonBodyCreater());//Json请求
        post.setParams(params);
        post.setFileParams(fileParams);
        post.setFullListener(listener);
        post.doPost();
    }

    /**
     * 1.7.27	根据订单获取评价详情
     * /rebate-interface/order/getEvaluateByOrder.jhtml
     * POST
     * json
     *
     * @param userId   用户id
     * @param entityId 订单ID
     */
    public static void getOrderGetEvaluateByOrder(String userId,
                                                  String entityId,
                                                  Post.FullListener listener) {
        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(),
                "/rebate-interface/order/getEvaluateByOrder.jhtml");

        // 参数
        List<Post.ParamNameValuePair> params = new ArrayList<>();
        params.add(new Post.ParamNameValuePair("userId", userId));
        params.add(new Post.ParamNameValuePair("token", ApplicationData.getInstance().getToken()));
        params.add(new Post.ParamNameValuePair("entityId", entityId));
        // 文件参数
        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
        Post post = new RebatePost(url, new JsonBodyCreater());//Json请求
        post.setParams(params);
        post.setFileParams(fileParams);
        post.setFullListener(listener);
        post.doPost();
    }

    /**
     * 1.7.28	用户提交评价
     * /rebate-interface/order/userEvaluateOrder.jhtml
     * POST
     * text/html
     */
    public static void getFillEvaluate(String userId,
                                       String entityId,
                                       String score,
                                       String content,
                                       List<String> evaluateImage,
                                       Post.FullListener listener) {
        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(),
                "/rebate-interface/order/userEvaluateOrder.jhtml");

        // 参数
        List<Post.ParamNameValuePair> params = new ArrayList<>();
        params.add(new Post.ParamNameValuePair("userId", userId));
        params.add(new Post.ParamNameValuePair("token", ApplicationData.getInstance().getToken()));
        params.add(new Post.ParamNameValuePair("entityId", entityId));
        params.add(new Post.ParamNameValuePair("score", score));
        params.add(new Post.ParamNameValuePair("content", content));
        // 文件参数
        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
        if (evaluateImage != null) {
            for (int i = 0; i < evaluateImage.size(); i++) {
                fileParams.add(new Post.ParamNameValuePair("evaluateImage", evaluateImage.get(i)));
            }
        }
        Post post = new RebatePost(url, new TextBodyCreater());//Text请求
        post.setParams(params);
        post.setFileParams(fileParams);
        post.setFullListener(listener);
        post.doPost();
    }

    /**
     * 1.7.29	商家回复用户评价
     * /rebate-interface/order/sellerReply.jhtml
     * POST
     * json
     *
     * @param userId      用户id
     * @param entityId    订单ID
     * @param sellerReply 商家回复
     */
    public static void getOrderSellerReply(String userId,
                                           String entityId,
                                           String sellerReply,
                                           Post.FullListener listener) {
        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(),
                "/rebate-interface/order/sellerReply.jhtml");

        // 参数
        List<Post.ParamNameValuePair> params = new ArrayList<>();
        params.add(new Post.ParamNameValuePair("userId", userId));
        params.add(new Post.ParamNameValuePair("token", ApplicationData.getInstance().getToken()));
        params.add(new Post.ParamNameValuePair("entityId", entityId));
        params.add(new Post.ParamNameValuePair("sellerReply", sellerReply));
        // 文件参数
        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
        Post post = new RebatePost(url, new JsonBodyCreater());//Json请求
        post.setParams(params);
        post.setFileParams(fileParams);
        post.setFullListener(listener);
        post.doPost();
    }

    /**
     * 1.7.30	获取首页顶部图片
     * /rebate-interface/banner/hpTop.jhtml
     * POST
     * json
     */
    public static void getBannerTop(Post.FullListener listener) {
        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(),
                "/rebate-interface/banner/hpTop.jhtml");

        // 参数
        List<Post.ParamNameValuePair> params = new ArrayList<>();
        // 文件参数
        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
        Post post = new RebatePost(url, new JsonBodyCreater());//Json请求
        post.setParams(params);
        post.setFileParams(fileParams);
        post.setFullListener(listener);
        post.doPost();
    }

    /**
     * 1.7.31	热门城市
     * /rebate-interface/area/getHotCity.jhtml
     * POST
     * json
     */
    public static void getAreaGetHotCity(Post.FullListener listener) {
        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(),
                "/rebate-interface/area/getHotCity.jhtml");

        // 参数
        List<Post.ParamNameValuePair> params = new ArrayList<>();
        // 文件参数
        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
        Post post = new RebatePost(url, new JsonBodyCreater());//Json请求
        post.setParams(params);
        post.setFileParams(fileParams);
        post.setFullListener(listener);
        post.doPost();
    }

    /**
     * 1.7.32	首页商家列表
     * /rebate-interface/seller/list.jhtml
     * POST
     * json
     *
     * @param latitude        所在位置纬度
     * @param longitude       所在位置经度
     * @param sortType        排序类型，距离由近及远DISTANCEASC,好评分由高到低SCOREDESC,收藏由高到低COLLECTDESC,默认排序（时间先后顺序）DEFAULT
     * @param categoryId      行业类别ID
     * @param areaIds         地区ID
     * @param featuredService 特色服务；全部ALL,WIFIWIFI,免费停车FREE_PARKING
     * @param keyWord         查询关键字
     * @param pageNumber      分页：页数
     * @param pageSize        分页：每页大小
     */
    public static void getSellerList(String latitude,
                                     String longitude,
                                     String sortType,
                                     String categoryId,
                                     String areaIds,
                                     String featuredService,
                                     String keyWord,
                                     String pageNumber,
                                     String pageSize,
                                     Post.FullListener listener) {
        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(),
                "/rebate-interface/seller/list.jhtml");

        // 参数
        List<Post.ParamNameValuePair> params = new ArrayList<>();
        params.add(new Post.ParamNameValuePair("token", ApplicationData.getInstance().getToken()));
        params.add(new Post.ParamNameValuePair("latitude", latitude));
        params.add(new Post.ParamNameValuePair("longitude", longitude));
        params.add(new Post.ParamNameValuePair("sortType", sortType));
        params.add(new Post.ParamNameValuePair("categoryId", categoryId));
        params.add(new Post.ParamNameValuePair("areaIds", areaIds));
        params.add(new Post.ParamNameValuePair("featuredService", featuredService));
        params.add(new Post.ParamNameValuePair("keyWord", keyWord));
        params.add(new Post.ParamNameValuePair("pageNumber", pageNumber));
        params.add(new Post.ParamNameValuePair("pageSize", pageSize));
        // 文件参数
        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
        Post post = new RebatePost(url, new JsonBodyCreater());//Json请求
        post.setParams(params);
        post.setFileParams(fileParams);
        post.setFullListener(listener);
        post.doPost();
    }

    /**
     * 1.7.33	商家详情
     * /rebate-interface/seller/detail.jhtml
     * POST
     * json
     *
     * @param entityId  商家ID
     * @param latitude  经度
     * @param longitude 纬度
     */
    public static void getSellerDetail(String userId,
                                       String entityId,
                                       String latitude,
                                       String longitude,
                                       Post.FullListener listener) {
        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(),
                "/rebate-interface/seller/detail.jhtml");

        // 参数
        List<Post.ParamNameValuePair> params = new ArrayList<>();
        params.add(new Post.ParamNameValuePair("userId", userId));
        params.add(new Post.ParamNameValuePair("entityId", entityId));
        params.add(new Post.ParamNameValuePair("latitude", latitude));
        params.add(new Post.ParamNameValuePair("longitude", longitude));
        // 文件参数
        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
        Post post = new RebatePost(url, new JsonBodyCreater());//Json请求
        post.setParams(params);
        post.setFileParams(fileParams);
        post.setFullListener(listener);
        post.doPost();
    }

    /**
     * 1.7.34	商家评论列表
     * /rebate-interface/seller/evaluateList.jhtml
     * POST
     * json
     *
     * @param sellerId   店铺ID
     * @param pageNumber 分页：页数
     * @param pageSize   分页：每页大小
     */
    public static void getSellerEvaluateList(String sellerId,
                                             String pageNumber,
                                             String pageSize,
                                             Post.FullListener listener) {
        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(),
                "/rebate-interface/seller/evaluateList.jhtml");

        // 参数
        List<Post.ParamNameValuePair> params = new ArrayList<>();
        params.add(new Post.ParamNameValuePair("sellerId", sellerId));
        params.add(new Post.ParamNameValuePair("pageNumber", pageNumber));
        params.add(new Post.ParamNameValuePair("pageSize", pageSize));
        // 文件参数
        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
        Post post = new RebatePost(url, new JsonBodyCreater());//Json请求
        post.setParams(params);
        post.setFileParams(fileParams);
        post.setFullListener(listener);
        post.doPost();
    }

    /**
     * 1.7.35	获取支付方式(获取系统配置数据)
     * /rebate-interface/systemConfig/getConfigByKey.jhtml
     * POST
     * json
     *
     * @param userId    用户ID
     * @param configKey 配置，“PAYMENTTYPE”
     */

    public static void getSystemConfigGetConfigByKey(String userId,
                                                     String configKey,
                                                     Post.FullListener listener) {

        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(),
                "/rebate-interface/systemConfig/getConfigByKey.jhtml");

        // 参数
        List<Post.ParamNameValuePair> params = new ArrayList<>();
        params.add(new Post.ParamNameValuePair("userId", userId));
        params.add(new Post.ParamNameValuePair("token", ApplicationData.getInstance().getToken()));
        params.add(new Post.ParamNameValuePair("configKey", configKey));
        // 文件参数
        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
        Post post = new RebatePost(url, new JsonBodyCreater());//Json请求
        post.setParams(params);
        post.setFileParams(fileParams);
        post.setFullListener(listener);
        post.doPost();
    }


    /**
     * 1.7.37	获取支付相关信息
     * /rebate-interface/order/getPayInfo.jhtml
     * POST
     * json
     *
     * @param userId   用户ID
     * @param sellerId 商户ID
     */
    public static void getOrderGetPayInfo(
            String userId,
            String sellerId,
            Post.FullListener listener) {
        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(),
                "/rebate-interface/order/getPayInfo.jhtml");

        // 参数
        List<Post.ParamNameValuePair> params = new ArrayList<>();
        params.add(new Post.ParamNameValuePair("userId", userId));
        params.add(new Post.ParamNameValuePair("token", ApplicationData.getInstance().getToken()));
        params.add(new Post.ParamNameValuePair("sellerId", sellerId));
        params.add(new Post.ParamNameValuePair("configKey", "PAYMENTTYPE"));

        // 文件参数
        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
        Post post = new RebatePost(url, new JsonBodyCreater());//Json请求
        post.setParams(params);
        post.setFileParams(fileParams);
        post.setFullListener(listener);
        post.doPost();
    }


    /**
     * 1.7.38	订单支付
     * /rebate-interface/order/pay.jhtml
     * POST
     * json
     *
     * @param userId       用户ID
     * @param payType      支付方式
     * @param payTypeId    支付方式ID
     * @param amount       支付金额
     * @param sellerId     商户ID
     * @param isBeanPay    是否使用乐豆支付
     * @param deductLeBean 乐豆抵扣数量
     * @param remark       备注
     */
    public static void getOrderPay(String userId,
                                   String payType,
                                   String payTypeId,
                                   String amount,
                                   String sellerId,
                                   String isBeanPay,
                                   String deductLeBean,
                                   String remark,
                                   Post.FullListener listener) {
        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(),
                "/rebate-interface/order/pay.jhtml");

        // 参数
        List<Post.ParamNameValuePair> params = new ArrayList<>();
        params.add(new Post.ParamNameValuePair("userId", userId));
        params.add(new Post.ParamNameValuePair("token", ApplicationData.getInstance().getToken()));
        params.add(new Post.ParamNameValuePair("payType", payType));
        params.add(new Post.ParamNameValuePair("payTypeId", payTypeId));
        params.add(new Post.ParamNameValuePair("amount", amount));
        params.add(new Post.ParamNameValuePair("sellerId", sellerId));
        params.add(new Post.ParamNameValuePair("isBeanPay", isBeanPay));
        params.add(new Post.ParamNameValuePair("deductLeBean", deductLeBean));
        params.add(new Post.ParamNameValuePair("remark", remark));
        // 文件参数
        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
        Post post = new RebatePost(url, new JsonBodyCreater());//Json请求
        post.setParams(params);
        post.setFileParams(fileParams);
        post.setFullListener(listener);
        post.doPost();
    }

//    /**
//     * 1.7.33	订单支付
//     * /rebate-interface/order/pay.jhtml
//     * POST
//     * json
//     *
//     * @param reqData    请求数据
//     */
//    public static void getOrderPay(ReqIndexPayOrder reqData,
//                                   Post.FullListener listener) {
//        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(),
//                "/rebate-interface/order/pay.jhtml");
//
//        // 参数
//        List<Post.ParamNameValuePair> params = new ArrayList<>();
//        params.add(new Post.ParamNameValuePair("Request", ParseUtil.toJsonString(reqData)));
//        // 文件参数
//        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
//        Post post = new RebatePost(url, new JsonBodyCreater());//Json请求
//        post.setParams(params);
//        post.setFileParams(fileParams);
//        post.setFullListener(listener);
//        post.doPost();
//    }

    /**
     * 1.7.39	微信支付回调地址
     * /rebate-interface/payNotify/notify_wechat.jhtml
     * POST
     * json
     */
    public static void getPayNotifyNotifyWechat(Post.FullListener listener) {
        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(),
                "/rebate-interface/payNotify/notify_wechat.jhtml");

        // 参数
        List<Post.ParamNameValuePair> params = new ArrayList<>();
        // 文件参数
        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
        Post post = new RebatePost(url, new JsonBodyCreater());//Json请求
        post.setParams(params);
        post.setFileParams(fileParams);
        post.setFullListener(listener);
        post.doPost();
    }

    /**
     * 1.7.40	支付宝支付回调地址
     * /rebate-interface/payNotify/notify_alipay.jhtml
     * POST
     * json
     */
    public static void getPayNotifyNotifyAlipay(Post.FullListener listener) {
        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(),
                "/rebate-interface/payNotify/notify_alipay.jhtml");

        // 参数
        List<Post.ParamNameValuePair> params = new ArrayList<>();
        // 文件参数
        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
        Post post = new RebatePost(url, new JsonBodyCreater());//Json请求
        post.setParams(params);
        post.setFileParams(fileParams);
        post.setFullListener(listener);
        post.doPost();
    }

    /**
     * 1.7.41	用户获取全部订单<br >
     * 1.7.42	用户获取已完成订单<br >
     * 1.7.43	用户获取待评论订单<br >
     * 1.7.44	用户获取未支付订单<br >
     * /rebate-interface/order/getOrderUnderUser.jhtml
     * POST
     * json
     *
     * @param userId        用户ID
     * @param orderStatus   订单状态 ""或null为全部订单， "FINISHED"已完成订单、"PAID"待评论、"UNPAID"未支付
     * @param isSallerOrder 是否是录单订单
     * @param pageNumber    分页：页数
     * @param pageSize      分页：每页大小
     */
    public static void getOrderGetOrderUnderUser(String userId,
                                                 String orderStatus,
                                                 boolean isSallerOrder,
                                                 String pageNumber,
                                                 String pageSize,
                                                 Post.FullListener listener) {
        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(),
                "/rebate-interface/order/getOrderUnderUser.jhtml");

        // 参数
        List<Post.ParamNameValuePair> params = new ArrayList<>();
        params.add(new Post.ParamNameValuePair("userId", userId));
        params.add(new Post.ParamNameValuePair("token", ApplicationData.getInstance().getToken()));
        params.add(new Post.ParamNameValuePair("orderStatus", orderStatus));
        params.add(new Post.ParamNameValuePair("isSallerOrder", String.valueOf(isSallerOrder)));
        params.add(new Post.ParamNameValuePair("pageNumber", pageNumber));
        params.add(new Post.ParamNameValuePair("pageSize", pageSize));
        // 文件参数
        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
        Post post = new RebatePost(url, new JsonBodyCreater());//Json请求
        post.setParams(params);
        post.setFileParams(fileParams);
        post.setFullListener(listener);
        post.doPost();
    }

    /**
     * 1.7.45	用户订单详情
     * /rebate-interface/order/detail.jhtml
     * POST
     * json
     *
     * @param userId   用户ID
     * @param entityId 订单ID
     */
    public static void getUserOrderDetail(
            String userId,
            String entityId,
            Post.FullListener listener) {
        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(),
                "/rebate-interface/order/detail.jhtml");

        // 参数
        List<Post.ParamNameValuePair> params = new ArrayList<>();
        params.add(new Post.ParamNameValuePair("userId", userId));
        params.add(new Post.ParamNameValuePair("token", ApplicationData.getInstance().getToken()));
        params.add(new Post.ParamNameValuePair("entityId", entityId));
        // 文件参数
        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
        Post post = new RebatePost(url, new JsonBodyCreater());//Json请求
        post.setParams(params);
        post.setFileParams(fileParams);
        post.setFullListener(listener);
        post.doPost();
    }

    /**
     * 1.7.46	用户收藏店铺<br >
     * 1.7.47	用户取消店铺收藏<br >
     * /rebate-interface/endUser/favoriteSeller.jhtml
     * POST
     * json
     *
     * @param userId        用户ID
     * @param entityId      商户Id
     * @param isFavoriteAdd 是否为添加收藏操作
     */
    public static void getEndUserFavoriteSeller(String userId,
                                                String entityId,
                                                String isFavoriteAdd,
                                                Post.FullListener listener) {
        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(),
                "/rebate-interface/endUser/favoriteSeller.jhtml");

        // 参数
        List<Post.ParamNameValuePair> params = new ArrayList<>();
        params.add(new Post.ParamNameValuePair("userId", userId));
        params.add(new Post.ParamNameValuePair("token", ApplicationData.getInstance().getToken()));
        params.add(new Post.ParamNameValuePair("entityId", entityId));
        params.add(new Post.ParamNameValuePair("isFavoriteAdd", isFavoriteAdd));
        // 文件参数
        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
        Post post = new RebatePost(url, new JsonBodyCreater());//Json请求
        post.setParams(params);
        post.setFileParams(fileParams);
        post.setFullListener(listener);
        post.doPost();
    }


    /**
     * 1.7.48	用户收藏列表
     * /rebate-interface/endUser/favoriteSellerList.jhtml
     * POST
     * json
     *
     * @param userId     用户ID
     * @param latitude   用户所在位置纬度
     * @param longitude  用户所在位置经度
     * @param pageNumber 分页：页数
     * @param pageSize   分页：每页大小
     */
    public static void getEndUserFavoriteSellerList(String userId,
                                                    String latitude,
                                                    String longitude,
                                                    String pageNumber,
                                                    String pageSize,
                                                    Post.FullListener listener) {
        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(),
                "/rebate-interface/endUser/favoriteSellerList.jhtml");

        // 参数
        List<Post.ParamNameValuePair> params = new ArrayList<>();
        params.add(new Post.ParamNameValuePair("userId", userId));
        params.add(new Post.ParamNameValuePair("token", ApplicationData.getInstance().getToken()));
        params.add(new Post.ParamNameValuePair("latitude", latitude));
        params.add(new Post.ParamNameValuePair("longitude", longitude));
        params.add(new Post.ParamNameValuePair("pageNumber", pageNumber));
        params.add(new Post.ParamNameValuePair("pageSize", pageSize));
        // 文件参数
        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
        Post post = new RebatePost(url, new JsonBodyCreater());//Json请求
        post.setParams(params);
        post.setFileParams(fileParams);
        post.setFullListener(listener);
        post.doPost();
    }

    /**
     * 1.7.49	获取消息列表<br >
     * 1.7.50	获取消息列表
     * /rebate-interface/message/getMsgList.jhtml
     * POST
     * json
     *
     * @param userId     用户ID
     * @param pageNumber 分页：页数
     * @param pageSize   分页：每页大小
     */
    public static void getMessageGetMsgList(String userId,
                                            String pageNumber,
                                            String pageSize,
                                            Post.FullListener listener) {
        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(),
                "/rebate-interface/message/getMsgList.jhtml");

        // 参数
        List<Post.ParamNameValuePair> params = new ArrayList<>();
        params.add(new Post.ParamNameValuePair("userId", userId));
        params.add(new Post.ParamNameValuePair("token", ApplicationData.getInstance().getToken()));
        params.add(new Post.ParamNameValuePair("pageNumber", pageNumber));
        params.add(new Post.ParamNameValuePair("pageSize", pageSize));
        // 文件参数
        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
        Post post = new RebatePost(url, new JsonBodyCreater());//Json请求
        post.setParams(params);
        post.setFileParams(fileParams);
        post.setFullListener(listener);
        post.doPost();
    }

    /**
     * 1.7.51	阅读消息
     * /rebate-interface/message/readMessage.jhtml
     * POST
     * json
     *
     * @param userId 用户ID
     * @param msgId  消息ID
     */
    public static void getMessageReadMessage(String userId,
                                             String msgId,
                                             Post.FullListener listener) {
        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(),
                "/rebate-interface/message/readMessage.jhtml");

        // 参数
        List<Post.ParamNameValuePair> params = new ArrayList<>();
        params.add(new Post.ParamNameValuePair("userId", userId));
        params.add(new Post.ParamNameValuePair("token", ApplicationData.getInstance().getToken()));
        params.add(new Post.ParamNameValuePair("msgId", msgId));
        // 文件参数
        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
        Post post = new RebatePost(url, new JsonBodyCreater());//Json请求
        post.setParams(params);
        post.setFileParams(fileParams);
        post.setFullListener(listener);
        post.doPost();
    }


    /**
     * 1.7.52	删除消息
     * /rebate-interface/message/deleteMsgs.jhtml
     * POST
     * json
     *
     * @param userId 用户ID
     * @param msgIds 消息ID数组
     */
    public static void getMessageDeleteMsgs(String userId,
                                            List<String> msgIds,
                                            Post.FullListener listener) {
        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(),
                "/rebate-interface/message/deleteMsgs.jhtml");

        // 参数
        List<Post.ParamNameValuePair> params = new ArrayList<>();
        params.add(new Post.ParamNameValuePair("userId", userId));
        params.add(new Post.ParamNameValuePair("token", ApplicationData.getInstance().getToken()));
        params.add(new Post.ParamNameValuePair("msgIds", ParseUtil.toJsonString(msgIds)));
        // 文件参数
        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
        Post post = new RebatePost(url, new JsonBodyCreater());//Json请求
        post.setParams(params);
        post.setFileParams(fileParams);
        post.setFullListener(listener);
        post.doPost();
    }

    /**
     * 1.7.34	获取乐豆抵扣说明
     * 1.7.53	获取软件许可协议<br >
     * 1.7.54	获取客户电话<br >
     * 1.7.55	获取关于我们<br >
     * 1.7.61	获取实名认证说明<br >
     * 1.7.74	获取店铺货款说明
     * /rebate-interface/settingConfig/getConfigByKey.jhtml
     * POST
     * json
     *
     * @param configKey 配置项：乐豆抵扣说明("LEBEAN_PAY_DESC")，软件许可协议("LICENSE_AGREEMENT ")，关于我们("ABOUT "), 实名认证说明
     *                  ("USER_AUTH_DESC ") ,店铺货款说明("SELLER_PAYMENT_DESC")
     *                  <p>
     *                  客户电话("CUSTOMER_PHONE")
     */
    public static void getSettingConfigGetConfigByKey(String configKey,
                                                      Post.FullListener listener) {
        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(),
                "/rebate-interface/settingConfig/getConfigByKey.jhtml");

        // 参数
        List<Post.ParamNameValuePair> params = new ArrayList<>();
        params.add(new Post.ParamNameValuePair("configKey", configKey));
        // 文件参数
        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
        Post post = new RebatePost(url, new JsonBodyCreater());//Json请求
        post.setParams(params);
        post.setFileParams(fileParams);
        post.setFullListener(listener);
        post.doPost();
    }

    /**
     * 1.7.56	获取帮助信息列表
     * /rebate-interface/settingConfig/userHelpList.jhtml
     * POST
     * json
     */
    public static void getSettingConfigUserHelpList(Post.FullListener listener) {
        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(),
                "/rebate-interface/settingConfig/userHelpList.jhtml");

        // 参数
        List<Post.ParamNameValuePair> params = new ArrayList<>();
        // 文件参数
        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
        Post post = new RebatePost(url, new JsonBodyCreater());//Json请求
        post.setParams(params);
        post.setFileParams(fileParams);
        post.setFullListener(listener);
        post.doPost();
    }

    /**
     * 1.7.57	获取帮助信息详情
     * /rebate-interface/settingConfig/userHelpDetail.jhtml
     * POST
     * json
     *
     * @param entityId 帮助信息ID
     */
    public static void getSettingConfigUserHelpDetail(String entityId, Post.FullListener listener) {
        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(),
                "/rebate-interface/settingConfig/userHelpDetail.jhtml");

        // 参数
        List<Post.ParamNameValuePair> params = new ArrayList<>();
        params.add(new Post.ParamNameValuePair("entityId", entityId));
        // 文件参数
        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
        Post post = new RebatePost(url, new JsonBodyCreater());//Json请求
        post.setParams(params);
        post.setFileParams(fileParams);
        post.setFullListener(listener);
        post.doPost();
    }

    /**
     * 1.7.58	全国分红
     * /rebate-interface/report/getNationBonusReport.jhtml
     * POST
     * json
     *
     * @param userId 用户ID
     */
    public static void getReportGetNationBonusReport(
            String userId,
            Post.FullListener listener) {
        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(),
                "/rebate-interface/report/getNationBonusReport.jhtml");

        // 参数
        List<Post.ParamNameValuePair> params = new ArrayList<>();
        params.add(new Post.ParamNameValuePair("userId", userId));
        params.add(new Post.ParamNameValuePair("token", ApplicationData.getInstance().getToken()));
        // 文件参数
        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
        Post post = new RebatePost(url, new JsonBodyCreater());//Json请求
        post.setParams(params);
        post.setFileParams(fileParams);
        post.setFullListener(listener);
        post.doPost();
    }

    /**
     * 1.7.59	用户个人分红
     * /rebate-interface/report/getUserBonusReport.jhtml
     * POST
     * json
     *
     * @param userId 用户ID
     */
    public static void getReportGetUserBonusReport(
            String userId,
            Post.FullListener listener) {
        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(),
                "/rebate-interface/report/getUserBonusReport.jhtml");

        // 参数
        List<Post.ParamNameValuePair> params = new ArrayList<>();
        params.add(new Post.ParamNameValuePair("userId", userId));
        params.add(new Post.ParamNameValuePair("token", ApplicationData.getInstance().getToken()));
        // 文件参数
        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
        Post post = new RebatePost(url, new JsonBodyCreater());//Json请求
        post.setParams(params);
        post.setFileParams(fileParams);
        post.setFullListener(listener);
        post.doPost();
    }

    /**
     * 1.7.60	微信授权
     * /rebate-interface/endUser/doAuthByWechat.jhtml
     * POST
     * json
     *
     * @param userId     用户ID
     * @param openId     用户微信openid
     * @param wxNickName 用户微信昵称
     */
    public static void getEndUserDoAuthByWechat(
            String userId,
            String openId,
            String wxNickName,
            Post.FullListener listener) {
        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(),
                "/rebate-interface/endUser/doAuthByWechat.jhtml");

        // 参数
        List<Post.ParamNameValuePair> params = new ArrayList<>();
        params.add(new Post.ParamNameValuePair("userId", userId));
        params.add(new Post.ParamNameValuePair("token", ApplicationData.getInstance().getToken()));
        params.add(new Post.ParamNameValuePair("openId", openId));
        params.add(new Post.ParamNameValuePair("wxNickName", wxNickName));
        // 文件参数
        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
        Post post = new RebatePost(url, new JsonBodyCreater());//Json请求
        post.setParams(params);
        post.setFileParams(fileParams);
        post.setFullListener(listener);
        post.doPost();
    }

    /**
     * 1.7.61	微信解除授权(微信解除授权清空openid)
     * /rebate-interface/endUser/cancelAuthByWechat.jhtml
     * POST
     * json
     *
     * @param userId 用户ID
     */
    public static void getEndUserCancelAuthByWechat(
            String userId,
            Post.FullListener listener) {
        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(),
                "/rebate-interface/endUser/cancelAuthByWechat.jhtml");

        // 参数
        List<Post.ParamNameValuePair> params = new ArrayList<>();
        params.add(new Post.ParamNameValuePair("userId", userId));
        params.add(new Post.ParamNameValuePair("token", ApplicationData.getInstance().getToken()));
        // 文件参数
        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
        Post post = new RebatePost(url, new JsonBodyCreater());//Json请求
        post.setParams(params);
        post.setFileParams(fileParams);
        post.setFullListener(listener);
        post.doPost();
    }

    /**
     * 1.7.62	获取版本更新信息
     * /rebate-interface/common/getAppVersion.jhtml
     * POST
     * json
     *
     * @param versionCode 版本序列号
     */
    public static void getCommonGetAppVersion(
            String versionCode,
            Post.FullListener listener) {
        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(),
                "/rebate-interface/common/getAppVersion.jhtml");

        // 参数
        List<Post.ParamNameValuePair> params = new ArrayList<>();
        params.add(new Post.ParamNameValuePair("versionCode", versionCode));
        // 文件参数
        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
        Post post = new RebatePost(url, new JsonBodyCreater());//Json请求
        post.setParams(params);
        post.setFileParams(fileParams);
        post.setFullListener(listener);
        post.doPost();
    }

    /**
     * 1.7.63	验证支付密码
     * /rebate-interface/endUser/verifyPayPwd.jhtml
     * POST
     * json
     *
     * @param userId   用户ID
     * @param password 支付密码(rsa加密)
     */
    public static void getEndUserVerifyPayPwd(
            String userId,
            String password,
            Post.FullListener listener) {
        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(),
                "/rebate-interface/endUser/verifyPayPwd.jhtml");

        // 参数
        List<Post.ParamNameValuePair> params = new ArrayList<>();
        params.add(new Post.ParamNameValuePair("userId", userId));
        params.add(new Post.ParamNameValuePair("token", ApplicationData.getInstance().getToken()));
        params.add(new Post.ParamNameValuePair("password", password));
        // 文件参数
        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
        Post post = new RebatePost(url, new JsonBodyCreater());//Json请求
        post.setParams(params);
        post.setFileParams(fileParams);
        post.setFullListener(listener);
        post.doPost();
    }

    /**
     * 1.7.64	获取提现信息
     * /rebate-interface/endUser/getWithdrawInfo.jhtml
     * POST
     * json
     *
     * @param userId 用户ID
     */
    public static void getEndUserGetWithdrawInfo(
            String userId,
            Post.FullListener listener) {
        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(),
                "/rebate-interface/endUser/getWithdrawInfo.jhtml");

        // 参数
        List<Post.ParamNameValuePair> params = new ArrayList<>();
        params.add(new Post.ParamNameValuePair("userId", userId));
        params.add(new Post.ParamNameValuePair("token", ApplicationData.getInstance().getToken()));
        // 文件参数
        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
        Post post = new RebatePost(url, new JsonBodyCreater());//Json请求
        post.setParams(params);
        post.setFileParams(fileParams);
        post.setFullListener(listener);
        post.doPost();
    }

    /**
     * 1.7.65	用户确认提现
     * /rebate-interface/endUser/withdrawConfirm.jhtml
     * POST
     * json
     *
     * @param userId   用户ID
     * @param password 支付密码(rsa加密)
     * @param remark   提现备注
     */
    @Deprecated
    public static void getEndUserWithdrawConfirm(
            String userId,
            String password,
            String remark,
            String entityId,
            Post.FullListener listener) {
        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(),
                "/rebate-interface/endUser/withdrawConfirm.jhtml");

        // 参数
        List<Post.ParamNameValuePair> params = new ArrayList<>();
        params.add(new Post.ParamNameValuePair("userId", userId));
        params.add(new Post.ParamNameValuePair("token", ApplicationData.getInstance().getToken()));
        params.add(new Post.ParamNameValuePair("password", password));
        params.add(new Post.ParamNameValuePair("remark", remark));
        params.add(new Post.ParamNameValuePair("entityId", entityId));
        // 文件参数
        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
        Post post = new RebatePost(url, new JsonBodyCreater());//Json请求
        post.setParams(params);
        post.setFileParams(fileParams);
        post.setFullListener(listener);
        post.doPost();
    }

    /**
     * 1.7.65	用户确认提现
     * /rebate-interface/endUser/withdrawConfirm.jhtml
     * POST
     * json
     *
     * @param userId         用户ID
     * @param withdrawAmount 提现金额
     * @param remark         提现备注
     * @param entityId       提现银行卡ID
     *                       <p>
     *                       提现时验证支付密码需单独调用接口1.7.63验证支付密码
     */
    public static void getEndUserWithdrawConfirm(
            String userId,
            double withdrawAmount,
            String remark,
            String entityId,
            Post.FullListener listener) {
        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(),
                "/rebate-interface/endUser/withdrawConfirm.jhtml");

        // 参数
        List<Post.ParamNameValuePair> params = new ArrayList<>();
        params.add(new Post.ParamNameValuePair("userId", userId));
        params.add(new Post.ParamNameValuePair("token", ApplicationData.getInstance().getToken()));
        params.add(new Post.ParamNameValuePair("withdrawAmount", String.valueOf(withdrawAmount)));
        params.add(new Post.ParamNameValuePair("remark", remark));
        params.add(new Post.ParamNameValuePair("entityId", entityId));
        // 文件参数
        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
        Post post = new RebatePost(url, new JsonBodyCreater());//Json请求
        post.setParams(params);
        post.setFileParams(fileParams);
        post.setFullListener(listener);
        post.doPost();
    }

    /**
     * 1.7.66	获取APP下载地址
     * /rebate-interface/common/getAppDownUrl.jhtml
     * POST
     * json
     */
    public static void getCommonGetAppDownUrl(
            Post.FullListener listener) {
        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(),
                "/rebate-interface/common/getAppDownUrl.jhtml");

        // 参数
        List<Post.ParamNameValuePair> params = new ArrayList<>();
        // 文件参数
        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
        Post post = new RebatePost(url, new JsonBodyCreater());//Json请求
        post.setParams(params);
        post.setFileParams(fileParams);
        post.setFullListener(listener);
        post.doPost();
    }

    /**
     * 1.7.68	实名认证
     * /rebate-interface/endUser/doIdentityAuth.jhtml
     * POST
     * text/html
     *
     * @param userId       用户ID
     * @param realName     真实姓名
     * @param cardNo       身份证号码
     * @param cardFrontPic 身份证正面照
     * @param cardBackPic  身份证反面照
     */
    public static void getEndUserWithdrawConfirm(
            String userId,
            String realName,
            String cardNo,
            String cardFrontPic,
            String cardBackPic,
            Post.FullListener listener) {
        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(),
                "/rebate-interface/endUser/doIdentityAuth.jhtml");

        // 参数
        List<Post.ParamNameValuePair> params = new ArrayList<>();
        params.add(new Post.ParamNameValuePair("userId", userId));
        params.add(new Post.ParamNameValuePair("token", ApplicationData.getInstance().getToken()));
        params.add(new Post.ParamNameValuePair("realName", realName));
        params.add(new Post.ParamNameValuePair("cardNo", cardNo));
        // 文件参数
        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
        fileParams.add(new Post.ParamNameValuePair("cardFrontPic", cardFrontPic));
        fileParams.add(new Post.ParamNameValuePair("cardBackPic", cardBackPic));
        Post post = new RebatePost(url, new TextBodyCreater());//Json请求
        post.setParams(params);
        post.setFileParams(fileParams);
        post.setFullListener(listener);
        post.doPost();
    }

    /**
     * 1.7.72	银行卡四元素校验
     * /rebate-interface/bankCard/verifyCard.jhtml
     * POST
     * text/html
     *
     * @param userId         用户ID
     * @param ownerName      持卡人姓名
     * @param cardNum        银行卡号
     * @param idCard         身份证号
     * @param reservedMobile 预留手机号
     */
    public static void getBankCardVerifiCard(
            String userId,
            String ownerName,
            String cardNum,
            String idCard,
            String reservedMobile,
            Post.FullListener listener) {
        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(),
                "/rebate-interface/bankCard/verifyCard.jhtml");

        // 参数
        List<Post.ParamNameValuePair> params = new ArrayList<>();
        params.add(new Post.ParamNameValuePair("userId", userId));
        params.add(new Post.ParamNameValuePair("token", ApplicationData.getInstance().getToken()));
        params.add(new Post.ParamNameValuePair("ownerName", ownerName));
        params.add(new Post.ParamNameValuePair("cardNum", cardNum));
        params.add(new Post.ParamNameValuePair("idCard", idCard));
        params.add(new Post.ParamNameValuePair("reservedMobile", reservedMobile));
        // 文件参数
        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
        Post post = new RebatePost(url, new JsonBodyCreater());//Json请求
        post.setParams(params);
        post.setFileParams(fileParams);
        post.setFullListener(listener);
        post.doPost();
    }

    /**
     * 1.7.73	添加银行卡
     * /rebate-interface/bankCard/addCard.jhtml
     * POST
     * text/html
     *
     * @param userId         登录用户ID
     * @param smsCode        短信验证码
     * @param ownerName      持卡人姓名
     * @param idCard         身份证号
     * @param cardNum        银行卡号
     * @param bankName       银行名称
     * @param cardType       银行卡类型
     * @param isDefault      是否为默认银行卡
     * @param bankLogo       银行卡图标
     * @param reservedMobile 预留手机号
     */
    public static void getBankCardAddCard(
            String userId,
            String smsCode,
            String ownerName,
            String idCard,
            String cardNum,
            String bankName,
            String cardType,
            boolean isDefault,
            String bankLogo,
            String reservedMobile,
            Post.FullListener listener) {
        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(),
                "/rebate-interface/bankCard/addCard.jhtml");

        // 参数
        List<Post.ParamNameValuePair> params = new ArrayList<>();
        params.add(new Post.ParamNameValuePair("userId", userId));
        params.add(new Post.ParamNameValuePair("token", ApplicationData.getInstance().getToken()));
        params.add(new Post.ParamNameValuePair("smsCode", smsCode));
        params.add(new Post.ParamNameValuePair("ownerName", ownerName));
        params.add(new Post.ParamNameValuePair("idCard", idCard));
        params.add(new Post.ParamNameValuePair("cardNum", cardNum));
        params.add(new Post.ParamNameValuePair("bankName", bankName));
        params.add(new Post.ParamNameValuePair("cardType", cardType));
        params.add(new Post.ParamNameValuePair("isDefault", String.valueOf(isDefault)));
        params.add(new Post.ParamNameValuePair("bankLogo", bankLogo));
        params.add(new Post.ParamNameValuePair("reservedMobile", reservedMobile));
        // 文件参数
        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
        Post post = new RebatePost(url, new JsonBodyCreater());//Json请求
        post.setParams(params);
        post.setFileParams(fileParams);
        post.setFullListener(listener);
        post.doPost();
    }

    /**
     * 1.7.74	用户银行卡列表
     * /rebate-interface/bankCard/myCardList.jhtml
     * POST
     * json
     *
     * @param userId     用户id
     * @param pageNumber 分页：页数
     * @param pageSize   分页：每页大小
     */
    public static void getBankCardMyCardList(String userId,
                                             String pageNumber,
                                             String pageSize,
                                             Post.FullListener listener) {
        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(),
                "/rebate-interface/bankCard/myCardList.jhtml");

        // 参数
        List<Post.ParamNameValuePair> params = new ArrayList<>();
        params.add(new Post.ParamNameValuePair("userId", userId));
        params.add(new Post.ParamNameValuePair("token", ApplicationData.getInstance().getToken()));
        params.add(new Post.ParamNameValuePair("pageNumber", pageNumber));
        params.add(new Post.ParamNameValuePair("pageSize", pageSize));
        // 文件参数
        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
        Post post = new RebatePost(url, new JsonBodyCreater());//Json请求
        post.setParams(params);
        post.setFileParams(fileParams);
        post.setFullListener(listener);
        post.doPost();
    }

    /**
     * 1.7.75	删除银行卡
     * /rebate-interface/bankCard/delCard.jhtml
     * POST
     * json
     *
     * @param userId   用户id
     * @param entityId 银行卡ID
     */
    public static void getBankCardDelCard(String userId,
                                          String entityId,
                                          Post.FullListener listener) {
        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(),
                "/rebate-interface/bankCard/delCard.jhtml");

        // 参数
        List<Post.ParamNameValuePair> params = new ArrayList<>();
        params.add(new Post.ParamNameValuePair("userId", userId));
        params.add(new Post.ParamNameValuePair("token", ApplicationData.getInstance().getToken()));
        params.add(new Post.ParamNameValuePair("entityId", entityId));
        // 文件参数
        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
        Post post = new RebatePost(url, new JsonBodyCreater());//Json请求
        post.setParams(params);
        post.setFileParams(fileParams);
        post.setFullListener(listener);
        post.doPost();
    }


    /**
     * 1.7.76	获取用户默认银行卡
     * /rebate-interface/bankCard/getDefaultCard.jhtml
     * POST
     * json
     *
     * @param userId 用户id
     */
    public static void getBankCardGetDefaultCard(String userId,
                                                 Post.FullListener listener) {
        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(),
                "/rebate-interface/bankCard/getDefaultCard.jhtml");

        // 参数
        List<Post.ParamNameValuePair> params = new ArrayList<>();
        params.add(new Post.ParamNameValuePair("userId", userId));
        params.add(new Post.ParamNameValuePair("token", ApplicationData.getInstance().getToken()));
        // 文件参数
        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
        Post post = new RebatePost(url, new JsonBodyCreater());//Json请求
        post.setParams(params);
        post.setFileParams(fileParams);
        post.setFullListener(listener);
        post.doPost();
    }


    /**
     * 1.7.77	设置默认银行卡
     * /rebate-interface/bankCard/updateCardDefault.jhtml
     * POST
     * json
     *
     * @param userId   用户id
     * @param entityId 银行卡ID
     */
    public static void getBankCardUpdateCardDefault(String userId,
                                                    String entityId,
                                                    Post.FullListener listener) {
        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(),
                "/rebate-interface/bankCard/updateCardDefault.jhtml");

        // 参数
        List<Post.ParamNameValuePair> params = new ArrayList<>();
        params.add(new Post.ParamNameValuePair("userId", userId));
        params.add(new Post.ParamNameValuePair("token", ApplicationData.getInstance().getToken()));
        params.add(new Post.ParamNameValuePair("entityId", entityId));
        // 文件参数
        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
        Post post = new RebatePost(url, new JsonBodyCreater());//Json请求
        post.setParams(params);
        post.setFileParams(fileParams);
        post.setFullListener(listener);
        post.doPost();
    }


    /**
     * 1.7.78	获取用户身份证信息
     * /rebate-interface/bankCard/getIdCard.jhtml
     * POST
     * json
     *
     * @param userId 登录用户ID
     */
    public static void getBankCardGetIdCard(
            String userId,
            Post.FullListener listener) {
        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(),
                "/rebate-interface/bankCard/getIdCard.jhtml");

        // 参数
        List<Post.ParamNameValuePair> params = new ArrayList<>();
        params.add(new Post.ParamNameValuePair("userId", userId));
        params.add(new Post.ParamNameValuePair("token", ApplicationData.getInstance().getToken()));
        // 文件参数
        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
        Post post = new RebatePost(url, new JsonBodyCreater());//Json请求
        post.setParams(params);
        post.setFileParams(fileParams);
        post.setFullListener(listener);
        post.doPost();
    }

    /**
     * 1.7.79	业务员获取推荐商家列表
     * /rebate-interface/endUser/getRecommendSellerRec.jhtml
     * POST
     * json
     *
     * @param userId     用户id
     * @param pageNumber 分页：页数
     * @param pageSize   分页：每页大小
     */
    public static void getEndUserGetRecommendSellerRec(String userId,
                                                       String pageNumber,
                                                       String pageSize,
                                                       Post.FullListener listener) {
        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(),
                "/rebate-interface/endUser/getRecommendSellerRec.jhtml");

        // 参数
        List<Post.ParamNameValuePair> params = new ArrayList<>();
        params.add(new Post.ParamNameValuePair("userId", userId));
        params.add(new Post.ParamNameValuePair("token", ApplicationData.getInstance().getToken()));
        params.add(new Post.ParamNameValuePair("pageNumber", pageNumber));
        params.add(new Post.ParamNameValuePair("pageSize", pageSize));
        // 文件参数
        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
        Post post = new RebatePost(url, new JsonBodyCreater());//Json请求
        post.setParams(params);
        post.setFileParams(fileParams);
        post.setFullListener(listener);
        post.doPost();
    }

    /**
     * 1.7.80	录单页面自动填充商户信息
     * /rebate-interface/endUser/getCurrentSellerInfo.jhtml
     * POST
     * json
     *
     * @param userId 用户id
     */
    public static void getEndUserGetCurrentSellerInfo(String userId,
                                                      Post.FullListener listener) {
        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(),
                "/rebate-interface/endUser/getCurrentSellerInfo.jhtml");

        // 参数
        List<Post.ParamNameValuePair> params = new ArrayList<>();
        params.add(new Post.ParamNameValuePair("userId", userId));
        params.add(new Post.ParamNameValuePair("token", ApplicationData.getInstance().getToken()));
        // 文件参数
        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
        Post post = new RebatePost(url, new JsonBodyCreater());//Json请求
        post.setParams(params);
        post.setFileParams(fileParams);
        post.setFullListener(listener);
        post.doPost();
    }


    /**
     * 1.7.81	录单页面根据手机号获取消费者信息
     * /rebate-interface/endUser/getUserInfoByMobile.jhtml
     * POST
     * json
     *
     * @param userId       用户id
     * @param cellPhoneNum 消费者手机号
     */
    public static void getEndUserGetUserInfoByMobile(
            String userId,
            String cellPhoneNum,
            Post.FullListener listener) {
        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(),
                "/rebate-interface/endUser/getUserInfoByMobile.jhtml");

        // 参数
        List<Post.ParamNameValuePair> params = new ArrayList<>();
        params.add(new Post.ParamNameValuePair("userId", userId));
        params.add(new Post.ParamNameValuePair("token", ApplicationData.getInstance().getToken()));
        params.add(new Post.ParamNameValuePair("cellPhoneNum", cellPhoneNum));
        // 文件参数
        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
        Post post = new RebatePost(url, new JsonBodyCreater());//Json请求
        post.setParams(params);
        post.setFileParams(fileParams);
        post.setFullListener(listener);
        post.doPost();
    }

    /**
     * 1.7.82	录单加入购物车
     * /rebate-interface/sellerOrderCart/add.jhtml
     * POST
     * json
     *
     * @param userId         用户id
     * @param entityId       录单消费者ID
     * @param sellerId       录单商户ID
     * @param amount         录单消费金额
     * @param sellerDiscount 录单订单折扣
     */
    public static void getSellerOrderCartAdd(
            String userId,
            String entityId,
            String sellerId,
            String amount,
            String sellerDiscount,
            Post.FullListener listener) {
        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(),
                "/rebate-interface/sellerOrderCart/add.jhtml");

        // 参数
        List<Post.ParamNameValuePair> params = new ArrayList<>();
        params.add(new Post.ParamNameValuePair("userId", userId));
        params.add(new Post.ParamNameValuePair("token", ApplicationData.getInstance().getToken()));
        params.add(new Post.ParamNameValuePair("entityId", entityId));
        params.add(new Post.ParamNameValuePair("sellerId", sellerId));
        params.add(new Post.ParamNameValuePair("amount", amount));
        params.add(new Post.ParamNameValuePair("sellerDiscount", sellerDiscount));
        // 文件参数
        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
        Post post = new RebatePost(url, new JsonBodyCreater());//Json请求
        post.setParams(params);
        post.setFileParams(fileParams);
        post.setFullListener(listener);
        post.doPost();
    }

    /**
     * 1.7.73	购物车批量录单
     * /rebate-interface/sellerOrderCart/confirmOrder.jhtml
     * POST
     * json
     *
     * @param req 请求参数
     */
    public static void getSellerOrderCartConfirmOrder(ReqShopCartOrder req,
                                                      Post.FullListener listener) {
        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(),
                "/rebate-interface/sellerOrderCart/confirmOrder.jhtml");

        // 参数
        List<Post.ParamNameValuePair> params = new ArrayList<>();
        params.add(new Post.ParamNameValuePair("Request", new Gson().toJson(req)));
        // 文件参数
        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
        Post post = new RebatePost(url, new DataParamReqeustBodyCreater());//Json请求
        post.setParams(params);
        post.setFileParams(fileParams);
        post.setFullListener(listener);
        post.doPost();
    }

    /**
     * 1.7.84	录单购物车删除
     * /rebate-interface/sellerOrderCart/delete.jhtml
     * POST
     * json
     *
     * @param req 请求参数
     */
    public static void getSellerOrderCartDelete(ReqShopCartDelete req,
                                                Post.FullListener listener) {
        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(),
                "/rebate-interface/sellerOrderCart/delete.jhtml");

        // 参数
        List<Post.ParamNameValuePair> params = new ArrayList<>();
        params.add(new Post.ParamNameValuePair("Request", new Gson().toJson(req)));
        // 文件参数
        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
        Post post = new RebatePost(url, new DataParamReqeustBodyCreater());//Json请求
        post.setParams(params);
        post.setFileParams(fileParams);
        post.setFullListener(listener);
        post.doPost();
    }

    /**
     * 1.7.85	立即录单
     * /rebate-interface/order/generateSellerOrder.jhtml
     * POST
     * json
     *
     * @param userId         用户id
     * @param entityId       消费者ID
     * @param amount         录单金额
     * @param sellerId       商家ID
     * @param sellerDiscount 录单订单折扣
     */
    public static void getOrderGenerateSellerOrder(
            String userId,
            String entityId,
            String amount,
            String sellerId,
            String sellerDiscount,
            Post.FullListener listener) {
        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(),
                "/rebate-interface/order/generateSellerOrder.jhtml");

        // 参数
        List<Post.ParamNameValuePair> params = new ArrayList<>();
        params.add(new Post.ParamNameValuePair("userId", userId));
        params.add(new Post.ParamNameValuePair("token", ApplicationData.getInstance().getToken()));
        params.add(new Post.ParamNameValuePair("entityId", entityId));
        params.add(new Post.ParamNameValuePair("amount", amount));
        params.add(new Post.ParamNameValuePair("sellerId", sellerId));
        params.add(new Post.ParamNameValuePair("sellerDiscount", sellerDiscount));
        // 文件参数
        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
        Post post = new RebatePost(url, new JsonBodyCreater());//Json请求
        post.setParams(params);
        post.setFileParams(fileParams);
        post.setFullListener(listener);
        post.doPost();
    }


    /**
     * 1.7.86	录单支付
     * /rebate-interface/order/paySellerOrder.jhtml
     * POST
     * json
     *
     * @param userId    用户id
     * @param payType   支付方式
     * @param payTypeId 支付方式id
     * @param sellerId  商户id
     * @param sn        订单编号
     */
    public static void getPaySellerOrder(String userId,
                                         String payType,
                                         String payTypeId,
                                         String sellerId,
                                         String sn,
                                         Post.FullListener listener) {
        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(),
                "/rebate-interface/order/paySellerOrder.jhtml");

        // 参数
        List<Post.ParamNameValuePair> params = new ArrayList<>();
        params.add(new Post.ParamNameValuePair("userId", userId));
        params.add(new Post.ParamNameValuePair("token", ApplicationData.getInstance().getToken()));
        params.add(new Post.ParamNameValuePair("payType", payType));
        params.add(new Post.ParamNameValuePair("payTypeId", payTypeId));
        params.add(new Post.ParamNameValuePair("sellerId", sellerId));
        params.add(new Post.ParamNameValuePair("sn", sn));
        // 文件参数
        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
        Post post = new RebatePost(url, new JsonBodyCreater());//Json请求
        post.setParams(params);
        post.setFileParams(fileParams);
        post.setFullListener(listener);
        post.doPost();
    }

    /**
     * 1.7.87	录单购物车列表
     * /rebate-interface/sellerOrderCart/list.jhtml
     * POST
     * json
     *
     * @param userId     用户id
     * @param pageNumber 分页：页数
     * @param pageSize   分页：每页大小
     */
    public static void getSellerOrderCartList(String userId,
                                              String pageNumber,
                                              String pageSize,
                                              Post.FullListener listener) {
        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(),
                "/rebate-interface/sellerOrderCart/list.jhtml");

        // 参数
        List<Post.ParamNameValuePair> params = new ArrayList<>();
        params.add(new Post.ParamNameValuePair("userId", userId));
        params.add(new Post.ParamNameValuePair("token", ApplicationData.getInstance().getToken()));
        params.add(new Post.ParamNameValuePair("pageNumber", pageNumber));
        params.add(new Post.ParamNameValuePair("pageSize", pageSize));
        // 文件参数
        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
        Post post = new RebatePost(url, new JsonBodyCreater());//Json请求
        post.setParams(params);
        post.setFileParams(fileParams);
        post.setFullListener(listener);
        post.doPost();
    }

    /**
     * 1.7.88	商户获取录单列表
     * /rebate-interface/order/getSallerOrder.jhtml
     * POST
     * json
     *
     * @param userId     用户id
     * @param pageNumber 分页：页数
     * @param pageSize   分页：每页大小
     */
    public static void getOrderGetSallerOrder(String userId,
                                              String pageNumber,
                                              String pageSize,
                                              Post.FullListener listener) {
        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(),
                "/rebate-interface/order/getSallerOrder.jhtml");

        // 参数
        List<Post.ParamNameValuePair> params = new ArrayList<>();
        params.add(new Post.ParamNameValuePair("userId", userId));
        params.add(new Post.ParamNameValuePair("token", ApplicationData.getInstance().getToken()));
        params.add(new Post.ParamNameValuePair("pageNumber", pageNumber));
        params.add(new Post.ParamNameValuePair("pageSize", pageSize));
        // 文件参数
        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
        Post post = new RebatePost(url, new JsonBodyCreater());//Json请求
        post.setParams(params);
        post.setFileParams(fileParams);
        post.setFullListener(listener);
        post.doPost();
    }


    /**
     * 1.7.90	店铺货款列表
     * /rebate-interface/seller/paymentList.jhtml
     * POST
     * json
     *
     * @param userId     用户id
     * @param pageNumber 分页：页数
     * @param pageSize   分页：每页大小
     */
    public static void getSellerPaymentList(String userId,
                                            String startTime,
                                            String endTime,
                                            String pageNumber,
                                            String pageSize,
                                            Post.FullListener listener) {
        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(),
                "/rebate-interface/seller/paymentList.jhtml");

        // 参数
        List<Post.ParamNameValuePair> params = new ArrayList<>();
        params.add(new Post.ParamNameValuePair("userId", userId));
        params.add(new Post.ParamNameValuePair("token", ApplicationData.getInstance().getToken()));
        params.add(new Post.ParamNameValuePair("startTime", startTime));
        params.add(new Post.ParamNameValuePair("endTime", endTime));
        params.add(new Post.ParamNameValuePair("pageNumber", pageNumber));
        params.add(new Post.ParamNameValuePair("pageSize", pageSize));
        // 文件参数
        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
        Post post = new RebatePost(url, new JsonBodyCreater());//Json请求
        post.setParams(params);
        post.setFileParams(fileParams);
        post.setFullListener(listener);
        post.doPost();
    }

    /**
     * 1.7.91	货款明细
     * /rebate-interface/seller/paymentDetail.jhtml
     * POST
     * json
     *
     * @param userId     用户id
     * @param entityId   货款记录id
     * @param pageNumber 分页：页数
     * @param pageSize   分页：每页大小
     */
    public static void getSellerPaymentDetail(String userId,
                                              String entityId,
                                              String pageNumber,
                                              String pageSize,
                                              Post.FullListener listener) {
        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(),
                "/rebate-interface/seller/paymentDetail.jhtml");

        // 参数
        List<Post.ParamNameValuePair> params = new ArrayList<>();
        params.add(new Post.ParamNameValuePair("userId", userId));
        params.add(new Post.ParamNameValuePair("token", ApplicationData.getInstance().getToken()));
        params.add(new Post.ParamNameValuePair("entityId", entityId));
        params.add(new Post.ParamNameValuePair("pageNumber", pageNumber));
        params.add(new Post.ParamNameValuePair("pageSize", pageSize));
        // 文件参数
        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
        Post post = new RebatePost(url, new JsonBodyCreater());//Json请求
        post.setParams(params);
        post.setFileParams(fileParams);
        post.setFullListener(listener);
        post.doPost();
    }

    /**
     * 1.7.92 获取营业中心交易额
     * /rebate-interface/agent/consumeAmountReport.jhtml
     * POST
     * json
     *
     * @param userId 用户id
     */
    public static void getConsumeAmountReport(String userId,
                                              String pageNumber,
                                              String pageSize,
                                              String areaId,
                                              String startDate,
                                              String endDate,
                                              Post.FullListener listener) {
        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(),
                "/rebate-interface/agent/consumeAmountReport.jhtml");

        // 参数
        List<Post.ParamNameValuePair> params = new ArrayList<>();
        params.add(new Post.ParamNameValuePair("userId", userId));
        params.add(new Post.ParamNameValuePair("token", ApplicationData.getInstance().getToken()));
        params.add(new Post.ParamNameValuePair("pageNumber", pageNumber));
        params.add(new Post.ParamNameValuePair("pageSize", pageSize));
        params.add(new Post.ParamNameValuePair("areaId", areaId));
        params.add(new Post.ParamNameValuePair("startDate", startDate));
        params.add(new Post.ParamNameValuePair("endDate", endDate));
        // 文件参数
        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
        Post post = new RebatePost(url, new JsonBodyCreater());//Json请求
        post.setParams(params);
        post.setFileParams(fileParams);
        post.setFullListener(listener);
        post.doPost();
    }

    /**
     * 1.7.93 获取营业中心商家数
     * /rebate-interface/agent/sellerCountReport.jhtml
     * POST
     * json
     *
     * @param userId 用户id
     */
    public static void getSellerCountReport(String userId,
                                            String pageNumber,
                                            String pageSize,
                                            String areaId,
                                            Post.FullListener listener) {
        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(),
                "/rebate-interface/agent/sellerCountReport.jhtml");

        // 参数
        List<Post.ParamNameValuePair> params = new ArrayList<>();
        params.add(new Post.ParamNameValuePair("userId", userId));
        params.add(new Post.ParamNameValuePair("token", ApplicationData.getInstance().getToken()));
        params.add(new Post.ParamNameValuePair("pageNumber", pageNumber));
        params.add(new Post.ParamNameValuePair("pageSize", pageSize));
        params.add(new Post.ParamNameValuePair("areaId", areaId));
        // 文件参数
        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
        Post post = new RebatePost(url, new JsonBodyCreater());//Json请求
        post.setParams(params);
        post.setFileParams(fileParams);
        post.setFullListener(listener);
        post.doPost();
    }

    /**
     * 1.7.94 获取营业中心消费者数
     * /rebate-interface/agent/endUserCountReport.jhtml
     * POST
     * json
     *
     * @param userId 用户id
     */
    public static void getEndUserCountReport(String userId,
                                             String pageNumber,
                                             String pageSize,
                                             String areaId,
                                             Post.FullListener listener) {
        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(),
                "/rebate-interface/agent/endUserCountReport.jhtml");

        // 参数
        List<Post.ParamNameValuePair> params = new ArrayList<>();
        params.add(new Post.ParamNameValuePair("userId", userId));
        params.add(new Post.ParamNameValuePair("token", ApplicationData.getInstance().getToken()));
        params.add(new Post.ParamNameValuePair("pageNumber", pageNumber));
        params.add(new Post.ParamNameValuePair("pageSize", pageSize));
        params.add(new Post.ParamNameValuePair("areaId", areaId));
        // 文件参数
        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
        Post post = new RebatePost(url, new JsonBodyCreater());//Json请求
        post.setParams(params);
        post.setFileParams(fileParams);
        post.setFullListener(listener);
        post.doPost();
    }

    /**
     * 1.7.95 获取营业中心业务员数
     * /rebate-interface/agent/salesmanCountReport.jhtml
     * POST
     * json
     *
     * @param userId 用户id
     */
    public static void getSalesmanCountReport(String userId,
                                              String pageNumber,
                                              String pageSize,
                                              String areaId,
                                              Post.FullListener listener) {
        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(),
                "/rebate-interface/agent/salesmanCountReport.jhtml");

        // 参数
        List<Post.ParamNameValuePair> params = new ArrayList<>();
        params.add(new Post.ParamNameValuePair("userId", userId));
        params.add(new Post.ParamNameValuePair("token", ApplicationData.getInstance().getToken()));
        params.add(new Post.ParamNameValuePair("pageNumber", pageNumber));
        params.add(new Post.ParamNameValuePair("pageSize", pageSize));
        params.add(new Post.ParamNameValuePair("areaId", areaId));
        // 文件参数
        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
        Post post = new RebatePost(url, new JsonBodyCreater());//Json请求
        post.setParams(params);
        post.setFileParams(fileParams);
        post.setFullListener(listener);
        post.doPost();
    }

    /**
     * 1.7.96用户转账
     * /rebate-interface/ transferRebate/doTransfer.jhtml
     * POST
     * json
     *
     * @param userId       用户id
     * @param transType    转账类型:乐心LE_MIND,乐豆LE_BEAN,乐分LE_SCORE
     * @param smsCode      短信验证码
     * @param cellPhoneNum 转账接收手机号
     * @param amount       转账数量
     */
    public static void getTransferRebateDoTransfer(String userId,
                                                   String transType,
                                                   String smsCode,
                                                   String cellPhoneNum,
                                                   String amount,
                                                   Post.FullListener listener) {
        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(),
                "/rebate-interface/transferRebate/doTransfer.jhtml");

        // 参数
        List<Post.ParamNameValuePair> params = new ArrayList<>();
        params.add(new Post.ParamNameValuePair("userId", userId));
        params.add(new Post.ParamNameValuePair("token", ApplicationData.getInstance().getToken()));
        params.add(new Post.ParamNameValuePair("transType", transType));
        params.add(new Post.ParamNameValuePair("smsCode", smsCode));
        params.add(new Post.ParamNameValuePair("cellPhoneNum", cellPhoneNum));
        params.add(new Post.ParamNameValuePair("amount", amount));
        // 文件参数
        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
        Post post = new RebatePost(url, new JsonBodyCreater());//Json请求
        post.setParams(params);
        post.setFileParams(fileParams);
        post.setFullListener(listener);
        post.doPost();
    }

    /**
     * 1.7.96用户转账
     * /rebate-interface/ transferRebate/verifyReceiver.jhtml
     * POST
     * json
     *
     * @param userId         用户id
     * @param receiverMobile 转账接收手机号
     */
    public static void getTransferRebateVerifyReceiver(String userId,
                                                       String receiverMobile,
                                                       Post.FullListener listener) {
        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(),
                "/rebate-interface/transferRebate/verifyReceiver.jhtml");

        // 参数
        List<Post.ParamNameValuePair> params = new ArrayList<>();
        params.add(new Post.ParamNameValuePair("userId", userId));
        params.add(new Post.ParamNameValuePair("token", ApplicationData.getInstance().getToken()));
        params.add(new Post.ParamNameValuePair("receiverMobile", receiverMobile));
        // 文件参数
        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
        Post post = new RebatePost(url, new JsonBodyCreater());//Json请求
        post.setParams(params);
        post.setFileParams(fileParams);
        post.setFullListener(listener);
        post.doPost();
    }

    /**
     * 设置极光推送注册id
     * userId 用户id
     * jpushRegId 极光推送RegistId
     */
    public static void setJpushRegistId(String userId, Post.FullListener listener) {
        String url = HostUtil.splicelHost(ApplicationData.getInstance().getServerUrl(), "/rebate-interface/common/setJpushId.jhtml");
        // 参数
        List<Post.ParamNameValuePair> params = new ArrayList<>();
        params.add(new Post.ParamNameValuePair("userId", userId));
        params.add(new Post.ParamNameValuePair("token", ApplicationData.getInstance().getToken()));
        params.add(new Post.ParamNameValuePair("jpushRegId", JPushInterface.getRegistrationID(AppApplication.getInstance())));
        params.add(new Post.ParamNameValuePair("appPlatform", "ANDROID"));
        // 文件参数
        List<Post.ParamNameValuePair> fileParams = new ArrayList<>();
        Post post = new RebatePost(url, new JsonBodyCreater());//Json请求
        post.setParams(params);
        post.setFileParams(fileParams);
        post.setFullListener(listener);
        post.doPost();
    }
}
