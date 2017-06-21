package com.eryuzhisen.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
/**
 * 生成邀请码
 * 
 * @author huangmiao
 * @version $Id: InviteCodeUtil.java, v 0.1 2017年5月2日 上午11:39:38 huangmiao Exp $
 */
public class InviteCodeUtil {
    public static synchronized String getInviteCode(int length) throws InterruptedException {
        Thread.sleep(1);
        String str="0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder();
        Random r = new Random(System.currentTimeMillis());
        for (int i = 0; i < length; i++) {
            int d =r.nextInt(62);
            sb.append(str.charAt(d));
        }
        return sb.toString();
    }
    
    public static void main(String[] args) {
        Map<String,String> map = new HashMap<String,String>();
        for(int i = 0;i<10000000;i++) {
            String code = "";
            try {
                code = InviteCodeUtil.getInviteCode(6);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(map.containsKey(code)) {
                System.out.println("重复的邀请码");
            } else {
                map.put(code, "1");
            }
        }
    }
}
