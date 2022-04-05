package Education_System.Servlet;

import Education_System.common.Result;
import Education_System.dao.UserDao;
import Education_System.dao.impl.TeacherUserDaoImp;
import Education_System.pojo.Teacher_User;
import Education_System.service.UserService;
import Education_System.service.impl.TeacherUserServiceImp;
import Education_System.utils.Request2Str;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "TeacherRegisterServlet", value = "/TeacherRegisterServlet")
public class TeacherRegisterServlet extends HttpServlet {
    UserService userService = new TeacherUserServiceImp();

    UserDao userDao = new TeacherUserDaoImp();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String json = Request2Str.getRequestPostStr(request);
        JSONObject jobj = JSON.parseObject(json);

        String identity = jobj.getString("identity");
        String name =jobj.getString("name");
        String password = jobj.getString("password");
        String email = jobj.getString("email");
        String department = jobj.getString("teacher_department");
        String title= jobj.getString("teacher_title");
        Result result = null;

        // 3、检查 用户名是否可用
        if (userService.existsIdentity(identity)) {
            //用户名不可用
            result = Result.error(-1,"教师号" + identity + "已存在!");
//            System.out.println("教师号[" + identity + "]已存在!");//打印到控制台上
//
//            // 把回显信息，保存到Request中
//            req.setAttribute("msg", "教师号已存在！！");
//            req.setAttribute("email", email);
//            req.setAttribute("department", department);
//            req.setAttribute("title", title);
//
//
//            // 返回注册页面
//            req.getRequestDispatcher("/pages/user/teacher_regist.jsp").forward(req, resp);
        } else {
            //用户名可用，检查邮箱
            if (userDao.queryUserByemail(email) != null) {
                //用户名不可用
                result = Result.error(-1,"邮箱" + email + "已存在!");

//                System.out.println("邮箱[" + email + "]已存在!");//打印到控制台上
//
//                // 把回显信息，保存到Request中
//                req.setAttribute("msg", "邮箱已存在！！");
//                req.setAttribute("identity", identity);
//                req.setAttribute("department", department);
//                req.setAttribute("title", title);
//
//
//                // 返回注册页面
//                req.getRequestDispatcher("/pages/user/teacher_regist.jsp").forward(req, resp);

            } else {
                // 调用Service的接口保存到数据库
                userService.registUser(new Teacher_User(null, identity, name,password, email, department, title));
                // 跳到注册成功页面 regist_success.jsp
                result = Result.success();
//                req.getRequestDispatcher("/pages/user/regist_success.jsp").forward(req, resp);
            }
        }
        Gson gson = new Gson();
        String Rson = gson.toJson(result);
        response.getWriter().print(Rson);
    }
}
