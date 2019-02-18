package com.cetc.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * HttpUtils
 */
@Component
public class HttpUtils {

    private static RestTemplate restTemplate;

    @Autowired
    private void setRestTemplate(RestTemplate restTemplate) {
        HttpUtils.restTemplate = restTemplate;
    }

    public static <T> T doPost(String url, Map<String, ? extends Object> params, Class<T> resultType) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<Map<String, ? extends Object>> httpEntity = new HttpEntity<>(params, httpHeaders);
        ResponseEntity<T> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, resultType);
        return responseEntity.getBody();
    }

    public static <T> T doGet(String url, Class<T> resultType) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.TEXT_HTML);
        HttpEntity<String> httpEntity = new HttpEntity(httpHeaders);
        ResponseEntity<T> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, resultType);
        return responseEntity.getBody();
    }

    public static <T> T doFrom(String url, LinkedMultiValueMap<String, ? extends Object> params, Class<T> resultType) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<LinkedMultiValueMap<String, ? extends Object>> httpEntity = new HttpEntity(params, httpHeaders);
        ResponseEntity<T> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, resultType);
        return responseEntity.getBody();
    }
}
