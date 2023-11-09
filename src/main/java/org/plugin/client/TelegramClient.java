package org.plugin.client;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.EntityTemplate;
import org.apache.http.entity.StringEntity;
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
    public void sendReport(Project project) {
        SamplePluginExtension extension = (SamplePluginExtension) project.getExtensions().getByName("defineToken");
        Map<String, String> resultsOfTests = getTestResults((Test) getTaskByName("test", project));
        StringBuilder resume = new StringBuilder();
        resultsOfTests.forEach((key, value) -> resume.append(key).append("***").append(value).append("___"));
        try {
            long chatId = extension.getChatId();
            String token = extension.getToken();

            String sendDocumentApiUrl = String.format("https://api.telegram.org/bot%s/sendDocument?chat_id=%d",
                   token , chatId);
            String sendMessageApiUrl = String.format("https://api.telegram.org/bot%s/sendMessage?chat_id=%d",
                    token, chatId);

            File fileToUpload = Paths.get("/home/ivan/projects/cucumberTestNg/build/cucumber/tests.html").toFile();
            HttpClient httpClient = HttpClients.createDefault();

            String bodySendMessage = String.format("""
                        {"text": "%s"}
                    """, resume);

            HttpPost httpSendMessage = new HttpPost(sendMessageApiUrl);
            httpSendMessage.setEntity(new StringEntity(bodySendMessage, ContentType.APPLICATION_JSON));
            HttpResponse responseSendMessage = httpClient.execute(httpSendMessage);

            HttpPost httpSendDocument = new HttpPost(sendDocumentApiUrl);
            HttpEntity entityDocument = MultipartEntityBuilder.create()
                    .addBinaryBody("document", fileToUpload, ContentType.DEFAULT_BINARY, fileToUpload.getName())
                    .build();
            httpSendDocument.setEntity(entityDocument);
            HttpResponse responseSendDocument = httpClient.execute(httpSendDocument);

            log.warn(String.format("Response status is %d ", responseSendMessage.getStatusLine().getStatusCode()));
            log.warn(String.format("Response status is %d ", responseSendDocument.getStatusLine().getStatusCode()));

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
                log.info("before Suite");
            }

            @Override
            public void afterSuite(TestDescriptor testDescriptor, TestResult testResult) {
                log.info("after Suite");
            }

            @Override
            public void beforeTest(TestDescriptor testDescriptor) {
                log.info("before Test: " + testDescriptor.getName());
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
