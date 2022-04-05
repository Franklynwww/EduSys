package Education_System.dao;

import Education_System.pojo.Student_Course;

import java.util.List;

public interface Student_Course_Dao {

    public Student_Course querySCByStudentandCourse(String student_identity, String course_id);

    public List<Student_Course> querySCByStudent(String student_identity);

    public List<Student_Course> querySCByCourse(String course_id);

    public List<Student_Course> querySCByCourseandClazz(String course_id, String clazz_id);

    //选课
    public int saveStudentCourse(Student_Course student_course);

    //退课
    public int removeStudentCourse(Student_Course student_course);

    public int removeStudentCourseByStudentandCourse(String student_identity, String course_id);

    //修改成绩
    public int changescore(Student_Course student_course, int score);

    //修改状态
    public int changestate(Student_Course student_course, String state);



}
