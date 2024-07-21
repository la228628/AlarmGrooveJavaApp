package alarmgroove.alarmgrooveapp.Models;

public class DataStruct {
    private String SSID;
    private String password;
    private float latitude;
    private float longitude;

    private int comPort;

    private String APIKey;

    public DataStruct(String SSID, String password, float latitude, float longitude, String APIKey, int comPort) {
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

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public String getAPIKey() {
        return APIKey;
    }



}


