package Education_System.dao.impl;

import Education_System.dao.UserDao;
import Education_System.pojo.Student_User;
import Education_System.pojo.User;

public class StudentUserDaoImp extends BaseDao implements UserDao {

    @Override
    public User queryUserByIdentity(String identity) {
        String sql = "select `index`,`identity`,`name`,`password`,`email`,`clazz_id` from student_t where identity = ?";
        return queryForOne(Student_User.class, sql, identity);
    }

    @Override
    public User queryUserByIdentityAndPassword(String identity, String password) {
        String sql = "select `index`,`identity`,`name`,`password`,`email`,`clazz_id` from student_t where identity = ? and password = ?";
        return queryForOne(Student_User.class, sql, identity, password);
    }

    @Override
    public User queryUserByemail(String email) {
        String sql = "select `index`,`identity`,`name`,`password`,`email`,`clazz_id` from student_t where email = ?";
        return queryForOne(Student_User.class, sql, email);
    }

    @Override
    public int saveUser(User user) {
        String sql = "insert into student_t(`identity`,`name`,`password`,`email`,`clazz_id`) values(?,?,?,?,?)";

        //创建学生用户的时候，会自动为学生用户分配一个identity
        return update(sql, user.getIdentity(),user.getName(),user.getPassword(),user.getEmail(),user.getClazz_id());
    }

    @Override
    public int removeUser(User user) {
        String sql = "delete from student_t where identity=?";

        //创建教师用户的时候，会自动为教师用户分配一个identity
        return update(sql, user.getIdentity());
    }

}
