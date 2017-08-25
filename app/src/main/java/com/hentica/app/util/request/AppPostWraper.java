package com.hentica.app.util.request;

import com.hentica.app.framework.data.ApplicationData;
import com.hentica.app.framework.data.Constants;
import com.hentica.app.lib.net.NetData;
import com.hentica.app.lib.net.Post;
import com.hentica.app.util.event.DataEvent.OnLoginInvaildEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * 包装post工具，与App相关，响应App事件
 */
public class AppPostWraper extends Post {

    /**
     * 是否是调试模式
     */
    public static boolean IS_DEBUG = true;

    /**
     * 是否包装
     */
    private static boolean mIsWrap = true;

    /**
     * 构造函数，url为将要访问的网址
     */
    public AppPostWraper(String url) {
        super(url);
    }

    /** 激励平台请求参数不需要加密处理。 */

//    @Override
//    public void setParams(List<ParamNameValuePair> params) {
//
//        if (!mIsWrap) {
//
//            super.setParams(params);
//            return;
//        }
//
//        if (mIsClearNullParam) {
//
//            // 去除空参数
//            params = clearNullParam(params);
//        }
//
//        // 组装参数
//        if (params == null) {
//
//            params = new ArrayList<ParamNameValuePair>();
//        }
//
//        // 组装json参数
//        String dataParam = "";
//
//        // 对于已经包含DataParam的，则只传递DataParam
//        boolean hasDataParam = false;
//        for (ParamNameValuePair param : params) {
//
//            if ("DataParam".equals(param.getName())) {
//
//                hasDataParam = true;
//                dataParam = param.getValue();
//                break;
//            }
//        }
//
//        // 若不包含DataParam，则组装json参数
//        if (!hasDataParam) {
//
//            try {
//
//                JSONObject object = new JSONObject();
//
//                for (ParamNameValuePair param : params) {
//
//                    if (param.getType() == ParamType.kString) {
//
//                        object.put(param.getName(), param.getValue());
//
//                    } else if (param.getType() == ParamType.kArray) {
//
//                        // 为空则不添加
//                        if (!TextUtils.isEmpty(param.getValue())) {
//
//                            JSONArray array = new JSONArray(param.getValue());
//                            object.put(param.getName(), array);
//                        }
//                    } else if (param.getType() == ParamType.kObject) {
//
//                        if (!TextUtils.isEmpty(param.getValue())) {
//
//                            JSONObject object2 = new JSONObject(param.getValue());
//                            object.put(param.getName(), object2);
//                        }
//                    }
//                }
//                dataParam = object.toString();
//
//            } catch (Exception e) {
//
//                e.printStackTrace();
//
//                if (IS_DEBUG) {
//
//                    Toast.makeText(AppApplication.getInstance(), e.toString(), Toast.LENGTH_LONG)
//                            .show();
//                }
//            }
//        }
//
//        // UserID=用户ID&Session=来源于登陆&Key=来源于登陆&DataParam=需要传递的参数
//        // 计算md5值
//        String entryFormat = "UserId=%s&Session=%s&Key=%s&DataParam=%s";
//        String entryString = String.format(entryFormat,
//                ApplicationData.getInstance().getLoginUserId(),
//                ApplicationData.getInstance().getLoginSession(),
//                ApplicationData.getInstance().getLoginSignKey(), dataParam);
//        String md5Str = MD5Util.MD5(entryString);
//
//        // 组装加密参数
//        List<ParamNameValuePair> newParams = new ArrayList<ParamNameValuePair>();
//        newParams.add(
//                new ParamNameValuePair("UserId", ApplicationData.getInstance().getLoginUserId()));
//        newParams.add(
//                new ParamNameValuePair("Session", ApplicationData.getInstance().getLoginSession()));
//        newParams.add(new ParamNameValuePair("DataParam", dataParam));
//        newParams.add(new ParamNameValuePair("SignData", md5Str));
//
//        super.setParams(newParams);
//    }

    @Override
    protected void onResult(NetData result) {

        if (result != null) {

            // 若需要登录，则说明已经登录失效了
            if (result.getErrCode() == Constants.POST_RESULT_CODE_NEED_LOGIN || result.getErrCode() == Constants.POST_RESULT_CODE_OTHER_LOGIN) {

                if (ApplicationData.getInstance().isLogin()) {

                    // 清空登录信息
                    ApplicationData.getInstance().loginInvaild(false);

                    // 发出事件
                    EventBus.getDefault().post(new OnLoginInvaildEvent(false));
                }
            }
        }
        super.onResult(result);
    }
}
