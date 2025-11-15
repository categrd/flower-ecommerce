import flowerShop.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class OrderListTest {
    private static LoginMenu lm = new LoginMenu();
    private static Customer c;

    @BeforeAll
    public static void setUp(){
        Program.getInstance().initUsers();
        OrderList.getInstance().init();
        ArrayList<Integer> num = new ArrayList<>();
        num.add(2);
        num.add(11);
        c = new Customer("test", "test", "test", "test", lm.encode("test"), false);
        c.createOrder(num);
    }

    @Test
    @DisplayName("Test that checks if order has been added to orderlist")
    void testPutOrder() {
        Order o = new Order(c);
        int test = OrderList.getInstance().getSize();
        OrderList.getInstance().putOrder(o);
        assertEquals(o, OrderList.getInstance().getLastOrder());
        assertEquals(test+1, OrderList.getInstance().getSize());
    }

    @Test
    @DisplayName("Test that checks if getOrder actually returns the last Order")
    void testGetOrder() {
        Order o = OrderList.getInstance().getOrder();
        assertNotNull(o);
        assertFalse(o.isComplete());
        assertEquals("Processing", o.getStatus());
    }
}