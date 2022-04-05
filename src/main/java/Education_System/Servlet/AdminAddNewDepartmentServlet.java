package Education_System.Servlet;

import Education_System.common.Result;
import Education_System.pojo.Department;
import Education_System.service.DepartmentService;
import Education_System.utils.Request2Str;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "AdminAddNewDepartmentServlet", value = "/AdminAddNewDepartmentServlet")
public class AdminAddNewDepartmentServlet extends HttpServlet {
    DepartmentService departmentService = new DepartmentService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String json = Request2Str.getRequestPostStr(request);
        JSONObject jobj = JSON.parseObject(json);
        String name = jobj.getString("departmentName");
        Department department = departmentService.queryDepartmentByname(name);
        Result result=null;
        // 如果等于null,说明登录失败!
        if (department!=null) {
            //用户名不可用
            result=Result.error(-1,"学院名 " + name + " 已存在!");

        } else { //用户名可用
            // 调用Service的接口保存到数据库
            departmentService.add(new Department(null, name));
            // 跳到注册成功页面 regist_success.jsp
            result = Result.success();
        }
        Gson gson = new Gson();
        String Rson = gson.toJson(result);
        response.getWriter().print(Rson);
    }
}
