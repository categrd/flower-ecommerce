package flowerShop;

import java.util.ArrayList;

public class Bouquet extends Product {
    private final ArrayList<Product> childProduct;

    public Bouquet(String name) {
        this.price = 5;
        this.name = name;
        this.childProduct = new ArrayList<>();
    }

    public void addItem(Product p) {
        childProduct.add(p);
        this.price += p.getPrice();
    }

    public Product getItem(int i) {
        return childProduct.get(i);
    }

    public int getSize() {
        return childProduct.size();
    }

    @Override
    protected Bouquet clone() {
        Bouquet copy = new Bouquet(this.name);
        for (Product i : this.childProduct) {
            if (i instanceof Flower) {
                Flower fc = ((Flower) i).clone();
                copy.addItem(fc);
            }
            if (i instanceof Decoration) {
                Decoration dc = ((Decoration) i).clone();
                copy.addItem(dc);
            }
        }
        return copy;
    }
}