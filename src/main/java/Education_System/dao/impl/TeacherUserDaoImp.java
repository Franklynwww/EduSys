package Education_System.dao.impl;

import Education_System.dao.UserDao;
import Education_System.pojo.Teacher_User;
import Education_System.pojo.User;

public class TeacherUserDaoImp extends BaseDao implements UserDao {
    @Override
    public User queryUserByIdentity(String identity) {
        String sql = "select `index`,`identity`,`name`,`password`,`email`,`department`,`title` from teacher_t where identity = ?";
        return queryForOne(Teacher_User.class, sql, identity);
    }

    @Override
    public User queryUserByIdentityAndPassword(String identity, String password) {
        String sql = "select `index`,`identity`,`name`,`password`,`email`,`department`,`title` from teacher_t where identity = ? and password = ?";
        return queryForOne(Teacher_User.class, sql, identity, password);
    }

    @Override
    public User queryUserByemail(String email) {
        String sql = "select `index`,`identity`,`name`,`password`,`email`,`department`,`title` from teacher_t where email = ?";
        return queryForOne(Teacher_User.class, sql, email);
    }

    @Override
    public int saveUser(User user) {
        String sql = "insert into teacher_t(`identity`,`name`,`password`,`email`,`department`,`title`) values(?,?,?,?,?,?)";

        //创建教师用户的时候，会自动为教师用户分配一个identity
        return update(sql, user.getIdentity(),user.getName(),user.getPassword(),user.getEmail(),user.getDepartment(),user.getTitle());
    }

    @Override
    public int removeUser(User user) {
        String sql = "delete from teacher_t where identity=?";

        //创建教师用户的时候，会自动为教师用户分配一个identity
        return update(sql, user.getIdentity());
    }
}
