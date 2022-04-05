package ServletTest;

import Education_System.dao.Student_Course_Dao;
import Education_System.dao.Teacher_Course_Dao;
import Education_System.dao.UserDao;
import Education_System.dao.impl.StudentUserDaoImp;
import Education_System.dao.impl.Student_Course_Dao_Imp;
import Education_System.dao.impl.Teacher_Course_Dao_Imp;
import Education_System.pojo.Course;
import Education_System.pojo.Student_Course;
import Education_System.service.Student_Course_Service;
import Education_System.utils.Term;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.util.List;

public class StuScore {
    @Test
    public void stuScore(){
        String loginUser_identity = "201930140334";
        UserDao userDao = new StudentUserDaoImp();
        Student_Course_Dao student_course_dao = new Student_Course_Dao_Imp();
        Student_Course_Service student_course_service = new Student_Course_Service();
        Teacher_Course_Dao teacher_course_dao = new Teacher_Course_Dao_Imp();
        List<Course> courses = student_course_service.querycourseinoneterm(loginUser_identity, Term.get_current_term());
        JSONArray jsonArray = new JSONArray();
        for(Course course :courses){
            Student_Course sc = student_course_dao.querySCByStudentandCourse(loginUser_identity,course.getId());
//            courseName: "ASP.Net开发",
//                    credit: 2,
//                    score: 100,
//                    studentCourseId: 20,
//                    term: "2021-2022-1"
            String courseName =course.getCourse_name();
            String credit =  Float.toString(course.getCredit());
            String score = sc.getState().equals("未评定") ?"未评定":String.valueOf(sc.getScore());
            String studentCourseId =course.getId();
            String term =teacher_course_dao.queryTCByCourseandClazz(course.getId(),userDao.queryUserByIdentity(loginUser_identity).getClazz_id()).getTerm();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("courseName",courseName);
            jsonObject.put("credit",credit);
            jsonObject.put("score",score);
            jsonObject.put("studentCourseId",studentCourseId);
            jsonObject.put("term",term);
            jsonArray.add(jsonObject);
        }
        String res = jsonArray.toString();
        System.out.println(res);
    }
}
