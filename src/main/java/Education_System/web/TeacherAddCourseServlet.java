package Education_System.web;

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
import Education_System.utils.Term;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TeacherAddCourseServlet extends HttpServlet {
    Teacher_Course_Service teacher_course_service = new Teacher_Course_Service();
    CourseDao courseDao = new CourseDaoImp();
    UserService userService = new TeacherUserServiceImp();
    ClazzDao clazzDao = new ClazzDaoImp();
    UserDao userDao = new TeacherUserDaoImp();
    Teacher_Course_Dao teacher_course_dao = new Teacher_Course_Dao_Imp();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        // 1、获取请求的参数，即用户提交的要注册的用户名，密码，邮箱，以及用户填写的验证码
        String teacher_identity = req.getParameter("teacher_identity");
        String course_id = req.getParameter("course_id");
        String clazz_id = req.getParameter("clazz_id");
        String schoolyear = req.getParameter("schoolyear");
        String season = req.getParameter("season");
        String term = schoolyear + "-" + season;
        if (!Term.isvalidinputterm(term)) {
            System.out.println("开课学期[" + term + "]不能再当前学期[" + Term.get_current_term() + "]之后!");//打印到控制台上

            // 把回显信息，保存到Request中
            req.setAttribute("msg", "开课学期[" + term + "]不能再当前学期[" + Term.get_current_term() + "]之后!");
            req.setAttribute("teacher_identity", teacher_identity);
            req.setAttribute("course_id", course_id);
            req.setAttribute("clazz_id", clazz_id);

            // 返回注册页面
            req.getRequestDispatcher("/pages/user/teacher_add_course.jsp").forward(req, resp);
        } else {
            // 3、检查 用户名是否可用
            if (!userService.existsIdentity(teacher_identity)) {
                System.out.println("教师号[" + teacher_identity + "]不存在!");//打印到控制台上

                // 把回显信息，保存到Request中
                req.setAttribute("msg", "教师号不存在！！");
                req.setAttribute("course_id", course_id);
                req.setAttribute("clazz_id", clazz_id);

                // 返回注册页面
                req.getRequestDispatcher("/pages/user/teacher_add_course.jsp").forward(req, resp);
            } else {
                if (courseDao.queryCourseById(course_id) == null) {
                    System.out.println("课程号[" + course_id + "]不存在!");//打印到控制台上

                    // 把回显信息，保存到Request中
                    req.setAttribute("msg", "课程号不存在！！");
                    req.setAttribute("teacher_identity", teacher_identity);
                    req.setAttribute("clazz_id", clazz_id);

                    // 返回注册页面
                    req.getRequestDispatcher("/pages/user/teacher_add_course.jsp").forward(req, resp);
                } else {
                    if (clazzDao.queryClazzById(clazz_id) == null) {
                        System.out.println("班级[" + clazz_id + "]不存在!");//打印到控制台上

                        // 把回显信息，保存到Request中
                        req.setAttribute("msg", "班级号不存在！！");
                        req.setAttribute("teacher_identity", teacher_identity);
                        req.setAttribute("course_id", course_id);
                        // 返回注册页面
                        req.getRequestDispatcher("/pages/user/teacher_add_course.jsp").forward(req, resp);
                    } else {
                        if (Integer.parseInt(Term.extractfirstyear(term)) >= Integer.parseInt(clazzDao.queryClazzById(clazz_id).getGrade().substring(0, 4)) + 4) {
                            System.out.println("开设学期在班级[" + clazz_id + "]的毕业年限[" + (Integer.parseInt(clazzDao.queryClazzById(clazz_id).getGrade().substring(0, 4)) + 4) + "]年之后!");//打印到控制台上

                            // 把回显信息，保存到Request中
                            req.setAttribute("msg", "开设学期在班级[" + clazz_id + "]的毕业年限[" + clazzDao.queryClazzById(clazz_id).getGrade().substring(0, 4) + "]年之后!！！");
                            req.setAttribute("teacher_identity", teacher_identity);
                            req.setAttribute("course_id", course_id);
                            // 返回注册页面
                            req.getRequestDispatcher("/pages/user/teacher_add_course.jsp").forward(req, resp);
                        } else {
                            Course course = courseDao.queryCourseById(course_id);
                            User user = userDao.queryUserByIdentity(teacher_identity);
                            String teacher_s_department = user.getDepartment();
                            String course_s_reg_department = course.getReg_department();
                            if (!teacher_s_department.equals(course_s_reg_department)) {
                                System.out.println("教师所在学院[" + teacher_s_department + "]与课程开设学院[" + course_s_reg_department + "]不匹配!");//打印到控制台上

                                // 把回显信息，保存到Request中
                                req.setAttribute("msg", "教师所在学院与课程开设学院不匹配!");

                                // 返回注册页面
                                req.getRequestDispatcher("/pages/user/teacher_add_course.jsp").forward(req, resp);
                            } else {
                                Teacher_Course teacher_course = teacher_course_dao.queryTCByCourseandClazz(course_id, clazz_id);
                                if (teacher_course != null) {
                                    System.out.println("该课程[" + course_id + "]在该班级[" + clazz_id + "]已经开设!");//打印到控制台上

                                    // 把回显信息，保存到Request中
                                    req.setAttribute("msg", "该课程[" + course_id + "]在该班级[" + clazz_id + "]已经开设!");

                                    // 返回注册页面
                                    req.getRequestDispatcher("/pages/user/teacher_add_course.jsp").forward(req, resp);

                                } else {
                                    // 调用Service的接口保存到数据库
                                    teacher_course_service.add_course_clazz(teacher_identity, course_id, clazz_id, term);
                                    // 跳到注册成功页面 regist_success.jsp
                                    req.getRequestDispatcher("/pages/user/manager_add_success.jsp").forward(req, resp);
                                }
                            }
                        }
                    }
                }
            }//教师所在学院 == 该教师所讲授的课程的课程开设学院 ！= 该教师所讲授的课程的开课班级所在学院

        }
    }
}
