package ord.sid.ebankbackend.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ord.sid.ebankbackend.entities.Transfer;
import ord.sid.ebankbackend.services.PredectionService;
import ord.sid.ebankbackend.services.PredictionServiceImpl;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin("*")
public class PredictionController {
    private PredictionServiceImpl predectionService;

    @GetMapping("/getPredictions")
    public List<Transfer> getPredictions() {

        return predectionService.predictions();
    }
}
