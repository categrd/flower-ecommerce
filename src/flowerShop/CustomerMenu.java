package flowerShop;

import java.util.Scanner;

public class CustomerMenu implements Menu{

    @Override
    public void show() {
        int menuItem;
        boolean exit = false;
        Customer currentCustomer = null;

        if(Program.getInstance().getCurrentUser() instanceof Customer){
            currentCustomer = (Customer) Program.getInstance().getCurrentUser();
        }
        else {
            Program.getInstance().getCurrentUser().setLogged(false);
            Program.getInstance().setMenu(new LoginMenu());
        }

        do {
            System.out.println("\nChoose an option below:");
            System.out.println("1: View my orders");
            System.out.println("2: Place an order");
            System.out.println("3: Inbox");
            System.out.println("0: Logout");

            Scanner input = new Scanner(System.in);
            menuItem = input.nextInt();

            switch (menuItem) {
                case 1:
                    OrderList.getInstance().printCustomerOrders(currentCustomer);
                    break;

                case 2:
                    currentCustomer.placeOrder();
                    System.out.println("Order placed successfully.");
                    break;

                case 3:
                    System.out.println("Welcome to your Inbox.");
                    Program.getInstance().setMenu(new InboxMenu(currentCustomer));
                    exit = true;
                    break;


                case 0:
                    currentCustomer.setLogged(false);
                    exit = true;
                    Program.getInstance().logout();
                    Program.getInstance().setMenu(new LoginMenu());
                    System.out.println("Logged out successfully. Bye bye! \n");
                    break;

                default:
                    System.err.println("Invalid input.");
            }
        } while(!exit);
    }

}
