package Education_System.dao.impl;

import Education_System.dao.CourseDao;
import Education_System.pojo.Course;

import java.util.List;

public class CourseDaoImp extends BaseDao implements CourseDao {

    @Override
    public int addCourse(Course course) {
        String sql = "insert into course_t(`id`,`course_name`,`credit`,`reg_department`,`stage`) values(?,?,?,?,?)";
        return update(sql, course.getId(), course.getCourse_name(), course.getCredit(), course.getReg_department(), course.getStage());
    }

    @Override
    public int deleteCourseById(String id) {
        String sql = "delete from course_t where id=?";

        //创建学生用户的时候，会自动为学生用户分配一个identity
        return update(sql, id);
    }

    @Override
    public List<Course> queryAllCourse() {
        String sql = "select `index`,`id`,`course_name`,`credit`,`reg_department`,`stage` from course_t";
        return queryForList(Course.class, sql);
    }


    @Override
    public Course queryCourseById(String id) {
        String sql = "select `index`,`id`,`course_name`,`credit`,`reg_department`,`stage` from course_t where id = ?";
        return queryForOne(Course.class, sql, id);
    }

    @Override
    public Course queryCourseBycoursename_department_stage(String course_name, String department, String stage) {
        String sql = "select `index`,`id`,`course_name`,`credit`,`reg_department`,`stage` from course_t where course_name = ? and reg_department=? and stage=?";
        return queryForOne(Course.class, sql, course_name,department,stage);
    }


}