package org.plugin.results;

import org.gradle.api.tasks.testing.Test;
import org.plugin.reportCreator.report.Report;

import java.util.List;

public interface TestResultsGetter {
    List<Report> getTestResults(Test task);
}
