package Education_System.dao.impl;

import Education_System.dao.DepartmentDao;
import Education_System.pojo.Department;

import java.util.ArrayList;
import java.util.List;

public class DepartmentDaoImp extends BaseDao implements DepartmentDao {
    @Override
    public Department queryDepartmentByName(String name) {
        String sql = "select `index`,`name`from department_t where name = ?";
        return queryForOne(Department.class, sql, name);
    }

    @Override
    public int saveDepartment(Department department) {
        String sql = "insert into department_t(`name`) values(?)";

        //创建学生用户的时候，会自动为学生用户分配一个identity
        return update(sql, department.getName());
    }

    @Override
    public List<Department> queryAllDepartment() {
        String sql = "select `index`,`name`from department_t";
        return queryForList(Department.class, sql);
    }

    @Override
    public List<String> queryAllDepartmentname() {
        List<String> l = new ArrayList<String>();
        List<Department> l1 = queryAllDepartment();
        for(Department department:l1){
            l.add(department.getName());
        }
        return l;
    }


}
