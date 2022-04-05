package Education_System.web;

import Education_System.dao.CourseDao;
import Education_System.dao.Student_Course_Dao;
import Education_System.dao.impl.CourseDaoImp;
import Education_System.dao.impl.Student_Course_Dao_Imp;
import Education_System.service.Student_Course_Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class StudentCourseDeletionServlet extends HttpServlet {
    private Student_Course_Service student_course_service = new Student_Course_Service();
    private CourseDao courseDao = new CourseDaoImp();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        // 1、获取请求的参数，即用户提交的要注册的用户名，密码，邮箱，以及用户填写的验证码
        String student_identity=req.getParameter("student_identity");
        String course_id = req.getParameter("course_id");
        List<String> courseids = student_course_service.querycourseId(student_identity);
        Student_Course_Dao student_course_dao = new Student_Course_Dao_Imp();

        if (courseids.indexOf(course_id)==-1) {
            //没有选过该课程
            System.out.println("没有选过该课程!");//打印到控制台上

            // 把回显信息，保存到Request中
            req.setAttribute("msg", "没有选过该课程！！");
            req.setAttribute("loginUser", student_identity);

            req.getRequestDispatcher("/pages/user/course_deletion.jsp").forward(req, resp);
        }// 调用Service的接口保存到数据库
            else {
//                if(student_course_dao.querySCByStudentandCourse(student_identity, course_id).getState().equals("已结课")){
//                    System.out.println("该课程已经结课!");//打印到控制台上
//
//                    // 把回显信息，保存到Request中
//                    req.setAttribute("msg", "该课程已经结课！！");
//                    req.setAttribute("loginUser", student_identity);
//
//                    req.getRequestDispatcher("/pages/user/course_deletion.jsp").forward(req, resp);
//                }
//                else {
                    student_course_service.delete_course(student_identity, course_id);
                    // 跳到注册成功页面 regist_success.jsp
                    req.setAttribute("loginUser", student_identity);
                    req.getRequestDispatcher("/pages/user/student_course.jsp").forward(req, resp);
                }

//            }
    }
}
