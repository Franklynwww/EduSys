import Education_System.dao.ClazzDao;
import Education_System.dao.impl.ClazzDaoImp;
import Education_System.pojo.Clazz;
import org.junit.Test;

public class ClazzDaoTest {
    private ClazzDao clazzDao = new ClazzDaoImp();
    @Test
    public void t1(){
        System.out.println(clazzDao.queryClazzById("B19-C01"));
    }
    @Test
    public void t2(){
        System.out.println(clazzDao.add_Clazz(new Clazz(null,"C02","计算机学院","计算机类2班","本科","2019级")));
    }
}
