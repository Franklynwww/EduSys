import Education_System.dao.UserDao;
import Education_System.dao.impl.TeacherUserDaoImp;
import Education_System.pojo.Teacher_User;
import org.junit.Test;

public class TeacherUserDaoTest {

    UserDao userDao = new TeacherUserDaoImp();

    @Test
    public void queryUserByUsername() {

        if (userDao.queryUserByIdentity("20001201") == null ){
            System.out.println("该教师号不存在！");
        } else {
            System.out.println("该教师号已存在！");
        }
    }

    @Test
    public void queryUserByUsernameAndPassword() {
        if ( userDao.queryUserByIdentityAndPassword("admin","admin1234") == null) {
            System.out.println("教师号或密码错误，登录失败");
        } else {
            System.out.println("查询成功");
        }
    }

    @Test
    public void saveUser() {
        System.out.println(userDao.saveUser(new Teacher_User(null,"20001009","王宇","111111","12345@163.com","计算机学院","教授")));
    }

    @Test
    public void removeUser() {
        System.out.println(userDao.removeUser(new Teacher_User(null,"20001204",null,null,null,null,null)));
    }
}