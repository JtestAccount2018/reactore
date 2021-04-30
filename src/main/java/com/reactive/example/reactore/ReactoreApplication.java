package com.reactive.example.reactore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class ReactoreApplication {

  public static void main(String[] args) {
    SpringApplication.run(ReactoreApplication.class, args);
//    new SpringApplicationBuilder(ReactoreApplication.class)
//        .web(WebApplicationType.REACTIVE)
//        .run(args);
  }

}
