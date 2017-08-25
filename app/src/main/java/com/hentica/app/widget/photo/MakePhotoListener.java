package com.hentica.app.widget.photo;

import java.util.List;

/**
 * Created by Snow on 2017/5/12.
 */

/**
 * 取图片事件
 */
public interface MakePhotoListener {


    /**
     * 取图片输入完成
     *
     * @param imgFilePaths 图片路径
     */
    void onComplete(List<String> imgFilePaths);

}
