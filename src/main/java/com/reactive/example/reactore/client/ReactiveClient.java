package com.reactive.example.reactore.client;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import java.util.concurrent.TimeUnit;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

@Component
public class ReactiveClient {

  private final WebClient webClient;

  public ReactiveClient() {
    webClient = WebClient.builder()
        .clientConnector(getHttpConnector())
        .exchangeStrategies(ExchangeStrategies.builder()
            .codecs(configurer -> configurer
                .defaultCodecs()
                .maxInMemorySize(8 * 1024 * 1024))
            .build())
        .build();
  }

  private ClientHttpConnector getHttpConnector() {
    HttpClient httpClient = HttpClient.create()
        .tcpConfiguration(tcpClient -> {
          // it makes sense to have one connect timeout across read/write/connect operations
          // if we ever decide to have separate values, we can easily introduce them in configuration
          tcpClient = tcpClient.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10 * 1000);
          tcpClient = tcpClient.doOnConnected(connection -> connection
              .addHandlerLast(new ReadTimeoutHandler(10, TimeUnit.SECONDS))
              .addHandlerLast(new WriteTimeoutHandler(10, TimeUnit.SECONDS)));
          return tcpClient;
        })
        .wiretap(false);
    return new ReactorClientHttpConnector(httpClient);
  }

  public Mono<String> requestToServer(String url) {
    return webClient.get()
        .uri(url)
        .retrieve()
        .bodyToMono(String.class)
        .doOnError(RuntimeException::new);
  }
}
