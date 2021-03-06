package Education_System.Servlet;

import Education_System.dao.*;
import Education_System.dao.impl.*;
import Education_System.global.exam_state;
import Education_System.pojo.Course;
import Education_System.pojo.Exam;
import Education_System.pojo.User;
import Education_System.service.ExamService;
import Education_System.service.Student_Course_Service;
import Education_System.service.Teacher_Course_Service;
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

@WebServlet(name = "StuCourseSelectServlet", value = "/StuCourseSelectServlet")
public class StuCourseSelectServlet extends HttpServlet {
    CourseDao courseDao = new CourseDaoImp();
    UserDao userDao = new StudentUserDaoImp();
    ClazzDao clazzDao = new ClazzDaoImp();
    Teacher_Course_Dao teacher_course_dao = new Teacher_Course_Dao_Imp();
    Student_Course_Service student_course_service= new Student_Course_Service();
    Teacher_Course_Service teacher_course_service = new Teacher_Course_Service();
    ExamDao examDao = new ExamDaoImp();
    ExamService examService = new ExamService();
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
        User loginUser = userDao.queryUserByIdentity(loginUser_identity);
        String clazz_id = loginUser.getClazz_id();
        List<Course> l =teacher_course_service.querycourseByclazzId(clazz_id);
        JSONObject resObj = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        String curTerm =Term.get_current_term();
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
                    selectAble = -1; //????????????????????????
                }
                else {
                    Exam exam= examDao.queryExamBycourseidandclazzid(course.getId(),loginUser.getClazz_id());
                    if(examService.queryexamstate(exam)== exam_state.ARRANGED_NOTSTARTED){
                        selectAble = -2; //??????????????????????????????????????????????????????
                    }
                    else {
                        if(examService.queryexamstate(exam)==exam_state.STARTED_NOTFINISHED){
                            selectAble = -3;//?????????????????????????????????????????????
                        }
                        else {
                            if(examService.queryexamstate(exam)==exam_state.FINISHED) {
                                selectAble = -4;//??????????????????????????????????????????
                            }
                            else {
                                if(examService.queryexamstate(exam)==exam_state.NOT_ARRANGED){
                                    selectAble = 1;//??????
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
                jsonObject.put("curTerm",curTerm);
                jsonArray.add(jsonObject);
            }
        }
//        String res = jsonArray.toString();
        resObj.put("curTerm",curTerm);
        resObj.put("result",jsonArray);
        String resObjStr = resObj.toString();
        response.getWriter().print(resObjStr);
    }
}
