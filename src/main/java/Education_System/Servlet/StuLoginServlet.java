package Education_System.Servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import Education_System.common.Result;
import Education_System.pojo.User;
import Education_System.service.UserService;
import Education_System.service.impl.StudentUserServiceImp;
import Education_System.utils.Request2Str;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

@WebServlet(name = "StuLoginServlet", value = "/StuLoginServlet")
public class StuLoginServlet extends HttpServlet {
    private UserService userService=new StudentUserServiceImp();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        // 先把request转换为json String对象
        String json = Request2Str.getRequestPostStr(request);
        // 转换为jobj以便可以通过key开读取value
        JSONObject jobj = JSON.parseObject(json);
        String identity = jobj.getString("identity");
        String password = jobj.getString("password");

        User loginUser = userService.login(identity,password);
        Result result = null;
        if (loginUser == null) {
            result = Result.error(-1,"用户名或密码错误");
        } else {
            // 登录成功
            // 登录成功，跳到成功页面login_success.html
            result = Result.success();
        }
        Gson gson = new Gson();
        String Rson = gson.toJson(result);
        response.getWriter().print(Rson);

    }
}
