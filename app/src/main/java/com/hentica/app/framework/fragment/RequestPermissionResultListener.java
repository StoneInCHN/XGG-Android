package com.hentica.app.framework.fragment;

import android.support.annotation.NonNull;

/**
 * Created by Snow on 2017/5/10.
 */

public interface RequestPermissionResultListener {

    void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults);

}
