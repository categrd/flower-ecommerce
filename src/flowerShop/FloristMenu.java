package flowerShop;
import java.util.Scanner;

public class FloristMenu implements Menu{

    @Override
    public void show() {
        int menuItem;
        boolean logout = false;
        Florist currentFlorist = null;

        if(Program.getInstance().getCurrentUser() instanceof Florist){
            currentFlorist = (Florist) Program.getInstance().getCurrentUser();
        }
        else {
            Program.getInstance().getCurrentUser().setLogged(false);
            Program.getInstance().setMenu(new LoginMenu());
        }

        do{
            System.out.println("\nChoose an option below:");
            System.out.println("1. Fill order");
            System.out.println("2: View order list");
            System.out.println("3: View storage");
            System.out.println("0: Logout");


            Scanner input = new Scanner(System.in);
            menuItem = input.nextInt();

            switch(menuItem){
                case 0:
                    currentFlorist.setLogged(false);
                    logout = true;
                    Program.getInstance().logout();
                    Program.getInstance().setMenu(new LoginMenu());
                    System.out.println("Logged out successfully. Bye bye! \n");
                    break;

                case 1:
                    currentFlorist.fillOrder();
                    break;

                case 2:
                    OrderList.getInstance().displayOrders();
                    break;

                case 3:
                    currentFlorist.checkStorage();
                    break;

                default:
                    System.err.println("Invalid input.");
            }
        }
        while(!logout);
    }
}