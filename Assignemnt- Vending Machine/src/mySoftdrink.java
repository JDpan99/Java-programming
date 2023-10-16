public class mySoftdrink {

    private String name;
    private double price;
    private int quantity;
    private String imagepath;

    public mySoftdrink(){

    }

    public mySoftdrink(String name, double price, int quantity, String imagepath){
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.imagepath = imagepath;
    }

    public void Setname(String name){
        this.name = name;
    }

    public void Setprice(double price){
        this.price = price;
    }

    public void Setquantity(int quantity){
        this.quantity = quantity;
    }

    public void Setimagepath(String imagepath){
        this.imagepath =imagepath;
    }

    public String Getname() {
        return name;
    }
    
    public double Getprice() {
        return price;
    }
    
    public int Getquantity() {
        return quantity;
    }

    public String Getimagepath() {
        return imagepath;
    }

    public String returnItem(){
        return name +", "+ price +", "+ quantity +", "+ imagepath + "\n";
    }

}
