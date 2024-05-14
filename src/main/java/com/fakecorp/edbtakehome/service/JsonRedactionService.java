package com.fakecorp.edbtakehome.service;


import com.fasterxml.jackson.databind.JsonNode;

public interface JsonRedactionService {

    //This method parses fields of a JSON object. Any field (key) matching the provided RegEx will have its value redacted

    JsonNode redactField(JsonNode node);

}
