package flowerShop;
public class Flower extends Product {

    public Flower(String name, float price) {
        this.price = price;
        this.name = name;
    }

    @Override
    protected Flower clone() {
        return new Flower(this.name, this.price);
    }
}
