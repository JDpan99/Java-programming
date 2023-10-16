import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class myManagement {

    private Stage dispStage;
    private Scene dispScene;
    private Parent dispRoot;

    private Stage GUIStage;
    private Scene GUIScene;
    private Parent GUIRoot;

    private Stage mngtStage;
    private Scene mngtScene;
    private Parent mngtRoot;

    private Stage addAdmStage;
    private Scene addAdmScene;
    private Parent addAdmRoot;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnAddStaff;

    @FXML
    private Button btnClose;

    @FXML
    private Button btnEditStaff;

    @FXML
    private Button btnExit;

    @FXML
    private Button btnPrint;

    @FXML
    private Label lblProductList;

    @FXML
    private Label lblTitle;

    @FXML
    private AnchorPane myManagementPane;

    @FXML
    private AnchorPane paneProduct;


    public void initialize() {
        display();
    }

    // Display product
    public void display() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("products.txt"));
            String line;
            
            GridPane productGrid = new GridPane();
            productGrid.setHgap(20);
            productGrid.setVgap(8);
            // Declare Index
            int rowIndex = 0;
            int colIndex = 0;
            // Read text file
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(", ");
                String productName = data[0];
                String price = data[1];
                String imagePath = data[3];    

                // Set Image
                ImageView imageView = new ImageView(new Image(imagePath));
                imageView.setFitWidth(80);
                imageView.setFitHeight(60);

                Label nameLabel = new Label(productName);
                Label priceLabel = new Label(price);
                Button edButton = new Button("Edit Product");
                // Set the background color of the button using CSS
                edButton.setStyle("-fx-background-color: #0598ff; -fx-text-fill: #ffffff;");

                edButton.setOnAction(event -> editproduct());

                VBox productBox = new VBox(); // Use VBox for vertical stacking
                productBox.getChildren().addAll(imageView, nameLabel, priceLabel, edButton);
                productBox.setStyle("-fx-border-color: #000000; -fx-border-width: 1; -fx-padding: 10;");

                // Add the VBox to the GridPane & Set Index of Gridpane
                productGrid.add(productBox, rowIndex, colIndex);
                rowIndex++; // Move to the next row

                if (rowIndex >= 4) { 
                    colIndex++ ;
                    rowIndex = 0; // Reset colIndex
                }
                                
            }
            paneProduct.getChildren().add(productGrid);
            reader.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException io) {
            io.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Edit product
    public void editproduct(){

    }
    @FXML
    void addProduct(ActionEvent event) {
        try{
            // move next stage
            mngtRoot = FXMLLoader.load(getClass().getResource("Addproduct.fxml"));
            mngtStage = (Stage)((Node)event.getSource()).getScene().getWindow();
            mngtScene = new Scene(mngtRoot);
            mngtStage.setScene(mngtScene);
            mngtStage.setTitle("Add Product");
            mngtStage.show();

        } catch (IOException e){
            e.printStackTrace();
        }
        
    }
    
    @FXML
    void addStaff(ActionEvent event) {
        try{
            // move next stage
            addAdmRoot = FXMLLoader.load(getClass().getResource("addStaff.fxml"));
            addAdmStage = (Stage)((Node)event.getSource()).getScene().getWindow();
            addAdmScene = new Scene(addAdmRoot);
            addAdmStage.setScene(addAdmScene);
            addAdmStage.setTitle("Add Admin Staff");
            addAdmStage.show();

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    void editStaff(ActionEvent event) {

    }
    
    @FXML
    void onClickSignOut(ActionEvent event) {
        try{
            // move next stage
            GUIRoot = FXMLLoader.load(getClass().getResource("AppGUI.fxml"));
            GUIStage = (Stage)((Node)event.getSource()).getScene().getWindow();
            GUIScene = new Scene(GUIRoot);
            GUIStage.setScene(GUIScene);
            GUIStage.setTitle("Vending Machine");
            GUIStage.show();

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    void onClickClose(ActionEvent event){
        Platform.exit();
    }

    @FXML
    void printStatement(ActionEvent event) {
        try{
            // move next stage
            dispRoot = FXMLLoader.load(getClass().getResource("salesreport.fxml"));
            dispStage = (Stage)((Node)event.getSource()).getScene().getWindow();
            dispScene = new Scene(dispRoot);
            dispStage.setScene(dispScene);
            dispStage.setTitle("Add Admin Staff");
            dispStage.show();

        } catch (IOException e){
            e.printStackTrace();
        }
    }

}


