package org.plugin.client;

import org.gradle.api.Project;

public interface Client {
    void sendReport(Project project);
}
