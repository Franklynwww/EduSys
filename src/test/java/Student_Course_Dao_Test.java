import Education_System.dao.Student_Course_Dao;
import Education_System.dao.impl.Student_Course_Dao_Imp;
import Education_System.pojo.Student_Course;
import org.junit.Test;

public class Student_Course_Dao_Test {
    private Student_Course_Dao student_course_dao = new Student_Course_Dao_Imp();

    @Test
    public void querybystudent() {
        System.out.println(student_course_dao.querySCByStudent("201930140334"));
    }

    @Test
    public void remove() {
        student_course_dao.removeStudentCourse(new Student_Course(null, "201930140334", "001", 0,null));
    }

    @Test
    public void querybycourseandclazz() {
        System.out.println(student_course_dao.querySCByCourseandClazz("003", "B19-C01"));
    }

    @Test
    public void add() {
        student_course_dao.saveStudentCourse(new Student_Course(null, "201930140334", "002", 0,"未评定"));
    }
}
