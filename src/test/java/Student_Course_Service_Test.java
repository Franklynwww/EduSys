import Education_System.service.Student_Course_Service;
import org.junit.Test;

public class Student_Course_Service_Test {
    private Student_Course_Service student_course_service = new Student_Course_Service();
    @Test
    public void querycourse(){
        System.out.println(student_course_service.querycourse("201930140334"));
    }

    @Test
    public void select_course(){
        student_course_service.select_course("201930140444","001");
    }

    @Test
    public void querybyStudentandCourse(){
        System.out.println(student_course_service.querybyStudentandCourse("201930140444","001"));
    }

    @Test
    public void delete_course(){
        student_course_service.delete_course("201930140334","003");
    }
    @Test
    public void queryoneecore(){
        System.out.println(student_course_service.queryonescore("201930140444","005"));
    }

    @Test
    public void changescore(){
        student_course_service.changescore("201930140444","001",90);
    }

}
