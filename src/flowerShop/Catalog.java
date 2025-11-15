package flowerShop;

import com.opencsv.CSVReader;
import java.util.ArrayList;
import java.io.*;
import java.util.*;

public class Catalog {
    private final ArrayList<Product> floristcat = new ArrayList<>();
    private final ArrayList<Product> suppliercat = new ArrayList<>();
    public static Catalog cat = new Catalog();

    private Catalog() {
        try {
            String pathToCSV = "/home/beatrice/Scrivania/VICARIO/FlowerShop/flower.csv";
            CSVReader reader = new CSVReader(new FileReader(pathToCSV));
            List<String[]> csvBody = reader.readAll();
            for (String[] strings : csvBody) {
                Flower f = new Flower(strings[0], Float.parseFloat(strings[1]) * (float) 1);
                Flower f2 = new Flower(strings[0], Float.parseFloat(strings[1]) * (float) 0.35);
                addToFloristCatalog(f);
                addToSupplierCatalog(f2);
            }
            reader.close();

            pathToCSV = "/home/beatrice/Scrivania/VICARIO/FlowerShop/decoration.csv";
            reader = new CSVReader(new FileReader(pathToCSV));
            csvBody = reader.readAll();
            for (String[] strings : csvBody) {
                Decoration d = new Decoration(strings[0], Float.parseFloat(strings[1]) * (float) 1);
                Decoration d2 = new Decoration(strings[0], Float.parseFloat(strings[1]) * (float) 0.35);
                addToFloristCatalog(d);
                addToSupplierCatalog(d2);
            }
            reader.close();

            pathToCSV = "/home/beatrice/Scrivania/VICARIO/FlowerShop/bouquet.csv";
            reader = new CSVReader(new FileReader(pathToCSV));
            csvBody = reader.readAll();
            for (String[] strings : csvBody) {
                Bouquet b = new Bouquet(strings[0]);
                for (int i = 1; i < strings.length; i++) {
                    Product item = cloneCatalogItem(strings[i], true);
                    b.addItem(item);
                }
                addToFloristCatalog(b);
            }
        } catch (Exception e) {
            System.err.println("Error: Csv Exception");
        }
    }

    public static Catalog getInstance() {
        return cat;
    }

    public void addToFloristCatalog(Product p) {
        floristcat.add(p);
    }

    public void addToSupplierCatalog(Product p) {
        suppliercat.add(p);
    }

    public void displayFloristCatalog() {
        int i = 1;
        for (Product item : floristcat) {
            System.out.println(i + ":");
            item.display();
            System.out.println("\n");
            i++;
        }
    }

    public Product cloneCatalogItem(String s, boolean floristcat) {
        Product copy = null;
        ArrayList<Product> list;
        if (floristcat) {
            list = this.floristcat;
        } else {
            list = this.suppliercat;
        }
        for (Product p : list) {
            if (Objects.equals(p.getName(), s)) {
                if (p instanceof Flower) {
                    copy = ((Flower) p).clone();
                } else {
                    if (p instanceof Decoration) {
                        copy = ((Decoration) p).clone();
                    }
                    else{
                        if(p instanceof Bouquet){
                            copy = ((Bouquet) p).clone();
                        }
                    }
                }
            }
        }
        return copy;
    }

    public Product getFloristProduct(int num) {
        return floristcat.get(num - 1);
    }

    public int getFloristCatSize(){
        return floristcat.size();
    }
}
