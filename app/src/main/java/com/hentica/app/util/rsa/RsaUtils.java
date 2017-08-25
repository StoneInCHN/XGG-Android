package com.hentica.app.util.rsa;

import com.hentica.app.module.config.ConfigDataUtl;
import com.hentica.pay.alipay.Base64;

import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

/**
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/3/30.11:47
 */

public class RsaUtils {

    public static PublicKey publicKey;

    public static PublicKey getPublicKey(String key){
        byte[] bytes = Base64.decode(key);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(bytes);
        try {
            KeyFactory factory = KeyFactory.getInstance("RSA");
            publicKey = factory.generatePublic(spec);
            return publicKey;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getKeyString(Key key){
        byte[] keybytes = key.getEncoded();
        return Base64.encode(keybytes);
    }

    /** 对数据加密 */
    public static String encrypt(String value){
        return encrypt(value, publicKey);
    }

    /** 对数据加密 */
    public static String encrypt(final String value, PublicKey key){
        String result = value;
        try {
//            Cipher cipher = Cipher.getInstance("RSA");//Cipher.getInstance("RSA/ECB/PKCS1Padding");
            //因android端加密与服务器端加密标准不同，造成服务器端解密失败，所以采用"RSA/ECB/PKCS1Padding"
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] enBytes = cipher.doFinal(value.getBytes());
            result = Base64.encode(enBytes);
        } catch (GeneralSecurityException e) {
            ConfigDataUtl.getInstance().getRsaPublicKey(null);
        }
        return result;
    }

}
