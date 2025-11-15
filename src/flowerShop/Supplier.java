package flowerShop;

public class Supplier implements Observer {
    private Subject s;

    public Supplier(Storage s) {
        this.s = s;
        s.subscribe(this);
    }

    @Override
    public void update(Object str) {
        sendItem((String)str);
    }

    public void sendItem(String str) {
        Catalog c = Catalog.getInstance();
        for (int tmp = 0; tmp < 30; tmp++) {
            Product p = c.cloneCatalogItem(str, false);
            p.setPrice(p.getPrice()/(float)0.35);
            ((Storage)s).restock(p);
        }
    }

    public void setSubject(Storage s) {
        if (s != null) {
            this.s.unsubscribe(this);
            this.s = s;
            s.subscribe(this);
        }
    }
}
