

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application{
    public void start(Stage stage) throws Exception{
    try{
        Parent root = FXMLLoader.load(getClass().getResource("AppGUI.fxml"));
        Scene scene = new Scene(root,750,500);
        
        stage.setScene(scene);
        stage.setTitle("Vending Machine");
        stage.show();
    } catch(Exception e){
        e.printStackTrace();
    }
    }
    public static void main(String[] args){
        launch(args);
    }
}