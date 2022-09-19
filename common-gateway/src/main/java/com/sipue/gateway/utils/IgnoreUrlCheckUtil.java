package com.sipue.gateway.utils;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 验证请求url是否忽略
 * @Author mustang
 * @date 2022/1/24 17:03
 */
public class IgnoreUrlCheckUtil {

    private static final String TMP_PLACEHOLDER = "@@@@@#####$$$$$";
    private static List<Pattern> urlToPatterns(List<String> urls) {
        List<Pattern> patterns = new ArrayList<>();
        if(CollectionUtils.isEmpty(urls)) return patterns;

        for (String patternItem : urls) {
            patternItem = patternItem.trim();
            if("".equals(patternItem)) continue;

            patternItem = patternItem.replace("**", TMP_PLACEHOLDER);
            patternItem = patternItem.replace("*", "[^/]*?");//替换*
            patternItem = patternItem.replace(TMP_PLACEHOLDER, "**");
            patternItem = patternItem.replace("**", ".*?");//替换**
            patterns.add(Pattern.compile(patternItem));
        }

        return patterns;
    }

    public static boolean check(List<String> ignoreUrls,String url) {
        List<Pattern> patterns=urlToPatterns(ignoreUrls);
        for (Pattern pattern : patterns) {
            Matcher matcher = pattern.matcher(url);
            if(matcher.matches()) return true;
        }
        return false;
    }

    public static void main(String[] args)
    {
        List<String> urls= Arrays.asList(new String[]{"/login/**","/abc/*/*","**/v3/api-docs","**/callback/**"});
        System.out.println(check(urls,"/api/order/wx/pay/callback/aa"));
    }

}
