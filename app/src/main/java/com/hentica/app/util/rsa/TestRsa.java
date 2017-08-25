package com.hentica.app.util.rsa;

import com.hentica.app.module.entity.login.ResLoginData;

/**
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/3/30.12:00
 */

public class TestRsa {

    public static void main(String[] args){
        String key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCBMPGboxzPh9SApXHBKMQHF31rgB6LQBZxg3VirK9Rbp0qvgIDw+2ygZxPQAkgiK24PTWuBbw2UTNy5NxglSCsCnY8+vJXd8cwZKrBpnwXEcO0Wuh5G8Z++X0AIisMCIoiDZZwWnvqJ7a3vUQIj62qTX259s0UqvjGA7uvoDM9tQIDAQAB";
//        PublicKey publicKey = RsaUtils.getPublicKey(key);
//        String text = "396926020@qq.com";
//        String encrypt = null;
//        try {
//            encrypt = RsaUtils.encrypt(text, publicKey);
//            System.out.println(encrypt);
//            encrypt = RsaUtils.encrypt(text, publicKey);
//            System.out.println(encrypt);
//            encrypt = RsaUtils.encrypt(text, publicKey);
//            System.out.println(encrypt);
//            encrypt = RsaUtils.encrypt(text, publicKey);
//            System.out.println(encrypt);
//            encrypt = RsaUtils.encrypt(text, publicKey);
//            System.out.println(encrypt);
//        } catch (EncryptFailedException e) {
//            e.printStackTrace();
//        }
        ResLoginData.AgentBean bean = new ResLoginData.AgentBean();
        System.out.println(bean.getAgencyLevel());


    }

}
