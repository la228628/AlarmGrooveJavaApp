package alarmgroove.alarmgrooveapp.Models;

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

public class Geocoding {

    public static ArrayList<String> getCoordinates(String country, String city) {
        String url = String.format("https://nominatim.openstreetmap.org/search?country=%s&city=%s&format=json", country, city);
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

                    System.out.println("Latitude: " + latitude);
                    System.out.println("Longitude: " + longitude);
                } else {
                    System.out.println("Aucune coordonnée trouvée pour cette ville et ce pays.");
                    coordinates.add("error");
                }
            }
        } catch (JSONException | NullPointerException  | IOException e ) {
            coordinates.add("error");

        }

        return coordinates;

    }
}
