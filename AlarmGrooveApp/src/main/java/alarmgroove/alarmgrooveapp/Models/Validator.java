package alarmgroove.alarmgrooveapp.Models;

public class Validator {

        public static boolean validateData(String SSID, String password, String latitude, String longitude, String APIKey, String comPort) {
            if (SSID.isEmpty() || password.isEmpty() || latitude.isEmpty() || longitude.isEmpty() || APIKey.isEmpty() || comPort.isEmpty()) {
                return false;
            }
            try {
                Float.parseFloat(latitude);
                Float.parseFloat(longitude);
                Integer.parseInt(comPort);
            } catch (NumberFormatException e) {
                return false;
            }
            return true;
        }
}
