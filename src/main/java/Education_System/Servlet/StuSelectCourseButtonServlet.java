package Education_System.Servlet;

import Education_System.common.Result;
import Education_System.service.Student_Course_Service;
import Education_System.utils.Request2Str;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "StuSelectCourseButtonServlet", value = "/StuSelectCourseButtonServlet")
public class StuSelectCourseButtonServlet extends HttpServlet {
    Student_Course_Service student_course_service = new Student_Course_Service();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String json = Request2Str.getRequestPostStr(request);
        JSONObject jobj = JSON.parseObject(json);

        String student_identity=jobj.getString("student_identity");
        String course_id = jobj.getString("course_id");
//        String department = req.getParameter("department");
//        String stage = req.getParameter("stage");
        Result result = null;



        if (student_course_service.querybyStudentandCourse(student_identity,course_id)!=null) {
            //已经选过该课程
            result = Result.error(-1,"已经选过该课程");
//            System.out.println("已经选过该课程!");//打印到控制台上
//
//            // 把回显信息，保存到Request中
//            req.setAttribute("msg", "已经选过该课程！！");
//            req.setAttribute("loginUser", student_identity);
//
//            req.getRequestDispatcher("/pages/user/course_selection.jsp").forward(req, resp);
        } else {

            student_course_service.select_course(student_identity, course_id);
            result = Result.success();
            // 跳到注册成功页面 regist_success.jsp
//            req.setAttribute("loginUser", student_identity);
//            req.getRequestDispatcher("/pages/user/student_course.jsp").forward(req, resp);
        }
        Gson gson = new Gson();
        String Rson = gson.toJson(result);
        response.getWriter().print(Rson);

    }
}
