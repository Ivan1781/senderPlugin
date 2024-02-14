package org.plugin.client;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.gradle.api.Project;

import org.plugin.extensions.SamplePluginExtension;
import org.plugin.reportCreator.ReportCreator;
import org.plugin.reportCreator.ReportCreatorMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

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
            String sendMessageApiUrl = String.format("https://api.telegram.org/bot%s/sendMessage?chat_id=%d",
                    token, chatId);
            HttpClient httpClient = HttpClients.createDefault();
            HttpPost httpSendMessage = new HttpPost(sendMessageApiUrl);
            String bodySendMessage = String.format("""
                       {"text": "%s"}
                   """, report);
            httpSendMessage.setEntity(new StringEntity(bodySendMessage, ContentType.APPLICATION_JSON));
            HttpResponse responseSendMessage = httpClient.execute(httpSendMessage);
            log.warn(String.format("Response status is %d ", responseSendMessage.getStatusLine().getStatusCode()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
