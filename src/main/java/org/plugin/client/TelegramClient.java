package org.plugin.client;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClients;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.tasks.testing.Test;


import org.plugin.extensions.SamplePluginExtension;
import org.plugin.reportCreator.ReportCreator;
import org.plugin.reportCreator.ReportCreatorMessage;
import org.plugin.reportCreator.report.Report;
import org.plugin.results.TestResultGetterMessage;
import org.plugin.results.TestResultsGetter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class TelegramClient implements Client {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private String getReport(Project project) {
        ReportCreator reportCreator = new ReportCreatorMessage();
        return reportCreator.createReport(project);
    }

    @Override
    public void sendReport(Project project) {
        
        SamplePluginExtension extension = (SamplePluginExtension) project.getExtensions().getByName("defineToken");
        String report = getReport(project);
        try {
            long chatId = extension.getChatId();
            String token = extension.getToken();

            String sendDocumentApiUrl = String.format("https://api.telegram.org/bot%s/sendDocument?chat_id=%d",
                   token , chatId);
            String sendMessageApiUrl = String.format("https://api.telegram.org/bot%s/sendMessage?chat_id=%d",
                    token, chatId);

            // File fileToUpload = Paths.get("/home/ivan/Projects/cucumberTestNG/build/cucumber/tests.html").toFile();
            HttpClient httpClient = HttpClients.createDefault();

            // report.forEach(x->log.warn("ququq" + x.toString()));
           
            // ObjectMapper objectMapper = new ObjectMapper();
            HttpPost httpSendMessage = new HttpPost(sendMessageApiUrl);
            // String qqq = objectMapper.writeValueAsString(report.get(0));
            
            String bodySendMessage = String.format("""
                       {"text": "%s"}
                   """, report);
            log.warn("atata " + bodySendMessage);

            httpSendMessage.setEntity(new StringEntity(bodySendMessage, ContentType.APPLICATION_JSON));
            HttpResponse responseSendMessage = httpClient.execute(httpSendMessage);


            // HttpPost httpSendDocument = new HttpPost(sendDocumentApiUrl);
            // HttpEntity entityDocument = MultipartEntityBuilder.create()
            //         .addBinaryBody("document", fileToUpload, ContentType.DEFAULT_BINARY, fileToUpload.getName())
            //         .build();
            // httpSendDocument.setEntity(entityDocument);
            // HttpResponse responseSendDocument = httpClient.execute(httpSendDocument);

            log.warn(String.format("Response status is %d ", responseSendMessage.getStatusLine().getStatusCode()));
            // log.warn(String.format("Response status is %d ", responseSendDocument.getStatusLine().getStatusCode()));
            log.error("atata " + responseSendMessage.getEntity().toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    
}
