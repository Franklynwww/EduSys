package Education_System.service;

import Education_System.dao.ManagerDao;
import Education_System.dao.impl.ManagerDaoImp;
import Education_System.pojo.Manager;

public class ManagerService {
    public ManagerDao managerDao = new ManagerDaoImp();

    public Manager login(String identity, String password) {
        return managerDao.queryManagerByIdentityAndPassword(identity,password);
    }
}
