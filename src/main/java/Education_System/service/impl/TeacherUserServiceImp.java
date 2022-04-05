package Education_System.service.impl;

import Education_System.dao.UserDao;
import Education_System.dao.impl.TeacherUserDaoImp;
import Education_System.pojo.User;
import Education_System.service.UserService;

public class TeacherUserServiceImp implements UserService {

    public UserDao userDao = new TeacherUserDaoImp();

    @Override
    public void registUser(User user) {
        userDao.saveUser(user);
    }

    @Override
    public User login(String identity, String password) {
        return userDao.queryUserByIdentityAndPassword(identity,password);
    }

    @Override
    public boolean existsIdentity(String username) {
        if (userDao.queryUserByIdentity(username) == null) {
            // 等于null,说明没查到，没查到表示该用户名可用
            return false;
        }

        return true;//用户名已经存在，不可用（即不可再申请同样的用户名）
    }

    @Override
    public User querybyIndentity(String identity) {
        return userDao.queryUserByIdentity(identity);
    }

    @Override
    public void remove(User user) {
        userDao.removeUser(user);
    }
}
