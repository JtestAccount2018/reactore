package com.reactive.example.reactore.service;

import com.reactive.example.reactore.client.ReactiveClient;
import com.reactive.example.reactore.model.RequestToService;
import com.reactive.example.reactore.model.ResponseFromService;
import com.reactive.example.reactore.transformer.ResponseTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Service
@RequiredArgsConstructor
public class ReactiveService {

  private final ReactiveClient reactiveClient;
  private final ResponseTransformer responseTransformer;
  
  public Flux<ResponseFromService> getResponse(RequestToService request){
    return Flux.fromIterable(request.getUrls())
        .flatMap(reactiveClient::requestToServer)
        .publishOn(Schedulers.parallel())
        .map(responseTransformer::transformToResponse);
  }
  
}
