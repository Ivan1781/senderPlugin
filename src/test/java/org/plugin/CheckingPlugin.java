package org.plugin;

import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.testfixtures.ProjectBuilder;
import org.gradle.testkit.runner.GradleRunner;
import org.testng.annotations.Test;

import static org.testng.Assert.*;


public class CheckingPlugin {

    @Test
    public void getNumberTaskExists(){
        Project project = ProjectBuilder.builder().build();
        project.getPluginManager().apply("org.plugin.sender");
        assertEquals(project.getTasksByName("sendTestResults", true).size(), 1);
    }

    @Test
    public void getTestResul(){
        Project project = ProjectBuilder.builder().build();
        project.getPluginManager().apply("org.plugin.sender");
        Task test = project.getTasks().stream().filter(x->x.getName().equals("sendTestResults")).findAny().get();
        assertEquals(test.getDependsOn().size(), 1);
    }

//    @Test
//    public void getTestResul33(){
//        Project project = ProjectBuilder.builder().build();
//        project.getPluginManager().apply("org.plugin.sender");
//        project.getTasksByName("sendTestResults", true).iterator().next().
//
//    }
}
