import flowerShop.LoginMenu;
import flowerShop.Program;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProgramTest {
    private static LoginMenu lm = new LoginMenu();

    @BeforeAll
    static void setUp() {
        Program.getInstance().initUsers();
    }

    @Test
    @DisplayName("Test that checks log in when a correct password is entered")
    void testLoginCorrectPassword() {
        boolean b = Program.getInstance().login("pippobaudo@gmail.com", lm.encode("pippo"));
        assertTrue(b, "La password inserita in realtà è sbagliata.");
    }

    @Test
    @DisplayName("Test that checks log in when an invalid password is entered")
    void testLoginWrongPass() {
        boolean b = Program.getInstance().login("pippobaudo@gmail.com", lm.encode("rai1"));
        assertFalse(b, "La password inserita in realtà è giusta");
    }

    @Test
    @DisplayName("Test that registers and logs in a new user")
    void signIn() {
        Program.getInstance().signUp("customer", "mikebuongiorno@rai1.it", "mike", "buongiorno",
                "via vai di mike buongiorno", lm.encode("allegria"));

        boolean b = Program.getInstance().login("mikebuongiorno@rai1.it", lm.encode("allegria"));
        assertTrue(b, "La password inserita in realtà è sbagliata.");
    }

    @Test
    @DisplayName("Test that checks if getUserFromID returns a User given an invalid ID")
    void testGetCustomerFromIDIfInvalidID() {
        int id = -1;
        assertNull(Program.getInstance().getCustomerFromId(id));
    }

    @Test
    @DisplayName("Test that checks if getUserFromID returns a User given a valid ID")
    void testGetCustomerFromID() {
        int id = 1;
        assertNotNull(Program.getInstance().getCustomerFromId(id));
    }

    @Test
    @DisplayName("Test that checks if getUser returns a User given an invalid email")
    void testGetUserIfNull() {
        String email = "emailinesistente@gmail.com";
        assertNull(Program.getInstance().getUser(email));
    }

    @Test
    @DisplayName("Test that checks if getUser returns a NotNull User given a valid email")
    void testGetUser() {
        String email = "pippobaudo@gmail.com";
        assertNotNull(Program.getInstance().getUser(email));
        assertNotNull(email);
    }

    @Test
    @DisplayName("Test that checks if an email is already in our program given a valid email")
    void testCheckEmail() {
        boolean b = Program.getInstance().checkEmail("pippobaudo@gmail.com");
        assertTrue(b);
    }

    @Test
    @DisplayName("Test that checks if an email is already in our program given an invalid email")
    void testCheckNonexistentEmail() {
        boolean b = Program.getInstance().checkEmail("emailinesistente@gmail.com");
        assertFalse(b);
    }

    @Test
    @DisplayName("Test that checks if the current user is null after logging out")
    void testLogout() {
        Program.getInstance().logout();
        assertNull(Program.getInstance().getCurrentUser());
    }
}