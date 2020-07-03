package com.troublemakersacdemy.webSocketExample.service;

import com.troublemakersacdemy.webSocketExample.domain.MessageRequest;
import com.troublemakersacdemy.webSocketExample.domain.MessageResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.Instant;
import java.util.stream.Stream;

@Component
public class MessageService {

  public   Flux<MessageResponse> handleMessage(MessageRequest messageRequest){
        return   Flux.fromStream(Stream.generate(() -> new MessageResponse(" Message Received: " + messageRequest.getMessage() + Instant.now())))
                .delayElements(Duration.ofSeconds(1));

    }
}
