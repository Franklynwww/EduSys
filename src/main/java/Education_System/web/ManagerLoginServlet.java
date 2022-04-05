package Education_System.web;

import Education_System.pojo.Manager;
import Education_System.service.ManagerService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ManagerLoginServlet extends HttpServlet {
    ManagerService managerService = new ManagerService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        //  1、获取请求的参数
        String identity = req.getParameter("identity");
        String password = req.getParameter("password");
        // 调用 userService.login()登录处理业务
        Manager manager = managerService.login(identity,password);
        // 如果等于null,说明登录失败!
        if (manager == null) {
            // 把错误信息，和回显的表单项信息，保存到Request中
            req.setAttribute("msg","工号或密码错误！");
            req.setAttribute("identity", identity);
            // 登录失败，返回登录界面
            req.getRequestDispatcher("/pages/user/manager_login.jsp").forward(req, resp);
        } else {
            // 登录成功
            // 登录成功，跳到成功页面login_success.html
            req.getRequestDispatcher("/pages/manager/manager.jsp").forward(req, resp);
        }
    }
}
