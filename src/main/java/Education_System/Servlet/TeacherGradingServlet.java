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

@WebServlet(name = "TeacherGradingServlet", value = "/TeacherGradingServlet")
public class TeacherGradingServlet extends HttpServlet {
    Student_Course_Service student_course_service = new Student_Course_Service();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String json = Request2Str.getRequestPostStr(request);
        // 转换为jobj以便可以通过key开读取value
        JSONObject jobj = JSON.parseObject(json);

        String student_identity = jobj.getString("student_identity");
        String course_id = jobj.getString("course_id");
        int s_grade = jobj.getInteger("grade");
        student_course_service.changescore(student_identity,course_id,s_grade);
        student_course_service.changestate(student_identity,course_id,"已评定");
        Result result = Result.success(); //? 这里不太妥!
        Gson gson = new Gson();
        String Rson = gson.toJson(result);
        response.getWriter().print(Rson);
    }
}
