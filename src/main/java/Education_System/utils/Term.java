package Education_System.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Term {
    public static String get_current_year(){
        Calendar date = Calendar.getInstance();
        return String.valueOf(date.get(Calendar.YEAR));
    }

    public static String get_current_month(){
        Calendar date = Calendar.getInstance();
        return String.valueOf(date.get(Calendar.MONTH)+1);
    }

    public static String get_current_season(){
        int current_month = Integer.parseInt(get_current_month());
        return (current_month>=2 && current_month<=7?"2":"1");
    }

    public static String get_current_schoolyear(){
        int year = Integer.parseInt(get_current_year());
        int month = Integer.parseInt(get_current_month());
        if(month>=1 && month<=7){
            return String.valueOf(year - 1) +"-"+ year;
        }
        else {
            return String.valueOf(year)+"-"+ (year+1);
        }
    }

    public static String get_current_term(){
        return get_current_schoolyear()+"-"+get_current_season();
    }

    public static String get_first_month(String season){
        return String.valueOf(Integer.parseInt(season)==1?8:2);
    }

    public static String get_last_month(String season){
        return String.valueOf(Integer.parseInt(season)==1?1:7);
    }

    public static String extractschoolyear(String term){
        return term.substring(0,9);
    }

    public static String extractseason(String term){
        return term.substring(10);
    }

    public static String extractfirstyear(String term){
        return term.substring(0,4);
    }

    public static String extractsecondyear(String term){
        return term.substring(5,9);
    }

    public static int cmp(String term1,String term2){
        int firstyear_1 = Integer.parseInt(extractfirstyear(term1));
        int season_1 = Integer.parseInt(extractseason(term1));
        int firstyear_2 = Integer.parseInt(extractfirstyear(term2));
        int season_2 = Integer.parseInt(extractseason(term2));
        if(firstyear_1==firstyear_2 && season_1==season_2)return 0;
        else {
            if(firstyear_1<firstyear_2 || (firstyear_1==firstyear_2 && season_1<season_2)){
                return -1;
            }
            else {
                return 1;
            }
        }
    }


    public static String get_first_date(String term){
        String first_year = extractfirstyear(term);
        String second_year = extractsecondyear(term);
        String season = extractseason(term);
        int year = Integer.parseInt(first_year);
        if(season.equals("2")){
            year = Integer.parseInt(first_year+1);
        }
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR,year);
        cal.set(Calendar.MONTH,Integer.parseInt(get_first_month(season))-1);
        cal.set(Calendar.DAY_OF_MONTH,1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        return sdf.format(cal.getTime());

    }

    public static String get_last_date(String term){
        String second_year = extractsecondyear(term);
        String season = extractseason(term);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR,Integer.parseInt(second_year));
        cal.set(Calendar.MONTH,Integer.parseInt(get_last_month(season))-1);
        int last_day = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH,last_day);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
        return sdf.format(cal.getTime());

    }

    public static  boolean time_in_term(String term, Timestamp exam_timestamp){

        Timestamp timestamp_start = Timestamp.valueOf(get_first_date(term));
        Timestamp timestamp_end = Timestamp.valueOf(get_last_date(term));
        return exam_timestamp.after(timestamp_start) && exam_timestamp.before(timestamp_end);


    }

    public static boolean isvalidinputterm(String input_term){
        int input_first_year = Integer.parseInt(extractfirstyear(input_term));
        int input_second_year = Integer.parseInt(extractsecondyear(input_term));
        int input_season = Integer.parseInt(extractseason(input_term));
        if(input_second_year-input_first_year!=1)return false;
        else {
            String current_term = get_current_term();
            if (cmp(input_term, current_term) != -1) return true;
            else return false;
        }
    }

    public static void main(String[] args) {
        System.out.println(get_current_year());
        System.out.println(get_current_month());
        System.out.println(get_current_term());
        System.out.println(get_first_date("2019-2020-1"));
        System.out.println(get_last_date("2019-2020-1"));
        System.out.println(isvalidinputterm("2023-2025-2"));
    }
}
