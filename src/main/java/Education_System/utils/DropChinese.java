package Education_System.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DropChinese {
    private static String REGEX_CHINESE = "[\u4e00-\u9fa5]";// 中文正则
    public static String dropCN(String str){
        Pattern pat = Pattern.compile(REGEX_CHINESE);
        Matcher mat = pat.matcher(str);
        String res = mat.replaceAll("");
        return  res;
    }
}
