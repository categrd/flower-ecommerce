package flowerShop;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class Program {
    public static Program p = new Program();
    private ArrayList<User> users;
    private User currentUser;
    private Menu menu;
    private CustomerNotifier cn;
    private boolean quit = false;


    private Program() {
        currentUser = null;
        users = new ArrayList<>();
        menu = new LoginMenu();
        cn = new CustomerNotifier();
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public void createCatalog() {
        Catalog.getInstance();
    }

    public void run() {
        initUsers();
        initInboxes();
        createCatalog();
        OrderList.getInstance().init();
        while (!quit) {
            menu.show();
        }
    }

    public boolean login(String email, String encoded) {
        boolean b = false;
        for (User u : users) {
            if (u.getEmail().equals(email)) {
                if (Objects.equals(u.getHashPass(), encoded)) {
                    System.out.println("Successfully logged in. \n");
                    u.setLogged(true);
                    currentUser = getUser(email);
                    System.out.println("Welcome to the Flower Shop, " +  currentUser.getName() + "!");
                    b = true;
                } else {
                    System.out.println("Wrong password!");
                    currentUser = null;
                    b = false;
                }
            }
        }
        return b;
    }

    public void logout(){
        currentUser = null;
    }

    // registrazione
    public void signUp(String category, String email, String name, String surname, String address, String encoded) {
        if (category.equals("customer")) {
            currentUser = new Customer(email, name, surname, address, encoded, true);
            users.add(currentUser);
        }
        if (category.equals("florist")) {
            currentUser = new Florist(email, name, surname, address, encoded, true);
            users.add(currentUser);
        }
        writeUserOnCSV(category, currentUser);
    }

    public void writeUserOnCSV(String category, User currentUser) {
        String pathToCSV = "users.csv";
        try {
            CSVReader reader = new CSVReader(new FileReader(pathToCSV));
            List<String[]> csvBody = reader.readAll();
            String[] newuser = {category, currentUser.getEmail(), currentUser.getName(), currentUser.getSurname(),
                    currentUser.getAddress(), currentUser.getHashPass(), String.valueOf(currentUser.getId())};
            csvBody.add(newuser);
            reader.close();

            CSVWriter writer = new CSVWriter(new FileWriter(pathToCSV));
            writer.writeAll(csvBody);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            System.err.println("Error: Csv Exception.");
        }
    }

    public void initUsers() {
        String pathToCSV = "users.csv";
        try {
            CSVReader reader = new CSVReader(new FileReader(pathToCSV));
            List<String[]> csvBody = reader.readAll();
            for (String[] strings : csvBody) {
                if (strings[0].equals("florist")) {
                    Florist f = new Florist(strings[1], strings[2], strings[3], strings[4], strings[5], false );
                    users.add(f);
                }
                if (strings[0].equals("customer")) {
                    Customer c = new Customer(strings[1], strings[2], strings[3], strings[4], strings[5], false );
                    users.add(c);
                }
            }
            reader.close();
        } catch (Exception e) {
            System.err.println("Error: init on Program while reading csv.");
        }
    }

    public void initInboxes(){
        String pathToCSV = "messages.csv";
        try {
            CSVReader reader = new CSVReader(new FileReader(pathToCSV));
            List<String[]> csvBody = reader.readAll();
            for (User u : users) {
                if(u instanceof Customer){
                    for (String[] strings : csvBody) {
                        if(Integer.parseInt(strings[0]) == u.getId()){
                            Message msg = new Message(((Customer)u), strings[1], strings[2], Boolean.parseBoolean(strings[3]));
                            ((Customer) u).receiveMessage(msg);
                        }
                    }
                }
            }
            reader.close();
        } catch (Exception e) {
            System.err.println("Error: Program error while initializing message csv.");
        }
    }

    public boolean checkEmail(String str) {
        for (User u : users) {
            if (u.getEmail().equals(str)) {
                return true;
            }
        }
        return false;
    }

    public static Program getInstance() {
        return p;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public User getUser(String email) {
        for (User u : users) {
            if (Objects.equals(u.getEmail(), email)) {
                return u;
            }
        }
        return null;
    }

    public Customer getCustomerFromId(int id) {
        for (User u : users) {
            if (u.getId() == id && u instanceof Customer) {
                return ((Customer) u);
            }
        }
        System.out.println("ID non-existent or non-belonging to a Customer.");
        return null;
    }

    public int getNumUsers() {
        return users.size();
    }

    public void setQuit(boolean quit) {
        this.quit = quit;
    }

    public CustomerNotifier getCustomerNotifier(){
        return cn;
    }

    public void hold(int seconds){
        try{
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e){
            System.err.println("Error: interrupted exception.");
        }
    }
}