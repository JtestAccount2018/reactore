package com.reactive.example.reactore.facade;

import com.reactive.example.reactore.model.RequestToService;
import com.reactive.example.reactore.model.ResponseFromService;
import com.reactive.example.reactore.service.ReactiveService;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController()
@AllArgsConstructor
public class ReactiveController {

  private final ReactiveService reactiveService;
  
  @PostMapping(value = "/test", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
  public Flux<ResponseFromService> doTest(@RequestBody RequestToService request){
    return reactiveService.getResponse(request);
  }
  
  @PostMapping(value = "/nonReactive", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<ResponseFromService> doNonFullyReactiveTest(@RequestBody RequestToService request){
    return reactiveService.getResponse(request).collect(Collectors.toList()).block(Duration.ofSeconds(10));
  }

}
