import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class appGUI {

    private Stage admLoginStage;
    private Scene admLoginScene;
    private Parent admLoginRoot;

    @FXML
    private Button btnClear;

    
    @FXML
    private Button btnExit;

    @FXML
    private Button btnRemove;

    @FXML
    private Button btnProceed;

    @FXML
    private Button btnLogin;

    @FXML
    private TableColumn<myProduct, Double> colPrice;

    @FXML
    private TableColumn<myProduct, String> colProductName;

    @FXML
    private TableColumn<myProduct, Integer> colQty;

    @FXML
    private TextField lblAmount;

    @FXML
    private Label lblPaymentTotal;

    @FXML
    private Label lblprompt;

    @FXML
    private TableView<myProduct> tbProduct;

    @FXML
    private TextField txtBalance;

    @FXML
    private Label rqPayment;

    @FXML
    private Label lblprompt1;

    @FXML
    private AnchorPane paneShowProduct;

    @FXML
    private TextField txtfAmount;

    private ObservableList<myProduct> productData = FXCollections.observableArrayList();

    public void initialize() throws InvocationTargetException{
        showProduct();
        lblAmount.setEditable(false);
        txtBalance.setEditable(false);

        // TableView
        colProductName.setCellValueFactory(new PropertyValueFactory<myProduct, String>("name"));
        colPrice.setCellValueFactory(new PropertyValueFactory<myProduct, Double>("price"));
        colQty.setCellValueFactory(new PropertyValueFactory<myProduct, Integer>("Qty"));

        tbProduct.setItems(productData);
    }
    // Show product 
    public void showProduct(){
        try {
            BufferedReader reader = new BufferedReader(new FileReader("products.txt"));
            String line;
            int rowIndex = 0;
            int colIndex = 0;
            GridPane productGrid = new GridPane();
            productGrid.setHgap(20);
            productGrid.setVgap(8);
            // Read text file
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(", ");
                String productName = data[0];
                String price = data[1];
                String imagePath = data[3];    

                // Set Image
                ImageView imageView = new ImageView(new Image(imagePath));
                imageView.setFitWidth(80);
                imageView.setFitHeight(70);

                Label nameLabel = new Label(productName);
                Label priceLabel = new Label("RM " + price);
                Button selButton = new Button("                        ");
                // Set the background color of the button using CSS
                selButton.setStyle("-fx-background-color: #0598ff; -fx-text-fill: white; -fx-font-size: 6px;");
                selButton.setPrefWidth(80);
                selButton.setPrefHeight(15);

                // Action Event 
                selButton.setOnAction(event -> addItem(productName, Double.parseDouble(price),1));

                VBox productBox = new VBox(); // Use VBox for vertical stacking
                productBox.getChildren().addAll(imageView, nameLabel, priceLabel, selButton);
                productBox.setStyle("-fx-background-color: #ffffff; -fx-padding: 10; -fx-border-color: #000000; -fx-border-radius: 20;");

                // Add the VBox to the GridPane
                
                productGrid.add(productBox, rowIndex, colIndex);
                rowIndex++; // Move to the next row

                if (rowIndex >= 4) { 
                    colIndex++ ;
                    rowIndex = 0; // Reset rowIndex
                }
                                
            }
            paneShowProduct.getChildren().add(productGrid);
            reader.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException io) {
            io.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Add item to Table
    private void addItem(String pdtName, double pdtPrice, int pdtQty) {
        
        
        for(int row = 0; row <tbProduct.getItems().size(); row++){
            if (pdtName == tbProduct.getColumns().get(0).getCellObservableValue(row).getValue()){
                int selQty = (int) tbProduct.getColumns().get(2).getCellObservableValue(row).getValue();
                pdtQty += selQty;
                tbProduct.getItems().remove(row);
            }
        }
        myProduct productAdded = new myProduct(pdtName, pdtPrice, pdtQty);
        tbProduct.getItems().add(productAdded);
        total();
    }
    
    //calculate total and display
    public void total(){
        double total = 0;
        for (int i= 0;i<tbProduct.getItems().size();i++){
            double price = Double.parseDouble(String.valueOf(tbProduct.getColumns().get(1).getCellObservableValue(i).getValue()));
            int Qty = Integer.parseInt(String.valueOf(tbProduct.getColumns().get(2).getCellObservableValue(i).getValue()));
            total += price*Qty;
        }
        DecimalFormat df = new DecimalFormat("0.00");
        lblAmount.setText(df.format(total));
    }
    
    // Save record for product sold
    public void saveRecord(){
        try{
            
            BufferedWriter writer = new BufferedWriter(new FileWriter("records.txt", true));
            for(int row = 0; row < tbProduct.getItems().size(); row++){
                
                String name = (String.valueOf(tbProduct.getColumns().get(0).getCellObservableValue(row).getValue()));
                if (!checkItemExisted(name)){

                    double price = Double.parseDouble(String.valueOf(tbProduct.getColumns().get(1).getCellObservableValue(row).getValue()));
                    int Qty = Integer.parseInt(String.valueOf(tbProduct.getColumns().get(2).getCellObservableValue(row).getValue()));
                    myProduct salesProduct = new myProduct(name, price, Qty);
                    writer.write(salesProduct.returnItem());
                } else{
                    double price = Double.parseDouble(String.valueOf(tbProduct.getColumns().get(1).getCellObservableValue(row).getValue()));
                    int Qty = Integer.parseInt(String.valueOf(tbProduct.getColumns().get(2).getCellObservableValue(row).getValue()));
                    addQuantity(name,price, Qty);

                }
                
            }
            writer.close();
        } catch (IOException io){
            System.out.println("File not Found");
        }
    }

    //add quantity of product 
    public void addQuantity(String name,double price, int Qty){
        List<myProduct> updatedLine = new ArrayList<myProduct>();
        try{
            BufferedReader reader = new BufferedReader(new FileReader("records.txt"));
            String line;
            
            while((line = reader.readLine())!= null){
                String[] data = line.split(", ");
                if(name.equals(data[0])){
                    int totQty = Qty + Integer.parseInt(data[2]);
                    data[2] = Integer.toString(totQty);
                }
                myProduct product = new myProduct(data[0], Double.parseDouble(data[1]), Integer.parseInt(data[2]));
                updatedLine.add(product);
            }
            reader.close();
        } catch (IOException io){
            System.out.println(io);
        }
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter("records.txt"));
            for (myProduct pdt :updatedLine){
                writer.write(pdt.returnItem());
            }
            writer.close();
        } catch (IOException io){
            System.out.println(io);
        }
    }

    //Check item in File, if not add new row
    public Boolean checkItemExisted(String name){
        try{
            BufferedReader reader= new BufferedReader(new FileReader("records.txt"));
            String line;
            while((line = reader.readLine()) != null){
                String[] data = line.split(", ");
                if (name.equals(data[0])){
                    reader.close();
                    return true;
                }
            }

            reader.close();
            
        } catch(IOException io){
            System.out.println(io);
        }
        return false;
        
    }
    
    //Deduce the quantity
    public void reduceRemain(){
        for(int row = 0; row < tbProduct.getItems().size(); row++){
                String name = (String.valueOf(tbProduct.getColumns().get(0).getCellObservableValue(row).getValue()));
                double price = Double.parseDouble(String.valueOf(tbProduct.getColumns().get(1).getCellObservableValue(row).getValue()));
                int Qty = Integer.parseInt(String.valueOf(tbProduct.getColumns().get(2).getCellObservableValue(row).getValue()));
                if (checkItemExisted(name)){
                    itemDeduce(name, price, Qty);
                }   
            }
        }

    public void itemDeduce(String name, double price, int Qty){
        List<mySoftdrink> productUpd = new ArrayList<mySoftdrink>();
        //read list 
        try{
            BufferedReader reader = new BufferedReader(new FileReader("products.txt"));
            String line;
            while ((line = reader.readLine()) != null ){
                String[] data = line.split(", ");
                if (name.equals(data[0])){
                    System.out.println(data[0]);
                    int reduceQty = Integer.parseInt(data[2]) - Qty;
                    data[2] = Integer.toString(reduceQty);
                }
                mySoftdrink softdrink = new mySoftdrink(data[0], Double.parseDouble(data[1]), Integer.parseInt(data[2]), data[3]);
                productUpd.add(softdrink);
            }
            reader.close();
        } catch (IOException io) {
            System.out.println(io);
        }
        //rewrite file
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter("products.txt"));
            for (mySoftdrink pdt : productUpd){
                writer.write(pdt.returnItem());
            }
            writer.close();
        } catch (IOException io){
            System.out.println(io);
        }
    }

    @FXML
    void onClickGoLogin(ActionEvent event) {
        try{
            // move next stage
            admLoginRoot = FXMLLoader.load(getClass().getResource("adminLogin.fxml"));
            admLoginStage = (Stage)((Node)event.getSource()).getScene().getWindow();
            admLoginScene = new Scene(admLoginRoot);
            admLoginStage.setScene(admLoginScene);
            admLoginStage.setTitle("Admin Login");
            admLoginStage.show();

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    void onClickExit(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void onClickRemoveItem(ActionEvent event) {
        ObservableList<myProduct> allProduct, SingleProduct;
        allProduct = tbProduct.getItems();
        SingleProduct = tbProduct.getSelectionModel().getSelectedItems();
        SingleProduct.forEach(allProduct::remove);
        total();
    }

    @FXML
    void onClickClearList(ActionEvent event) {
        ObservableList<myProduct> allProduct;
        allProduct = tbProduct.getItems();
        allProduct.clear();
        lblAmount.setText("");
    }

    @FXML
    void onClickFinish(ActionEvent event) {
        DecimalFormat df = new DecimalFormat("0.00");
        
        //Check TextField
        if(lblAmount.getText().isEmpty()){
            rqPayment.setText("You haven't selected items!");
            return;
        } 
        if (txtfAmount.getText().isEmpty()){
            rqPayment.setText("Please insert the payment amount!");
            return;
        }
        try{
            double totPrice = Double.parseDouble(lblAmount.getText());
            double paidAmount = Double.parseDouble(txtfAmount.getText());
            if (paidAmount < totPrice){
            rqPayment.setText("Insufficient amount!");
            return;
            } else {
            //display balance
            double balance = paidAmount - totPrice;
            txtBalance.setText(df.format(balance));

            //Show Payment Successful
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("Payment Success.");
            alert.setContentText("Payment done. Thanks for Supporting!");
            alert.showAndWait();
            // Save record
            saveRecord();
            reduceRemain();
            // Clear data
            txtBalance.setText("");
            lblAmount.setText("");
            txtfAmount.setText("");
            rqPayment.setText("");
            ObservableList<myProduct> allProduct;
            allProduct = tbProduct.getItems();
            allProduct.clear();

            
        }
        } catch (NumberFormatException e){
            rqPayment.setText("Invalid input. Please enter number!");
        }  
         
    }

    
}

