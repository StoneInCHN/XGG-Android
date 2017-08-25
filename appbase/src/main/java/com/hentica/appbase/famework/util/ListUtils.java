package com.hentica.appbase.famework.util;

import java.util.Collection;

/**
 * Created by kezhong.
 * E-Mail:396926020@qq.com
 * on 2016/10/28 16:21
 */

public class ListUtils {

    public static boolean isEmpty(Collection list){
        if(list == null || list.isEmpty()){
            return true;
        }
        return false;
    }


}
