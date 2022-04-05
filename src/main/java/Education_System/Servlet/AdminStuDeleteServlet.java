package Education_System.Servlet;

import Education_System.common.Result;
import Education_System.pojo.User;
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

@WebServlet(name = "AdminStuDeleteServlet", value = "/AdminStuDeleteServlet")
public class AdminStuDeleteServlet extends HttpServlet {
    UserService userService = new StudentUserServiceImp();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String json = Request2Str.getRequestPostStr(request);
        JSONObject jobj = JSON.parseObject(json);

        String identity =  jobj.getString("stuId");
        Result result = null;
        // 3、检查 用户名是否可用
        User user=userService.querybyIndentity(identity);
        if (user==null) {
            //用户名不存在
            result = Result.error(-1,"学号" + identity + "不存在!");
//            System.out.println("学号[" + identity + "]不存在!");//打印到控制台上

            // 把回显信息，保存到Request中
//            req.setAttribute("msg", "学号不存在！！");
//
//            // 返回注册页面
//            req.getRequestDispatcher("/pages/user/student_remove.jsp").forward(req, resp);
        } else { //学号存在
            // 调用Service的接口保存到数据库
            userService.remove(user);
            result = Result.success();
            // 跳到注册成功页面 regist_success.jsp
//            req.getRequestDispatcher("/pages/user/remove_success.jsp").forward(req, resp);
        }
        Gson gson = new Gson();
        String Rson = gson.toJson(result);
        response.getWriter().print(Rson);
    }
}
