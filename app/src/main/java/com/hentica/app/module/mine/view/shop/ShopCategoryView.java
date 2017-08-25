package com.hentica.app.module.mine.view.shop;

import com.hentica.app.framework.fragment.FragmentListener;
import com.hentica.app.module.entity.mine.shop.ResShopCategory;

import java.util.List;

/**
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/4/5.16:33
 */

public interface ShopCategoryView extends FragmentListener.UsualViewOperator{

    /**
     * 设置行业类型数据
     * @param datas
     */
    void setCategory(List<ResShopCategory> datas);

}
