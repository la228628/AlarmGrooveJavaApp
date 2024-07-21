package alarmgroove.alarmgrooveapp.View;

import java.net.URL;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

public class MainWindowViewController {

    private MainWindowViewListener listener;

    @FXML
    private TextField APIKeyField;

    @FXML
    private TextField LonField;

    @FXML
    private TextField SSIDField;

    @FXML
    private TextField comPortField;

    @FXML
    private TextField latField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button sendInfoButton;

    @FXML
    private ChoiceBox<String> detectedSSIDChoice;
    @FXML
    private Button refreshSSIDButton;


    public static URL getFXMLPath() {
        return MainWindowViewController.class.getResource("MainWindow.fxml");
    }

    public void onSendButtonClick(ActionEvent event) {

        listener.onSendButtonClick(SSIDField.getText(), passwordField.getText(), latField.getText(), LonField.getText(), APIKeyField.getText(), comPortField.getText());
    }

    public List<String> getDetectedSSIDs() {
        return listener.getDetectedSSIDs();
    }

    public void onSelectedExistingSSID(ActionEvent event) {
        listener.onSelectedExistingSSID(detectedSSIDChoice.getValue());
    }

    public void replaceSSIDText(String SSID) {
        SSIDField.setText(SSID);
    }

    public void onRefreshSSIDButtonClick(ActionEvent event) {
        listener.onRefreshSSIDButtonClick();
    }

    public void clearDetectedSSIDs() {
        detectedSSIDChoice.getItems().clear();
    }

    public void showDataValidationError() {
        listener.showDataValidationError();
    }

    public interface MainWindowViewListener {

        void onSelectedExistingSSID(String SSID);

        List<String> getDetectedSSIDs();

        void onSendButtonClick(String SSID, String password, String latitude, String longitude, String APIKey, String comPort);

        void onRefreshSSIDButtonClick();

        void showDataValidationError();
    }

    public void setListener(MainWindowViewListener listener) {
        this.listener = listener;
    }

    public void setDetectedSSIDs(List<String> detectedSSIDs) {
        for (String ssid : detectedSSIDs) {
            detectedSSIDChoice.getItems().add(ssid);
        }
    }
}




