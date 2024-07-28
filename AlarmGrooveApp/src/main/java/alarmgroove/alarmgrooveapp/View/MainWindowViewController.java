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
    private TextField cityTextBox;

    @FXML
    private TextField countryTextBox;

    @FXML
    private Button sendPlaceButton;

    @FXML
    private Label errorCoordinatesLabel;

    @FXML
    private TextField SSIDField;

    @FXML
    private ChoiceBox comPortField;

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

        listener.onSendButtonClick(SSIDField.getText(), passwordField.getText(), latField.getText(), LonField.getText(), APIKeyField.getText(), comPortField.getValue().toString());
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

    public void onSendPlaceButtonClick(ActionEvent event) {
        listener.onSendPlaceButtonClick(countryTextBox.getText(), cityTextBox.getText());
    }



    public void showErrorCoordinatesLabel() {
        errorCoordinatesLabel.setText("Erreur lors de la récupération des coordonnées.");
    }

    public void setCoordinatesLabel(String latitude, String longitude) {
        latField.setText(latitude);
        LonField.setText(longitude);
    }

    public void fillComPortChoiceBox(List<Integer> comPorts) {
        comPortField.getItems().addAll(comPorts);
    }

    public void hideErrorCoordinatesLabel() {
        errorCoordinatesLabel.setText("");
    }

    public interface MainWindowViewListener {

        void onSelectedExistingSSID(String SSID);

        List<String> getDetectedSSIDs();

        void onSendButtonClick(String SSID, String password, String latitude, String longitude, String APIKey, String comPort);

        void onRefreshSSIDButtonClick();

        void showDataValidationError();

        void onSendPlaceButtonClick(String country, String city);
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




