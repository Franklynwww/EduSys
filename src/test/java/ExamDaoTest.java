import Education_System.dao.ExamDao;
import Education_System.dao.impl.ExamDaoImp;
import Education_System.pojo.Exam;
import org.junit.Test;

import java.sql.Timestamp;

public class ExamDaoTest {
    ExamDao examDao = new ExamDaoImp();
    @Test
    public void t1(){
        System.out.println(examDao.queryByExamId("E01"));
    }
    @Test
    public void t2(){
        System.out.println(examDao.queryExamBycourseidandclazzid("001","计算机类1班"));
    }
    @Test
    public void t3(){
        System.out.println(examDao.queryExamBycourseid("001"));
    }
    @Test
    public void t4(){
        System.out.println(examDao.queryExamByclazzid("C01"));
    }
    @Test
    public void t5(){
        System.out.println(examDao.add_Exam(new Exam(null,"E02","002","C02","A3-204", Timestamp.valueOf("2021-10-1 14:00:00"),Timestamp.valueOf("2021-10-1 16:00:00"))));
    }
    @Test
    public void t6(){
        System.out.println(examDao.queryAllStarttimeByclassroom("A3-201"));
        System.out.println(examDao.queryAllEndtimeByclassroom("A3-201"));
    }
    @Test
    public void t7(){
        System.out.println(examDao.queryAllStarttimeByclazzid("B19-C01"));
        System.out.println(examDao.queryAllEndtimeByclazzid("B19-C01"));
    }
}
