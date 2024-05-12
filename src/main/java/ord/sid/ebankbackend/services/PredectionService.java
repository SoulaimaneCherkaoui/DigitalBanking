package ord.sid.ebankbackend.services;

import ord.sid.ebankbackend.entities.Transfer;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.http.HttpResponse;
import java.util.List;

public interface PredectionService {
    String MapToJson(Long id);
    HttpResponse<String> predictionResponce(String path,String gson) throws IOException, InterruptedException;

    List<Transfer> predictions();

}
