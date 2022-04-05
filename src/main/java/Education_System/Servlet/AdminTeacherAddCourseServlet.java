package Education_System.Servlet;

import Education_System.common.Result;
import Education_System.dao.ClazzDao;
import Education_System.dao.CourseDao;
import Education_System.dao.Teacher_Course_Dao;
import Education_System.dao.UserDao;
import Education_System.dao.impl.ClazzDaoImp;
import Education_System.dao.impl.CourseDaoImp;
import Education_System.dao.impl.TeacherUserDaoImp;
import Education_System.dao.impl.Teacher_Course_Dao_Imp;
import Education_System.pojo.Course;
import Education_System.pojo.Teacher_Course;
import Education_System.pojo.User;
import Education_System.service.Teacher_Course_Service;
import Education_System.service.UserService;
import Education_System.service.impl.TeacherUserServiceImp;
import Education_System.utils.Request2Str;
import Education_System.utils.Term;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "AdminTeacherAddCourseServlet", value = "/AdminTeacherAddCourseServlet")
public class AdminTeacherAddCourseServlet extends HttpServlet {
    Teacher_Course_Service teacher_course_service = new Teacher_Course_Service();
    CourseDao courseDao = new CourseDaoImp();
    UserService userService = new TeacherUserServiceImp();
    ClazzDao clazzDao = new ClazzDaoImp();
    UserDao userDao = new TeacherUserDaoImp();
    Teacher_Course_Dao teacher_course_dao = new Teacher_Course_Dao_Imp();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String json = Request2Str.getRequestPostStr(request);
        JSONObject jobj = JSON.parseObject(json);
        String teacher_identity = jobj.getString("teacherCode");



        String course_id = jobj.getString("courseCode");
        String clazz_id = jobj.getString("clazzCode");
        String schoolyear = jobj.getString("term");
        String season = jobj.getString("UDTerm");
        String term = schoolyear + "-" + season;
        Result result = null;
        if (!Term.isvalidinputterm(term)) {
            result = Result.error(-1,"开课学期[" + term + "]不能再当前学期[" + Term.get_current_term() + "]之后!");
        }else {

            if (!userService.existsIdentity(teacher_identity)) {
                result = Result.error(-1,"教师号[" + teacher_identity + "]不存在!");
            }
            else {
                if (courseDao.queryCourseById(course_id) == null) {
                    result=Result.error(-1,"课程号[" + course_id + "]不存在!");
                }
                else {
                    if (clazzDao.queryClazzById(clazz_id) == null) {
                        result=Result.error(-1,"班级[" + clazz_id + "]不存在!!");
                    }else {
                        if (Integer.parseInt(Term.extractfirstyear(term)) >= Integer.parseInt(clazzDao.queryClazzById(clazz_id).getGrade().substring(0, 4)) + 4) {
                            result = Result.error(-1,"开设学期在班级[" + clazz_id + "]的毕业年限[" + (Integer.parseInt(clazzDao.queryClazzById(clazz_id).getGrade().substring(0, 4)) + 4) + "]年之后!");

                        }
                        else {
                            Course course = courseDao.queryCourseById(course_id);
                            User user = userDao.queryUserByIdentity(teacher_identity);
                            String teacher_s_department = user.getDepartment();
                            String course_s_reg_department = course.getReg_department();
                            if (!teacher_s_department.equals(course_s_reg_department)) {
                                result = Result.error(-1,"教师所在学院[" + teacher_s_department + "]与课程开设学院[" + course_s_reg_department + "]不匹配!");
                            }
                            else {
                                Teacher_Course teacher_course = teacher_course_dao.queryTCByCourseandClazz(course_id, clazz_id);
                                if (teacher_course != null) {
                                    result = Result.error(-1,"该课程[" + course_id + "]在该班级[" + clazz_id + "]已经开设!");
                                }
                                else {
                                    teacher_course_service.add_course_clazz(teacher_identity, course_id, clazz_id, term);
                                    result = Result.success();
                                }
                            }
                        }
                    }
                }
            }
        }
        Gson gson = new Gson();
        String Rson = gson.toJson(result);
        response.getWriter().print(Rson);
    }
}
