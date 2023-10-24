package org.plugin.client;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClients;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.tasks.testing.Test;
import org.gradle.api.tasks.testing.TestDescriptor;
import org.gradle.api.tasks.testing.TestListener;
import org.gradle.api.tasks.testing.TestResult;
import org.plugin.extensions.SamplePluginExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class TelegramClient implements Client {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Override
    public void send(Project project) {
        SamplePluginExtension extension = (SamplePluginExtension) project.getExtensions().getByName("defineToken");
        Map<String, String> resultsOfTests = getTestResults((Test) getTaskByName("test", project));
        StringBuilder resume = new StringBuilder();
        resultsOfTests.forEach((key, value) -> resume.append(key).append("***").append(value).append("___"));
        try {
            String apiUrl = String.format("https://api.telegram.org/bot%s/sendDocument?chat_id=%d",
                    extension.getToken(), extension.getChatId());
            File fileToUpload = Paths.get("/home/ivan/projects/cucumberTestNg/build/cucumber/tests.html").toFile();
            HttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(apiUrl);
            HttpEntity entity = MultipartEntityBuilder.create()
                    .addBinaryBody("document", fileToUpload, ContentType.DEFAULT_BINARY, fileToUpload.getName())
                    .build();
            httpPost.setEntity(entity);
            HttpResponse response = httpClient.execute(httpPost);

            log.warn(String.format("Response status is %d ", response.getStatusLine().getStatusCode()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Task getTaskByName(String taskName, Project project){
        Set<Task> tasks = project.getTasks();
        Optional<Task> taskGet = tasks.stream().filter(x -> x.getName().equals(taskName)).findAny();
        Task task = null;
        if (taskGet.isPresent()) {
            task = taskGet.get();
        }
        return task;
    }

    private Map<String, String> getTestResults(Test test){
        Map<String, String> resultsOfTests = new HashMap<>();
        test.addTestListener(new TestListener() {
            @Override
            public void beforeSuite(TestDescriptor testDescriptor) {

            }

            @Override
            public void afterSuite(TestDescriptor testDescriptor, TestResult testResult) {

            }

            @Override
            public void beforeTest(TestDescriptor testDescriptor) {

            }

            @Override
            public void afterTest(TestDescriptor testDescriptor, TestResult testResult) {
                resultsOfTests.put(testDescriptor.getClassName() + ":" + testDescriptor.getName(), testResult.getResultType().name());
            }
        });
        test.executeTests();
        return resultsOfTests;
    }
}
