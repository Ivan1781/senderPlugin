package org.plugin.reportCreator;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.tasks.testing.Test;
import org.plugin.reportCreator.report.Report;
import org.plugin.results.TestResultGetterMessage;
import org.plugin.results.TestResultsGetter;

public class ReportCreatorMessage implements ReportCreator {
    @Override
    public String createReport(Project project) {
        TestResultsGetter testResults = new TestResultGetterMessage();       
        List<Report> reports = testResults.getTestResults((Test) getTaskByName("test", project));
        StringBuilder stringReport = new StringBuilder();
        reports.forEach(result -> stringReport.append(result));
        return stringReport.toString();
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
}
