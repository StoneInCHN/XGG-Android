package com.hentica.app.module.mine.view;

import com.hentica.app.framework.fragment.FragmentListener;

/**
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/4/6.16:47
 */

public interface QrCodeView<T> extends FragmentListener.UsualViewOperator{

    /**
     * 设置二维码数据
     * @param datas
     */
    void setQrCode(byte[] datas);

    /**
     * 设置数据
     * @param data
     */
    void setData(T data);
}
