package TestJUnit;

import br.com.project.report.util.DateUtils;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author michael
 */
public class TestDate {

    @Test
    public void TestDate() {

        try {
            assertEquals("25032018", DateUtils.getDateAtualReportName());
        } catch (Exception e) {

            e.printStackTrace();
            fail(e.getMessage());
        }

    }

}
