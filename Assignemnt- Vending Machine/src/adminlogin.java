import java.io.BufferedReader;
import java.io.FileReader;
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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class adminlogin {
    private Stage GUIStage;
    private Scene GUIScene;
    private Parent GUIRoot;

    private Stage mngtStage;
    private Scene mngtScene;
    private Parent mngtRoot;
    
    @FXML
    private Button btnLogin;

    @FXML
    private Button btnReturn;

    @FXML
    private Label lblADTitle;

    @FXML
    private Label lblPassword;

    @FXML
    private Label lblTitle;

    @FXML
    private Label lblUsername;

    @FXML
    private AnchorPane paneBackG1;

    @FXML
    private AnchorPane paneBackG2;

    @FXML
    private PasswordField pwdPassword;

    @FXML
    private Label rqMention;

    @FXML
    private TextField txtUsername;

    public Boolean checkCredential(String username, String password){
        try{
        BufferedReader reader = new BufferedReader(new FileReader("staffs.txt"));
        String line;
        while((line = reader.readLine()) != null){
            String[] data = line.split(", ");
            if(username.equals(data[0])||password.equals(data[1])){
                reader.close();
                return true;
            }
        }
        reader.close();
        } catch (IOException io){
            rqMention.setText("File not found");
        }
        return false;
    }

    @FXML
    void onClickLogin(ActionEvent event) {
        //check text
        if(txtUsername.getText().isEmpty() || pwdPassword.getText().isEmpty()){
            rqMention.setText("*Please enter Username and Password");
            return;
        }
        //Get Text
        String username = txtUsername.getText();
        String password = pwdPassword.getText();
        //Identify username & password
        if (!checkCredential(username, password)) {
            rqMention.setText("*Invalid Username or Password!");
            return;
        }
        // Show alert
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Admin Management");
            alert.setContentText("Login successfully.");
            alert.showAndWait();
         // Move to Next Stage
         try{
         mngtRoot = FXMLLoader.load(getClass().getResource("myManagementScene.fxml"));
         mngtStage = (Stage)((Node)event.getSource()).getScene().getWindow();
         mngtScene = new Scene(mngtRoot);
         mngtStage.setScene(mngtScene);
         mngtStage.show();
        }catch (IOException e){
            System.out.println(e);
        }
    }

    @FXML
    void onClickReturn(ActionEvent event) {
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

}


