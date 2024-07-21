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

}


