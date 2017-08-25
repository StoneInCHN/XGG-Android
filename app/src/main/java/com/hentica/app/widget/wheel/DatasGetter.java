package com.hentica.app.widget.wheel;

import java.util.List;

/**
 * Created by Snow on 2017/2/13.
 */

public interface DatasGetter<T> {

    /**
     * 根据某数据获取其他数据
     * @param selectedDatas 条件数据
     * @return 数据源
     */
    List<T> getDatas(T selectedDatas);

}
