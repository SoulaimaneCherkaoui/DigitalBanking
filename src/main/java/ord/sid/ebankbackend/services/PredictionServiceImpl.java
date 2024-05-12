package ord.sid.ebankbackend.services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jakarta.transaction.Transactional;
import ord.sid.ebankbackend.entities.Transfer;
import ord.sid.ebankbackend.repositories.TransferRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;

@Service
@Transactional
public class PredictionServiceImpl implements PredectionService{
    @Autowired
    private TransferRepo transferRepo;
    private String pathFlask = "http://localhost:5000/predict";


    @Override
    public String MapToJson(Long id) {
        Transfer transfer=transferRepo.findById(id).orElseThrow(()->new RuntimeException("ne marche pas"));
        SortedMap<String, Double> elements = new TreeMap();
        elements.put("amount", transfer.getAmount());
        elements.put("oldbalanceOrg", transfer.getOldSoldeSource());
        elements.put("newbalanceOrig", transfer.getNewSoldeSource());
        elements.put("oldbalanceDest", transfer.getOldSoldeDestination());
        elements.put("newbalanceDest", transfer.getNewSoldeDestination());


        Gson gson = new Gson();
        Type gsonType = new TypeToken<HashMap>(){}.getType();
        return gson.toJson(elements,gsonType);
    }

    @Override
    public HttpResponse<String> predictionResponce(String path, String gson) throws IOException, InterruptedException {


        // Send POST request to Flask API
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(path))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(gson))
                .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    @Override
    public List<Transfer> predictions() {
        List<Transfer> transferList=transferRepo.findAll();
        transferList.forEach(transfer -> {
            String gson = MapToJson(transfer.getId());
            try {
                HttpResponse<String> response = predictionResponce(pathFlask, gson);
                JsonObject jsonResponse = new Gson().fromJson(response.body(), JsonObject.class);
                JsonArray predictions = jsonResponse.getAsJsonArray("predictions");
                double prediction = predictions.get(0).getAsDouble();
                System.out.println(prediction);
                transfer.setColor(prediction == 0.0 ? "V" : "R");
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        return transferList;
    }
}
