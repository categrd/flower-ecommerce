package flowerShop;
import java.util.Scanner;

public class InboxMenu implements Menu {
    private Customer currentCustomer;

    public InboxMenu(Customer currentCustomer) {
        this.currentCustomer = currentCustomer;
    }

    @Override
    public void show() {
        int menuItem;
        boolean quit = false;


        do {
            System.out.println("\nChoose an option below:");
            System.out.println("1: View messages");
            System.out.println("2: Empty inbox");
            System.out.println("0: Quit");

            currentCustomer = (Customer) Program.getInstance().getCurrentUser();

            Scanner input = new Scanner(System.in);
            menuItem = input.nextInt();

            switch (menuItem) {
                case 1:
                    currentCustomer.viewInbox();
                    break;

                case 2:
                    currentCustomer.clearInbox();
                    System.out.println("Inbox emptied successfully.");
                    break;

                case 0:
                    Program.getInstance().setMenu(new CustomerMenu());
                    quit = true;
                    break;
            }
        } while (!quit);
    }

}
