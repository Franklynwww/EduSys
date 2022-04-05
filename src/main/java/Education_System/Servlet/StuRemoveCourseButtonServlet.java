package Education_System.Servlet;

import Education_System.common.Result;
import Education_System.dao.CourseDao;
import Education_System.dao.Student_Course_Dao;
import Education_System.dao.impl.CourseDaoImp;
import Education_System.dao.impl.Student_Course_Dao_Imp;
import Education_System.service.Student_Course_Service;
import Education_System.utils.Request2Str;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "StuRemoveCourseButtonServlet", value = "/StuRemoveCourseButtonServlet")
public class StuRemoveCourseButtonServlet extends HttpServlet {
    private Student_Course_Service student_course_service = new Student_Course_Service();
    private CourseDao courseDao = new CourseDaoImp();
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
        List<String> courseids = student_course_service.querycourseId(student_identity);
        Result result = null;
        if (courseids.indexOf(course_id)==-1) {
            //没有选过该课程
            result = Result.error(-1,"没有选过该课程");

//            // 把回显信息，保存到Request中
//            req.setAttribute("msg", "没有选过该课程！！");
//            req.setAttribute("loginUser", student_identity);
//
//            req.getRequestDispatcher("/pages/user/course_deletion.jsp").forward(req, resp);
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
