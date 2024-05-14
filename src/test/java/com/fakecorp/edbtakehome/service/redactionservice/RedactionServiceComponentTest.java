package com.fakecorp.edbtakehome.service.redactionservice;

import com.fakecorp.edbtakehome.EdbTakeHomeApplication;
import com.intuit.karate.junit5.Karate;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(
        classes = {EdbTakeHomeApplication.class},
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class RedactionServiceComponentTest {

    final private String localServerPort = "8080";

    @Karate.Test
    Karate testRedaction() {
        return Karate.run("redaction").relativeTo(getClass());
    }

    private Karate karateScenario(String scenarioName) {
        return Karate.run()
                .scenarioName(scenarioName)
                .relativeTo(getClass())
                .systemProperty("karate.port", localServerPort)
                .karateEnv("dev");
    }


}
