package team14.arms.backend.data.entity;

import org.junit.Assert;
import org.junit.Test;

public class UserTest {

    @Test
    public void equalsTest() {
        User o1 = new User();
        o1.setPasswordHash("hash");
        o1.setEmail("foo@bar.com");
        o1.setFirstName("foo");
        o1.setLastName("bar");
        o1.setRole("role");

        User o2 = new User();
        o2.setPasswordHash("hashhash");
        o2.setEmail("foo@bar.com");
        o2.setFirstName("foobar");
        o2.setLastName("bar");
        o2.setRole("role");

        Assert.assertNotEquals(o1, o2);

        o2.setFirstName("foo");
        Assert.assertEquals(o1, o2);
    }
}
