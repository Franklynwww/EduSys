package Education_System.Servlet;

import Education_System.dao.CourseDao;
import Education_System.dao.Teacher_Course_Dao;
import Education_System.dao.impl.CourseDaoImp;
import Education_System.dao.impl.Teacher_Course_Dao_Imp;
import Education_System.pojo.Exam;
import Education_System.pojo.Teacher_Course;
import Education_System.service.ExamService;
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

@WebServlet(name = "StuExamServlet", value = "/StuExamServlet")
public class StuExamServlet extends HttpServlet {
    ExamService examService = new ExamService();
    Teacher_Course_Dao teacher_course_dao = new Teacher_Course_Dao_Imp();
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
        String loginUser_identity = jobj.getString("userId");
        /*根据这个学生的identity查询出所有的*/
        List<Exam> L = examService.query_Arranged_Not_Started_Exam_By_StudentIdentity(loginUser_identity);
        JSONArray jsonArray = new JSONArray();
        for(Exam exam:L){
            Teacher_Course teacher_course = teacher_course_dao.queryTCByCourseandClazz(exam.getCourse_id(),exam.getClazz_id());
            if(teacher_course.getTerm().equals(Term.get_current_term())) {
//                course_id:'123',
//                        course_name:'456',
//                        classroom:'a4-111',
//                        start_time:'2021-9-9 18:00',
//                        end_time:'2021-9-9 20:00',
                String course_id =exam.getCourse_id();
                String course_name =courseDao.queryCourseById(exam.getCourse_id()).getCourse_name();
                String classroom =exam.getClassroom();
                String start_time = exam.getStart_time().toString();
                String end_time = exam.getEnd_time().toString();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("course_id",course_id);
                jsonObject.put("classroom",classroom);
                jsonObject.put("course_name",course_name);
                jsonObject.put("start_time",start_time);
                jsonObject.put("end_time",end_time);
                jsonArray.add(jsonObject);
            }
        }
        String res = jsonArray.toString();
        response.getWriter().print(res);
        //System.out.println(jsonArray.toJSONString());
    }
}
