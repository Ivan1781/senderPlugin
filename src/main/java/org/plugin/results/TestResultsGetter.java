package org.plugin.results;

import org.gradle.api.tasks.testing.Test;
import org.plugin.reportCreator.report.ReportMessage;

import java.util.List;

public interface TestResultsGetter {
    List<ReportMessage> getTestResults(Test task);
}
