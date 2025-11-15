import flowerShop.LoginMenu;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginMenuTest {
    private static LoginMenu lm = new LoginMenu();

    @Test
    @DisplayName("Test that checks if the password the user chose is encoded")
    void testEncode() {
        String password = "passwordsegretissima";
        assertNotEquals(password, lm.encode(password));
    }
}