package Education_System.web;

import Education_System.dao.CourseDao;
import Education_System.dao.impl.CourseDaoImp;
import Education_System.pojo.Course;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddnewcourseServlet extends HttpServlet {
    CourseDao courseDao = new CourseDaoImp();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        //  1、获取请求的参数
        String id = req.getParameter("id");
        String course_name = req.getParameter("course_name");
        String credit_str = req.getParameter("credit");
        float credit = Float.parseFloat(credit_str);
        String reg_department = req.getParameter("reg_department");
        String stage = req.getParameter("stage");
        // 调用 userService.login()登录处理业务
        Course course = courseDao.queryCourseById(id);
        // 如果等于null,说明登录失败!
        if (course!=null) {
            //用户名不可用
            System.out.println("课程号[" + id + "]已存在!");//打印到控制台上

            // 把回显信息，保存到Request中
            req.setAttribute("msg", "课程号已存在！！");

            // 返回注册页面
            req.getRequestDispatcher("/pages/user/add_new_course.jsp").forward(req, resp);
        } else {
            Course course1 = courseDao.queryCourseBycoursename_department_stage(course_name, reg_department, stage);
            if (course1 != null) {
                System.out.println("[" +reg_department+"]在["+stage+"]阶段中已开设的课程已存在该课程名["+ course_name + "的课程");//打印到控制台上

                // 把回显信息，保存到Request中
                req.setAttribute("msg", "[" +reg_department+"]在["+stage+"]阶段中已开设的课程已存在该课程名["+ course_name + "的课程");

                // 返回注册页面
                req.getRequestDispatcher("/pages/user/add_new_course.jsp").forward(req, resp);

            } else {
                // 调用Service的接口保存到数据库
                courseDao.addCourse(new Course(null, id, course_name, credit, reg_department, stage));
                // 跳到注册成功页面 regist_success.jsp
                req.getRequestDispatcher("/pages/user/manager_add_success.jsp").forward(req, resp);
            }
        }
    }
}
