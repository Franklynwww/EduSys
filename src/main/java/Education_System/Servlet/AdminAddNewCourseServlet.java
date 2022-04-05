package Education_System.Servlet;

import Education_System.common.Result;
import Education_System.dao.CourseDao;
import Education_System.dao.impl.CourseDaoImp;
import Education_System.pojo.Course;
import Education_System.utils.Request2Str;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "AdminAddNewCourseServlet", value = "/AdminAddNewCourseServlet")
public class AdminAddNewCourseServlet extends HttpServlet {
    CourseDao courseDao = new CourseDaoImp();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String json = Request2Str.getRequestPostStr(request);
        JSONObject jobj = JSON.parseObject(json);

        String id = jobj.getString("courseCode");
        String course_name = jobj.getString("courseName");
        String credit_str = jobj.getString("courseCredit");
        float credit = Float.parseFloat(credit_str);
        String reg_department = jobj.getString("department");
        String stage = jobj.getString("stage");
        Result result = null;
        // 调用 userService.login()登录处理业务
        Course course = courseDao.queryCourseById(id);
        // 如果等于null,说明登录失败!
        if (course!=null) {
            //用户名不可用
            result = Result.error(-1,"课程号" + id + "已存在!");
//            System.out.println("课程号[" + id + "]已存在!");//打印到控制台上
//
//            // 把回显信息，保存到Request中
//            req.setAttribute("msg", "课程号已存在！！");
//
//            // 返回注册页面
//            req.getRequestDispatcher("/pages/user/add_new_course.jsp").forward(req, resp);
        } else {
            Course course1 = courseDao.queryCourseBycoursename_department_stage(course_name, reg_department, stage);
            if (course1 != null) {
                result = Result.error(-1,reg_department+"在"+stage+"阶段中已开设的课程已存在该课程名"+ course_name + "的课程");
//                System.out.println("[" +reg_department+"]在["+stage+"]阶段中已开设的课程已存在该课程名["+ course_name + "的课程");//打印到控制台上
//
//                // 把回显信息，保存到Request中
//                req.setAttribute("msg", "[" +reg_department+"]在["+stage+"]阶段中已开设的课程已存在该课程名["+ course_name + "的课程");
//
//                // 返回注册页面
//                req.getRequestDispatcher("/pages/user/add_new_course.jsp").forward(req, resp);

            } else {
                // 调用Service的接口保存到数据库
                courseDao.addCourse(new Course(null, id, course_name, credit, reg_department, stage));
                result = Result.success();
                // 跳到注册成功页面 regist_success.jsp
//                req.getRequestDispatcher("/pages/user/manager_add_success.jsp").forward(req, resp);
            }
        }
        Gson gson = new Gson();
        String Rson = gson.toJson(result);
        response.getWriter().print(Rson);
    }
}
