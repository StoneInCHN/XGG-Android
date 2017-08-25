package com.hentica.app.module.mine.ui.intf;

import android.view.View;

/**
 * Created by Snow on 2017/5/28 0028.
 */

public interface OnDeleteViewClickListener<T> {

    void onDeleteClick(View view, int pos, T data);

}
