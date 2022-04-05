package Education_System.dao;

import Education_System.pojo.User;

public interface UserDao {
    //根据id和密码查询是否已有该用户
    public User queryUserByIdentity(String identity);

    //根据id和密码查询已有用户，返回用户的JavaBean对象，如果返回null，说明用户名或者密码错误
    public User queryUserByIdentityAndPassword(String identity, String password);

    public User queryUserByemail(String email);

    //增加新用户，返回影响的行数，返回-1表示增加新用户失败（通常是因为破坏了数据约束）
    public int saveUser(User user);

    //删除用户
    public int removeUser(User user);


}

