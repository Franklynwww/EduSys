package Education_System.Servlet;

import Education_System.dao.*;
import Education_System.dao.impl.*;
import Education_System.global.exam_state;
import Education_System.pojo.Course;
import Education_System.pojo.Exam;
import Education_System.pojo.User;
import Education_System.service.ExamService;
import Education_System.service.Student_Course_Service;
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

@WebServlet(name = "StuCourseDelServlet", value = "/StuCourseDelServlet")
public class StuCourseDelServlet extends HttpServlet {
    CourseDao courseDao = new CourseDaoImp();
    UserDao userDao = new StudentUserDaoImp();
    ClazzDao clazzDao = new ClazzDaoImp();
    Teacher_Course_Dao teacher_course_dao = new Teacher_Course_Dao_Imp();
    Student_Course_Service student_course_service= new Student_Course_Service();
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

        JSONArray jsonArray = new JSONArray();
        List<Course> l = student_course_service.querycourseinoneterm(loginUser_identity, Term.get_current_term());
//                <td>课程号</td>
//        <td>课程名</td>
//        <td>学分</td>
//        <td>开课学院</td>
//        <td>开课阶段</td>
//        <td>开课学期</td>
//        <td>是否可退</td>
//        course_id: '123',
//                course_name:'计算机原理',
//                credit:12,
//                reg_department:'计算机学院',
//                stage:'本科',
//                term:'2021-2022-1',
//                dropAble:-1,
        String curTerm =Term.get_current_term();
        for (Course course : l){
            String course_id =course.getId();
            String course_name = course.getCourse_name();
            String credit = Float.toString(course.getCredit());
            String reg_department = course.getReg_department();
            String stage = course.getStage();
            String term = teacher_course_dao.queryTCByCourseandClazz(course.getId(),clazz_id).getTerm();
            Exam exam= examDao.queryExamBycourseidandclazzid(course.getId(),loginUser.getClazz_id());
            int dropAble = 0;
            if(examService.queryexamstate(exam)== exam_state.ARRANGED_NOTSTARTED){
                dropAble = -1;//当前该科目的考试已安排未开始，不可退
            }
            else {
                if(examService.queryexamstate(exam)==exam_state.STARTED_NOTFINISHED){
                    dropAble = -2;//当前该科目正在进行考试，不可退
                }
                else {
                    if(examService.queryexamstate(exam)==exam_state.FINISHED){
                        dropAble = -3;//当前科目已经结束考试，不可退
                    }
                    else {
                        if(examService.queryexamstate(exam)==exam_state.NOT_ARRANGED){
                            dropAble = 1;//可退
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
            jsonObject.put("dropAble",dropAble);
            jsonObject.put("curTerm",curTerm);
            jsonArray.add(jsonObject);
        }
//        String res = jsonArray.toString();
//        response.getWriter().print(res);
        JSONObject resObj = new JSONObject();
        resObj.put("curTerm",curTerm);
        resObj.put("result",jsonArray);
        String resObjStr = resObj.toString();
        response.getWriter().print(resObjStr);
    }
}
