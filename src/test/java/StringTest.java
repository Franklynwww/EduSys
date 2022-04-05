import Education_System.utils.DropChinese;
import Education_System.utils.TimeTableString;
import org.junit.Test;
public class StringTest {
    @Test
    public void TimeTableStringTest(){
//        String str = "3-4节";
//        String noCN_str = DropChinese.dropCN(str);
//        String startSection = noCN_str.substring(0,1);
//        String endSection = noCN_str.substring(2);
//        Integer duration = (Integer.parseInt(endSection)-Integer.parseInt(startSection)+1);
//        System.out.println(duration);
        System.out.println(TimeTableString.getCourseTime("周二","3-4节"));
    }
}
