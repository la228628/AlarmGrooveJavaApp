package alarmgroove.alarmgrooveapp.Models;

public class DataStruct {
    private String SSID;
    private String password;
    private String latitude;
    private String longitude;

    private int comPort;

    private String APIKey;

    public DataStruct(String SSID, String password, String latitude, String longitude, String APIKey, int comPort) {
        this.SSID = SSID;
        this.password = password;
        this.latitude = latitude;
        this.longitude = longitude;
        this.APIKey = APIKey;
        this.comPort = comPort;
    }

    public String getComPort() {
        return Integer.toString(comPort);
    }

    public String getSSID() {
        return SSID;
    }

    public String getPassword() {
        return password;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getAPIKey() {
        return APIKey;
    }



}


