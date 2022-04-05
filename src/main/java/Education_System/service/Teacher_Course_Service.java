package Education_System.service;

import Education_System.dao.CourseDao;
import Education_System.dao.Student_Course_Dao;
import Education_System.dao.Teacher_Course_Dao;
import Education_System.dao.TimetableDao;
import Education_System.dao.impl.CourseDaoImp;
import Education_System.dao.impl.Student_Course_Dao_Imp;
import Education_System.dao.impl.Teacher_Course_Dao_Imp;
import Education_System.dao.impl.TimetableDaoImp;
import Education_System.pojo.Course;
import Education_System.pojo.Student_Course;
import Education_System.pojo.Teacher_Course;
import Education_System.utils.Term;

import java.util.ArrayList;
import java.util.List;

public class Teacher_Course_Service {
    private Teacher_Course_Dao Teacher_course_dao = new Teacher_Course_Dao_Imp();
    private Student_Course_Dao student_course_dao = new Student_Course_Dao_Imp();
    private CourseDao courseDao = new CourseDaoImp();
    private TimetableDao timetableDao = new TimetableDaoImp();

    public boolean exists_teacher_course_clazz(String teacher_identity,String course_id,String clazz_id){
        if (Teacher_course_dao.queryTCByTeacherandCourseandClazz(teacher_identity,course_id,clazz_id)==null){
            return false;
        }
        return true;
    }

    public void add_course_clazz(String Teacher_identity, String course_id,String clazz_id,String term){
        Teacher_course_dao.saveTeacherCourse(new Teacher_Course(null,Teacher_identity,course_id,clazz_id,term));
    }

    public void delete_course_clazz(String Teacher_identity,String course_id,String clazz_id){
        Teacher_course_dao.removeTeacherCourse(new Teacher_Course(null,Teacher_identity,course_id,clazz_id,null));
        List<String> student_identitys = querystudentIdentity(course_id,clazz_id);
        for(String student_identity:student_identitys){
            student_course_dao.removeStudentCourseByStudentandCourse(student_identity,course_id);
        }
        timetableDao.removeTimetableBycourseIdandclazzId(course_id,clazz_id);

    }


    public List<List<String>> querycourseIdandclazzId(String Teacher_identity){
        List<List<String>> l = new ArrayList<List<String>>();
        List<Teacher_Course> l1 = Teacher_course_dao.queryTCByTeacher(Teacher_identity);
        for (Teacher_Course Teacher_course:l1){
            List<String> l_temp = new ArrayList<String>();
            l_temp.add(Teacher_course.getCourse_id());
            l_temp.add(Teacher_course.getClazz_id());
            l.add(l_temp);
        }
        return l;
    }

    public List<List<String>> querycourseIdandclazzIdinoneterm(String Teacher_identity,String term){
        List<List<String>> l = new ArrayList<List<String>>();
        List<Teacher_Course> l1 = Teacher_course_dao.queryTCByTeacher(Teacher_identity);
        for (Teacher_Course Teacher_course:l1) {
            if (Teacher_course.getTerm().equals(term)) {
                List<String> l_temp = new ArrayList<String>();
                l_temp.add(Teacher_course.getCourse_id());
                l_temp.add(Teacher_course.getClazz_id());
                l.add(l_temp);
            }
        }
        return l;
    }

    public List<Course> querycourseByclazzId(String clazz_id){
        List<Course> L = new ArrayList<Course>();
        List<Teacher_Course> l = Teacher_course_dao.queryTCByClazz(clazz_id);
        for (Teacher_Course teacher_course:l){
            L.add(courseDao.queryCourseById(teacher_course.getCourse_id()));
        }
        return L;
    }


    public List<String> querycourseIdByclazzId(String clazz_id){
        List<String> L = new ArrayList<String>();
        List<Teacher_Course> l = Teacher_course_dao.queryTCByClazz(clazz_id);
        for (Teacher_Course teacher_course:l){
            L.add(teacher_course.getCourse_id());
        }
        return L;
    }

    public boolean is_course_selected_By_one_clazz(String course_id,String clazz_id){
        List<String> L = querycourseIdByclazzId(clazz_id);
        return L.indexOf(course_id) != -1;

    }

    public List<String> querystudentIdentity(String course_id,String clazz_id){
        List<String> L = new ArrayList<String>() ;
        List<Student_Course> l1 = student_course_dao.querySCByCourseandClazz(course_id,clazz_id);
        for (Student_Course student_course:l1){
            L.add(student_course.getStudent_identity());
        }
        return L;
    }

    public boolean is_ended(Teacher_Course teacher_course){
        if(Term.cmp(teacher_course.getTerm(),Term.get_current_term())==-1)return true;
        else return false;
    }





}
