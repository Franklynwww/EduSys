package Education_System.Servlet;

import Education_System.dao.ClazzDao;
import Education_System.dao.CourseDao;
import Education_System.dao.Teacher_Course_Dao;
import Education_System.dao.impl.ClazzDaoImp;
import Education_System.dao.impl.CourseDaoImp;
import Education_System.dao.impl.Teacher_Course_Dao_Imp;
import Education_System.pojo.Clazz;
import Education_System.pojo.Course;
import Education_System.pojo.Teacher_Course;
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

@WebServlet(name = "TeacherHistoyServlet", value = "/TeacherHistoyServlet")
public class TeacherHistoyServlet extends HttpServlet {
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
        List<Teacher_Course> teacher_courses = teacher_course_dao.queryTCByTeacher(login_t_User_identity);
        JSONArray jsonArray = new JSONArray();
        for(Teacher_Course teacher_course:teacher_courses) {
            JSONObject jsonObject = new JSONObject();
            Course course = courseDao.queryCourseById(teacher_course.getCourse_id());
            Clazz clazz = clazzDao.queryClazzById(teacher_course.getClazz_id());
            String term = teacher_course.getTerm();
//            course_id:'123',
//                    course_name:'456',
//                    classCode:"123",
//                    className:"计科1",
//                    grade:"2019级",
//                    stage:"本科",
//                    term:"2020-2021-1",
//                    curStates:1,  /*1,2,3状态*/
            jsonObject.put("course_id",teacher_course.getCourse_id());
            jsonObject.put("course_name",course.getCourse_name());
            jsonObject.put("classCode",clazz.getId());
            jsonObject.put("className",clazz.getClazz_name());
            jsonObject.put("grade",clazz.getGrade());
            jsonObject.put("stage",clazz.getStage());
            jsonObject.put("term",term);
            int curStates;
            int i = Term.cmp(term,Term.get_current_term());
            if(i==-1){
                curStates=2;//已结课
            }
            else{
                if(i==0){
                    curStates=1;//进行中
                }
                else {
                    curStates=3;//未开始，未开放选课
                }
            }
            jsonObject.put("curStates",curStates);
            jsonArray.add(jsonObject);
        }
        String res = jsonArray.toString();
        response.getWriter().print(res);
    }
}
