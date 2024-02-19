package com.novel.core.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 昴星
 * @date 2023-10-13 21:00
 * @explain
 */
public class NumsOfChineseUtils {
    public static int countChineseCharacters(String str) {
        if (str == null || str.isEmpty()) {
            return 0;
        }
        int count = 0;
        String regex = "[\u4e00-\u9fa5]"; // 中文字符的 Unicode 范围
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            count++;
        }
        return count;
    }
}
