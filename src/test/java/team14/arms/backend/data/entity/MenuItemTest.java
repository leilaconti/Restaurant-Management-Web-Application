package team14.arms.backend.data.entity;

import org.junit.Assert;
import org.junit.Test;

public class MenuItemTest {

    @Test
    public void equalsTest() {
        MenuItem o1 = new MenuItem();
        o1.setName("foo");
        o1.setPrice(999);

        MenuItem o2 = new MenuItem();
        o2.setName("bar");
        o2.setPrice(999);

        Assert.assertNotEquals(o1, o2);

        o2.setName("foo");
        Assert.assertEquals(o1, o2);
    }
}
