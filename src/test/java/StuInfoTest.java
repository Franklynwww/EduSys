import Education_System.dao.ClazzDao;
import Education_System.dao.UserDao;
import Education_System.dao.impl.ClazzDaoImp;
import Education_System.dao.impl.StudentUserDaoImp;
import Education_System.pojo.User;
import org.junit.Test;

public class StuInfoTest {
    @Test
    public void stuInfoTest(){
        String loginUser_identity = "201930140334";
        UserDao userDao = new StudentUserDaoImp();
        User loginUser = userDao.queryUserByIdentity(loginUser_identity);
        System.out.println("学号:"+loginUser.getIdentity());
        System.out.println("姓名:"+loginUser.getName());
        System.out.println("邮箱:"+loginUser.getEmail());
        String clazz_id = loginUser.getClazz_id();
        ClazzDao clazzDao = new ClazzDaoImp();
        System.out.println("学院:"+clazzDao.queryClazzById(clazz_id).getDepartment());
        System.out.println("班级:"+clazzDao.queryClazzById(clazz_id).getClazz_name());
        System.out.println("年级:"+clazzDao.queryClazzById(clazz_id).getGrade());
        System.out.println("阶段:"+clazzDao.queryClazzById(clazz_id).getStage());
    }
}
