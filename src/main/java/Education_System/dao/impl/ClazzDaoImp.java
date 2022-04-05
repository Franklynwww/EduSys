package Education_System.dao.impl;

import Education_System.dao.ClazzDao;
import Education_System.pojo.Clazz;
import Education_System.pojo.Student_User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ClazzDaoImp  extends BaseDao implements ClazzDao {

    @Override
    public List<Student_User> queryAllStudent(String clazz_id) {
        String sql = "select `index`,`identity`,`name`,`password`,`email`,`clazz_id` from student_t where clazz_id = ?";
        return queryForList(Student_User.class, sql, clazz_id);
    }

    @Override
    public Clazz queryClazzById(String clazz_id) {
        String sql = "select `index`,`id`,`department`,`clazz_name`,`stage`,`grade`  from clazz_t where id = ?";
        return queryForOne(Clazz.class,sql,clazz_id);
    }

    @Override
    public Clazz queryClazzByDepartment_Clazzname_Stage_Grade(String department,String clazz_name, String stage, String grade) {
        String sql = "select `index`,`id`,`department`,`clazz_name`,`stage`,`grade`  from clazz_t where department = ? and clazz_name = ? and stage=? and grade=?";
        return queryForOne(Clazz.class,sql,department,clazz_name,stage,grade);
    }

    @Override
    public int add_Clazz(Clazz clazz) {
        String sql = "insert into clazz_t(`id`,`department`,`clazz_name`,`stage`,`grade`) values(?,?,?,?,?)";
        return update(sql, clazz.getId(),clazz.getDepartment(),clazz.getClazz_name(),clazz.getStage(),clazz.getGrade());
    }

    @Override
    public int delete_Clazz(Clazz clazz) {
        String sql = "delete from clazz_t where id=?";

        //创建教师用户的时候，会自动为教师用户分配一个identity
        return update(sql, clazz.getId());
    }

    @Override
    public List<Clazz> queryClazzByDepartment(String department) {
        String sql = "select `index`,`id`,`department`,`clazz_name`,`stage`,`grade`  from clazz_t where department = ?";
        return queryForList(Clazz.class,sql,department);
    }

    @Override
    public List<Clazz> queryAllClazz() {
        String sql = "select `index`,`id`,`department`,`clazz_name`,`stage`,`grade`  from clazz_t";
        return queryForList(Clazz.class,sql);
    }

    @Override
    public List<String> queryAllClazzname() {
        List<String> l =new ArrayList<String>();
        List<Clazz> l1 =queryAllClazz();
        for(Clazz clazz:l1){
                l.add(clazz.getClazz_name());
        }
        HashSet<String> hashSet = new HashSet<String>(l);
        return new ArrayList<String>(hashSet);
    }


}
