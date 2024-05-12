package ord.sid.ebankbackend.services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class TestPrediction {
    private PredictionServiceImpl predictionService;

    public static void main(String[] args) throws IOException, InterruptedException {

        /*SortedMap<String, Double> elements = new TreeMap();
        elements.put("amount", 5000.2);
        elements.put("oldbalanceOrg", 600.2);
        elements.put("newbalanceOrig", 7000.2);
        elements.put("oldbalanceDest", 4444.3);
        elements.put("newbalanceDest", 444555.2);

        Gson gson = new Gson();
        Type gsonType = new TypeToken<HashMap>(){}.getType();
        String gsonString = gson.toJson(elements,gsonType);
        System.out.println(gsonString);

        // API endpoint
        String apiEndpoint = "http://localhost:5000/predict";

        // Send POST request to Flask API
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiEndpoint))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(gsonString))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
*/


        // Process API response

}}
