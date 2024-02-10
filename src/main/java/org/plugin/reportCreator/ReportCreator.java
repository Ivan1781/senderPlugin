package org.plugin.reportCreator;

import org.gradle.api.Project;

public interface ReportCreator {
    String createReport(Project project);
}
