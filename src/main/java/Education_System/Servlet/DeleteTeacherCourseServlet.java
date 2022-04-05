package Education_System.Servlet;

import Education_System.common.Result;
import Education_System.dao.ClazzDao;
import Education_System.dao.CourseDao;
import Education_System.dao.ExamDao;
import Education_System.dao.Teacher_Course_Dao;
import Education_System.dao.impl.ClazzDaoImp;
import Education_System.dao.impl.CourseDaoImp;
import Education_System.dao.impl.ExamDaoImp;
import Education_System.dao.impl.Teacher_Course_Dao_Imp;
import Education_System.global.exam_state;
import Education_System.pojo.Exam;
import Education_System.pojo.Teacher_Course;
import Education_System.service.ExamService;
import Education_System.service.Student_Course_Service;
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

@WebServlet(name = "DeleteTeacherCourseServlet", value = "/DeleteTeacherCourseServlet")
public class DeleteTeacherCourseServlet extends HttpServlet {
    Teacher_Course_Service teacher_course_service = new Teacher_Course_Service();
    CourseDao courseDao = new CourseDaoImp();
    UserService userService = new TeacherUserServiceImp();
    ClazzDao clazzDao = new ClazzDaoImp();
    ExamDao examDao = new ExamDaoImp();
    ExamService examService = new ExamService();
    Teacher_Course_Dao teacher_course_dao = new Teacher_Course_Dao_Imp();
    Student_Course_Service student_course_service = new Student_Course_Service();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String json = Request2Str.getRequestPostStr(request);
        JSONObject jobj = JSON.parseObject(json);
        // 1、获取请求的参数，即用户提交的要注册的用户名，密码，邮箱，以及用户填写的验证码

