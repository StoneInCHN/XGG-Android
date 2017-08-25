package com.hentica.app.module.update;

import com.hentica.app.framework.fragment.FragmentListener;

/**
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/4/11.20:20
 */

public interface UpdateAppView<T> extends FragmentListener.UsualViewOperator{

    void setUpdateCheckData(T data);

}
