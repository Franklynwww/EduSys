package Education_System.Servlet;

import Education_System.dao.UserDao;
import Education_System.dao.impl.TeacherUserDaoImp;
import Education_System.pojo.User;
import Education_System.utils.Request2Str;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "TeacherInfoServlet", value = "/TeacherInfoServlet")
public class TeacherInfoServlet extends HttpServlet {
    UserDao userDao = new TeacherUserDaoImp();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String json = Request2Str.getRequestPostStr(request);
        // 转换为jobj以便可以通过key开读取value
        JSONObject jobj = JSON.parseObject(json);
        String login_t_User_identity = jobj.getString("userId");
        User login_t_User = userDao.queryUserByIdentity(login_t_User_identity);
        JSONObject infoObj = new JSONObject();
        infoObj.put("identity",login_t_User.getIdentity());
        infoObj.put("name",login_t_User.getName());
        infoObj.put("email",login_t_User.getEmail());
        infoObj.put("department",login_t_User.getDepartment());
        infoObj.put("title",login_t_User.getTitle());
        String res = infoObj.toString();
        response.getWriter().print(res);
    }
}
