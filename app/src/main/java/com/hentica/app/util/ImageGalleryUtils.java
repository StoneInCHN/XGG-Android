package com.hentica.app.util;

import com.hentica.app.framework.data.ApplicationData;
import com.hentica.appbase.famework.util.ListUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/4/10.21:18
 */

public class ImageGalleryUtils {

    public static List<String> wrapImageUrls(List<String> images){
        List<String> result = new ArrayList<>();
        if (!ListUtils.isEmpty(images)) {
            for (String env : images) {
                result.add(ApplicationData.getInstance().getImageUrl(env));
            }
        }
        return result;
    }

}
