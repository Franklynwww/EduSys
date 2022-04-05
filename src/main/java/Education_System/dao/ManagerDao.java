package Education_System.dao;


import Education_System.pojo.Manager;

public interface ManagerDao{
    public Manager queryManagerByIdentityAndPassword(String identity, String password);
}
