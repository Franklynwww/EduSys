package Education_System.Servlet;

import Education_System.dao.impl.Student_Course_Dao_Imp;
import Education_System.pojo.Student_Course;
import Education_System.pojo.Student_User;
import Education_System.pojo.User;
import Education_System.service.impl.StudentUserServiceImp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "MyTestServlet", value = "/MyTestServlet")
public class MyTestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println(1111);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User s = new StudentUserServiceImp().querybyIndentity("201930140334");
        PrintWriter out = response.getWriter();
        //此打印流对象可向浏览器输出信息
        out.write(s.toString());

    }
}
