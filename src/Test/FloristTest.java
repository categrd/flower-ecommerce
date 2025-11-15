import flowerShop.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FloristTest {
    private static Order o;
    private static Florist f;
    private static Customer c;


    @BeforeAll
    static void setUp() {
        f = new Florist("testemail", "testname", "testsurname", "testaddress",
                "a2242ead55c94c3deb7cf2340bfef9d5bcaca22dfe66e646745ee4371c633fc8", false);
        assertNotNull(f);
        assertAll("f",
                () -> assertEquals( "testemail", f.getEmail()),
                () -> assertEquals("testname", f.getName()),
                () -> assertEquals("testsurname", f.getSurname()),
                () -> assertEquals("testaddress", f.getAddress()),
                () -> assertEquals( "a2242ead55c94c3deb7cf2340bfef9d5bcaca22dfe66e646745ee4371c633fc8", f.getHashPass()));

        c = new Customer("testemail", "testname", "testsurname", "testaddress",
                "a2242ead55c94c3deb7cf2340bfef9d5bcaca22dfe66e646745ee4371c633fc8", false);
        assertNotNull(c);
        assertAll("c",
                () -> assertEquals("testemail", c.getEmail() ),
                () -> assertEquals("testname", c.getName()),
                () -> assertEquals("testsurname", c.getSurname() ),
                () -> assertEquals("testaddress", c.getAddress()),
                () -> assertEquals("a2242ead55c94c3deb7cf2340bfef9d5bcaca22dfe66e646745ee4371c633fc8",
                        c.getHashPass()));

        o = new Order(c);
        assertNotNull(o);
        Flower flower = new Flower("peony", 0);
        o.addProduct(flower);
        assertNotNull(flower);
        OrderList.getInstance().putOrder(o);
    }


    @Test
    @DisplayName("Test that checks order status")
    void testSendOrder() {
        Box b = new Box(o);
        b.close();
        assertNotNull(b);
        f.sendOrder(b);
        assertTrue(b.isClosed());
        assertEquals("Delivered", o.getStatus());
    }

    @Test
    @DisplayName("Test that checks that products of the order are correct")
    void testFillOrder() {
        Box b = f.fillOrder();
        assertNotNull(b);
        ArrayList<Product> products = b.getItems();
        Order o = b.getOrder();
        ArrayList<Product> components = o.getComponents();
        int i = 0;
        for(Product p : products){
            assertEquals(components.get(i).getName(), p.getName());
        }
    }
}