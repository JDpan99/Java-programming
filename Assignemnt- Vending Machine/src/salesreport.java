import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class salesreport {

    private Stage mngtStage;
    private Scene mngtScene;
    private Parent mngtRoot;

    @FXML
    private TableView<mySalesProduct> TableStatic;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnPrint;

    @FXML
    private Label lblTotalRevenue;

    @FXML
    private Label lbltxt1;

    @FXML
    private Label lbltxt2;

    @FXML
    private TableColumn<mySalesProduct, Integer> pdtRemain;

    @FXML
    private TableColumn<mySalesProduct, Double> pdtSales;

    @FXML
    private TableColumn<mySalesProduct, Integer> pdtSold;

    @FXML
    private TableColumn<mySalesProduct, String> pdtname;

    @FXML
    private TableColumn<mySalesProduct, Double> pdtprice;

    @FXML
    private TextField txtfRevenue;

    private ObservableList<mySalesProduct> salesData = FXCollections.observableArrayList();

    public void initialize() throws InvocationTargetException{
;
        txtfRevenue.setEditable(false);
        // TableView
        pdtname.setCellValueFactory(new PropertyValueFactory<mySalesProduct, String>("name"));
        pdtprice.setCellValueFactory(new PropertyValueFactory<mySalesProduct, Double>("price"));
        pdtRemain.setCellValueFactory(new PropertyValueFactory<mySalesProduct, Integer>("remain"));
        pdtSold.setCellValueFactory(new PropertyValueFactory<mySalesProduct, Integer>("sold"));
        pdtSales.setCellValueFactory(new PropertyValueFactory<mySalesProduct, Double>("total"));
        TableStatic.setItems(salesData);
        addSales();
        addRevenue();
        
    }
    // Add all item to table
    public void addSales(){
        try{
            BufferedReader reader = new BufferedReader(new FileReader("products.txt"));
            String line;
            while((line = reader.readLine()) != null){
                String[] data= line.split(", ");
                String salesProductName = data[0];
                double salesProductPrice = Double.parseDouble(data[1]);
                int soldQty = readRemain(salesProductName);
                int qtyRemain = Integer.parseInt(data[2]);
                double totSales = salesProductPrice * soldQty; 

                mySalesProduct product = new mySalesProduct(salesProductName, salesProductPrice, qtyRemain, soldQty, totSales);
                TableStatic.getItems().add(product);
            }
            reader.close();
        } catch (IOException io){
            System.out.println(io);
        }
        
    }
    //read remaining quatity
    public int readRemain(String name){
        try{
            BufferedReader reader = new BufferedReader(new FileReader("records.txt"));
            String line;
            while((line = reader.readLine()) != null){
                String[] data = line.split(", ");
                if(name.equals(data[0])){
                    reader.close();
                    return Integer.parseInt(data[2]);
                }
            }
            reader.close();
            
        } catch (IOException io){
            System.out.println(io);
        }
        return 0;
    }
    // Add revenue
    public void addRevenue(){
        double VMrevenue = 0;
        for (int i= 0;i<TableStatic.getItems().size();i++){
            double totSales = Double.parseDouble(String.valueOf(TableStatic.getColumns().get(1).getCellObservableValue(i).getValue()));
            
            VMrevenue += totSales;
        }
        DecimalFormat df = new DecimalFormat("0.00");
        txtfRevenue.setText("RM "+df.format(VMrevenue));
    }
    @FXML
    void onClickBack(ActionEvent event) {
        try{
        // Move to Next Stage
                mngtRoot = FXMLLoader.load(getClass().getResource("myManagementScene.fxml"));
                mngtStage = (Stage)((Node)event.getSource()).getScene().getWindow();
                mngtScene = new Scene(mngtRoot);
                mngtStage.setScene(mngtScene);
                mngtStage.show();
        } catch (IOException io){
            System.out.println(io);
        }
    }

    @FXML
    void onClickPrint(ActionEvent event) {

    }

}
