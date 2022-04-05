import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class Maptest {
    @Test
    public void testMap() {
        Map<String,String> M = new HashMap<String, String>();
        M.put("111","90");
        M.put("111","100");
        System.out.println(M);
    }
    @Test
    public void test1000(){
        for (int i = 0; i < 1000; i++) {
            for (int i1 = 0; i1 < 1000; i1++) {
                System.out.println(i1*i);
            }
        }
    }
}
