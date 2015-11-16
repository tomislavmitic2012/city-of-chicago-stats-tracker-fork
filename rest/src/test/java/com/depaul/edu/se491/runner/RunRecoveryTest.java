package com.depaul.edu.se491.runner;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created by adampodraza on 5/12/15.
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        format = {"pretty", "html:target/cucumber"},
        glue = "com.depaul.edu.se491.steps",
        features = "classpath:cucumber/recovery.feature"
)
public class RunRecoveryTest {
}
