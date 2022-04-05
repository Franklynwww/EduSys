import Education_System.dao.UserDao;
import Education_System.dao.impl.StudentUserDaoImp;
import Education_System.pojo.Student_User;
import org.junit.Test;

public class StudentUserDaoTest {
    UserDao userDao = new StudentUserDaoImp();

    @Test
    public void queryUserByUsername() {

        if (userDao.queryUserByIdentity("111111") == null ){
            System.out.println("学号不存在！");
        } else {
            System.out.println("学号已存在！");
        }
    }

    @Test
    public void queryUserByUsernameAndPassword() {
        if ( userDao.queryUserByIdentityAndPassword("201930140334","666666") == null) {
            System.out.println("学号或密码错误，登录失败");
        } else {
            System.out.println("查询成功");
        }
    }

    @Test
    public void saveUser() {
        System.out.println(userDao.saveUser(new Student_User(null,"201930140888","陈一","666666","2308211111@qq.com","B19-C02")));
    }

    @Test
    public void removeUser() {
        System.out.println(userDao.removeUser(new Student_User(null,"201930140888","陈一","666666","2308211111@qq.com","B19-C02")));
    }
}
