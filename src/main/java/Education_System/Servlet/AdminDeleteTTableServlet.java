package Education_System.Servlet;

import Education_System.common.Result;
import Education_System.dao.TimetableDao;
import Education_System.dao.impl.TimetableDaoImp;
import Education_System.pojo.timetable;
import Education_System.service.TimetableService;
import Education_System.utils.Request2Str;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "AdminDeleteTTableServlet", value = "/AdminDeleteTTableServlet")
public class AdminDeleteTTableServlet extends HttpServlet {
    TimetableDao timetableDao = new TimetableDaoImp();
    TimetableService timetableService = new TimetableService();
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
        String clazz_id = jobj.getString("clazzCode");
        String start_week_str = jobj.getString("classStartWeek");
        String end_week_str = jobj.getString("classEndWeek");
        String week_day = jobj.getString("date");
        String section = jobj.getString("time");
        Integer start_week = Integer.parseInt(start_week_str);

        Integer end_week = Integer.parseInt(end_week_str);
        Result result = null;
        // 调用 userService.login()登录处理业务
        timetable ti = timetableDao.queryTimetableBycourseIdandclazzIdandstartweekandendweekandweekdayandsection(course_id,clazz_id,start_week,end_week,week_day,section);
        if(ti==null){
            result = Result.error(-1,"班级" + clazz_id + "没有在"+start_week_str+"周到"+end_week_str+"周的"+week_day+"的"+section+"上课程"+course_id+"的记录");
//            System.out.println("班级[" + clazz_id + "]没有在["+start_week_str+"]周到["+end_week_str+"]周的["+week_day+"]的"+section+"上课程["+course_id+"]的记录");//打印到控制台上
//
//            // 把回显信息，保存到Request中
//            req.setAttribute("msg", "班级[" + clazz_id + "]没有在["+start_week_str+"]周到["+end_week_str+"]周的["+week_day+"]的"+section+"上课程["+course_id+"]的记录");
//
//            // 返回注册页面
//            req.getRequestDispatcher("/pages/user/delete_timetable.jsp").forward(req, resp);
        }
        else {
            timetableService.delete_timetable(new timetable(null, course_id, clazz_id, null, start_week, end_week, week_day, section));
            result = Result.success();
            //req.getRequestDispatcher("/pages/user/manager_delete_success.jsp").forward(req, resp);
        }
        Gson gson = new Gson();
        String Rson = gson.toJson(result);
        response.getWriter().print(Rson);
    }
}
