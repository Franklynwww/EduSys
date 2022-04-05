import Education_System.dao.*;
import Education_System.dao.impl.*;
import Education_System.pojo.Clazz;
import Education_System.pojo.Teacher_Course;
import Education_System.pojo.timetable;
import Education_System.service.Teacher_Course_Service;
import Education_System.service.TimetableService;
import Education_System.utils.Term;
import Education_System.utils.TimeTableString;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeTableTest {
    @Test
    public void queryTimeTableByIdentity(){
        CourseDao courseDao = new CourseDaoImp();
        UserDao userDao = new StudentUserDaoImp();
        UserDao t_userDao = new TeacherUserDaoImp();
        Teacher_Course_Dao teacher_course_dao=new Teacher_Course_Dao_Imp();
        TimetableService timetableService = new TimetableService();
        String identity = "201930140334";
        JSONArray jsonArray = new JSONArray();
        List<timetable> tis = timetableService.querycurrentTimetableBystudentidentityincurrentterm(identity);
        for (timetable ti:
             tis) {
//            1, 课程在周几第几节开始, 持续几节  2, 课程名,   3, 课程老师,  4, 持续的周  5, 上课地点
            System.out.println(ti);
            String time = TimeTableString.getCourseTime(ti.getWeek_day(),ti.getSection());
            String courseName = courseDao.queryCourseById(ti.getCourse_id()).getCourse_name();
            String teacherName = t_userDao.queryUserByIdentity(teacher_course_dao.queryTCByCourseandClazz(ti.getCourse_id(),
                    ti.getClazz_id()).getTeacher_identity()).getName();
            String durationWeek = ti.getStart_week().toString()+"-"+ti.getEnd_week().toString()+"周";
            String location = ti.getClassroom()+"/"+durationWeek;
            System.out.println(time);
            System.out.println(courseName);
            System.out.println(teacherName);
            System.out.println(location);
            JSONObject timeTableObj = new JSONObject();
            timeTableObj.put("time",time);
            timeTableObj.put("courseName",courseName);
            timeTableObj.put("teacherName",teacherName);
            timeTableObj.put("location",location);
            jsonArray.add(timeTableObj);



        }
//        String REGEX_CHINESE = "[\u4e00-\u9fa5]";// 中文正则
//        String str = "3-4节";
//        Pattern pat = Pattern.compile(REGEX_CHINESE);
//        Matcher mat = pat.matcher(str);
//        String res = mat.replaceAll("");
//        System.out.println(res);
        String stuTimeTables = jsonArray.toJSONString();
        System.out.println(stuTimeTables);
    }


    @Test
    public void reacherTimeTable(){
        Teacher_Course_Service teacher_course_service = new Teacher_Course_Service();
        ClazzDao clazzDao = new ClazzDaoImp();
        CourseDao courseDao = new CourseDaoImp();
        TimetableService timetableService = new TimetableService();
        Teacher_Course_Dao teacher_course_dao = new Teacher_Course_Dao_Imp();
        UserDao t_userDao = new TeacherUserDaoImp();
        ExamDao examDao = new ExamDaoImp();
        String login_t_User_identity = "20001203";
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
            System.out.println(ti);
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
                exam_related = "未安排考试";
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
            System.out.println(time);
            System.out.println(courseName);
            System.out.println(clazz_name);
            System.out.println(location);


        }
        JSONObject res = new JSONObject();
        res.put("otCourse",othersInfo);
        res.put("basicCourse",jsonArray);
        String r = res.toString();
        System.out.println(r);
    }
}
