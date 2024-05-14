package com.fakecorp.edbtakehome.service.impl;

import com.fakecorp.edbtakehome.constants.ApplicationConstants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@ExtendWith(MockitoExtension.class)
class JsonRedactionServiceImplTest {

    final String inputJson = "{\"objects\":[{\"name\":\"Test Data\",\"test\":\"TEST_FAIL\"}, {\"name\":\"More Test Data\",\"test\":\"TEST_FAIL\"}]}";
    JsonRedactionServiceImpl redactionService;
    ObjectMapper jsonMapper;
    JsonNode testInputJsonAsNode;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        redactionService = new JsonRedactionServiceImpl();
        redactionService.setPatternToCompile("test");
        redactionService.init();
        jsonMapper = new ObjectMapper();
        testInputJsonAsNode = jsonMapper.readTree(inputJson);
    }

    @Test
    void traversesNodesAndRedactMatchingFields() throws IOException {
        redactionService.traverseJsonTreeAndRedact(testInputJsonAsNode);

        String firstTestNodeValue = testInputJsonAsNode.get("objects").get(0).get("test").textValue();
        String secondTestNodeValue = testInputJsonAsNode.get("objects").get(1).get("test").textValue();

        assertNotEquals("TEST_FAIL", firstTestNodeValue);
        assertNotEquals("TEST_FAIL", secondTestNodeValue);

        assertEquals(ApplicationConstants.REDACTED, firstTestNodeValue);
        assertEquals(ApplicationConstants.REDACTED, secondTestNodeValue);
    }
}