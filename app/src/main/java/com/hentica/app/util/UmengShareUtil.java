/**
 *
 */
package com.hentica.app.util;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.fiveixlg.app.customer.R;

/**
 * 有盟分享辅助类
 *
 * @author mili
 * @createTime 2016-5-23 下午4:06:46
 */
public class UmengShareUtil {

    /**
     * 直接分享，不弹出界面
     *
     * @param activity 当前界面
     * @param platform 分享平台
     * @param title    分享标题
     * @param text     分享内容
     * @param imgUrl   图片地址
     * @param url      分享网址
     */
    public static void share(Activity activity, SHARE_MEDIA platform, String title, String text,
                             String url, String imgUrl, UMShareListener listener) {

        // 若要显示列表，使用 setDisplayList()
        // 若要直接分享，使用 setPlatform
        ShareAction shareAction = new ShareAction(activity);
        shareAction.setPlatform(platform).withTitle(title).withText(text).withTargetUrl(url).setCallback(listener);
        shareAction.withMedia(createUiImage(activity, imgUrl));

        shareAction.share();
    }

    /**
     * 弹出分享界面
     *
     * @param activity 当前界面
     * @param title    分享标题
     * @param text     分享内容
     * @param imgUrl   图片地址
     * @param url      分享网址
     * @param listener 回调
     */
    public static void shareList(Activity activity, String title, String text, String url,
                                 String imgUrl, UMShareListener listener) {

        // 若要显示列表，使用 setDisplayList()
        // 若要直接分享，使用 setPlatform
        ShareAction shareAction = new ShareAction(activity);
        shareAction.setDisplayList(getShareMedias()).withTitle(title).withText(text).withTargetUrl(url).setCallback(listener);
//        shareAction.withMedia(createUiImage(activity, imgUrl));
        shareAction.withMedia(createUiImage(activity, imgUrl));

        shareAction.open();
    }

    // 创建分享图片
    private static UMImage createUiImage(Context context, String imgUrl) {

        if (TextUtils.isEmpty(imgUrl)) {

            return new UMImage(context, R.drawable.ic_launcher);
        } else {

            return new UMImage(context, imgUrl);
        }
    }

    // 取得所有分享平台
    private static SHARE_MEDIA[] getShareMedias() {

        return new SHARE_MEDIA[]{

                SHARE_MEDIA.WEIXIN,
                SHARE_MEDIA.WEIXIN_CIRCLE/*,
                SHARE_MEDIA.QQ,
                SHARE_MEDIA.QZONE,
                SHARE_MEDIA.SINA*/
        };

    }
}
