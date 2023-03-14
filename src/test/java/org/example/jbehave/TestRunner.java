package org.example.jbehave;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.junit.JUnit4StoryRunner;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.jbehave.core.steps.Steps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(JUnit4StoryRunner.class)
public class TestRunner extends JUnitStories {

    private static final Logger logger = LoggerFactory.getLogger(TestRunner.class);

    public TestRunner() {
    }

    /*
    Override run from JupiterStories.java, but logging anything thrown
     */
    @Override
    @Test
    public void run() {
        Embedder embedder = configuredEmbedder();
        try {
            embedder.runStoriesAsPaths(storyPaths());
        } catch (Throwable e) {
            logger.error("Test run failed", e);
        } finally {
            embedder.generateSurefireReport();
        }
    }

    @Override
    public List<String> storyPaths() {
        String storiesDirectoryWithinResources = "addArray";

        List<String> result = new ArrayList<>();
        URL resource = this.getClass().getClassLoader().getResource(storiesDirectoryWithinResources);
        try {
            File folder = new File(resource.toURI());
            File file = folder.listFiles()[0];
            result.add(file.getPath().substring(folder.getPath().lastIndexOf(storiesDirectoryWithinResources)));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public Configuration configuration() {
        return new MostUsefulConfiguration()
                .useStoryLoader(new LoadFromClasspath(this.getClass()))
                .useStoryReporterBuilder(new StoryReporterBuilder()
                        .withFormats(Format.CONSOLE));
    }

    @Override
    public InjectableStepsFactory stepsFactory() {
        final List<Steps> stepsClassInstances = new ArrayList<>();
        getStepsClasses().forEach(stepsClass -> stepsClassInstances.add(getStepsClassInstance(stepsClass)));
        return new InstanceStepsFactory(configuration(), stepsClassInstances);
    }

    protected Steps getStepsClassInstance(Class<? extends Steps> cls) {
        try {
            return cls.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected List<Class<? extends Steps>> getStepsClasses() {
        return List.of(AddSteps.class);
    }
}
