package Education_System.Servlet;

import Education_System.dao.ClazzDao;
import Education_System.dao.impl.ClazzDaoImp;
import Education_System.pojo.Clazz;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "QueryClazzByIdServlet", value = "/QueryClazzByIdServlet")
public class QueryClazzByIdServlet extends HttpServlet {
    ClazzDao clazzDao = new ClazzDaoImp();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        List<Clazz> l = clazzDao.queryAllClazz();
        JSONArray jsonArray = new JSONArray();
        for (Clazz clazz:l) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("classCode",clazz.getId());
            jsonObject.put("classDepartment",clazz.getDepartment());
            jsonObject.put("className",clazz.getClazz_name());
            jsonObject.put("classStage",clazz.getStage());
            jsonObject.put("classGrade",clazz.getGrade());
            jsonArray.add(jsonObject);
        }
        String res = jsonArray.toString();
        response.getWriter().print(res);
    }
}
