package Education_System.dao;

import Education_System.pojo.Clazz;
import Education_System.pojo.Student_User;

import java.util.List;

public interface ClazzDao {

    public List<Student_User> queryAllStudent(String clazz_id);

    public Clazz queryClazzById(String clazz_id);

    public Clazz queryClazzByDepartment_Clazzname_Stage_Grade(String department, String clazz_name, String stage, String grade);

    public int add_Clazz(Clazz clazz);

    public int delete_Clazz(Clazz clazz);

    public List<Clazz> queryClazzByDepartment(String department);

    public List<Clazz> queryAllClazz();

    public List<String> queryAllClazzname();
}
