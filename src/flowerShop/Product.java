package flowerShop;

public abstract class Product {
    protected float price;
    protected String name;

    protected abstract Product clone() throws CloneNotSupportedException;

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void display() {
        String str = String.format("%.02f", price);
        System.out.println("Article: " + name + "                    Price: " + str + "€");
    }
}