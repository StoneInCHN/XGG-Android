package com.hentica.app.widget.wheel;

import org.junit.Assert;
import org.junit.Test;

/**
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/4/7.11:28
 */
public class TakeHHTimeHelperTest {
    @Test
    public void getDefaultValue() throws Exception {
//       String[] arrays = TakeHHTimeHelper.splitStringBy("08:00-15:00", "-");
//       arrays = TakeHHTimeHelper.splitStringBy("08", ":");
//        Assert.assertEquals("不相同", 2, arrays.length);
        int[] arrays = TakeHHTimeHelper.getHours("08:00-15:00");
        Assert.assertEquals("不相同", true, arrays[0] == 8 && arrays[1] == 15);
    }

}