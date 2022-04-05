package ServletTest;

import Education_System.dao.ClazzDao;
import Education_System.dao.CourseDao;
import Education_System.dao.DepartmentDao;
import Education_System.dao.impl.ClazzDaoImp;
import Education_System.dao.impl.CourseDaoImp;
import Education_System.dao.impl.DepartmentDaoImp;
import Education_System.pojo.Clazz;
import Education_System.pojo.Course;
import Education_System.utils.Term;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.util.Calendar;
import java.util.List;

public class AdminOptionsTest {
    @Test
    public void optionTest(){
//        1, 开课学期year_str  ok
//        2, 班级代号clazz_id ok
//        3, 课程号course_id ok
//        4, 所在学院department_name ok
//        5, 所在年级grade ok
        JSONObject res = new JSONObject();
        JSONArray schoolyearJsA = new JSONArray();
        String year_str = Term.get_current_year();
        int year_int = Integer.parseInt(year_str);
        for(int i=0;i<3;i++) {
            String school_year = String.valueOf(year_int + i) + "-" + String.valueOf(year_int + 1 + i);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("opt",school_year);
            schoolyearJsA.add(jsonObject);
            System.out.println(school_year);
        }
        res.put("year_str",schoolyearJsA);

        /*class id*/
        ClazzDao clazzDao = new ClazzDaoImp();
        List<Clazz> l1 =clazzDao.queryAllClazz();
        JSONArray clazzIdJsA = new JSONArray();
        for(Clazz l_cl:l1){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("opt",l_cl.getId());
            clazzIdJsA.add(jsonObject);
        }
        res.put("clazz_id",clazzIdJsA);

        List<String> l3 =clazzDao.queryAllClazzname();
        JSONArray classnameJsA = new JSONArray();
        for(String l_name:l3){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("opt",l_name);
            classnameJsA.add(jsonObject);
        }
        res.put("clazz_name",classnameJsA);

        /*course_id*/
        CourseDao courseDao = new CourseDaoImp();
        List<Course> l = courseDao.queryAllCourse();
        JSONArray courseIdJsA = new JSONArray();
        for(Course co:l){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("opt",co.getId());
            courseIdJsA.add(jsonObject);
        }
        res.put("course_id",courseIdJsA);

        /*department_name*/
        DepartmentDao departmentDao = new DepartmentDaoImp();
        List<String> l2 = departmentDao.queryAllDepartmentname();
        JSONArray departNameJsA = new JSONArray();
        for(String l_name:l2){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("opt",l_name);
            departNameJsA.add(jsonObject);
        }
        res.put("department_name",departNameJsA);

        /*grade*/
        JSONArray gradeJsA = new JSONArray();
        int step=1;
        Calendar date = Calendar.getInstance();
        String year = String.valueOf(date.get(Calendar.YEAR));
        int year_int_2 = Integer.parseInt(year);
        String month = String.valueOf(date.get(Calendar.MONTH));
        if(Integer.parseInt(month)>=7)step=0;
        for(int i=0;i<4;i++) {
            int year_temp_int = year_int_2 - step - i;
            String year_temp = String.valueOf(year_temp_int) + "级";
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("opt",year_temp);
            gradeJsA.add(jsonObject);
        }

        /*course_id*/


        res.put("grade",gradeJsA);
        System.out.println(res.toJSONString());
    }
}
