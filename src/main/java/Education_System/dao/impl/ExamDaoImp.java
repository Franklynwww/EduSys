package Education_System.dao.impl;

import Education_System.dao.ExamDao;
import Education_System.pojo.Exam;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ExamDaoImp extends BaseDao implements ExamDao {

    @Override
    public Exam queryByExamId(String id) {
        String sql = "select `index`,`id`,`course_id`,`clazz_id`,`classroom`,`start_time`,`end_time` from exam_t where id = ?";
        return queryForOne(Exam.class,sql,id);
    }

    @Override
    public Exam queryExamBycourseidandclazzid(String course_id, String clazz_id) {
        String sql = "select `index`,`id`,`course_id`,`clazz_id`,`classroom`,`start_time`,`end_time` from exam_t where course_id = ? and clazz_id=?";
        return queryForOne(Exam.class,sql,course_id,clazz_id);
    }

    @Override
    public List<Timestamp> queryAllStarttimeByclazzid(String clazz_id) {
        List<Timestamp> l = new ArrayList<Timestamp>();
        String sql = "select `index`,`id`,`course_id`,`clazz_id`,`classroom`,`start_time`,`end_time` from exam_t where clazz_id=?";
        List<Exam> li =  queryForList(Exam.class,sql,clazz_id);
        for (Exam exam:li){
            l.add(exam.getStart_time());
        }
        return l;
    }

    @Override
    public List<Timestamp> queryAllEndtimeByclazzid(String clazz_id) {
        List<Timestamp> l = new ArrayList<Timestamp>();
        String sql = "select `index`,`id`,`course_id`,`clazz_id`,`classroom`,`start_time`,`end_time` from exam_t where clazz_id=?";
        List<Exam> li =  queryForList(Exam.class,sql,clazz_id);
        for (Exam exam:li){
            l.add(exam.getEnd_time());
        }
        return l;
    }

    @Override
    public List<Timestamp> queryAllStarttimeByclassroom(String classroom) {
        List<Timestamp> l = new ArrayList<Timestamp>();
        String sql = "select `index`,`id`,`course_id`,`clazz_id`,`classroom`,`start_time`,`end_time` from exam_t where classroom=?";
        List<Exam> li =  queryForList(Exam.class,sql,classroom);
        for (Exam exam:li){
            l.add(exam.getStart_time());
        }
        return l;
    }

    @Override
    public List<Timestamp> queryAllEndtimeByclassroom(String classroom) {
        List<Timestamp> l = new ArrayList<Timestamp>();
        String sql = "select `index`,`id`,`course_id`,`clazz_id`,`classroom`,`start_time`,`end_time` from exam_t where classroom=?";
        List<Exam> li =  queryForList(Exam.class,sql,classroom);
        for (Exam exam:li){
            l.add(exam.getEnd_time());
        }
        return l;
    }

    @Override
    public List<Exam> queryExamBycourseid(String course_id) {
        String sql = "select `index`,`id`,`course_id`,`clazz_id`,`classroom`,`start_time`,`end_time` from exam_t where course_id = ?";
        return queryForList(Exam.class,sql,course_id);
    }

    @Override
    public List<Exam> queryExamByclazzid(String clazz_id) {
        String sql = "select `index`,`id`,`course_id`,`clazz_id`,`classroom`,`start_time`,`end_time` from exam_t where clazz_id = ?";
        return queryForList(Exam.class,sql,clazz_id);
    }

    @Override
    public List<Exam> queryAllExam() {
        String sql = "select `index`,`id`,`course_id`,`clazz_id`,`classroom`,`start_time`,`end_time` from exam_t";
        return queryForList(Exam.class,sql);
    }


    @Override
    public int add_Exam(Exam exam) {
        String sql = "insert into exam_t(`id`,`course_id`,`clazz_id`,`classroom`,`start_time`,`end_time`) values(?,?,?,?,?,?)";
        return update(sql, exam.getId(),exam.getCourse_id(),exam.getClazz_id(),exam.getClassroom(),exam.getStart_time(),exam.getEnd_time());
    }

    @Override
    public int delete_Exam(Exam exam) {
        String sql = "delete from exam_t where id=?";

        //创建教师用户的时候，会自动为教师用户分配一个identity
        return update(sql, exam.getId());
    }

    @Override
    public int delete_ExamBycourseidandclazzid(String course_id, String clazz_id) {
        String sql = "delete from exam_t where course_id=? and clazz_id=?";

        //创建教师用户的时候，会自动为教师用户分配一个identity
        return update(sql, course_id,clazz_id);
    }

    @Override
    public int delete_All_Exam() {
        String sql = "delete from exam_t";

        //创建教师用户的时候，会自动为教师用户分配一个identity
        return update(sql);
    }


}
