package alarmgroove.alarmgrooveapp.Models;

import alarmgroove.alarmgrooveapp.Controllers.AlarmGrooveAppController;
import org.apache.http.HttpException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class Geocoding extends Thread {

    private String country;
    private String city;

    private GeocodingListener listener;
    private volatile boolean triggered = false; //volatile keyword is used to make sure that the value of the variable is always read from the main memory

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void run() {
        System.out.println("Geocoding thread started");
        while (true) {

            if (triggered ) {
                launchGeocoding();
                triggered = false;
            }
        }

    }

    private void launchGeocoding() {
        String url = String.format("https://nominatim.openstreetmap.org/search?country=%s&city=%s&format=json", country.trim(), city.trim());
        ArrayList<String> coordinates = new ArrayList<>();
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            request.addHeader("User-Agent", "Mozilla/5.0");


            try (CloseableHttpResponse response = httpClient.execute(request)) {
                String jsonResponse = EntityUtils.toString(response.getEntity());
                JSONArray jsonArray = new JSONArray(jsonResponse);

                if (!jsonArray.isEmpty()) {
                    JSONObject location = jsonArray.getJSONObject(0);
                    String latitude = location.getString("lat");
                    String longitude = location.getString("lon");
                    coordinates.add(latitude);
                    coordinates.add(longitude);
                } else {
                    System.out.println("Aucune coordonnée trouvée pour cette ville et ce pays.");
                    coordinates.add("error");
                }
            }
        } catch (JSONException | NullPointerException  | IOException e ) {
            coordinates.clear();
            coordinates.add("error");

        }
        listener.onCoordinatesReceived(coordinates);

    }



    public void setListener(GeocodingListener listener) {
        this.listener = listener;
    }

    public void triggerGeocoding() {
        this.triggered = true;
    }

    public interface GeocodingListener {
        void onCoordinatesReceived(ArrayList<String> coordinates);
    }
}
