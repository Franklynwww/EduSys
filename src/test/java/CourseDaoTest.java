import Education_System.dao.CourseDao;
import Education_System.dao.impl.CourseDaoImp;
import Education_System.pojo.Course;
import org.junit.Test;

public class CourseDaoTest {
    private CourseDao courseDao = new CourseDaoImp();
    @Test
    public void t(){
        System.out.println(courseDao.queryCourseById("001"));
    }


    @Test
    public void t1(){
        System.out.println(courseDao.addCourse(new Course(null,"002","计算机组成原理",(float) 4.5,"计算机学院","本科")));
    }

    @Test
    public void t2(){
        System.out.println(courseDao.queryCourseBycoursename_department_stage("计算机概论","计算机学院","本科"));
    }
}
