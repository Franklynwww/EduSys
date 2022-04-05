package ServletTest;

import Education_System.dao.*;
import Education_System.dao.impl.*;
import Education_System.global.exam_state;
import Education_System.pojo.Course;
import Education_System.pojo.Exam;
import Education_System.pojo.User;
import Education_System.service.ExamService;
import Education_System.service.Student_Course_Service;
import Education_System.service.Teacher_Course_Service;
import Education_System.utils.Term;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonArray;
import org.junit.Test;

import java.util.List;

public class StuCourseSelectTest {
    @Test
    public void courseSelect(){
        CourseDao courseDao = new CourseDaoImp();
        UserDao userDao = new StudentUserDaoImp();
        ClazzDao clazzDao = new ClazzDaoImp();
        Teacher_Course_Dao teacher_course_dao = new Teacher_Course_Dao_Imp();
        Student_Course_Service student_course_service= new Student_Course_Service();
        Teacher_Course_Service teacher_course_service = new Teacher_Course_Service();
        ExamDao examDao = new ExamDaoImp();
        ExamService examService = new ExamService();

        String loginUser_identity = "201930140334";
        User loginUser = userDao.queryUserByIdentity(loginUser_identity);
        String clazz_id = loginUser.getClazz_id();
        List<Course> l =teacher_course_service.querycourseByclazzId(clazz_id);
        JSONArray jsonArray = new JSONArray();
//        <td>课程号</td>
//        <td>课程名</td>
//        <td>学分</td>
//        <td>开课学院</td>
//        <td>开课阶段</td>
//        <td>开课学期</td>
//        <td>是否可选</td>
//        course_id: '123',
//        course_name:'计算机原理',
//        credit:12,
//        reg_department:'计算机学院',
//        stage:'本科',
//        term:'2021-2022-1',
        for (Course course : l){
            if (teacher_course_dao.queryTCByCourseandClazz(course.getId(), clazz_id).getTerm().equals(Term.get_current_term())) {
                String course_id =course.getId();
                String course_name =course.getCourse_name();
                String credit = Float.toString(course.getCredit());
                String reg_department = course.getReg_department();
                String stage = course.getStage();
                String term =teacher_course_dao.queryTCByCourseandClazz(course.getId(),clazz_id).getTerm();
                int selectAble = 0;
                if(student_course_service.querycourseId(loginUser_identity).indexOf(course.getId())!=-1){
                    selectAble = -1; //已经选过，不可选
                }
                else {
                    Exam exam= examDao.queryExamBycourseidandclazzid(course.getId(),loginUser.getClazz_id());
                    if(examService.queryexamstate(exam)== exam_state.ARRANGED_NOTSTARTED){
                        selectAble = -2; //当前该科目的考试已安排未开始，不可选
                    }
                    else {
                        if(examService.queryexamstate(exam)==exam_state.STARTED_NOTFINISHED){
                            selectAble = -3;//当前该科目正在进行考试，不可选
                        }
                        else {
                            if(examService.queryexamstate(exam)==exam_state.FINISHED) {
                                selectAble = -4;//当前科目已经结束考试，不可选
                            }
                            else {
                                if(examService.queryexamstate(exam)==exam_state.NOT_ARRANGED){
                                    selectAble = 1;//可选
                                }
                            }
                        }
                    }
                }
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("course_id",course_id);
                jsonObject.put("course_name",course_name);
                jsonObject.put("credit",credit);
                jsonObject.put("reg_department",reg_department);
                jsonObject.put("stage",stage);
                jsonObject.put("term",term);
                jsonObject.put("selectAble",selectAble);
                jsonArray.add(jsonObject);
            }
        }
        String res = jsonArray.toString();
        JSONObject resObj = new JSONObject();
        resObj.put("test","sdasdasdasd");
        resObj.put("res",jsonArray);
        String resObjStr = resObj.toString();
        System.out.println(resObjStr);
    }
}
