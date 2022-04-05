package Education_System.web;

import Education_System.dao.ClazzDao;
import Education_System.dao.ExamDao;
import Education_System.dao.Teacher_Course_Dao;
import Education_System.dao.impl.ClazzDaoImp;
import Education_System.dao.impl.ExamDaoImp;
import Education_System.dao.impl.Teacher_Course_Dao_Imp;
import Education_System.pojo.Exam;
import Education_System.pojo.Teacher_Course;
import Education_System.service.ExamService;
import Education_System.service.Teacher_Course_Service;
import Education_System.utils.Term;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

public class AddnewexamServlet extends HttpServlet {
    ClazzDao clazzDao = new ClazzDaoImp();
    ExamDao examDao = new ExamDaoImp();
    Teacher_Course_Dao teacher_course_dao = new Teacher_Course_Dao_Imp();
    Teacher_Course_Service teacher_course_service = new Teacher_Course_Service();
    ExamService examService = new ExamService();
    Date date = new Date();
    Timestamp nowtime = new Timestamp(date.getTime());
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        //  1、获取请求的参数

        String id = req.getParameter("id");
        String course_id = req.getParameter("course_id");
        String clazz_id = req.getParameter("clazz_id");
        String classroom = req.getParameter("classroom");
        String start_time = req.getParameter("start_time");
        String end_time = req.getParameter("end_time");
        // 调用 userService.login()登录处理业务
        Exam exam = examDao.queryByExamId(id);
        // 如果等于null,说明登录失败!
        if (exam!=null) {
            //用户名不可用
            System.out.println("考试代号[" + id + "]已存在!");//打印到控制台上

            // 把回显信息，保存到Request中
            req.setAttribute("msg", "考试代号已存在！！");

            // 返回注册页面
            req.getRequestDispatcher("/pages/user/add_new_exam.jsp").forward(req, resp);
        } else { //用户名可用
            if(!examService.is_legal_start_end(start_time,end_time)){
                System.out.println("开始时间["+start_time+"]没有在结束时间["+end_time+"]之前!");//打印到控制台上

                // 把回显信息，保存到Request中
                req.setAttribute("msg", "开始时间["+start_time+"]没有在结束时间["+end_time+"]之前!");

                // 返回注册页面
                req.getRequestDispatcher("/pages/user/add_new_exam.jsp").forward(req, resp);
            }
            else{
                if(examService.if_time_conf_in_classroom(start_time,end_time,classroom)){
                    System.out.println("在教室[" + classroom + "]上的考试安排发生时间冲突!");//打印到控制台上

                    // 把回显信息，保存到Request中
                    req.setAttribute("msg", "在教室[" + classroom + "]上的考试安排发生冲突!");

                    // 返回注册页面
                    req.getRequestDispatcher("/pages/user/add_new_exam.jsp").forward(req, resp);
                }
                else{
                    if(examService.if_time_conf_in_clazz(start_time,end_time,clazz_id)){
                        System.out.println("在班级[" + clazz_id + "]上的考试安排发生时间冲突!");//打印到控制台上

                        // 把回显信息，保存到Request中
                        req.setAttribute("msg", "在班级[" + clazz_id + "]上的考试安排发生时间冲突!");

                        // 返回注册页面
                        req.getRequestDispatcher("/pages/user/add_new_exam.jsp").forward(req, resp);
                    }
                    else{
                        Exam exam1 = examDao.queryExamBycourseidandclazzid(course_id,clazz_id);
                        if(exam1!=null){
                            System.out.println("已存在相同的班级[" + clazz_id + "]，相同的课程["+course_id+"]的考试安排!");//打印到控制台上

                            // 把回显信息，保存到Request中
                            req.setAttribute("msg", "已存在相同的班级[" + clazz_id + "]，相同的课程["+course_id+"]的考试安排!");

                            // 返回注册页面
                            req.getRequestDispatcher("/pages/user/add_new_exam.jsp").forward(req, resp);
                        }
                        else {
                            if(!teacher_course_service.is_course_selected_By_one_clazz(course_id, clazz_id)){
                                System.out.println("课程[" + course_id + "]没有在班级["+clazz_id+"]的可选课计划中");//打印到控制台上

                                // 把回显信息，保存到Request中
                                req.setAttribute("msg", "课程[" + course_id + "]没有在班级["+clazz_id+"]的可选课计划中！！！!");

                                // 返回注册页面
                                req.getRequestDispatcher("/pages/user/add_new_exam.jsp").forward(req, resp);
                            }
                            else {
                                Teacher_Course teacher_course = teacher_course_dao.queryTCByCourseandClazz(course_id,clazz_id);
                                String term = teacher_course.getTerm();
                                if(!(Term.time_in_term(term,Timestamp.valueOf(start_time))&&Term.time_in_term(term,Timestamp.valueOf(end_time)))){
                                    System.out.println("考试时间已超出课程的开课学期["+term+"]的范围["+Term.get_first_date(term)+"]到["+Term.get_last_date(term)+"]");//打印到控制台上

                                    // 把回显信息，保存到Request中
                                    req.setAttribute("msg", "考试时间已超出课程的开课学期["+term+"]的范围["+Term.get_first_date(term)+"]到["+Term.get_last_date(term)+"]！！！");

                                    // 返回注册页面
                                    req.getRequestDispatcher("/pages/user/add_new_exam.jsp").forward(req, resp);
                                }
                                else{
                                    if(teacher_course_service.is_ended(teacher_course)) {
                                        System.out.println("在班级[" + clazz_id + "]的课程["+clazz_id+"已经结课");//打印到控制台上

                                        // 把回显信息，保存到Request中
                                        req.setAttribute("msg", "在班级[" + clazz_id + "]的课程["+clazz_id+"已经结课！！！");

                                        // 返回注册页面
                                        req.getRequestDispatcher("/pages/user/add_new_exam.jsp").forward(req, resp);
                                    }
                                    else {
                                        if (!teacher_course.getTerm().equals(Term.get_current_term())) {
                                            System.out.println("不能设置不在本学期[" + Term.get_current_term() + "]开课的课程的考试");//打印到控制台上

                                            // 把回显信息，保存到Request中
                                            req.setAttribute("msg", "不能设置不在本学期[" + Term.get_current_term() + "]开课的课程的考试！！！");

                                            // 返回注册页面
                                            req.getRequestDispatcher("/pages/user/add_new_exam.jsp").forward(req, resp);
                                        } else {
                                            if (nowtime.after(Timestamp.valueOf(start_time))) {
                                                System.out.println("开始时间不能设置在当前时间之前");//打印到控制台上

                                                // 把回显信息，保存到Request中
                                                req.setAttribute("msg", "开始时间不能设置在当前时间之前！！！");

                                                // 返回注册页面
                                                req.getRequestDispatcher("/pages/user/add_new_exam.jsp").forward(req, resp);
                                            } else {
                                                examDao.add_Exam(new Exam(null, id, course_id, clazz_id, classroom, Timestamp.valueOf(start_time), Timestamp.valueOf(end_time)));
                                                // 跳到注册成功页面 regist_success.jsp
                                                req.getRequestDispatcher("/pages/user/manager_add_success.jsp").forward(req, resp);
                                            }
                                        }
                                    }
                            }
                            }
                        }
                    }
                }
            }

        }
    }
}
