package com.reactive.example.reactore.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class ResponseFromService {

  public ResponseFromService() {
    this.responseList = new ArrayList<>();
  }

  private List<String> responseList;
}
