package alarmgroove.alarmgrooveapp.Models;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class WifiScanner {

    public static List<String> getAvailableSSIDs() {
        List<String> ssids = new ArrayList<>();
        try {
            Process process = Runtime.getRuntime().exec("netsh wlan show networks");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.trim().startsWith("SSID")) {
                    String ssid = line.split(":")[1].trim();
                    if(ssid.isEmpty()){
                        continue;
                    }
                    ssids.add(ssid);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ssids;
    }
}
