package Education_System.Servlet;

import Education_System.dao.ClazzDao;
import Education_System.dao.UserDao;
import Education_System.dao.impl.ClazzDaoImp;
import Education_System.dao.impl.StudentUserDaoImp;
import Education_System.pojo.User;
import Education_System.utils.Request2Str;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "StuInfoServlet", value = "/StuInfoServlet")
public class StuInfoServlet extends HttpServlet {
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
        String loginUser_identity = jobj.getString("userId");
        JSONObject infoObj = new JSONObject();
        UserDao userDao = new StudentUserDaoImp();
        User loginUser = userDao.queryUserByIdentity(loginUser_identity);
        String clazz_id = loginUser.getClazz_id();
        ClazzDao clazzDao = new ClazzDaoImp();
        infoObj.put("stu_identity",loginUser.getIdentity());
        infoObj.put("stu_name",loginUser.getName());
        infoObj.put("stu_email",loginUser.getEmail());
        infoObj.put("stu_department",clazzDao.queryClazzById(clazz_id).getDepartment());
        infoObj.put("stu_clazz_name",clazzDao.queryClazzById(clazz_id).getClazz_name());
        infoObj.put("stu_grade",clazzDao.queryClazzById(clazz_id).getGrade());
        infoObj.put("stu_stage",clazzDao.queryClazzById(clazz_id).getStage());
        String resString = infoObj.toJSONString();
        response.getWriter().print(resString);



    }
}
