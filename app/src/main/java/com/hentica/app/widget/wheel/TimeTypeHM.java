package com.hentica.app.widget.wheel;

import com.hentica.app.lib.util.TextGetter;

import java.util.ArrayList;
import java.util.List;

/**
 * 时：分
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/4/7.10:43
 */

public class TimeTypeHM implements TimeType<Integer> {

    @Override
    public List<Integer> getWheel1Datas() {
        List<Integer> result = new ArrayList<>();
        for(int i = 0; i < 24; i++){
            result.add(i);
        }
        return result;
    }

    @Override
    public List<Integer> getWheel2Datas() {
        List<Integer> result = new ArrayList<>();
        for(int i = 0; i < 60; i++){
            result.add(i);
        }
        return result;
    }

    @Override
    public TextGetter<Integer> getWheel1TextGetter() {
        return new TextGetter<Integer>() {
            @Override
            public String getText(Integer obj) {
                int value = 0;
                if(obj != null){
                    value = obj;
                }
                return String.format("%02d时", value);
            }
        };
    }

    @Override
    public TextGetter<Integer> getWheel2TextGetter() {
        return new TextGetter<Integer>() {
            @Override
            public String getText(Integer obj) {
                int value = 0;
                if(obj != null){
                    value = obj;
                }
                return String.format("%02d分", value);
            }
        };
    }


}
