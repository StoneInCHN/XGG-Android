package com.hentica.app.widget.wheel;

import com.hentica.app.lib.util.TextGetter;

import java.util.ArrayList;
import java.util.List;

/**
 * 时：时
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/4/7.10:43
 */

public class TimeTypeHH implements TimeType<Integer> {

    @Override
    public List<Integer> getWheel1Datas() {
        return getDatas();
    }

    @Override
    public List<Integer> getWheel2Datas() {
        return getDatas();
    }

    @Override
    public TextGetter<Integer> getWheel1TextGetter() {
        return getTextGetter();
    }

    @Override
    public TextGetter<Integer> getWheel2TextGetter() {
        return getTextGetter();
    }

    private List<Integer> getDatas(){
        List<Integer> result = new ArrayList<>();
        for(int i = 0; i < 24; i++){
            result.add(i);
        }
        return result;
    }

    private TextGetter<Integer> getTextGetter(){
        return new TextGetter<Integer>() {
            @Override
            public String getText(Integer obj) {
                int value = 0;
                if(obj != null){
                    value = obj;
                }
                return String.format("%02d:00", value);
            }
        };
    }
}
