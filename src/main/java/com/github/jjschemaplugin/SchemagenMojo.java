package com.github.jjschemaplugin;


import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;


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
        normalizeInputParameters();;

        schemaGenerator = new SchemaGenerator(workingDirectory, targetDirectory);

        String dtoDirectory = workingDirectory.concat(dtoPackage);

        scanAndProcessClassesInDir(new File(dtoDirectory));
    }

    private void normalizeInputParameters(){
        dtoPackage = normalizePath(dtoPackage);
        workingDirectory = normalizePath(workingDirectory);
        targetDirectory = normalizePath(targetDirectory);
    }

    private String normalizePath(String path){
        return path.replaceAll("/", "\\\\").replaceAll("\\.", "\\\\");
    }

    private void scanAndProcessClassesInDir(File file) throws MojoExecutionException {
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
    private void generateSchemaFromClass(File classFile) throws MojoExecutionException {
        schemaGenerator.generate(classFile);
    }
}
