package Education_System.service;

import Education_System.pojo.User;

public interface UserService {
    //用户注册业务
    public void registUser(User user);

    //用户登录业务
    public User login(String identity, String password);

    //用户名已存在，如果用户名存在返回“用户名已经存在”
    public boolean existsIdentity(String username);

    //用户名已存在，返回该用户
    public User querybyIndentity(String identity);

    //删除用户
    public void remove(User user);

}
