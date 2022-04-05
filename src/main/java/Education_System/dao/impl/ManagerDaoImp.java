package Education_System.dao.impl;

import Education_System.dao.ManagerDao;
import Education_System.pojo.Manager;

public class ManagerDaoImp extends BaseDao implements ManagerDao {
    @Override
    public Manager queryManagerByIdentityAndPassword(String identity, String password) {
        String sql = "select `index`,`identity`,`password` from manager_t where identity = ? and password = ?";
        return queryForOne(Manager .class, sql, identity, password);
    }
}
