package alarmgroove.alarmgrooveapp.Controllers;

import alarmgroove.alarmgrooveapp.Models.*;
import alarmgroove.alarmgrooveapp.View.MainWindowViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

import javafx.stage.Stage;


import java.util.ArrayList;
import java.util.List;

import com.fazecast.jSerialComm.*;


public class AlarmGrooveAppController extends Application implements MainWindowViewController.MainWindowViewListener, Geocoding.GeocodingListener{

    MainWindowViewController mainWindowViewController;
    Geocoding geocoding;


    @Override
    public void start(Stage stage) throws Exception {

        geocoding = new Geocoding();
        geocoding.setListener(this);
        geocoding.start();

        FXMLLoader fxmlLoader = new FXMLLoader(MainWindowViewController.getFXMLPath());
        Scene scene = new Scene(fxmlLoader.load());
        this.mainWindowViewController = fxmlLoader.getController();
        this.mainWindowViewController.setListener(this);
        mainWindowViewController.setDetectedSSIDs(getDetectedSSIDs());
        mainWindowViewController.fillComPortChoiceBox(getAllPorts());


        stage.setMaxHeight(583);
        stage.setMaxWidth(940);
        stage.setMinHeight(583);
        stage.setMinWidth(940);
        stage.setTitle("ALARM GROOVE APP");
        stage.setScene(scene);
        stage.show();
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
    public void onSendPlaceButtonClick(String country, String city) {
        mainWindowViewController.hideErrorCoordinatesLabel();
        geocoding.setCountry(country);
        geocoding.setCity(city);
        geocoding.triggerGeocoding();



    }

    public void onCoordinatesReceived(ArrayList<String> coordinates) {
        if (coordinates.size() == 2) {
            mainWindowViewController.setCoordinatesLabel(coordinates.get(0), coordinates.get(1));
        } else {
            mainWindowViewController.showErrorCoordinatesLabel();
        }
    }

    @Override
    public void onRefreshPortBoxClick() {


        mainWindowViewController.fillComPortChoiceBox(getAllPorts());

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
    public void onSendButtonClick(String SSID, String password, String latitude, String longitude, String APIKey, Object comPort) {

        try {
            String comPortString = comPort.toString();

            boolean isDataValid = Validator.validateData(SSID, password, latitude, longitude, APIKey, comPortString);
            if (isDataValid) {
                DataStruct data = constructDataStruct(SSID, password, latitude, longitude, APIKey, Integer.parseInt(comPortString));
                sendDataToESP32(data);
                System.out.println("Data sent");
            } else {
                mainWindowViewController.showDataValidationError();
            }
        } catch (NullPointerException e) {
            mainWindowViewController.showDataValidationError();
        }

    }

    @Override
    public void onRefreshSSIDButtonClick() {
        mainWindowViewController.clearDetectedSSIDs();
        mainWindowViewController.setDetectedSSIDs(getDetectedSSIDs());
        System.out.println("Refreshed SSIDs");
    }

    public ArrayList<Integer> getAllPorts() {
        ArrayList<Integer> ports = new ArrayList<>();
        SerialPort[] portNames = SerialPort.getCommPorts();
        for (SerialPort port : portNames) {
            ports.add(Integer.parseInt(port.getSystemPortName().substring(3)));
        }
        return ports;
    }


    private DataStruct constructDataStruct(String SSID, String password, String latitude, String longitude, String APIKey, Integer comPort) {
        String ssid = SSID;
        String pass = password;
        String key = APIKey;
        int port = comPort;
        String latString = latitude;
        String lonString = longitude;

        DataStruct data;

        if (SSID == null || SSID.isEmpty()) {
            ssid = "default";
        }
        if (password == null || password.isEmpty()) {
            pass = "default";
        }
        if (latitude.isEmpty()) {
            latString = "default";
        }
        if (longitude.isEmpty()) {
            lonString = "default";
        }
        if (APIKey == null || APIKey.isEmpty()) {
            key = "default";
        }


        data = new DataStruct(ssid, pass, latString, lonString, key, port);
        System.out.println(data);

        return data;

    }


    private void sendDataToESP32(DataStruct data) {

        System.out.println("COORDONNEES : ");
        System.out.println(data.getLatitude());
        System.out.println(data.getLongitude());

        String nomPortCOM = "COM" + data.getComPort();
        System.out.println("Tentative d'ouverture du port : " + nomPortCOM);
        SerialPort comPort;
        comPort = SerialPort.getCommPort(nomPortCOM); // Remplacez "COM3" par votre port série
        comPort.setComPortParameters(115200, 8, 1, 0); // Configurer les paramètres du port
        comPort.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 0);

        try {
            if (comPort.openPort()) {
                System.out.println("Port ouvert avec succès : " + nomPortCOM);
            } else {
                System.out.println("Échec de l'ouverture du port : " + nomPortCOM);
                return;
            }

            comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);


            String ssid = "SSID:" + data.getSSID() + "\n";
            byte[] ssidBuffer = ssid.getBytes();
            comPort.writeBytes(ssidBuffer, ssidBuffer.length);
            Thread.sleep(500);


            String password = "PASSWORD:" + data.getPassword() + "\n";
            byte[] passwordBuffer = password.getBytes();
            comPort.writeBytes(passwordBuffer, passwordBuffer.length);
            Thread.sleep(500);


            String latitude = "LATITUDE:" + data.getLatitude() + "\n";
            System.out.println(latitude);
            byte[] latBuffer = latitude.getBytes();
            comPort.writeBytes(latBuffer, latBuffer.length);
            Thread.sleep(500);

            String longitude = "LONGITUDE:" + data.getLongitude() + "\n";
            System.out.println(longitude);
            byte[] lonBuffer = longitude.getBytes();
            comPort.writeBytes(lonBuffer, lonBuffer.length);
            Thread.sleep(500);

            String apiKey = "APIKEY:" + data.getAPIKey() + "\n";
            byte[] keyBuffer = apiKey.getBytes();
            comPort.writeBytes(keyBuffer, keyBuffer.length);
            Thread.sleep(500);


            comPort.closePort();
            System.out.println("Données envoyées avec succès");
        } catch (Exception e) {
            System.out.println("Une erreur s'est produite lors de l'ouverture ou de l'utilisation du port : " + e.getMessage());
            e.printStackTrace();
        }


    }

    public static void main(String[] args) {
        launch(args);
    }


}
