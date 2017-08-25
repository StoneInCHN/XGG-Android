package com.hentica.app.widget.photo;

/**
 * Created by Snow on 2017/5/12.
 */

public interface IGetPhoto {

    /**
     * 设置图片裁剪属性
     * @param aspectX
     * @param aspectY
     * @param outputX
     * @param outputY
     */
    void setCropConfig(int aspectX, int aspectY, int outputX, int outputY);

    /**
     * 设置图片压缩属性
     * @param maxSize 最大大小
     * @param maxWidthPx 宽度最大像素
     * @param maxHeightPx 高度最大像素
     */
    void setCompressConfig(int maxSize, int maxWidthPx, int maxHeightPx);

    void setOnMakePhotoListener(MakePhotoListener l);

}
