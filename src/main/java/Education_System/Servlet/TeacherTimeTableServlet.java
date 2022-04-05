package Education_System.Servlet;

import Education_System.dao.*;
import Education_System.dao.impl.*;
import Education_System.pojo.Clazz;
import Education_System.pojo.Teacher_Course;
import Education_System.pojo.timetable;
import Education_System.service.Teacher_Course_Service;
import Education_System.service.TimetableService;
import Education_System.utils.Request2Str;
import Education_System.utils.Term;
import Education_System.utils.TimeTableString;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "TeacherTimeTableServlet", value = "/TeacherTimeTableServlet")
public class TeacherTimeTableServlet extends HttpServlet {
    Teacher_Course_Service teacher_course_service = new Teacher_Course_Service();
    ClazzDao clazzDao = new ClazzDaoImp();
    CourseDao courseDao = new CourseDaoImp();
    TimetableService timetableService = new TimetableService();
    Teacher_Course_Dao teacher_course_dao = new Teacher_Course_Dao_Imp();
    UserDao t_userDao = new TeacherUserDaoImp();
    ExamDao examDao = new ExamDaoImp();
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
        List<timetable> tis = timetableService.querycurrentTimetableByteacheridentityincurrentterm(login_t_User_identity);
        List<List<String>> list_not_in_timetable_course_id_clazz_ids = timetableService.querycurrentcourseIdandclazzIdnotinTimetableByteacheridentity(login_t_User_identity);
        String othersInfo = "";
        int NumOtInfo = 0;
        for(List<String> l:list_not_in_timetable_course_id_clazz_ids){

            ExamDao examDaoOT  = new ExamDaoImp();
            Teacher_Course teacher_course = teacher_course_dao.queryTCByCourseandClazz(l.get(0),l.get(1));
            if(teacher_course.getTerm().equals(Term.get_current_term())) {
                String info =courseDao.queryCourseById(l.get(0)).getCourse_name()+"/";
                Clazz clazz = clazzDao.queryClazzById(l.get(1));
                info=info+clazz.getStage()+clazz.getGrade()+clazz.getClazz_name()+"/";
                if(examDaoOT.queryExamBycourseidandclazzid(l.get(0),l.get(1))==null){
                    info+="(未安排考试)";
                }
                else{
                    info+="(已安排考试)";
                }
                if (NumOtInfo!=0){info = " | "+info;}
                othersInfo+=info;
                NumOtInfo+=1;
            }

        }
        JSONArray jsonArray = new JSONArray();
        for(timetable ti:tis){
//            System.out.println(ti);
            String time = TimeTableString.getCourseTime(ti.getWeek_day(),ti.getSection());
            String courseName = courseDao.queryCourseById(ti.getCourse_id()).getCourse_name();
            String teacherName = t_userDao.queryUserByIdentity(teacher_course_dao.queryTCByCourseandClazz(ti.getCourse_id(),
                    ti.getClazz_id()).getTeacher_identity()).getName();
            String durationWeek = ti.getStart_week().toString()+"-"+ti.getEnd_week().toString()+"周";
            String location = ti.getClassroom()+"/"+durationWeek;
            Clazz clazz = clazzDao.queryClazzById(ti.getClazz_id());
            String stage=clazz.getStage();
            String grade =clazz.getGrade();
            String clazz_name = clazz.getClazz_name();
            String exam_related = null;
            if(examDao.queryExamBycourseidandclazzid(ti.getCourse_id(),ti.getClazz_id())==null){
                exam_related = "未安排考试";
            }
            else{
                exam_related = "已安排考试";
            }
            courseName = courseName+"("+exam_related+")";
            clazz_name = clazz_name+"/"+stage+grade;
//            {time: '2-3-2', courseName: '数据库实用技术啊啊啊啊啊啊啊啊啊啊', teacherName: '张三', location: 'C区202'},
            JSONObject timeTableObj = new JSONObject();
            timeTableObj.put("time",time);
            timeTableObj.put("courseName",courseName);
            timeTableObj.put("teacherName",clazz_name);
            timeTableObj.put("location",location);
            jsonArray.add(timeTableObj);
//            System.out.println(time);
//            System.out.println(courseName);
//            System.out.println(clazz_name);
//            System.out.println(location);


        }
        JSONObject res = new JSONObject();
        res.put("otCourse",othersInfo);
        res.put("basicCourse",jsonArray);
        String r = res.toString();
        response.getWriter().print(r);
    }
}
