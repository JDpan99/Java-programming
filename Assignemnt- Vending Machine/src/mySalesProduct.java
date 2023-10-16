import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class mySalesProduct{

    private SimpleStringProperty name;
    private SimpleDoubleProperty price;
    private SimpleIntegerProperty remain;
    private SimpleIntegerProperty sold;
    private SimpleDoubleProperty total;

    public mySalesProduct(String name, double price, int remain, int sold, double total){
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleDoubleProperty(price);
        this.remain = new SimpleIntegerProperty(remain);
        this.sold = new SimpleIntegerProperty(sold);
        this.total = new SimpleDoubleProperty(total);
    }

    public void returnItem(){

    }

    public String getName(){
        return name.get();
    }

    public double getPrice(){
        return price.get();
    }

    public int getRemain(){
        return remain.get();
    }

    public int getSold(){
        return sold.get();
    }

    public double getTotal(){
        return total.get();
    }
}