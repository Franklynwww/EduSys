package Education_System.Servlet;

import Education_System.dao.*;
import Education_System.dao.impl.*;
import Education_System.pojo.Course;
import Education_System.pojo.Student_Course;
import Education_System.pojo.User;
import Education_System.utils.Request2Str;
import Education_System.utils.Term;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "StuHistoryServlet", value = "/StuHistoryServlet")
public class StuHistoryServlet extends HttpServlet {
    CourseDao courseDao = new CourseDaoImp();
    ClazzDao clazzDao = new ClazzDaoImp();
    UserDao userDao = new StudentUserDaoImp();
    Student_Course_Dao student_course_dao = new Student_Course_Dao_Imp();
    Teacher_Course_Dao teacher_course_dao = new Teacher_Course_Dao_Imp();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String json = Request2Str.getRequestPostStr(request);
        JSONObject jobj = JSON.parseObject(json);
        String loginUser_identity = jobj.getString("userId");
        JSONArray jsonArray = new JSONArray();
        List<Student_Course> student_courses = student_course_dao.querySCByStudent(loginUser_identity);
        for(Student_Course student_course:student_courses){
            Course course = courseDao.queryCourseById(student_course.getCourse_id());
            String term = teacher_course_dao.queryTCByCourseandClazz(course.getId(),userDao.queryUserByIdentity(loginUser_identity).getClazz_id()).getTerm();
//            course_id:'123',
//                    course_name:'数据库',
//                    reg_department:'计算机学院',
//                    term:'2021-2022-1',
//                    state:'已结束',
//                    score:'99',
            String course_id =student_course.getCourse_id();
            String course_name =course.getCourse_name();
            String reg_department =course.getReg_department();
            String state =term.equals(Term.get_current_term()) ?"进行中":"已结课";
            String score = student_course.getState().equals("未评定") ?"未评定":String.valueOf(student_course.getScore());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("course_id",course_id);
            jsonObject.put("course_name",course_name);
            jsonObject.put("reg_department",reg_department);
            jsonObject.put("state",state);
            jsonObject.put("score",score);
            jsonObject.put("term",term);
            jsonArray.add(jsonObject);
        }
        String res = jsonArray.toString();
        response.getWriter().print(res);
    }
}
