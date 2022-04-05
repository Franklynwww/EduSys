import Education_System.dao.TimetableDao;
import Education_System.dao.impl.TimetableDaoImp;
import Education_System.pojo.timetable;
import org.junit.Test;

public class TimetableDaoTest {
    private TimetableDao timetableDao = new TimetableDaoImp();
    @Test
    public void t1(){
        System.out.println(timetableDao.queryTimetableBycourseIdandclazzId("001","B19-C01"));
    }
    @Test
    public void t2(){
        System.out.println(timetableDao.saveTimetable(new timetable(null,"001","B19-C02","A3-301",1,10,"周一","1-2节")));
    }
    @Test
    public void t3(){
        System.out.println(timetableDao.removeTimetable(new timetable(null,null,"B19-C01",null,1,12,"周五","5-7节")));
    }
    @Test
    public void t4(){
        System.out.println(timetableDao.removeTimetableBycourseIdandclazzId("001","B19-C03"));
    }

}
