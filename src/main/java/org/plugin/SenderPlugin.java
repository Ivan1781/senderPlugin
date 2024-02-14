package org.plugin;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.tasks.testing.Test;
import org.plugin.extensions.SamplePluginExtension;
import org.plugin.tasks.SenderTestsTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class SenderPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        project.getPluginManager().apply("java");
        project.getTasks().register("sendTestResults", SenderTestsTask.class);

        Set<Task> tasks = project.getTasks();
        List<Task> listTasks = new ArrayList<>();

        Optional<Task> sendTestResults = tasks.stream().filter(x->x.getName().equals("sendTestResults")).findAny();
        Optional <Task> test = tasks.stream().filter(x->x.getName().equals("test")).findAny();

        Task sendTestResultTask = null;
        if(sendTestResults.isPresent()){
            sendTestResultTask = sendTestResults.get();
        }
        Test testTask=null;
        if(test.isPresent()) {
            testTask = (Test) test.get();
        }
        listTasks.add(testTask);
        sendTestResultTask.setDependsOn(listTasks);
        project.getExtensions().create("defineToken", SamplePluginExtension.class);
    }
}
