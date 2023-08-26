package org.plugin.tasks;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.tasks.testing.Test;
import org.gradle.api.tasks.testing.TestDescriptor;
import org.gradle.api.tasks.testing.TestListener;
import org.gradle.api.tasks.testing.TestResult;
import org.plugin.extensions.SamplePluginExtension;
import org.plugin.httpClient.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Paths;
import java.util.*;

public class SenderTestsTask extends DefaultTask {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final Project project = this.getProject();

    @TaskAction
    public void send() {
        SamplePluginExtension extension = (SamplePluginExtension) project.getExtensions().getByName("defineToken");
        Map<String, String> resultsOfTests = getTestResults((Test) getTaskByName("test"));

//        StringBuilder resume = new StringBuilder();
//        resultsOfTests.forEach((key, value) -> resume.append(key).append("***").append(value).append("___"));
//        try {
//            HttpResponse<String> response = Client.post(String.format(
//                    "https://api.telegram.org/%s/sendMessage?chat_id=%d&text=%s",
//                    extension.getToken(), extension.getChatId(), resume
//            ), HttpRequest.BodyPublishers.noBody());
//            log.warn(String.format("Response status is %d ", response.statusCode()));
//        } catch (IOException | InterruptedException e) {
//            throw new RuntimeException(e);
//        }
    }

    private Task getTaskByName(String taskName){
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


        Response e = RestAssured.given().
                baseUri("https://api.telegram.org/bot5979849534:AAF4WfbQUzehAGcLFY4ptcXukVb4iyLwFyA")
                .multiPart("document", test.getReports().getHtml().getEntryPoint(), "text/plain")
                .multiPart("chat_id", 665918456, "multipart/form-data")
                .post("/sendMessage");
        System.out.println("99000009999" + e.body().print());
        return resultsOfTests;
    }
}
