package Education_System.Servlet;

import Education_System.common.Result;
import Education_System.dao.ClazzDao;
import Education_System.dao.impl.ClazzDaoImp;
import Education_System.pojo.Clazz;
import Education_System.utils.Request2Str;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "AdminAddNewClazzServlet", value = "/AdminAddNewClazzServlet")
public class AdminAddNewClazzServlet extends HttpServlet {
    ClazzDao clazzDao = new ClazzDaoImp();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String json = Request2Str.getRequestPostStr(request);
        JSONObject jobj = JSON.parseObject(json);

        String department = jobj.getString("department");
        String id = jobj.getString("clazzCode");
        String clazz_name = jobj.getString("clazzName");
        String stage = jobj.getString("stage");
        String grade = jobj.getString("grade");

        Result result = null;
        // 调用 userService.login()登录处理业务
        Clazz clazz = clazzDao.queryClazzById(id);
        // 如果等于null,说明登录失败!
        if (clazz!=null) {
            result = Result.error(-1,"班级代号" + id + "已存在!");
        } else { //用户名可用
            Clazz clazz1 = clazzDao.queryClazzByDepartment_Clazzname_Stage_Grade(department, clazz_name, stage, grade);
            if (clazz1 != null) {
//                System.out.println("已存在相同的学院[" + department + "]，相同的班级名["+clazz_name+"]，相同的学历阶段["+stage+"]，相同的年级["+grade+"]的班级!");//打印到控制台上
                result = Result.error(-1,"已存在相同的学院" + department + "]，相同的班级名"+clazz_name+"]，相同的学历阶段"+stage+"]，相同的年级"+grade+"]的班级!");
            } else {
                // 调用Service的接口保存到数据库
                clazzDao.add_Clazz(new Clazz(null, id, department, clazz_name, stage, grade));
                result=Result.success();
            }
        }
        Gson gson = new Gson();
        String Rson = gson.toJson(result);
        response.getWriter().print(Rson);
    }
}
