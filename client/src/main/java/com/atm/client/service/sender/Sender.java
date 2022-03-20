package com.atm.client.service.sender;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@NoArgsConstructor
@Slf4j
public class Sender<T> {

    public Optional<T> sendCreationCardRequestToServer(
            T objectDTO, String urlAddressToServer
    ) {
        RestTemplate restTemplate = new RestTemplate();

        Optional<HttpEntity<String>> optionalHttpEntity = prepareHttpEntity(objectDTO);

        if (optionalHttpEntity.isPresent()){
            HttpEntity<String> entity = optionalHttpEntity.get();
            sendLoginRequest(restTemplate, entity, urlAddressToServer);

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

    private void sendLoginRequest(
            RestTemplate restTemplate, HttpEntity<String> entity, String urlAddressToServer
    ) {
        ResponseEntity<String> loginResponse = restTemplate
                .exchange(urlAddressToServer, HttpMethod.POST, entity, String.class);

        if (loginResponse.getStatusCode() == HttpStatus.OK) {
            log.info("Success sent request");
        } else {
            log.error("Request has been denied");
        }
    }
}
