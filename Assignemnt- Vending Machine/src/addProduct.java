import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class addProduct {

    private Stage mngtStage;
    private Scene mngtScene;
    private Parent mngtRoot;
    private String productName;
    private double productPrice;
    private int productQuantity;
    private String pathproImg;

    @FXML
    private Button btnAddProduct;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnSelectImage;

    @FXML
    private ChoiceBox<String> cBoxCategory;

    @FXML
    private Label lblCategory;

    @FXML
    private Label lblImage;

    @FXML
    private Label lblImagePath;

    @FXML
    private Label lblPrice;

    @FXML
    private Label lblProduct;

    @FXML
    private Label lblQuantity;

    @FXML
    private Label lblTitle;

    @FXML
    private ImageView productImg;

    @FXML
    private Label rqCategory;

    @FXML
    private Label rqImage;

    @FXML
    private Label rqName;

    @FXML
    private Label rqPrice;

    @FXML
    private Label rqQuantity;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtProductName;

    @FXML
    private TextField txtQuantity;

    @FXML
    void Cancel(ActionEvent event) throws IOException {
        try{
            // Return previous stage
            mngtRoot = FXMLLoader.load(getClass().getResource("myManagementScene.fxml"));
            mngtStage = (Stage)((Node)event.getSource()).getScene().getWindow();
            mngtScene = new Scene(mngtRoot);
            mngtStage.setScene(mngtScene);
            mngtStage.show();

        } catch (IOException e){
            e.printStackTrace();
        }

    
    }

    @FXML
    void addNewProduct(ActionEvent event) {
        
        // Check Text Field is not empty
        // Check product name is not empty
        if (txtProductName.getText().trim().isEmpty()) {
            rqName.setText("*Please enter product name!");
            return;
        } else {
            rqName.setText("*");
            productName = txtProductName.getText();
        }

        // Check quantity is not empty
        if(txtQuantity.getText().trim().isEmpty()){
            rqQuantity.setText("*Please enter product quantity!");
            return;
        } else {
            try{
                rqQuantity.setText("*");
                productQuantity = Integer.parseInt(txtQuantity.getText());
            }   catch (NumberFormatException e) {
                rqQuantity.setText("*Please enter number only!");
            }
        }
        
        // Check price is not empty
        if(txtPrice.getText().trim().isEmpty()){
            rqPrice.setText("*Please enter product price!");
            return;
        } else {
            try{
                rqPrice.setText("*");
                productPrice = Double.parseDouble(txtPrice.getText());
            }   catch (NumberFormatException e) {
                rqPrice.setText("*Please enter number only!");
            }
        }

        // Check image path is not empty
        // Save product info to file
        if (lblImagePath.getText().trim().isEmpty()){
            rqImage.setText("*Please provide an image!");
            return;
        } else {
            pathproImg = lblImagePath.getText();
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter("products.txt",true));
                mySoftdrink softdrink = new mySoftdrink(productName, productPrice, productQuantity, pathproImg);
                writer.write(softdrink.returnItem()); 
                writer.close();
                
                // Show alert
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Product Added");
                alert.setContentText("Save successfully.");
                alert.showAndWait();

                // Move to Next Stage
                mngtRoot = FXMLLoader.load(getClass().getResource("myManagementScene.fxml"));
                mngtStage = (Stage)((Node)event.getSource()).getScene().getWindow();
                mngtScene = new Scene(mngtRoot);
                mngtStage.setScene(mngtScene);
                mngtStage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void selectImage(ActionEvent event) {
        FileChooser ImageChooser = new FileChooser();
        ImageChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png", "*.jpeg", "*.gif")
        );
    

        File selectedImage = ImageChooser.showOpenDialog(null);
        //Check File
        if (selectedImage != null) {
            String imagePath = computeRelativePath(selectedImage);
            String firImage = imagePath.replace("src\\", "");
            String secImage = firImage.replace("\\", "/");
            lblImagePath.setText(secImage);
            Image Image =new Image(selectedImage.toURI().toString());
            productImg.setImage(Image);
            }
        } 

    private String computeRelativePath(File file) {
            // Compute the relative path of the file based on your project's directory structure
            // This depends on where your images are stored relative to your application's working directory
            // You might need to modify this based on your project setup
            String projectPath = System.getProperty("user.dir");
            String absoluteImagePath = file.getAbsolutePath();
            
            if (absoluteImagePath.startsWith(projectPath)) {
                return absoluteImagePath.substring(projectPath.length() + 1);
            } else {
                // If the file is not within the project directory, return the absolute path
                return absoluteImagePath;
            }
        }

    public void initialize() {
        cBoxCategory.getItems().addAll("food", "softdrink");
        cBoxCategory.setValue("softdrink");
    }
    }   


