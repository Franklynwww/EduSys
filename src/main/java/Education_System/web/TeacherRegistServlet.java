package Education_System.web;

import Education_System.dao.UserDao;
import Education_System.dao.impl.TeacherUserDaoImp;
import Education_System.pojo.Teacher_User;
import Education_System.service.UserService;
import Education_System.service.impl.TeacherUserServiceImp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TeacherRegistServlet extends HttpServlet {

    UserService userService = new TeacherUserServiceImp();

    UserDao userDao = new TeacherUserDaoImp();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        // 1、获取请求的参数，即用户提交的要注册的用户名，密码，邮箱，以及用户填写的验证码
        String identity = req.getParameter("identity");
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String department = req.getParameter("department");
        String title= req.getParameter("title");

        // 3、检查 用户名是否可用
            if (userService.existsIdentity(identity)) {
                //用户名不可用
                System.out.println("教师号[" + identity + "]已存在!");//打印到控制台上

                // 把回显信息，保存到Request中
                req.setAttribute("msg", "教师号已存在！！");
                req.setAttribute("email", email);
                req.setAttribute("department", department);
                req.setAttribute("title", title);


                // 返回注册页面
                req.getRequestDispatcher("/pages/user/teacher_regist.jsp").forward(req, resp);
            } else {
                //用户名可用，检查邮箱
                if (userDao.queryUserByemail(email) != null) {
                    //用户名不可用
                    System.out.println("邮箱[" + email + "]已存在!");//打印到控制台上

                    // 把回显信息，保存到Request中
                    req.setAttribute("msg", "邮箱已存在！！");
                    req.setAttribute("identity", identity);
                    req.setAttribute("department", department);
                    req.setAttribute("title", title);


                    // 返回注册页面
                    req.getRequestDispatcher("/pages/user/teacher_regist.jsp").forward(req, resp);

                } else {
                    // 调用Service的接口保存到数据库
                    userService.registUser(new Teacher_User(null, identity, name,password, email, department, title));
                    // 跳到注册成功页面 regist_success.jsp
                    req.getRequestDispatcher("/pages/user/regist_success.jsp").forward(req, resp);
                }
            }
    }
}
