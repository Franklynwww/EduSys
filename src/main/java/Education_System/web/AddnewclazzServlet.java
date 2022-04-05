package Education_System.web;

import Education_System.dao.ClazzDao;
import Education_System.dao.impl.ClazzDaoImp;
import Education_System.pojo.Clazz;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddnewclazzServlet extends HttpServlet {
    ClazzDao clazzDao = new ClazzDaoImp();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        //  1、获取请求的参数
        String department = req.getParameter("department_name");
        String id = req.getParameter("id");
        String clazz_name = req.getParameter("clazz_name");
        String stage = req.getParameter("stage");
        String grade = req.getParameter("grade");
        // 调用 userService.login()登录处理业务
        Clazz clazz = clazzDao.queryClazzById(id);
        // 如果等于null,说明登录失败!
        if (clazz!=null) {
            //用户名不可用
            System.out.println("班级代号[" + id + "]已存在!");//打印到控制台上

            // 把回显信息，保存到Request中
            req.setAttribute("msg", "班级代号已存在！！");

            // 返回注册页面
            req.getRequestDispatcher("/pages/user/add_new_clazz.jsp").forward(req, resp);
        } else { //用户名可用
            Clazz clazz1 = clazzDao.queryClazzByDepartment_Clazzname_Stage_Grade(department, clazz_name, stage, grade);
            if (clazz1 != null) {
                System.out.println("已存在相同的学院[" + department + "]，相同的班级名["+clazz_name+"]，相同的学历阶段["+stage+"]，相同的年级["+grade+"]的班级!");//打印到控制台上

                // 把回显信息，保存到Request中
                req.setAttribute("msg", "已存在相同的学院[" + department + "]，相同的班级名["+clazz_name+"]，相同的学历阶段["+stage+"]，相同的年级["+grade+"]的班级!！！");

                // 返回注册页面
                req.getRequestDispatcher("/pages/user/add_new_clazz.jsp").forward(req, resp);
            } else {
                // 调用Service的接口保存到数据库
                clazzDao.add_Clazz(new Clazz(null, id, department, clazz_name, stage, grade));
                // 跳到注册成功页面 regist_success.jsp
                req.getRequestDispatcher("/pages/user/manager_add_success.jsp").forward(req, resp);
            }
        }
    }
}
