package com.reactive.example.reactore.transformer;

import com.reactive.example.reactore.model.ResponseFromService;
import java.util.Arrays;
import org.springframework.stereotype.Component;

@Component
public class ResponseTransformer {

  public ResponseFromService transformToResponse(String responseBody) {
    var response = new ResponseFromService();
    response.getResponseList().addAll(Arrays.asList(responseBody.split(" ").clone()));
    return response;
  }

}
