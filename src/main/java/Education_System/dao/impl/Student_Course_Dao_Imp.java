package Education_System.dao.impl;

import Education_System.dao.Student_Course_Dao;
import Education_System.dao.UserDao;
import Education_System.pojo.Student_Course;

import java.util.ArrayList;
import java.util.List;

public class Student_Course_Dao_Imp extends BaseDao implements Student_Course_Dao {
    private UserDao userDao = new StudentUserDaoImp();
    public List<Student_Course> querySCByStudent(String student_identity) {
        String sql = "select `index`,`student_identity`,`course_id`,`score`,`state` from sc_t where student_identity = ?";
        return queryForList(Student_Course.class,sql,student_identity);
    }

    @Override
    public List<Student_Course> querySCByCourse(String course_id) {
        String sql = "select `index`,`student_identity`,`course_id`,`score`,`state` from sc_t where course_id = ?";
        return queryForList(Student_Course.class,sql,course_id);
    }

    @Override
    public List<Student_Course> querySCByCourseandClazz(String course_id, String clazz_id) {
        List<Student_Course> l = new ArrayList<Student_Course>();
        String sql = "select sc_t.`index`,`student_identity`,`course_id`,sc_t.`score`,sc_t.`state` from sc_t,student_t where sc_t.student_identity=student_t.identity and course_id = ? and clazz_id=?";
        List<Student_Course> l1= queryForList(Student_Course.class,sql,course_id,clazz_id);
        for (Student_Course student_course:l1){
            if (userDao.queryUserByIdentity(student_course.getStudent_identity()).getClazz_id().equals(clazz_id)){
                l.add(student_course);
            }
        }
        return l;
    }

    @Override
    public Student_Course querySCByStudentandCourse(String student_identity, String course_id) {
        String sql = "select `index`,`student_identity`,`course_id`,`score`,`state` from sc_t where student_identity=? and course_id = ?";
        return queryForOne(Student_Course.class,sql,student_identity,course_id);
    }

    @Override
    public int saveStudentCourse(Student_Course student_course) {
        String sql = "insert into sc_t(`student_identity`,`course_id`,`score`,`state`) values(?,?,?,?)";

        //创建学生用户的时候，会自动为学生用户分配一个identity
        return update(sql, student_course.getStudent_identity(),student_course.getCourse_id(),student_course.getScore(),student_course.getState());
    }

    @Override
    public int removeStudentCourse(Student_Course student_course) {
        String sql = "delete from sc_t where student_identity=? and course_id=?";
        return update(sql, student_course.getStudent_identity(),student_course.getCourse_id());
    }

    @Override
    public int removeStudentCourseByStudentandCourse(String student_identity, String course_id) {
        String sql = "delete from sc_t where student_identity=? and course_id=?";
        return update(sql, student_identity,course_id);
    }

    @Override
    public int changescore(Student_Course student_course,int score) {
        String sql = "update sc_t set score=? where student_identity=? and course_id=?";
        return update(sql,score,student_course.getStudent_identity(),student_course.getCourse_id());
    }

    @Override
    public int changestate(Student_Course student_course, String state) {
        String sql = "update sc_t set state=? where student_identity=? and course_id=?";
        return update(sql,state,student_course.getStudent_identity(),student_course.getCourse_id());
    }


}
