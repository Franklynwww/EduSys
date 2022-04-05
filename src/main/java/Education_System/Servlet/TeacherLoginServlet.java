package Education_System.Servlet;

import Education_System.common.Result;
import Education_System.pojo.User;
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

@WebServlet(name = "TeacherLoginServlet", value = "/TeacherLoginServlet")
public class TeacherLoginServlet extends HttpServlet {
    private final UserService userService=new TeacherUserServiceImp();
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
        }
        else {
            result = Result.success();
        }
        Gson gson = new Gson();
        String Rson = gson.toJson(result);
        response.getWriter().print(Rson);
    }
}
