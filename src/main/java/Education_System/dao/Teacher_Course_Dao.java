package Education_System.dao;

import Education_System.pojo.Teacher_Course;

import java.util.List;

public interface Teacher_Course_Dao {
    public Teacher_Course queryTCByTeacherandCourseandClazz(String teacher_identity, String course_id, String clazz_id);

    public  Teacher_Course queryTCByCourseandClazz(String course_id, String clazz_id);

    public List<Teacher_Course> queryTCByTeacherandCourse(String teacher_identity, String course_id);

    public List<Teacher_Course> queryTCByTeacher(String teacher_identity);

    public List<Teacher_Course> queryTCByCourse(String course_id);

    public List<Teacher_Course> queryTCByClazz(String clazz_id);


    //选课
    public int saveTeacherCourse(Teacher_Course teacher_course);

    //退课
    public int removeTeacherCourse(Teacher_Course teacher_course);

}
