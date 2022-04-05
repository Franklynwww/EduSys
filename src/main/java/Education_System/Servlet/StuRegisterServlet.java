package Education_System.Servlet;

import Education_System.common.Result;
import Education_System.dao.ClazzDao;
import Education_System.dao.UserDao;
import Education_System.dao.impl.ClazzDaoImp;
import Education_System.dao.impl.StudentUserDaoImp;
import Education_System.pojo.Clazz;
import Education_System.pojo.Student_User;
import Education_System.service.UserService;
import Education_System.service.impl.StudentUserServiceImp;
import Education_System.utils.Request2Str;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "StuRegisterServlet", value = "/StuRegisterServlet")
public class StuRegisterServlet extends HttpServlet {
    UserService userService = new StudentUserServiceImp();

    ClazzDao clazzDao = new ClazzDaoImp();

    UserDao userDao = new StudentUserDaoImp();
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
        String name = jobj.getString("name");
        String password = jobj.getString("password");
        String email = jobj.getString("email");
        String department = jobj.getString("student_department");
        String clazz_name = jobj.getString("student_clazz");
        String stage = jobj.getString("student_stage");
        String grade = jobj.getString("student_grade");
        Result result = null;
        Clazz clazz = clazzDao.queryClazzByDepartment_Clazzname_Stage_Grade(department, clazz_name, stage, grade);

        // 3、检查 用户名是否可用
        if (userService.existsIdentity(identity)) {
            //用户名不可用
            result = Result.error(-1,"学号" + identity + "已存在!");
//            System.out.println("学号[" + identity + "]已存在!");//打印到控制台上

            // 把回显信息，保存到Request中
//            req.setAttribute("msg", "学号已存在！！");
//            req.setAttribute("email", email);
//            req.setAttribute("department", department);
//            req.setAttribute("clazz_name", clazz_name);
//            req.setAttribute("stage",stage);
//            req.setAttribute("grade",grade);
//
//            // 返回注册页面
//            req.getRequestDispatcher("/pages/user/student_regist.jsp").forward(req, resp);
        } else {
            //用户名可用，检查邮箱
            if (userDao.queryUserByemail(email) != null) {
                result = Result.error(-1,"邮箱"+email+"已存在");
//                System.out.println("邮箱["+email+"]已存在");//打印到控制台上
//
//                // 把回显信息，保存到Request中
//                req.setAttribute("msg", "邮箱["+email+"]已存在！！！");
//                req.setAttribute("identity", identity);
//                req.setAttribute("department", department);
//                req.setAttribute("clazz_name", clazz_name);
//                req.setAttribute("stage",stage);
//                req.setAttribute("grade",grade);
//                // 返回注册页面
//                req.getRequestDispatcher("/pages/user/student_regist.jsp").forward(req, resp);

            } else {
                if (clazz == null) {
                    //班级不可用
                    result = Result.error(-1,"不存在满足条件的班级!");
//                    System.out.println("不存在满足条件的班级!");//打印到控制台上
//
//                    // 把回显信息，保存到Request中
//                    req.setAttribute("msg", "不存在满足条件的班级！！");
//                    req.setAttribute("identity", identity);
//                    req.setAttribute("email", email);
//                    // 返回注册页面
//                    req.getRequestDispatcher("/pages/user/student_regist.jsp").forward(req, resp);
                } else {
                    // 调用Service的接口保存到数据库
                    userService.registUser(new Student_User(null, identity, name,password, email, clazz.getId()));
                    // 跳到注册成功页面 regist_success.jsp
                    result = Result.success();
                }
            }
        }
        Gson gson = new Gson();
        String Rson = gson.toJson(result);
        response.getWriter().print(Rson);
    }
}
