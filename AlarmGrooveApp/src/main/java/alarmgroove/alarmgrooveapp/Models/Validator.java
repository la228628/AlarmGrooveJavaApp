package alarmgroove.alarmgrooveapp.Models;

public class Validator {

    public static boolean validateData(String SSID, String password, String latitude, String longitude, String APIKey, String comPort) {
        if (comPort.isEmpty() ) {
            return false;
        }
        if (!latitude.isEmpty()) {
            try {
                if ((Float.parseFloat(latitude) < -90) || (Float.parseFloat(latitude) > 90)) {
                    System.out.println("Lat incorrecte");
                    return false;
                }
            } catch (NumberFormatException e) {
                return false;
            }
        }

        if (!longitude.isEmpty()) {
            try {
                if ((Float.parseFloat(longitude) < -180) || (Float.parseFloat(longitude) > 180)) {
                    System.out.println("Lon incorrecte");
                    return false;
                }
            } catch (NumberFormatException e) {
                return false;
            }
        }
        System.out.println(latitude);
        System.out.println(longitude);
        return true;

    }
}
