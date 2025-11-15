package flowerShop;

public class ShippingCompany {

    public void ship(Box b) {
        System.out.println("Order shipped.");
        b.setOrderStatus("Shipped");
        Program.getInstance().hold(5);
        System.out.println("Order delivered. \n");
        b.setOrderStatus("Delivered");
    }

    public void schedulePickUp() {
        Program.getInstance().hold(2);
        System.out.println("Withdrawl scheduled.");
    }
}
