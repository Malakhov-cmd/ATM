package com.atm.client.service.sender;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@NoArgsConstructor
@Slf4j
public class Sender<T> {

    public Optional<T> sendCreationEntityRequestToServer(
            T objectDTO, String urlAddressToServer, HttpMethod httpMethod
    ) {
        RestTemplate restTemplate = new RestTemplate();

        Optional<HttpEntity<String>> optionalHttpEntity = prepareHttpEntity(objectDTO);

        if (optionalHttpEntity.isPresent()) {
            HttpEntity<String> entity = optionalHttpEntity.get();
            sendRequest(restTemplate, entity, urlAddressToServer, httpMethod);

            return Optional.of(objectDTO);
        } else {
            return Optional.empty();
        }
    }

    private Optional<HttpEntity<String>> prepareHttpEntity(T objectDTO) {
        HttpHeaders headers = getHttpHeaders();

        try {
            return Optional.of(new HttpEntity<>(new ObjectMapper().writeValueAsString(objectDTO), headers));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return headers;
    }

    private void sendRequest(
            RestTemplate restTemplate, HttpEntity<String> entity, String urlAddressToServer, HttpMethod httpMethod
    ) {
        ResponseEntity<String> response = restTemplate
                .exchange(urlAddressToServer, httpMethod, entity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            log.info("Success sent request");
        } else {
            log.error("Request has been denied");
        }
    }
}
