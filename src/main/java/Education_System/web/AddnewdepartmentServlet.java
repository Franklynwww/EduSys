package Education_System.web;

import Education_System.pojo.Department;
import Education_System.service.DepartmentService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddnewdepartmentServlet extends HttpServlet {
    DepartmentService departmentService = new DepartmentService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        //  1、获取请求的参数
        String name = req.getParameter("name");
        // 调用 userService.login()登录处理业务
        Department department = departmentService.queryDepartmentByname(name);
        // 如果等于null,说明登录失败!
        if (department!=null) {
            //用户名不可用
            System.out.println("学院名[" + name + "]已存在!");//打印到控制台上

            // 把回显信息，保存到Request中
            req.setAttribute("msg", "学院名已存在！！");

            // 返回注册页面
            req.getRequestDispatcher("/pages/user/add_new_department.jsp").forward(req, resp);
        } else { //用户名可用
            // 调用Service的接口保存到数据库
            departmentService.add(new Department(null, name));
            // 跳到注册成功页面 regist_success.jsp
            req.getRequestDispatcher("/pages/user/manager_add_success.jsp").forward(req, resp);
        }
    }
}
