package Education_System.dao;

import Education_System.pojo.Course;

import java.util.List;

public interface CourseDao {
    public int addCourse(Course course);

    public int deleteCourseById(String id);

//    public int updateCourse(Course course);

    public List<Course> queryAllCourse();

    public Course queryCourseById(String id);

    public Course queryCourseBycoursename_department_stage(String course_name, String department, String stage);


}
