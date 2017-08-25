package com.hentica.app.module.mine.view.statistics;

import com.hentica.app.framework.fragment.ptr.PtrView;
import com.hentica.app.module.entity.mine.ResMineScore;

/**
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/4/13.20:20
 */

public interface ScorePtrView extends PtrView<ResMineScore> {

    /**
     * 设置积分转换数量
     * @param score
     */
    void setParseScore(String score);

}
