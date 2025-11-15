package flowerShop;

import java.util.ArrayList;

public class Box {
    private final Order o;
    private ArrayList<Product> products = new ArrayList<Product>();
    private boolean isClosed;

    public Box(Order o){
        this.o = o;
        isClosed = false;
    }

    public void pack(Product p){
        if(!isClosed)
            products.add(p);
        else{
            System.out.println("Box closed.");
        }
    }

    public void setOrderStatus(String s){
        o.setStatus(s);
        OrderList.getInstance().refreshCSV(o);
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void close() {
        isClosed = true;
    }

    public ArrayList<Product> getItems(){
        return products;
    }

    public Order getOrder(){
        return o;
    }
}
