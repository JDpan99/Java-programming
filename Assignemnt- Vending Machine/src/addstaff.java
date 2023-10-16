import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class addstaff {

    private Stage mngtStage;
    private Scene mngtScene;
    private Parent mngtRoot;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnBack;

    @FXML
    private PasswordField confPassword;

    @FXML
    private Label lblTitle;

    @FXML
    private Label lbladmPassword;

    @FXML
    private Label lbladmPassword1;

    @FXML
    private Label lbladmusername;

    @FXML
    private Label lbltxt1;

    @FXML
    private Label lbltxt2;

    @FXML
    private PasswordField password;

    @FXML
    private Label rqMention;

    @FXML
    private TextField txtfUsername;

    public Boolean checkExisted(String username){
        try{
            BufferedReader reader = new BufferedReader(new FileReader("staffs.txt"));
            String line;
            while((line = reader.readLine()) != null){
                String[] data = line.split(", ");
                System.out.println(data[0]);
                if (data[0].equals(username)){
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
    void onClickAddStaff(ActionEvent event) {
        if(txtfUsername.getText().isEmpty() || password.getText().isEmpty() || confPassword.getText().isEmpty()){
            rqMention.setText("*Please enter admin username & password!");
            return;
        }
        String setName = txtfUsername.getText();
        String setPassword = confPassword.getText();
        String passwordAgain = password.getText();

        if (!setPassword.equals(passwordAgain)){
            System.out.println(setPassword);
            System.out.println(confPassword);
            rqMention.setText("*Password & Confirm Password are not same!");
            return;
        }


        if(checkExisted(setName)){
            rqMention.setText("*Username existed!");
            System.out.println(checkExisted(setName));
            return;
        }

        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter( "staffs.txt", true));
            myStaff admin = new myStaff(setName, setPassword);
            writer.write(admin.returnItem());
            writer.close();
            rqMention.setText("");
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
            
        } catch (IOException io) {
            rqMention.setText("File not Found");
        }

        

    }

    @FXML
    void onClickBack(ActionEvent event) {
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

}
