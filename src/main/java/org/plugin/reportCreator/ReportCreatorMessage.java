package org.plugin.reportCreator;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.tasks.testing.Test;
import org.plugin.reportCreator.report.ReportMessage;
import org.plugin.results.TestResultGetterMessage;
import org.plugin.results.TestResultsGetter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReportCreatorMessage implements ReportCreator {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Override
    public String createReport(Project project) {
        TestResultsGetter testResults = new TestResultGetterMessage();       
        List<ReportMessage> reports = testResults.getTestResults((Test) getTaskByName("test", project));
        var results = reports.stream().collect(Collectors.groupingBy(ReportMessage::getResult,
                Collectors.groupingBy(x->x.getTestName(), Collectors.counting())));
        var failure = reports.stream().filter(x-> x.getFailure() != null).collect(Collectors.groupingBy(x->x.getTestName(),
                Collectors.groupingBy(x->x.getFailure())));
        return String.format("%s - %s", results, failure);
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
