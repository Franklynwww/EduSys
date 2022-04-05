import Education_System.pojo.timetable;
import Education_System.service.TimetableService;
import org.junit.Test;

public class TimetableServiceTest {
    TimetableService timetableService = new TimetableService();
    @Test
    public void t1(){
        timetableService.add_timetable(new timetable(null,"003","B19-C01","A4-101",2,7,"周一","1-2节"));
    }
    @Test
    public void t2(){
        System.out.println(timetableService.queryTimetableByteacher("20001203"));
    }
}
