package Education_System.utils;

import java.util.HashMap;
import java.util.Map;

public class TimeTableString {
    public static Map<String,String> myMap = new HashMap<String,String>();
    static {
        myMap.put("周一","1");
        myMap.put("周二","2");
        myMap.put("周三","3");
        myMap.put("周四","4");
        myMap.put("周五","5");
        myMap.put("周六","6");
        myMap.put("周日","7");
    }
    public static String getStartTime(String section){
        String noCN_str = DropChinese.dropCN(section);
        String startSection = noCN_str.substring(0,1);
        //String endSection = noCN_str.substring(2);
        //Integer duration = (Integer.parseInt(endSection)-Integer.parseInt(startSection)+1);
        return startSection;
    }
    public static String getCourseTime(String week_day,String section){
        String startSection = getStartTime(section);
        String week = myMap.get(week_day);
        return week+"-"+startSection+"-"+"2";
    }
}
