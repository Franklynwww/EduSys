import Education_System.service.Teacher_Course_Service;
import org.junit.Test;

public class Teacher_Course_Service_Test {
    private Teacher_Course_Service teacher_course_service = new Teacher_Course_Service();
    @Test
    public void t(){
        System.out.println(teacher_course_service.querycourseIdandclazzId("20001201"));
    }
    @Test
    public void t1(){
        System.out.println(teacher_course_service.querystudentIdentity("001","C02"));
    }
    @Test
    public void t2(){
        teacher_course_service.add_course_clazz("20001203","001","B19-C01","2020-2021-1");
    }

    @Test
    public void t3(){
        teacher_course_service.delete_course_clazz("20001201","005","C02");
    }


}
