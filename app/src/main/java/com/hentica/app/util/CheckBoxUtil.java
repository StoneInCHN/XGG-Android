package com.hentica.app.util;

import android.content.Context;
import android.widget.CheckBox;

import java.util.List;

/**
 * Created by YangChen on 2017/3/17 11:22.
 * E-mail:656762935@qq.com
 */

public class CheckBoxUtil {

 //   private static CheckBoxUtil mInstance = null;

    private Context mContext;
    private List<CheckBox> mCheckBoxList;
    private int mCheckedColorId;
    private int mNormalColorId;

    public CheckBoxUtil(Context context, List<CheckBox> checkBoxList,int checkedColorId, int normalColorId){
        mContext = context;
        mCheckBoxList = checkBoxList;
        mCheckedColorId = checkedColorId;
        mNormalColorId = normalColorId;
    }

    /** 切换checkbox选中和未选中时的颜色 */
    public void swichCheckBoxColor(CheckBox checkedBox){
        if(!ArrayListUtil.isEmpty(mCheckBoxList)){
            for(CheckBox checkBox : mCheckBoxList){
                if(checkBox.getId() == checkedBox.getId()){
                    checkBox.setTextColor(mContext.getResources().getColor(mCheckedColorId));
                }else {
                    checkBox.setTextColor(mContext.getResources().getColor(mNormalColorId));
                }
            }
        }
    }
}
