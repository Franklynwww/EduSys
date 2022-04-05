import Education_System.service.ManagerService;
import org.junit.Test;

public class ManagerServiceTest {
    ManagerService managerService = new ManagerService();
    @Test
    public void login(){
        System.out.println(managerService.login("001","1111111"));
    }
}
