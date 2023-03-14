package org.example.jbehave;

import org.example.AddArray;
import org.jbehave.core.annotations.AfterStories;
import org.jbehave.core.annotations.BeforeStories;
import org.jbehave.core.annotations.BeforeStory;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.steps.Steps;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddSteps extends Steps {
    private static final Logger logger = LoggerFactory.getLogger(AddSteps.class);
    private AddArray add = new AddArray();

    @BeforeStory
    public void beforeStory() {

    }

    @When("Number $number pushed to array")
    public void pushNumber(Integer number) {
        add.pushNumberToArray(number);
    }

    @When("Result is computed")
    public void computeResult() {
        add.computeResult();
    }

    @Then("Result is $expectedValue")
    public void assertResult(Integer expectedValue) {
        Assert.assertEquals(add.getResult(), expectedValue);
    }

    @BeforeStories
    public void beforeStories() {
        logger.info("Started stories without failing!");
    }

    @AfterStories
    public void afterStories() {
        logger.info("Completed stories without failing!");
    }
}