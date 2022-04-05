package Education_System.dao;

import Education_System.pojo.Department;

import java.util.List;

public interface DepartmentDao {
    public Department queryDepartmentByName(String name);

    public int saveDepartment(Department department);


    public List<Department> queryAllDepartment();


    public List<String> queryAllDepartmentname();

}
