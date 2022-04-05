import Education_System.pojo.Student_User;
import Education_System.pojo.Teacher_User;
import Education_System.service.UserService;
import Education_System.service.impl.StudentUserServiceImp;
import Education_System.service.impl.TeacherUserServiceImp;
import org.junit.Test;

public class UserServiceTest {

    UserService teacher_userService = new TeacherUserServiceImp();
    UserService student_userService = new StudentUserServiceImp();

    @Test
    public void registUser() {
        teacher_userService.registUser(new Teacher_User(null, "20001201", "张宇","666666", "bbj168@qq.com","CS","Professor"));
        student_userService.registUser(new Student_User(null, "201930140334", "张宇","666666", "abc168@qq.com","C01"));
    }

    @Test
    public void login() {
        System.out.println(teacher_userService.login("20001201","666666"));
        System.out.println(student_userService.login("20001009","11111"));
    }

    @Test
    public void existsUsername() {
        if (teacher_userService.existsIdentity("20001009")) {
            System.out.println("教师号已存在！");
        } else {
            System.out.println("教师号可用！");
        }

        if (student_userService.existsIdentity("201930140334")) {
            System.out.println("学号已存在！");
        } else {
            System.out.println("学号可用！");
        }
    }
}