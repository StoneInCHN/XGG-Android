package com.hentica.app.test.module.datetest;

import com.hentica.app.test.base.BaseTest;

import org.junit.Test;

/**
 * Created by Snow on 2016/11/17.
 */

public class DateHelperTest extends BaseTest {

    @Test
    public void testGetDateString(){
//        String value = DateHelper.getDateStr(new Date());
//        System.out.println(value);
//        Assert.assertEquals("不相等", "2016-11-17", value);
        // TODO Auto-generated method stub
        byte[] bs = { 0, 10, 0, 5, 0, 0, 0, 49, -84, -106, 82, -94, 48, 50, 56, 32, 32, 32, 0, 1, 0, 2, 6, 51, -22, -3,
                1, -46, 21, -20, 64, -112, 71, 20, 122, -31, 71, -82, 48, 100, 57, 99, 53, 53, 100, 50, 55, 55, 98, 100,
                48, 54, 48, 57, 100, 102, 52, 56, 49, 52, 49, 49, 49, 57, 99, 48, 97, 54, 97, 101 };
        String hexStr = bytesToHexString(bs);
        System.out.println(hexStr);
    }

    /**
     * byte数组转换成16进制字符串
     * @param src
     * @return
     */
    public static String bytesToHexString(byte[] src){
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

}
