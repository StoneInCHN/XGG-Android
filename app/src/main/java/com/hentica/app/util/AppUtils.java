package com.hentica.app.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YangChen on 2016/9/30 17:06.
 * E-mail:656762935@qq.com
 */

public class AppUtils {

    private static List<String> pName = new ArrayList<>();

    public static boolean isAvilible(Context context, String packageName){
        init(context);
        return pName.contains(packageName);
    }

    private static void init(Context context){
        PackageManager pm = context.getPackageManager();
        List<PackageInfo> packages = pm.getInstalledPackages(0);
        if(packages == null){
            return;
        }
        pName.clear();
        for(PackageInfo tmp : packages){
            pName.add(tmp.packageName);
        }
    }
}
