package com.fakecorp.edbtakehome.controller;

import com.fakecorp.edbtakehome.service.JsonRedactionService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedactionController {

    @Autowired
    JsonRedactionService jsonRedactionService;

    @PostMapping(value = "/", consumes = "application/json", produces = "application/json")
    public JsonNode redactBasedOnExpression(@RequestBody JsonNode RequestJson) {
        jsonRedactionService.redactField(RequestJson);
        //TODO Refactor to SLF4J Logger In Prod
        System.out.println(RequestJson);
        return RequestJson;
    }
}
