# senderPlugin

1. gradle publishToMavenLocal
2. to add the plugin into project from local .m2 you need add next sample to build.gradle 

````
buildscript{
    repositories {
        mavenLocal()
        dependencies{
            classpath 'org.plugin:senderPlugin:1.0-SNAPSHOT'
        }
    }
}

apply plugin: 'org.plugin.sender'
````