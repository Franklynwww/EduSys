package Education_System.Servlet;

import Education_System.common.Result;
import Education_System.dao.Teacher_Course_Dao;
import Education_System.dao.TimetableDao;
import Education_System.dao.impl.Teacher_Course_Dao_Imp;
import Education_System.dao.impl.TimetableDaoImp;
import Education_System.pojo.Teacher_Course;
import Education_System.pojo.timetable;
import Education_System.service.Teacher_Course_Service;
import Education_System.service.TimetableService;
import Education_System.utils.Request2Str;
import Education_System.utils.Term;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AdminAddNewTTableServlet", value = "/AdminAddNewTTableServlet")
public class AdminAddNewTTableServlet extends HttpServlet {
    TimetableDao timetableDao = new TimetableDaoImp();
    TimetableService timetableService = new TimetableService();
    Teacher_Course_Dao teacher_course_dao = new Teacher_Course_Dao_Imp();
    Teacher_Course_Service teacher_course_service = new Teacher_Course_Service();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String json = Request2Str.getRequestPostStr(request);
        JSONObject jobj = JSON.parseObject(json);

        String course_id = jobj.getString("courseCode");
        String clazz_id = jobj.getString("classCode");
        String classroom = jobj.getString("TTableRoom");
        String start_week_str = jobj.getString("classStartWeek");
        String end_week_str = jobj.getString("classEndWeek");
        String week_day = jobj.getString("date");
        String section = jobj.getString("time");
        Integer start_week = Integer.parseInt(start_week_str);
        Integer end_week = Integer.parseInt(end_week_str);
        Result result=  null;
        // 调用 userService.login()登录处理业务
        List<timetable> tis = timetableDao.queryTimetableByclazzIdandweekdayandsection(clazz_id, week_day, section);
        timetable ti_candidate = new timetable(null, course_id, clazz_id, classroom, start_week, end_week, week_day, section);
        // 如果等于null,说明登录失败!
        if (timetableService.conflict(ti_candidate)) {
            //用户名不可用
            result = Result.error(-1,"与同一（或有重叠区间）时段上的同一班级或同一课室上的其他课程安排冲突");
//            System.out.println("与同一（或有重叠区间）时段上的同一班级或同一课室上的其他课程安排冲突");//打印到控制台上
//
//            // 把回显信息，保存到Request中
//            req.setAttribute("msg", "与同一（或有重叠区间）时段上的同一班级或同一课室上的其他课程安排冲突！！！");
//
//            // 返回注册页面
//            req.getRequestDispatcher("/pages/user/add_new_timetable.jsp").forward(req, resp);
        } else {
            Teacher_Course_Dao teacher_course_dao = new Teacher_Course_Dao_Imp();
            Teacher_Course teacher_course = teacher_course_dao.queryTCByCourseandClazz(course_id, clazz_id);
            if (teacher_course == null) {
                result = Result.error(-1,"课程" + course_id + "不在班级" + clazz_id + "的课程计划中");
//                System.out.println("课程[" + course_id + "]不在班级[" + clazz_id + "]的课程计划中");//打印到控制台上
//
//                // 把回显信息，保存到Request中
//                req.setAttribute("msg", "课程[" + course_id + "]不在班级[" + clazz_id + "]的课程计划中！！！");
//
//                // 返回注册页面
//                req.getRequestDispatcher("/pages/user/add_new_timetable.jsp").forward(req, resp);
            } else {
                if (teacher_course_service.is_ended(teacher_course)) {
                    result = Result.error(-1,"在班级" + clazz_id + "的课程"+course_id+"已经结课");
//                    System.out.println("在班级[" + clazz_id + "]的课程["+course_id+"已经结课");//打印到控制台上
//
//                    // 把回显信息，保存到Request中
//                    req.setAttribute("msg", "在班级[" + clazz_id + "]的课程["+course_id+"已经结课！！！");
//
//                    // 返回注册页面
//                    req.getRequestDispatcher("/pages/user/add_new_timetable.jsp").forward(req, resp);

                } else {
                    if (!teacher_course.getTerm().equals(Term.get_current_term())) {
                        result = Result.error(-1,"不能设置不在本学期" + Term.get_current_term() + "开课的课程的课程安排");
//                        System.out.println("不能设置不在本学期[" + Term.get_current_term() + "开课的课程的课程安排");//打印到控制台上
//
//                        // 把回显信息，保存到Request中
//                        req.setAttribute("msg", "不能设置不在本学期[" + Term.get_current_term() + "开课的课程的课程安排！！！");
//
//                        // 返回注册页面
//                        req.getRequestDispatcher("/pages/user/add_new_timetable.jsp").forward(req, resp);

                    } else {
                        List<timetable> l = timetableService.queryTimetableByteacher(teacher_course.getTeacher_identity());
                        for (timetable ti2 : l) {
                            if (timetableService.queryterm(ti2).equals(timetableService.queryterm(ti_candidate)) && ti2.getWeek_day().equals(week_day) && ti2.getSection().equals(section) && (!(ti_candidate.getEnd_week() < ti2.getStart_week() || ti_candidate.getStart_week() > ti2.getEnd_week()))) {
                                result = Result.error(-1,"课程" + course_id + "在班级" + clazz_id + "的任课教师" + teacher_course.getTeacher_identity() + "在从" + ti2.getStart_week() + "周到" + ti2.getEnd_week() + "周的" + week_day + "的" + section + "有其他的待上课程安排,时间上有冲突");
                                //System.out.println("课程[" + course_id + "]在班级[" + clazz_id + "]的任课教师[" + teacher_course.getTeacher_identity() + "]在从[" + ti2.getStart_week() + "]周到" + ti2.getEnd_week() + "]周的[" + week_day + "]的[" + section + "]有其他的待上课程安排,时间上有冲突");//打印到控制台上

//                                // 把回显信息，保存到Request中
//                                req.setAttribute("msg", "课程[" + course_id + "]在班级[" + clazz_id + "]的任课教师[" + teacher_course.getTeacher_identity() + "]在从[" + ti2.getStart_week() + "]周到" + ti2.getEnd_week() + "]周的[" + week_day + "]的[" + section + "]有其他的待上课程安排,时间上有冲突！！！");
//
//                                // 返回注册页面
//                                req.getRequestDispatcher("/pages/user/add_new_timetable.jsp").forward(req, resp);
                                Gson gson = new Gson();
                                String Rson = gson.toJson(result);
                                response.getWriter().print(Rson);
                                return;
                            }
                        }
                        timetableService.add_timetable(ti_candidate);
                        result = Result.success();
//                        req.getRequestDispatcher("/pages/user/manager_add_success.jsp").forward(req, resp);
                    }
                }
            }
        }
        Gson gson = new Gson();
        String Rson = gson.toJson(result);
        response.getWriter().print(Rson);
    }
}
