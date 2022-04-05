package Education_System.service;

import Education_System.dao.DepartmentDao;
import Education_System.dao.impl.DepartmentDaoImp;
import Education_System.pojo.Department;

public class DepartmentService {
    DepartmentDao departmentDao = new DepartmentDaoImp();
    public Department queryDepartmentByname(String  name){
        return  departmentDao.queryDepartmentByName(name);
    }
    public void add(Department department){
        departmentDao.saveDepartment(department);
    }
}
