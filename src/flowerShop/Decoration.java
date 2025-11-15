package flowerShop;

public class Decoration extends Product{

    public Decoration(String name, float price){
        this.price = price;
        this.name = name;
    }

    @Override
    protected Decoration clone(){
        return new Decoration(this.name, this.price);
    }
}
