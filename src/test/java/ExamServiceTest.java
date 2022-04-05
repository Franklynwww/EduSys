import Education_System.dao.ExamDao;
import Education_System.dao.impl.ExamDaoImp;
import Education_System.pojo.Exam;
import Education_System.service.ExamService;
import org.junit.Test;

public class ExamServiceTest {
    ExamService examService = new ExamService();
    ExamDao examDao = new ExamDaoImp();
    @Test
    public void t1(){
        System.out.println(examService.query_Arranged_Not_Started_Exam_By_TeacherIdentity("20001201"));
    }
    @Test
    public void t2(){
        System.out.println(examService.query_Arranged_Not_Started_Exam_By_StudentIdentity("201930140444"));
    }
    @Test
    public void t3(){
        System.out.println(examService.query_Arranged_Not_Started_Exam_By_StudentIdentity("201930140334"));
    }
    @Test
    public void t4(){
        System.out.println(examService.query_All_Arranged_Exam_By_TeacherIdentity("20001201"));
    }
    @Test
    public void t5(){
        System.out.println(examService.query_All_Arranged_Exam_By_StudentIdentity("201930140334"));
    }

    @Test
    public void t6(){
        Exam exam= examDao.queryByExamId("E01");
        System.out.println(examService.queryexamstate(exam));
    }

    @Test
    public void t7(){
        System.out.println(examService.is_legal_start_end("2021-10-27 19:00:00","2021-10-27 19:00:01"));
    }

    @Test
    public void t8(){
        System.out.println(examService.if_time_conf_in_classroom("2021-07-01 07:00:00","2021-07-01 10:00:00","A3-201"));
        System.out.println(examService.if_time_conf_in_classroom("2021-07-01 07:00:00","2021-07-01 08:00:00","A3-201"));
        System.out.println(examService.if_time_conf_in_classroom("2021-07-01 08:30:00","2021-07-01 09:00:00","A3-201"));
    }

    @Test
    public void t9(){
        System.out.println(examService.if_time_conf_in_clazz("2021-07-01 07:00:00","2021-07-01 10:00:00","B19-C01"));
        System.out.println(examService.if_time_conf_in_clazz("2021-10-30 16:00:00","2021-10-30 17:30:00","B19-C01"));
        System.out.println(examService.if_time_conf_in_clazz("2021-10-30 18:00:00","2021-10-30 19:30:00","B19-C01"));
        System.out.println(examService.if_time_conf_in_clazz("2021-11-30 18:00:00","2021-11-30 19:30:00","B19-C01"));
    }
}
