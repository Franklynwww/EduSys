package Education_System.web;

import Education_System.pojo.User;
import Education_System.service.UserService;
import Education_System.service.impl.StudentUserServiceImp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class StudentRemoveServlet extends HttpServlet {
    UserService userService = new StudentUserServiceImp();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        // 1、获取请求的参数，即用户提交的要注册的用户名，密码，邮箱，以及用户填写的验证码
        String identity = req.getParameter("identity");

        // 3、检查 用户名是否可用
        User user=userService.querybyIndentity(identity);
        if (user==null) {
            //用户名不存在
            System.out.println("学号[" + identity + "]不存在!");//打印到控制台上

            // 把回显信息，保存到Request中
            req.setAttribute("msg", "学号不存在！！");

            // 返回注册页面
            req.getRequestDispatcher("/pages/user/student_remove.jsp").forward(req, resp);
        } else { //学号存在
            // 调用Service的接口保存到数据库
            userService.remove(user);
            // 跳到注册成功页面 regist_success.jsp
            req.getRequestDispatcher("/pages/user/remove_success.jsp").forward(req, resp);
        }
    }
}
