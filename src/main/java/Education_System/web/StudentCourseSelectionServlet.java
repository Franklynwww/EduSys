package Education_System.web;

import Education_System.dao.CourseDao;
import Education_System.dao.ExamDao;
import Education_System.dao.UserDao;
import Education_System.dao.impl.CourseDaoImp;
import Education_System.dao.impl.ExamDaoImp;
import Education_System.dao.impl.StudentUserDaoImp;
import Education_System.service.ExamService;
import Education_System.service.Student_Course_Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class StudentCourseSelectionServlet extends HttpServlet {
    private Student_Course_Service student_course_service = new Student_Course_Service();
    private CourseDao courseDao = new CourseDaoImp();
    private ExamDao examDao = new ExamDaoImp();
    private ExamService examService = new ExamService();
    private UserDao userDao = new StudentUserDaoImp();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        // 1、获取请求的参数，即用户提交的要注册的用户名，密码，邮箱，以及用户填写的验证码
        String student_identity=req.getParameter("student_identity");
        String course_id = req.getParameter("course_id");
//        String department = req.getParameter("department");
//        String stage = req.getParameter("stage");



        if (student_course_service.querybyStudentandCourse(student_identity,course_id)!=null) {
            //已经选过该课程
            System.out.println("已经选过该课程!");//打印到控制台上

            // 把回显信息，保存到Request中
            req.setAttribute("msg", "已经选过该课程！！");
            req.setAttribute("loginUser", student_identity);

            req.getRequestDispatcher("/pages/user/course_selection.jsp").forward(req, resp);
        } else {
//            if(courseDao.queryCourseIDbydepartmentandstage(department,stage).indexOf(course_id)==-1){
//                System.out.println("此课程号不合法或不在可选择范围内!");//打印到控制台上
//
//                // 把回显信息，保存到Request中
//                req.setAttribute("msg", "此课程号不合法或不在可选择范围内！！");
//                req.setAttribute("loginUser", student_identity);
//
//                req.getRequestDispatcher("/pages/user/course_selection.jsp").forward(req, resp);
//            }//用户名可用
//            // 调用Service的接口保存到数据库
//            else {
//                Exam exam = examDao.queryExamBycourseidandclazzid(course_id,userDao.queryUserByIdentity(student_identity).getClazz_id());
//                if (examService.queryexamstate(exam)!= exam_state.NOT_ARRANGED) {
//                    System.out.println("此课程已经安排或正在或已结束考试，不可再选!");//打印到控制台上
//
//                    // 把回显信息，保存到Request中
//                    req.setAttribute("msg", "此课程本学期已经安排考试，不可再选！！");
//                    req.setAttribute("loginUser", student_identity);
//
//                    req.getRequestDispatcher("/pages/user/course_selection.jsp").forward(req, resp);
//                } else {
                    student_course_service.select_course(student_identity, course_id);
                    // 跳到注册成功页面 regist_success.jsp
                    req.setAttribute("loginUser", student_identity);
                    req.getRequestDispatcher("/pages/user/student_course.jsp").forward(req, resp);
                }
//            }
        }
//    }

}
