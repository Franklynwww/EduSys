package Education_System.dao.impl;

import Education_System.dao.Teacher_Course_Dao;
import Education_System.pojo.Teacher_Course;

import java.util.List;

public class Teacher_Course_Dao_Imp extends BaseDao implements Teacher_Course_Dao {

    @Override
    public Teacher_Course queryTCByTeacherandCourseandClazz(String teacher_identity, String course_id, String clazz_id) {
        String sql = "select `index`,`teacher_identity`,`course_id`,`clazz_id`,`term` from tc_t where teacher_identity = ? and course_id=? and clazz_id=?";
        return queryForOne(Teacher_Course.class,sql,teacher_identity,course_id,clazz_id);
    }

    @Override
    public Teacher_Course queryTCByCourseandClazz(String course_id, String clazz_id) {
        String sql = "select `index`,`teacher_identity`,`course_id`,`clazz_id`,`term` from tc_t where course_id = ? and clazz_id=?";
        return queryForOne(Teacher_Course.class,sql,course_id,clazz_id);
    }

    @Override
    public List<Teacher_Course> queryTCByTeacherandCourse(String teacher_identity, String course_id) {
        String sql = "select `index`,`teacher_identity`,`course_id`,`clazz_id`,`term` from tc_t where teacher_identity = ? and course_id=?";
        return queryForList(Teacher_Course.class,sql,teacher_identity,course_id);
    }

    @Override
    public List<Teacher_Course> queryTCByTeacher(String teacher_identity) {
        String sql = "select `index`,`teacher_identity`,`course_id`,`clazz_id`,`term` from tc_t where teacher_identity = ?";
        return queryForList(Teacher_Course.class,sql,teacher_identity);
    }

    @Override
    public List<Teacher_Course> queryTCByCourse(String course_id) {
        String sql = "select `index`,`teacher_identity`,`course_id`,`clazz_id`,`term` from tc_t where course_id = ?";
        return queryForList(Teacher_Course.class,sql,course_id);
    }

    @Override
    public List<Teacher_Course> queryTCByClazz(String clazz_id) {
        String sql = "select `index`,`teacher_identity`,`course_id`,`clazz_id`,`term` from tc_t where clazz_id = ?";
        return queryForList(Teacher_Course.class,sql,clazz_id);
    }

    @Override
    public int saveTeacherCourse(Teacher_Course teacher_course) {
        String sql = "insert into tc_t(`teacher_identity`,`course_id`,`clazz_id`,`term`) values(?,?,?,?)";

        //创建学生用户的时候，会自动为学生用户分配一个identity
        return update(sql, teacher_course.getTeacher_identity(),teacher_course.getCourse_id(),teacher_course.getClazz_id(),teacher_course.getTerm());
    }

    @Override
    public int removeTeacherCourse(Teacher_Course teacher_course) {
        String sql = "delete from tc_t where teacher_identity=? and course_id=? and clazz_id=?";
        return update(sql, teacher_course.getTeacher_identity(),teacher_course.getCourse_id(),teacher_course.getClazz_id());
    }


}
