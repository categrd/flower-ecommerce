package flowerShop;

public interface Subject {
    void notify(Object obj);
    void subscribe(Observer o);
    void unsubscribe(Observer o);
}
