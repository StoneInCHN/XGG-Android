package com.hentica.app.test.shadow;

import com.litesuits.http.concurrent.SmartExecutor;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

/**
 * @author mili
 * @createTime 2016/10/12
 */
@Implements(SmartExecutor.class)
public class SmartExecutorShadow {

    // 立即执行
    @Implementation
    public void execute(final Runnable command) {
        if (command != null){
            command.run();
        }
    }
}
