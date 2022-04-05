package Education_System.Servlet;

import Education_System.dao.CourseDao;
import Education_System.dao.Teacher_Course_Dao;
import Education_System.dao.UserDao;
import Education_System.dao.impl.CourseDaoImp;
import Education_System.dao.impl.StudentUserDaoImp;
import Education_System.dao.impl.TeacherUserDaoImp;
import Education_System.dao.impl.Teacher_Course_Dao_Imp;
import Education_System.pojo.Teacher_Course;
import Education_System.pojo.timetable;
import Education_System.service.TimetableService;
import Education_System.utils.Request2Str;
import Education_System.utils.TimeTableString;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "StuTimeTableServlet", value = "/StuTimeTableServlet")
public class StuTimeTableServlet extends HttpServlet {
    CourseDao courseDao = new CourseDaoImp();
    UserDao userDao = new StudentUserDaoImp();
    UserDao t_userDao = new TeacherUserDaoImp();
    Teacher_Course_Dao teacher_course_dao=new Teacher_Course_Dao_Imp();
    TimetableService timetableService = new TimetableService();
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
        String identity = jobj.getString("userId");

        JSONArray jsonArray = new JSONArray();
        List<timetable> tis = timetableService.querycurrentTimetableBystudentidentityincurrentterm(identity);

        List<Teacher_Course> list_not_in_timetable_tcs = timetableService.querycurrentTCnotinTimetableBystudentidentityincurrentterm(identity);
        StringBuilder othersInfo = new StringBuilder();
        for(Teacher_Course tc:list_not_in_timetable_tcs){
            String info = courseDao.queryCourseById(tc.getCourse_id()).getCourse_name()+"/"+
                    t_userDao.queryUserByIdentity(tc.getTeacher_identity()).getName()+"   ";
            othersInfo.append(info);
        }
        String otInfo = othersInfo.toString();



        for (timetable ti: tis) {
            String time = TimeTableString.getCourseTime(ti.getWeek_day(),ti.getSection());
            String courseName = courseDao.queryCourseById(ti.getCourse_id()).getCourse_name();
            String teacherName = t_userDao.queryUserByIdentity(teacher_course_dao.queryTCByCourseandClazz(ti.getCourse_id(),
                    ti.getClazz_id()).getTeacher_identity()).getName();
            String durationWeek = ti.getStart_week().toString()+"-"+ti.getEnd_week().toString()+"周";
            String location = ti.getClassroom()+"/"+durationWeek;
            JSONObject timeTableObj = new JSONObject();
            timeTableObj.put("time",time);
            timeTableObj.put("courseName",courseName);
            timeTableObj.put("teacherName",teacherName);
            timeTableObj.put("location",location);
            jsonArray.add(timeTableObj);
        }
//        String stuTimeTables = jsonArray.toJSONString();
//        response.getWriter().print(stuTimeTables);


        JSONObject res = new JSONObject();
        res.put("otCourse",otInfo);
        res.put("basicCourse",jsonArray);
        String r = res.toString();
        response.getWriter().print(r);
    }
}
