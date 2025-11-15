import flowerShop.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {
    public static Customer c;

    @BeforeAll
    static void setUp(){
        Program.getInstance().initUsers();
        c = new Customer("testemail", "testname", "testsurname", "testaddress",
                "a2242ead55c94c3deb7cf2340bfef9d5bcaca22dfe66e646745ee4371c633fc8", true);
        assertNotNull(c);
        assertAll("c",
                () -> assertEquals("testemail", c.getEmail() ),
                () -> assertEquals("testname", c.getName()),
                () -> assertEquals("testsurname", c.getSurname() ),
                () -> assertEquals("testaddress", c.getAddress()),
                () -> assertEquals("a2242ead55c94c3deb7cf2340bfef9d5bcaca22dfe66e646745ee4371c633fc8",
                        c.getHashPass()));
        int num;
        ArrayList<Integer> testnum = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            num = (int) (Math.random() * 14 + 1);
            testnum.add(num);
        }
        c.createOrder(testnum);
    }

    @Test
    void testClearInbox(){
        assertNotEquals(0, c.getInboxSize());
        c.clearInbox();
        assertEquals(0, c.getInboxSize());
    }

    @Test
    @DisplayName("Test that order has the right size and that it has the same products of the last order in orderlist")
    void testCreateOrder() {
        ArrayList<Integer> testnum = new ArrayList<>();
        int num;
        for(int i = 0; i < 5; i++) {
            num = (int) (Math.random() * 14 + 1);
            testnum.add(num);
        }
        c.createOrder(testnum);
        Order o = OrderList.getInstance().getLastOrder();
        assertNotNull(o);
        assertEquals(c, o.getCustomer());
        assertEquals("Processing", o.getStatus());
        for(int i = 0; i < o.getComponents().size(); i++)
            assertEquals(Catalog.getInstance().getFloristProduct(
                    testnum.get(i)).getName(), o.getComponents().get(i).getName());
    }
}