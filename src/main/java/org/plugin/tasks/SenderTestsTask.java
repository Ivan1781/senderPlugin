package org.plugin.tasks;

import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskAction;
import org.plugin.client.Client;
import org.plugin.client.TelegramClient;

public class SenderTestsTask extends DefaultTask {
    private final Project project = this.getProject();

    @TaskAction
    public void send() {
        new TelegramClient().sendReport(project);
    }
}
