package flowerShop;

import java.util.Scanner;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginMenu implements Menu {
    private MessageDigest md;

    public LoginMenu() {
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            System.err.print("ERROR");
        }
    }

    @Override
    public void show() {
        Scanner in = new Scanner(System.in);
        Scanner inLog = new Scanner(System.in);
        boolean quit = false; // questo x uscire dal menu
        int menuItem;
        do {
            System.out.println("Welcome to the Flower Shop!");
            System.out.println("Choose an option below:");
            System.out.println("1. Login");
            System.out.println("2. Sign up");
            System.out.println("0. Quit");
            try {
                menuItem = Integer.parseInt(in.next());
            } catch (Exception e) {
                menuItem = -1;
            }

            switch (menuItem) {

                case 0:
                    quit = true;
                    Program.getInstance().setQuit(true);
                    System.out.println("Flower Shop is being shut.");
                    break;

                case 1:
                    System.out.println("LOGIN MENU");
                    System.out.println("Insert Email:");
                    String name = inLog.nextLine();
                    if (!Program.getInstance().checkEmail(name)) {
                        submit();
                    }
                    while (Program.getInstance().getCurrentUser() == null) {
                        System.out.println("Insert Password:");
                        String pass = inLog.nextLine();
                        Program.getInstance().login(name, encode(pass));
                    }
                    if (Program.getInstance().getCurrentUser() instanceof Florist) {
                        Program.getInstance().setMenu(new FloristMenu());
                    }
                    if (Program.getInstance().getCurrentUser() instanceof Customer) {
                        Program.getInstance().setMenu(new CustomerMenu());
                    }
                    quit = true;
                    break;

                case 2:
                    while (Program.getInstance().getCurrentUser() == null) {
                        submit();
                    }
                    if (Program.getInstance().getCurrentUser() instanceof Florist) {
                        Program.getInstance().setMenu(new FloristMenu());
                    }
                    if (Program.getInstance().getCurrentUser() instanceof Customer) {
                        Program.getInstance().setMenu(new CustomerMenu());
                    }
                    quit = true;
                    break;

            }
        }
        while (!quit);

    }

    public void submit() {
        Scanner inLog = new Scanner(System.in);
        System.out.println("REGISTRATION FORM");
        String a = null;
        String email = null;
        String name = null;
        String surname = null;
        String pass;
        String address = null;
        String encoded = null;
        boolean flag = false;
        do {
            System.out.println("Are you a florist or a customer? ");
            a = inLog.nextLine();
            switch (a) {

                case "florist":
                    System.out.println("Insert Email: ");
                    email = inLog.nextLine();
                    System.out.println("Insert Name: ");
                    name = inLog.nextLine();
                    System.out.println("Insert Surname: ");
                    surname = inLog.nextLine();
                    System.out.println("Insert Address (optional): ");
                    address = inLog.nextLine();
                    System.out.println("Create a Password: ");
                    pass = inLog.nextLine();
                    encoded = encode(pass);
                    flag = true;
                    break;


                case "customer":
                    System.out.println("Insert Email: ");
                    email = inLog.nextLine();
                    System.out.println("Insert Name: ");
                    name = inLog.nextLine();
                    System.out.println("Insert Surname: ");
                    surname = inLog.nextLine();
                    System.out.println("Insert Address: ");
                    address = inLog.nextLine();
                    System.out.println("Create a Password: ");
                    pass = inLog.nextLine();
                    encoded = encode(pass);
                    flag = true;
                    break;

                default:
                    System.err.println("You have to be either a florist or a customer.");

            }
        } while (!flag);

        if (!Program.getInstance().checkEmail(email))
            Program.getInstance().signUp(a, email, name, surname, address, encoded);
    }

    public String encode(String password) {
        md.update(password.getBytes());
        byte[] messageDigestSHA256 = md.digest();
        StringBuilder stringBuffer = new StringBuilder();
        for (byte bytes : messageDigestSHA256) {
            stringBuffer.append(String.format("%02x", bytes & 0xff));
        }
        return stringBuffer.toString();
    }
}