package com.eryuzhisen.utils;

import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.Key;
import java.security.NoSuchProviderException;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.eryuzhisen.utils.log.ErYuLogger;

public class AESUtil {
    public static boolean initialized = false;  
    /**
     * AES解密
     * @param content 密文
     * @return
     * @throws InvalidAlgorithmParameterException 
     * @throws NoSuchProviderException 
     */
    public static byte[] decrypt(byte[] content, byte[] keyByte, byte[] ivByte) throws InvalidAlgorithmParameterException {
        initialize();
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            Key sKeySpec = new SecretKeySpec(keyByte, "AES");
            
            cipher.init(Cipher.DECRYPT_MODE, sKeySpec, generateIV(ivByte));// 初始化 
            byte[] result = cipher.doFinal(content);
            return result;
        } catch (Exception e) {
            ErYuLogger.error("decrypt error:",e);
        }
        return null;
    }  
    
    public static void initialize(){  
        if (initialized) return;  
        Security.addProvider(new BouncyCastleProvider());  
        initialized = true;  
    }
    //生成iv  
    public static AlgorithmParameters generateIV(byte[] iv) throws Exception{  
        AlgorithmParameters params = AlgorithmParameters.getInstance("AES");  
        params.init(new IvParameterSpec(iv));  
        return params;  
    } 
}
