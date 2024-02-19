package com.novel.core.utils;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author 昴星
 * @date 2023-10-23 19:39
 * @explain
 */
public class FileMD5Utils {
    public static String calculateMD5(InputStream in) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] buffer = new byte[8192];
        int numOfBytesRead;
        while ((numOfBytesRead = in.read(buffer)) > 0) {
            md.update(buffer, 0, numOfBytesRead);
        }
        byte[] hashValue = md.digest();

        // 将哈希值转换为十六进制字符串
        StringBuilder sb = new StringBuilder();
        for (byte b : hashValue) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
