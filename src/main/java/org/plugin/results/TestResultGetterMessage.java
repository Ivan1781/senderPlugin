package org.plugin.results;

import org.gradle.api.tasks.testing.Test;
import org.gradle.api.tasks.testing.TestDescriptor;
import org.gradle.api.tasks.testing.TestListener;
import org.gradle.api.tasks.testing.TestResult;
import org.plugin.reportCreator.report.Report;
import org.plugin.reportCreator.report.ReportMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class TestResultGetterMessage implements TestResultsGetter {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    // public String getReport(Test test) {
    //     List<Report> reports = getTestResults(test);
    //     StringBuilder stringReport = new StringBuilder();
    //     reports.forEach(result -> stringReport.append(result));
    //     return stringReport.toString();
    // }

    @Override
    public List<Report> getTestResults(Test test) {
        List<Report> reports = new ArrayList<>();
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
                Report report = new ReportMessage.ReportMessageBuilder()
                        .setTestClassName(testDescriptor.getClassName())
                        .setTestName(testDescriptor.getName())
                        .setResult(testResult.getResultType().name())
                        .build();
                reports.add(report);
            }
        });
        test.executeTests();
        return reports;
    }
}
