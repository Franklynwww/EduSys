package Education_System.web;

import Education_System.dao.TimetableDao;
import Education_System.dao.impl.TimetableDaoImp;
import Education_System.pojo.timetable;
import Education_System.service.TimetableService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeletetimetableServlet extends HttpServlet {
    TimetableDao timetableDao = new TimetableDaoImp();
    TimetableService timetableService = new TimetableService();
//    Teacher_Course_Dao teacher_course_dao = new Teacher_Course_Dao_Imp();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        //  1、获取请求的参数
        String course_id = req.getParameter("course_id");
        String clazz_id = req.getParameter("clazz_id");
        String start_week_str = req.getParameter("start_week");
        String end_week_str = req.getParameter("end_week");
        String week_day = req.getParameter("week_day");
        String section = req.getParameter("section");
        Integer start_week = Integer.parseInt(start_week_str);
        Integer end_week = Integer.parseInt(end_week_str);
        // 调用 userService.login()登录处理业务
        timetable ti = timetableDao.queryTimetableBycourseIdandclazzIdandstartweekandendweekandweekdayandsection(course_id,clazz_id,start_week,end_week,week_day,section);
        if(ti==null){
            System.out.println("班级[" + clazz_id + "]没有在["+start_week_str+"]周到["+end_week_str+"]周的["+week_day+"]的"+section+"上课程["+course_id+"]的记录");//打印到控制台上

            // 把回显信息，保存到Request中
            req.setAttribute("msg", "班级[" + clazz_id + "]没有在["+start_week_str+"]周到["+end_week_str+"]周的["+week_day+"]的"+section+"上课程["+course_id+"]的记录");

            // 返回注册页面
            req.getRequestDispatcher("/pages/user/delete_timetable.jsp").forward(req, resp);
        }
        else {
//            if (!ti.getCourse_id().equals(course_id)) {
//                System.out.println("班级[" + clazz_id + "]在["+start_week_str+"]周到["+end_week_str+"]周的["+week_day+"]的"+section+"没有课程["+course_id+"]");//打印到控制台上
//
//                // 把回显信息，保存到Request中
//                req.setAttribute("msg", "班级[" + clazz_id + "]在["+start_week_str+"]周到["+end_week_str+"]周的["+week_day+"]的"+section+"没有课程["+course_id+"]");
//
//                // 返回注册页面
//                req.getRequestDispatcher("/pages/user/delete_timetable.jsp").forward(req, resp);
//            }
            timetableService.delete_timetable(new timetable(null, course_id, clazz_id, null, start_week, end_week, week_day, section));
            req.getRequestDispatcher("/pages/user/manager_delete_success.jsp").forward(req, resp);
        }
    }
}
