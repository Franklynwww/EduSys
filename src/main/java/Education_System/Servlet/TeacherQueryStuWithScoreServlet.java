package Education_System.Servlet;

import Education_System.dao.ClazzDao;
import Education_System.dao.ExamDao;
import Education_System.dao.Student_Course_Dao;
import Education_System.dao.UserDao;
import Education_System.dao.impl.ClazzDaoImp;
import Education_System.dao.impl.ExamDaoImp;
import Education_System.dao.impl.StudentUserDaoImp;
import Education_System.dao.impl.Student_Course_Dao_Imp;
import Education_System.global.exam_state;
import Education_System.pojo.Exam;
import Education_System.pojo.Student_Course;
import Education_System.pojo.User;
import Education_System.service.ExamService;
import Education_System.service.Student_Course_Service;
import Education_System.service.Teacher_Course_Service;
import Education_System.utils.Request2Str;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "TeacherQueryStuWithScoreServlet", value = "/TeacherQueryStuWithScoreServlet")
public class TeacherQueryStuWithScoreServlet extends HttpServlet {
    Teacher_Course_Service teacher_course_service = new Teacher_Course_Service();
    Student_Course_Dao student_course_dao = new Student_Course_Dao_Imp();
    Student_Course_Service student_course_service = new Student_Course_Service();
    UserDao userDao = new StudentUserDaoImp();
    ClazzDao clazzDao = new ClazzDaoImp();
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
        // 转换为jobj以便可以通过key开读取value
        JSONObject jobj = JSON.parseObject(json);
        String login_t_User_identity = jobj.getString("userId");
        String checking_course_id = jobj.getString("checking_course");
        String checking_clazz_id = jobj.getString("checking_clazz");
        JSONArray jsonArray = new JSONArray();
        List<String> l=teacher_course_service.querystudentIdentity(checking_course_id,checking_clazz_id);
        for (String student_identity : l) {
            User user = userDao.queryUserByIdentity(student_identity);
            Exam exam = examDao.queryExamBycourseidandclazzid(checking_course_id,checking_clazz_id);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("identity",user.getIdentity());
            jsonObject.put("name",user.getName());
            jsonObject.put("department",clazzDao.queryClazzById(user.getClazz_id()).getDepartment());
            jsonObject.put("clazz",clazzDao.queryClazzById(user.getClazz_id()).getClazz_name());
            jsonObject.put("grade",clazzDao.queryClazzById(user.getClazz_id()).getGrade());
            jsonObject.put("stage",clazzDao.queryClazzById(user.getClazz_id()).getStage());
            Student_Course sc= student_course_dao.querySCByStudentandCourse(user.getIdentity(),checking_course_id);
            int scoreStage;
            String score;
            if(examService.queryexamstate(exam)== exam_state.FINISHED && sc.getState().equals("未评定")){
                scoreStage=-1;//可以评定
                score = "--";
            }
            else{
                if(sc.getState().equals("已评定")){
                    scoreStage=sc.getScore();
                    score = Integer.toString(scoreStage);
                }
                else {
                    scoreStage=-2;//尚未安排考试或考试尚未结束，无法评定成绩
                    score = "--";
                }

            }
            jsonObject.put("scoreStage",scoreStage);
            jsonObject.put("score",score);
            jsonArray.add(jsonObject);
        }
        String res = jsonArray.toString();
        response.getWriter().print(res);
    }
}
