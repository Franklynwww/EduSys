package Education_System.web;

import Education_System.dao.Teacher_Course_Dao;
import Education_System.dao.TimetableDao;
import Education_System.dao.impl.Teacher_Course_Dao_Imp;
import Education_System.dao.impl.TimetableDaoImp;
import Education_System.pojo.Teacher_Course;
import Education_System.pojo.timetable;
import Education_System.service.Teacher_Course_Service;
import Education_System.service.TimetableService;
import Education_System.utils.Term;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class AddnewtimetableServlet extends HttpServlet {
    TimetableDao timetableDao = new TimetableDaoImp();
    TimetableService timetableService = new TimetableService();
    Teacher_Course_Dao teacher_course_dao = new Teacher_Course_Dao_Imp();
    Teacher_Course_Service teacher_course_service = new Teacher_Course_Service();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        //  1、获取请求的参数
        String course_id = req.getParameter("course_id");
        String clazz_id = req.getParameter("clazz_id");
        String classroom = req.getParameter("classroom");
        String start_week_str = req.getParameter("start_week");
        String end_week_str = req.getParameter("end_week");
        String week_day = req.getParameter("week_day");
        String section = req.getParameter("section");
        Integer start_week = Integer.parseInt(start_week_str);
        Integer end_week = Integer.parseInt(end_week_str);
        // 调用 userService.login()登录处理业务
        List<timetable> tis = timetableDao.queryTimetableByclazzIdandweekdayandsection(clazz_id, week_day, section);
        timetable ti_candidate = new timetable(null, course_id, clazz_id, classroom, start_week, end_week, week_day, section);
        // 如果等于null,说明登录失败!
        if (timetableService.conflict(ti_candidate)) {
            //用户名不可用
            System.out.println("与同一（或有重叠区间）时段上的同一班级或同一课室上的其他课程安排冲突");//打印到控制台上

            // 把回显信息，保存到Request中
            req.setAttribute("msg", "与同一（或有重叠区间）时段上的同一班级或同一课室上的其他课程安排冲突！！！");

            // 返回注册页面
            req.getRequestDispatcher("/pages/user/add_new_timetable.jsp").forward(req, resp);
        } else {
            Teacher_Course_Dao teacher_course_dao = new Teacher_Course_Dao_Imp();
            Teacher_Course teacher_course = teacher_course_dao.queryTCByCourseandClazz(course_id, clazz_id);
            if (teacher_course == null) {
                System.out.println("课程[" + course_id + "]不在班级[" + clazz_id + "]的课程计划中");//打印到控制台上

                // 把回显信息，保存到Request中
                req.setAttribute("msg", "课程[" + course_id + "]不在班级[" + clazz_id + "]的课程计划中！！！");

                // 返回注册页面
                req.getRequestDispatcher("/pages/user/add_new_timetable.jsp").forward(req, resp);
            } else {
                if (teacher_course_service.is_ended(teacher_course)) {
                    System.out.println("在班级[" + clazz_id + "]的课程["+course_id+"已经结课");//打印到控制台上

                    // 把回显信息，保存到Request中
                    req.setAttribute("msg", "在班级[" + clazz_id + "]的课程["+course_id+"已经结课！！！");

                    // 返回注册页面
                    req.getRequestDispatcher("/pages/user/add_new_timetable.jsp").forward(req, resp);

                } else {
                    if (!teacher_course.getTerm().equals(Term.get_current_term())) {
                        System.out.println("不能设置不在本学期[" + Term.get_current_term() + "开课的课程的课程安排");//打印到控制台上

                        // 把回显信息，保存到Request中
                        req.setAttribute("msg", "不能设置不在本学期[" + Term.get_current_term() + "开课的课程的课程安排！！！");

                        // 返回注册页面
                        req.getRequestDispatcher("/pages/user/add_new_timetable.jsp").forward(req, resp);

                    } else {
                        List<timetable> l = timetableService.queryTimetableByteacher(teacher_course.getTeacher_identity());
                        for (timetable ti2 : l) {
                            if (timetableService.queryterm(ti2).equals(timetableService.queryterm(ti_candidate)) && ti2.getWeek_day().equals(week_day) && ti2.getSection().equals(section) && (!(ti_candidate.getEnd_week() < ti2.getStart_week() || ti_candidate.getStart_week() > ti2.getEnd_week()))) {
                                System.out.println("课程[" + course_id + "]在班级[" + clazz_id + "]的任课教师[" + teacher_course.getTeacher_identity() + "]在从[" + ti2.getStart_week() + "]周到" + ti2.getEnd_week() + "]周的[" + week_day + "]的[" + section + "]有其他的待上课程安排,时间上有冲突");//打印到控制台上

                                // 把回显信息，保存到Request中
                                req.setAttribute("msg", "课程[" + course_id + "]在班级[" + clazz_id + "]的任课教师[" + teacher_course.getTeacher_identity() + "]在从[" + ti2.getStart_week() + "]周到" + ti2.getEnd_week() + "]周的[" + week_day + "]的[" + section + "]有其他的待上课程安排,时间上有冲突！！！");

                                // 返回注册页面
                                req.getRequestDispatcher("/pages/user/add_new_timetable.jsp").forward(req, resp);
                                return;
                            }
                        }
                        timetableService.add_timetable(ti_candidate);
                        req.getRequestDispatcher("/pages/user/manager_add_success.jsp").forward(req, resp);
                    }
                }
            }
        }
    }
}
