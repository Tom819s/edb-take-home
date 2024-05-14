package com.fakecorp.edbtakehome.service.impl;

import com.fakecorp.edbtakehome.constants.ApplicationConstants;
import com.fakecorp.edbtakehome.service.JsonRedactionService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class JsonRedactionServiceImpl implements JsonRedactionService {

    @Setter
    @Getter
    @Value("${regex}")
    private String patternToCompile;

    private Pattern compiledPattern;

    @PostConstruct
    void init() {
        this.compiledPattern = Pattern.compile(patternToCompile);
    }

    @Override
    public JsonNode redactField(JsonNode rootNode) {
        traverseJsonTreeAndRedact(rootNode);
        return rootNode;
    }

    //Traverses JsonNodes and their children. If a field name matches the provided regular expression, redact the value
    public void traverseJsonTreeAndRedact(JsonNode node) {
        if (node.isObject()) {
            Iterator<String> fieldNames = node.fieldNames();
            while (fieldNames.hasNext()) {
                String fieldName = fieldNames.next();
                if (isFieldToBeRedacted(fieldName)) {
                    updateNodeWithRedactedValue(node, fieldName);
                }
                //Travel to child node and search for matches
                redactField(node.get(fieldName));
            }
        } else if (node.isArray()) {
            //Travel to array element nodes and search for matches
            ArrayNode arrayNode = (ArrayNode) node;
            for (int i = 0; i < arrayNode.size(); i++) {
                JsonNode arrayElement = arrayNode.get(i);
                redactField(arrayElement);
            }
        }
    }

    private boolean isFieldToBeRedacted(String fieldName) {
        Matcher matcher = this.compiledPattern.matcher(fieldName);
        return matcher.find();
    }

    private void updateNodeWithRedactedValue(JsonNode nodeToUpdate, String fieldName) {
        //Cast node as Object node for mutability with put operation
        var objNode = (ObjectNode) nodeToUpdate;
        objNode.put(fieldName, ApplicationConstants.REDACTED);
    }
}
