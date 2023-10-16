import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class myProduct {
        private SimpleStringProperty name;
        private SimpleDoubleProperty price;
        private SimpleIntegerProperty Qty;

    public myProduct(){

    }

    public myProduct(String name, double price, int Qty){
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleDoubleProperty(price);
        this.Qty = new SimpleIntegerProperty(Qty);
    }

    public String getName(){
        return name.get();
    }

    public double getPrice(){
        return price.get();
    }

    public int getQty(){
        return Qty.get();
    }

    public double getTotal(){
        return price.get()*Qty.get();
    }

    public String returnItem(){
        return name.get()+ ", " + price.get() +", "+ Qty.get() + "\n"; 
    }

    public void setName(String name){
        this.name = new SimpleStringProperty(name);
    }

    public void setPrice(double price){
        this.price = new SimpleDoubleProperty(price);
    }

    public void setQty(int Qty){
        this.Qty = new SimpleIntegerProperty(Qty);
    }
}
