package flowerShop;
public class Florist extends User {
    private Order currentorder;
    private Storage s;
    private ShippingCompany sc;

    public Florist(String email, String name, String surname, String address, String pass, boolean log){
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.hashpass = pass;
        this.logged = log;
        this.id = Program.getInstance().getNumUsers();
        this.s = new Storage();
        this.sc = new ShippingCompany();
    }

    public void pickOrder() {
        this.currentorder = OrderList.getInstance().getOrder();
    }

    public void sendOrder(Box b) {
            callShippingCompany();
            sc.ship(b);
    }

    public void callShippingCompany() {
        sc.schedulePickUp();
    }

    public Box fillOrder() {
        pickOrder();
        if (currentorder != null) {
            System.out.println("The order is being processed.");
            Box box = new Box(currentorder);
            for (Product i : currentorder.getComponents()) {
                if (i instanceof Flower || i instanceof Decoration) {
                    box.pack(s.getItem(i.getName()));
                } else {
                    Bouquet b = new Bouquet(i.getName());
                    for (int itr = 0; itr < ((Bouquet) i).getSize(); itr++) {
                        Product tmp = ((Bouquet) i).getItem(itr);
                        b.addItem(s.getItem(tmp.getName()));
                    }
                    box.pack(b);
                }
            }
            box.close();
            currentorder.setComplete(true);
            System.out.println("The order #" + currentorder.getId() + " is completed.");
            sendOrder(box);
            OrderList.getInstance().refreshCSV(currentorder);
            return box;
        } else {
            System.out.println("There are no orders to process.");
        }
        return null;
    }

    public void checkStorage(){
        s.display();
    }
}