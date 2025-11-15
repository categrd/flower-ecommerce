package flowerShop;

import java.util.ArrayList;
import java.util.Objects;

public class CustomerNotifier implements Observer {
    private ArrayList<Subject> subjects;


    public CustomerNotifier(){
        subjects = new ArrayList<>();
    }

    public void addSubject(Subject s){
        subjects.add(s);
    }

    @Override
    public void update(Object obj) { //obj è ordine, valutare se passare altro for the design
        if (obj instanceof Order){
            sendMessage((Order) obj);
        }
        else {
            System.out.println("Order not received.");
        }
    }

    public void sendMessage(Order o){
        Message m = null;
        if (Objects.equals(o.getStatus(), "Processing")){
            m = new Message(o.getCustomer(), "New Order: #" + o.getId()+ ".", "Dear " +
                    o.getCustomer().getName() +",\nWe have received your order! " +
                    "We'll proceed with the " +
                    "making of your bouquets and flowers. Thank you for choosing us. \n                               " +
                    "                                        Sincerely, Flower shop.");
        }
        else{
            if (Objects.equals(o.getStatus(), "Shipped")){
                m = new Message(o.getCustomer(), "Shipping Info Order #" + o.getId()+ ".", "Dear " +
                        o.getCustomer().getName() +"," +
                        "\nYour order is on the way! You can track your" +
                        " pack visiting the shipping company website. \n                                     " +
                        "                                   Sincerely, Flower shop.");
            }
            else {
                m = new Message(o.getCustomer(), "Delivery Completed", "Dear " + o.getCustomer().getName() +
                        ",\nYour order has been delivered. Hope you enjoy it!" +
                        " If you have any doubts feel free to contact us. We're looking forward to seeing you again. \n" +
                        "                                                                       Sincerely, Flower shop.");
            }
        }
        o.getCustomer().receiveMessage(m);
    }

}