        String teacher_identity = jobj.getString("teacherCode");
        String course_id = jobj.getString("courseCode");
        String clazz_id = jobj.getString("clazzCode");
        Result result = null;
        // 3、检查 用户名是否可用
        Exam exam = examDao.queryExamBycourseidandclazzid(course_id,clazz_id);
        if(!userService.existsIdentity(teacher_identity)){
            result = Result.error(-1,"教师号" + teacher_identity + "不存在!");

//            System.out.println("教师号[" + teacher_identity + "]不存在!");//打印到控制台上
//
//            // 把回显信息，保存到Request中
//            req.setAttribute("msg", "教师号不存在！！");
//            req.setAttribute("course_id",course_id);
//            req.setAttribute("clazz_id",clazz_id);
//
//            // 返回注册页面
//            req.getRequestDispatcher("/pages/user/teacher_delete_course.jsp").forward(req, resp);
        }
        else{
            if (courseDao.queryCourseById(course_id)==null) {
                //用户名不存在
                result = Result.error(-1,"课程号" + course_id + "不存在!");
//                System.out.println("课程号[" + course_id + "]不存在!");//打印到控制台上
//
//                // 把回显信息，保存到Request中
//                req.setAttribute("msg", "课程号不存在！！");
//                req.setAttribute("teacher_identity",teacher_identity);
//                req.setAttribute("clazz_id",clazz_id);
//
//                // 返回注册页面
//                req.getRequestDispatcher("/pages/user/teacher_delete_course.jsp").forward(req, resp);
            } else {
                if (clazzDao.queryClazzById(clazz_id) == null) {
                    //用户名不存在
                    result = Result.error(-1,"班级号" + clazz_id + "不存在!");
//                    System.out.println("班级号[" + clazz_id + "]不存在!");//打印到控制台上
//
//                    // 把回显信息，保存到Request中
//                    req.setAttribute("msg", "班级号不存在！！");
//                    req.setAttribute("teacher_identity", teacher_identity);
//                    req.setAttribute("course_id",course_id);
//
//                    // 返回注册页面
//                    req.getRequestDispatcher("/pages/user/teacher_delete_course.jsp").forward(req, resp);
                } else {//学号存在
                    if (teacher_course_service.exists_teacher_course_clazz(teacher_identity, course_id, clazz_id)) {
                        Teacher_Course teacher_course = teacher_course_dao.queryTCByCourseandClazz(course_id,clazz_id);
                        if(teacher_course_service.is_ended(teacher_course)){
                            result = Result.error(-1,"该班级"+clazz_id+"的该课程号"+course_id+"的开设学期"+teacher_course.getTerm()+"在当前学期"+ Term.get_current_term()+"之前，即已经结课，不能删除该记录!");
//                            System.out.println("该班级["+clazz_id+"]的该课程号["+course_id+"]的开设学期["+teacher_course.getTerm()+"]在当前学期["+ Term.get_current_term()+"]之前，即已经结课，不能删除该记录!");//打印到控制台上
//
//                            // 把回显信息，保存到Request中
//                            req.setAttribute("msg", "该班级["+clazz_id+"]的该课程号["+course_id+"]的开设学期["+teacher_course.getTerm()+"]在当前学期["+Term.get_current_term()+"]之前，即已经结课，不能删除该记录!");
//
//                            // 返回注册页面
//                            req.getRequestDispatcher("/pages/user/teacher_delete_course.jsp").forward(req, resp);
                        }
                        if(examService.queryexamstate(exam)== exam_state.ARRANGED_NOTSTARTED){
                            result = Result.error(-1,"该班级"+clazz_id+"的该课程号"+course_id+"的考试已安排未开始，不能再删除该教师"+teacher_identity+"教授该班级该课程的记录!");
                            //System.out.println("该班级["+clazz_id+"]的该课程号["+course_id+"]的考试已安排未开始，不能再删除该教师["+teacher_identity+"]教授该班级该课程的记录!");//打印到控制台上
//
//                            // 把回显信息，保存到Request中
//                            req.setAttribute("msg", "该班级["+clazz_id+"]的该课程号["+course_id+"]的考试已安排未开始，不能再删除该教师["+teacher_identity+"]教授该班级该课程的记录!");
//
//                            // 返回注册页面
//                            req.getRequestDispatcher("/pages/user/teacher_delete_course.jsp").forward(req, resp);
                        }
                        else {
                            if(examService.queryexamstate(exam)==exam_state.STARTED_NOTFINISHED){
                                result = Result.error(-1,"该班级"+clazz_id+"的该课程号"+course_id+"的考试正在进行中，不能再删除该教师"+teacher_identity+"教授该班级该课程的记录!");
                                //System.out.println("该班级["+clazz_id+"]的该课程号["+course_id+"]的考试正在进行中，不能再删除该教师["+teacher_identity+"]教授该班级该课程的记录!");//打印到控制台上
//
//                                // 把回显信息，保存到Request中
//                                req.setAttribute("msg", "该班级["+clazz_id+"]的该课程号["+course_id+"]的考试正在进行中，不能再删除该教师["+teacher_identity+"]教授该班级该课程的记录!");
//
//                                // 返回注册页面
//                                req.getRequestDispatcher("/pages/user/teacher_delete_course.jsp").forward(req, resp);
                            }
                            else {
                                if(examService.queryexamstate(exam)==exam_state.NOT_ARRANGED){
                                    // 调用Service的接口保存到数据库
                                    teacher_course_service.delete_course_clazz(teacher_identity, course_id, clazz_id);
                                    result = Result.success();
                                    // 跳到注册成功页面 regist_success.jsp
//                                    req.getRequestDispatcher("/pages/user/manager_delete_success.jsp").forward(req, resp);
                                }
                                else{
                                    if(examService.queryexamstate(exam)==exam_state.FINISHED){
                                        result = Result.error(-1,"该班级"+clazz_id+"的该课程号"+course_id+"]的考试已经结束，不能再删除该教师"+teacher_identity+"教授该班级该课程的记录!");
//                                        System.out.println("该班级["+clazz_id+"]的该课程号["+course_id+"]的考试已经结束，不能再删除该教师["+teacher_identity+"]教授该班级该课程的记录!");//打印到控制台上
//
//                                        // 把回显信息，保存到Request中
//                                        req.setAttribute("msg", "该班级["+clazz_id+"]的该课程号["+course_id+"]的考试已经结束，不能再删除该教师["+teacher_identity+"]教授该班级该课程的记录!");
//
//                                        // 返回注册页面
//                                        req.getRequestDispatcher("/pages/user/teacher_delete_course.jsp").forward(req, resp);
                                    }
                                }
                            }
                        }
                    } else {
                        result = Result.error(-1,"该老师没有教授该班级该课程号的课程!");
                        System.out.println("该老师没有教授该班级该课程号的课程!");//打印到控制台上

//                        // 把回显信息，保存到Request中
//                        req.setAttribute("msg", "该老师没有教授该班级该课程号的课程!");
//
//                        // 返回注册页面
//                        req.getRequestDispatcher("/pages/user/teacher_delete_course.jsp").forward(req, resp);
                    }
                }
            }
        }
        Gson gson = new Gson();
        String Rson = gson.toJson(result);
        response.getWriter().print(Rson);
    }
}
