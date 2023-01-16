package org.plugin;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class SenderPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        project.task("sendResults").doLast(x-> System.out.println(" *********** " + x));
    }
}
