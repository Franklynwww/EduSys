package Education_System.Servlet;

import Education_System.dao.ClazzDao;
import Education_System.dao.CourseDao;
import Education_System.dao.Teacher_Course_Dao;
import Education_System.dao.impl.ClazzDaoImp;
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

@WebServlet(name = "TeacherExamServlet", value = "/TeacherExamServlet")
public class TeacherExamServlet extends HttpServlet {
    ExamService examService = new ExamService();
    CourseDao courseDao = new CourseDaoImp();
    ClazzDao clazzDao = new ClazzDaoImp();
    Teacher_Course_Dao teacher_course_dao = new Teacher_Course_Dao_Imp();
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
        String login_t_User_identity = jobj.getString("userId");
        JSONArray jsonArray = new JSONArray();
        List<Exam> L = examService.query_Arranged_Not_Started_Exam_By_TeacherIdentity(login_t_User_identity);
        for (Exam exam: L){
            Teacher_Course teacher_course = teacher_course_dao.queryTCByCourseandClazz(exam.getCourse_id(),exam.getClazz_id());
            if(teacher_course.getTerm().equals(Term.get_current_term())){
                JSONObject jsonObject = new JSONObject();
//                course_id:'123',
//                        course_name:'456',
//                        classroom:'a4-111',
//                        start_time:'2021-9-9 18:00',
//                        end_time:'2021-9-9 20:00',
//                        examClassCode:"123",
//                        examClass:"计科1",
//                        grade:"2019级"
                jsonObject.put("course_id",exam.getCourse_id());
                jsonObject.put("course_name",courseDao.queryCourseById(exam.getCourse_id()).getCourse_name());
                jsonObject.put("examClassCode",exam.getClazz_id());
                jsonObject.put("examClass",clazzDao.queryClazzById(exam.getClazz_id()).getClazz_name());
                jsonObject.put("grade",clazzDao.queryClazzById(exam.getClazz_id()).getGrade());
                jsonObject.put("classroom",exam.getClassroom());
                jsonObject.put("start_time",exam.getStart_time().toString());
                jsonObject.put("end_time",exam.getEnd_time().toString());
                jsonArray.add(jsonObject);
            }
        }
        String res = jsonArray.toString();
        response.getWriter().print(res);
    }
}
