package Education_System.service;

import Education_System.dao.*;
import Education_System.dao.impl.*;
import Education_System.pojo.Course;
import Education_System.pojo.Student_Course;
import Education_System.pojo.Teacher_Course;

import java.util.ArrayList;
import java.util.List;

public class Student_Course_Service {
    private Student_Course_Dao student_course_dao = new Student_Course_Dao_Imp();
    private Teacher_Course_Dao teacher_course_dao = new Teacher_Course_Dao_Imp();
    private CourseDao courseDao = new CourseDaoImp();
    private ClazzDao clazzDao = new ClazzDaoImp();
    private UserDao userDao = new StudentUserDaoImp();

    public void select_course(String student_identity, String course_id){
        student_course_dao.saveStudentCourse(new Student_Course(null,student_identity,course_id,0,"未评定"));
    }

    public void delete_course(String student_identity,String course_id){
        student_course_dao.removeStudentCourse(new Student_Course(null,student_identity,course_id,0,null));
    }

    public List<Course> querycourse(String student_identity){
        List<Course> L = new ArrayList<Course>() ;
        List<Student_Course> l1 = student_course_dao.querySCByStudent(student_identity);
        for (Student_Course student_course:l1){
            L.add(courseDao.queryCourseById(student_course.getCourse_id()));
        }
        return L;
    }

    public List<Course> querycourseinoneterm(String student_identity,String term){
        List<Course> L = new ArrayList<Course>();
        List<Student_Course> l1 = student_course_dao.querySCByStudent(student_identity);
        for (Student_Course student_course:l1){
            String course_id = student_course.getCourse_id();
            Teacher_Course teacher_course = teacher_course_dao.queryTCByCourseandClazz(course_id,userDao.queryUserByIdentity(student_identity).getClazz_id());
            if(teacher_course.getTerm().equals(term)){
                L.add(courseDao.queryCourseById(course_id));
            }
        }
        return L;
    }

    public List<String> querycourseId(String student_identity){
        List<String> L = new ArrayList<String>() ;
        List<Student_Course> l1 = student_course_dao.querySCByStudent(student_identity);
        for (Student_Course student_course:l1){
            L.add(student_course.getCourse_id());
        }
        return L;
    }

    public List<Integer> queryallgrade(String student_identity){
        List<Integer> L = new ArrayList<Integer>() ;
        List<Student_Course> l1 = student_course_dao.querySCByCourse(student_identity);
        for (Student_Course student_course:l1){
            L.add(student_course.getScore());
        }
        return L;
    }

    public Integer queryonescore(String student_identity, String course_id){
        List<Student_Course> l1 = student_course_dao.querySCByStudent(student_identity);
        for (Student_Course student_course:l1){
            if (student_course.getCourse_id().equals(course_id)) {
                return student_course.getScore();
            }
        }
        return null;
    }


    public Student_Course querybyStudentandCourse(String student_identity,String course_id){
        return student_course_dao.querySCByStudentandCourse(student_identity,course_id);
    }

    public void changescore(String student_identity, String course_id, int score){
        student_course_dao.changescore(new Student_Course(null,student_identity,course_id,0,null),score);
    }

    public void changestate(String student_identity, String course_id, String state){
        student_course_dao.changestate(new Student_Course(null,student_identity,course_id,0,null),state);
    }




}
