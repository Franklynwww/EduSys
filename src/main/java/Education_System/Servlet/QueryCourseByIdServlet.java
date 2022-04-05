package Education_System.Servlet;

import Education_System.dao.CourseDao;
import Education_System.dao.impl.CourseDaoImp;
import Education_System.pojo.Clazz;
import Education_System.pojo.Course;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "QueryCourseByIdServlet", value = "/QueryCourseByIdServlet")
public class QueryCourseByIdServlet extends HttpServlet {
    CourseDao courseDao =new CourseDaoImp();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        List<Course> l = courseDao.queryAllCourse();
        JSONArray jsonArray = new JSONArray();
        for (Course course:l)  {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("courseCode",course.getId());
            jsonObject.put("coursesName",course.getCourse_name());
            jsonObject.put("courseCredit",course.getCredit());
            jsonObject.put("courseDepartment",course.getReg_department());
            jsonObject.put("courseStage",course.getStage());
            jsonArray.add(jsonObject);
        }
        String res = jsonArray.toString();
        response.getWriter().print(res);
    }
}
