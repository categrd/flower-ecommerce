package flowerShop;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.lang.String;

public class OrderList {
    public static OrderList instance = new OrderList();
    private ArrayList<Order> orders;

    private OrderList() {
        orders = new ArrayList<>();
    }

    public static synchronized OrderList getInstance() {
        return instance;
    }

    public int getSize() {
        return orders.size();
    }

    // ritorna il primo ordine non completo first uncompleted order
    public Order getOrder() {
        int count = 0;
        for (Order order : orders) {
            if (order.isComplete()) {
                count++;
            } else {
                return orders.get(count);
            }
        }
        return null;
    }

    public Order getLastOrder() {
        return orders.get(getSize() - 1);
    }

    public void putOrder(Order o) {
        this.orders.add(o);
        o.notify(null);
        writeOrderOnCSV(o);
    }

    public void displayOrders() {
        for (Order o : orders) {
            o.displayOrderFloristPOV();
        }
        if (orders.size() == 0) {
            System.out.println("No orders to show.");
        }
    }

    public void removeOrder(Order o) {
        this.orders.remove(o);
    }

    public void init() {
        String pathToCSV = "orders.csv";
        List<String[]> csvBody;
        CSVReader reader;
        Order o;
        try {
            reader = new CSVReader(new FileReader(pathToCSV));
            csvBody = reader.readAll();

            for (String[] strings : csvBody) {
                Customer c = Program.getInstance().getCustomerFromId(Integer.parseInt(strings[2]));
                o = new Order(c, Boolean.parseBoolean(strings[0]), strings[3]);
                for (int j = 5; j < strings.length; j++) {
                    Product a = Catalog.getInstance().cloneCatalogItem(strings[j], true);
                    o.addProduct(a);
                }
                orders.add(o);
                Program.getInstance().getCustomerNotifier().addSubject(o);
            }
        } catch (Exception i) {
            System.err.println("Error: init on OrderList while reading csv.");
        }
    }

    public void printCustomerOrders(Customer c) {
        boolean flag = false;
        for (Order o : orders) {
            if (o.getCustomer() == c) {
                if (!flag) {
                    System.out.println("Here's your order list: \n");
                }
                flag = true;
                o.displayOrderCustomerPOV();
            }
        }
        if (!flag) {
            System.out.println("No orders to show!");
        }
    }

    // aggiorna il csv
    public void writeOrderOnCSV(Order order) {
        String pathToCSV = "orders.csv";
        try {
            CSVReader reader = new CSVReader(new FileReader(pathToCSV));
            List<String[]> csvBody = reader.readAll();

            int n = order.getComponents().size();
            String[] neworder = new String[n + 5];
            neworder[0] = String.valueOf(order.isComplete());
            neworder[1] = String.valueOf(order.getId());
            neworder[2] = String.valueOf(order.getCustomer().getId());
            neworder[3] = String.valueOf(order.getStatus());
            neworder[4] = String.valueOf(order.getSubtotal());
            int i = 0;
            for (Product a : order.getComponents()) {
                neworder[5 + i] = a.getName();
                i++;
            }
            csvBody.add(neworder);
            reader.close();

            CSVWriter writer = new CSVWriter(new FileWriter(pathToCSV));
            writer.writeAll(csvBody);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            System.err.println("Error: Csv Exception.");
        }
    }

    //aggiorna il csv
    public void refreshCSV(Order o) {
        String pathToCSV = "orders.csv";
        File inputFile = new File(pathToCSV);
        try {
            CSVReader reader = new CSVReader(new FileReader(pathToCSV));
            List<String[]> csvBody = reader.readAll();
            for (String[] strings : csvBody) {
                int a = o.getId();
                if (strings[1].equals(String.valueOf(a))) {
                    strings[3] = o.getStatus();
                    strings[0] = String.valueOf(o.isComplete());
                }
            }
            reader.close();
            CSVWriter writer = new CSVWriter(new FileWriter(inputFile));
            writer.writeAll(csvBody);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            System.err.println("ERROR: Csv Exception.");
        }
    }
}