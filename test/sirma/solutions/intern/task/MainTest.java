package sirma.solutions.intern.task;

import org.junit.Assert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainTest {

    @org.junit.Test
    public void checkSplit() {
        String str = "split, works fine";
        List<String> lst = Main.split(str);

        Assert.assertEquals("works fine", lst.get(1));
    }

    @org.junit.Test
    public void checkGetDifference() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date a = sdf.parse("2009-02-11");
        Date b = sdf.parse("2010-02-11");

        Long result = Main.getDifference(a, b);

        Assert.assertEquals(365, (long)result);
    }
}