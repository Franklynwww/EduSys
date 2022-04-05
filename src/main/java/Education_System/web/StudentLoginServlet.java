package Education_System.web;

import Education_System.pojo.User;
import Education_System.service.UserService;
import Education_System.service.impl.StudentUserServiceImp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class StudentLoginServlet extends HttpServlet {

    private UserService userService=new StudentUserServiceImp();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        //  1、获取请求的参数
        String identity = req.getParameter("identity");
        String password = req.getParameter("password");
        // 调用 userService.login()登录处理业务
        User loginUser = userService.login(identity,password);
        // 如果等于null,说明登录失败!
        if (loginUser == null) {
            // 把错误信息，和回显的表单项信息，保存到Request中
            req.setAttribute("msg","学号或密码错误！");
            req.setAttribute("identity", identity);
            // 登录失败，返回登录界面
            req.getRequestDispatcher("/pages/user/student_login.jsp").forward(req, resp);
        } else {
            // 登录成功
            // 登录成功，跳到成功页面login_success.html
            req.setAttribute("loginUser", loginUser.getIdentity());
            req.getRequestDispatcher("/pages/user/student_login_success.jsp").forward(req, resp);
        }
    }

}
