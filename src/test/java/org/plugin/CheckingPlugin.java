package org.plugin;

import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.testfixtures.ProjectBuilder;
import org.testng.annotations.Test;

import static org.testng.Assert.*;


public class CheckingPlugin {

    @Test
    public void getPluginTask() {
        Project project = ProjectBuilder.builder().build();
        project.getPluginManager().apply("org.plugin.sender");
        assertEquals(project.getTasksByName("sendTestResults", true).size(), 1);
    }

    @Test
    public void getPreviousTask(){
        Project project = ProjectBuilder.builder().build();
        project.getPluginManager().apply("org.plugin.sender");
        Task test = project.getTasks().stream().filter(x->x.getName().equals("sendTestResults")).findAny().get();
        assertEquals(test.getDependsOn().size(), 1);
    }
}
