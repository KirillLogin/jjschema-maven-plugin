package com.github.jjschemaplugin;


import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;


@Mojo(name = "schemagen")
public class SchemagenMojo extends AbstractMojo {

    @Parameter(property = "dtoPackage")
    private String dtoPackage;

    @Parameter(property = "workingDirectory", defaultValue = "${project.build.directory}\\target\\classes\\")
    private String workingDirectory;

    @Parameter(property = "targetDirectory", defaultValue = "${project.build.directory}\\target\\schemas\\")
    private String targetDirectory;

    private SchemaGenerator schemaGenerator;

    public void execute() throws MojoExecutionException, MojoFailureException {
        dtoPackage = normalizePackage(dtoPackage);
        workingDirectory = normalizePackage(workingDirectory);
        targetDirectory = normalizePackage(targetDirectory);

        schemaGenerator = new SchemaGenerator(workingDirectory, targetDirectory);

        String dtoDirectory = workingDirectory.concat(dtoPackage.replaceAll("\\.", "\\\\"));

        scanAndProcessClassesInDir(new File(dtoDirectory));
    }

    private String normalizePackage(String path){
        return path.replaceAll("/", "\\\\");
    }

    private void scanAndProcessClassesInDir(File file){
        if(file.isDirectory()){
            File[] files = file.listFiles();
            if(files == null)
                return;
            for(File subFile : files){
                scanAndProcessClassesInDir(subFile);
            }
        } else if (file.getName().endsWith(".class")){
            generateSchemaFromClass(file);
        }
    }

    //TODO перенести обработку исключения в подходящее место
    private void generateSchemaFromClass(File classFile){
        try {
            schemaGenerator.generate(classFile);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
