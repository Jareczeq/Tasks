package com.crud.tasks.client;

import com.crud.tasks.domain.TrelloBoardDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class TrelloClient {
    private final RestTemplate restTemplate;

    @Value("${trello.api.endpoint.prod}")
    private String trelloEndpointProd;

    @Value("${trello.app.key}")
    private String trelloAppKey;

    @Value("${trello.app.token}")
    private String trelloAppToken;

    @Value("${trello.username}")
    private String trelloUsername;

    public List<TrelloBoardDto> getTrelloBoards() {

        URI uri = buildURI();

        TrelloBoardDto[] boardsResponse = restTemplate.getForObject(
                uri,
                TrelloBoardDto[].class
        );

        return Optional.ofNullable(boardsResponse)
                .map(Arrays::asList)
                .orElse(Collections.emptyList());
    }

    private URI buildURI() {
        return UriComponentsBuilder
                .fromUriString(trelloEndpointProd + "/members/" + trelloUsername + "/boards")
                .queryParam("key", trelloAppKey)
                .queryParam("token", trelloAppToken)
                //.queryParam("fields", "id,name")
                .build()
                .encode()
                .toUri();
    }
}