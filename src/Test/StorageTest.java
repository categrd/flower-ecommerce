import flowerShop.Product;
import flowerShop.Storage;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("A storage test class")
class StorageTest {
    static Storage storage;

    @BeforeAll
    static void initAll() {
        storage = new Storage();
        storage.initStorage();
        assertNotNull(storage);
    }

    @Test
    @DisplayName("Test that checks if the returned value is a product name")
    void testGetItem() {
        Product p = storage.getItem("lily");
        assertNotNull(p);
        assertEquals(p.getName(), "lily");
    }

    @Test
    @DisplayName("A storage test that checks if the quantity is updated")
    void testRefresh() {
        int initial_q = storage.getQuantity("lily");
        storage.getItem("lily");
        assertEquals(storage.getQuantity("lily"), initial_q-1);
    }
}