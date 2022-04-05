package Education_System.web;

import Education_System.service.Student_Course_Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TeacherChangeScoreServlet extends HttpServlet {
    Student_Course_Service student_course_service = new Student_Course_Service();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        //  1、获取请求的参数
        String teacher_identity = req.getParameter("teacher_identity");
        String student_identity = req.getParameter("student_identity");
        String course_id = req.getParameter("course_id");
        String clazz_id=req.getParameter("clazz_id");
        String s_grade = req.getParameter("grade");

        // 调用 userService.login()登录处理业务
        // 如果等于null,说明登录失败!
            int grade = Integer.parseInt(s_grade);
            // 登录成功
            // 登录成功，跳到成功页面login_success.html
            student_course_service.changescore(student_identity,course_id,grade);
            student_course_service.changestate(student_identity,course_id,"已评定");
            req.setAttribute("login_t_User",teacher_identity);
            req.setAttribute("checking_course",course_id);
            req.setAttribute("checking_clazz",clazz_id);
            req.getRequestDispatcher("/pages/user/teacher_query_student.jsp").forward(req, resp);
    }
}
