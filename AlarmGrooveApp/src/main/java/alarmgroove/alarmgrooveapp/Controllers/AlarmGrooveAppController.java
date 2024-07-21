package alarmgroove.alarmgrooveapp.Controllers;

import alarmgroove.alarmgrooveapp.Models.*;
import alarmgroove.alarmgrooveapp.View.MainWindowViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.util.List;

public class AlarmGrooveAppController extends Application implements MainWindowViewController.MainWindowViewListener{

    MainWindowViewController mainWindowViewController;



    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(MainWindowViewController.getFXMLPath());
        Scene scene = new Scene(fxmlLoader.load());
        this.mainWindowViewController = fxmlLoader.getController();
        this.mainWindowViewController.setListener(this);
        mainWindowViewController.setDetectedSSIDs(getDetectedSSIDs());
        stage.setScene(scene);
        stage.show();
        System.out.println(WifiScanner.getAvailableSSIDs());

    }

    @Override
    public void showDataValidationError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur de données");
        alert.setHeaderText("Donnéés invalides");
        alert.setContentText("Certains champs sont vides ou invalides. Veuillez vérifier les données saisies.");
        alert.showAndWait();
    }

    @Override
    public void onSelectedExistingSSID(String SSID) {
        mainWindowViewController.replaceSSIDText(SSID);

    }

    @Override
    public List<String> getDetectedSSIDs() {
        return WifiScanner.getAvailableSSIDs();
    }

    @Override
    public void onSendButtonClick(String SSID, String password, String latitude, String longitude, String APIKey, String comPort) {

        boolean isDataValid = Validator.validateData(SSID, password, latitude, longitude, APIKey, comPort);
        if(isDataValid){
            System.out.println("Data is valid");
        }else{
            mainWindowViewController.showDataValidationError();
        }

    }

    @Override
    public void onRefreshSSIDButtonClick() {
        mainWindowViewController.clearDetectedSSIDs();
        mainWindowViewController.setDetectedSSIDs(getDetectedSSIDs());
    }
}
