import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import util.TestData;


/** 
 *
 * @author Nilesh Kapadia (nileshka@gmail.com)
 */
public class ApplicationContextTests extends TestCase {

    public void testLoadApplicationContext() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext(TestData.getApplicationContextFileList());
    }
    
}
