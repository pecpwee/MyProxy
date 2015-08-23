package com.fkgfw.proxy.Secure;


import com.fkgfw.proxy.Config.ConfigManager;
import com.fkgfw.proxy.Config.ConfigPojo;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.util.Arrays;


public class TransSecuritySupport {
    //KeyGenerator提供对称密钥生成器的功能，支持各种算法
    KeyGenerator keygen;
    //SecretKey负责保存对称密钥
    SecretKey deskey;
    //Cipher负责完成加密或解密工作
    private static Cipher mEncryptCipher;
    private static Cipher mDecryptCipher;
    byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    IvParameterSpec ivspec = new IvParameterSpec(iv);

    ConfigPojo configObj;

    public TransSecuritySupport(ConfigPojo configObj) {
        Security.addProvider(new com.sun.crypto.provider.SunJCE());
        try {
            deskey = getKeyFromString(configObj.getENCRYPT_SEED());
            //生成Cipher对象，指定其支持AES算法


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        this.configObj=configObj;


    }

    public Cipher getEncryptCipher() {

        try {
            mEncryptCipher = Cipher.getInstance("AES/CTR/NoPadding/");//It's very very important for networking.
            //if it's just "AES" ,it always block when read() invoke,because it expect more padding,but there is none

            mEncryptCipher.init(Cipher.ENCRYPT_MODE, getKeyFromString(configObj.getENCRYPT_SEED()), ivspec);


        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        return mEncryptCipher;

    }

    public Cipher getDecryptCipher() {
        try {
            mDecryptCipher = Cipher.getInstance("AES/CTR/NoPadding/");
            mDecryptCipher.init(Cipher.DECRYPT_MODE, getKeyFromString(configObj.getENCRYPT_SEED()), ivspec);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        return mDecryptCipher;


    }

    private static SecretKeySpec getKeyFromString(String keyseed) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        byte[] key = keyseed.getBytes("UTF-8");
        MessageDigest sha = MessageDigest.getInstance("SHA-1");
        key = sha.digest(key);
        key = Arrays.copyOf(key, 16); //128bit

        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
        return secretKeySpec;
    }


}
