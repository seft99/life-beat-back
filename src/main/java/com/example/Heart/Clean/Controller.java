package com.example.Heart.Clean;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/")
@CrossOrigin(origins = "http://localhost:4200/")
public class Controller {

    @PostMapping("/test")
    public double test(@RequestBody Data data){
        Heart_Back predictor = new Heart_Back();
        return predictor.predictInConsole(data);






    }


}
