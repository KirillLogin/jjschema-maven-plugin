package com.github.jjschemaplugin;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.reinert.jjschema.Attributes;
import com.github.reinert.jjschema.v1.JsonSchemaFactory;
import com.github.reinert.jjschema.v1.JsonSchemaV4Factory;
import org.apache.maven.plugin.MojoExecutionException;

import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class SchemaGenerator {

    private ClassLoader classLoader;
    private JsonSchemaFactory schemaFactory;
    private String targetSchemaDirectory;

    public SchemaGenerator(String workingDirectory, String targetSchemaDirectory){
        this.targetSchemaDirectory = targetSchemaDirectory;
        this.schemaFactory = new JsonSchemaV4Factory();
        this.schemaFactory.setAutoPutDollarSchema(false);

        URL[] urls;
        try {
            urls = new URL[]{new URL("file:///" + workingDirectory)};
            classLoader = new URLClassLoader(urls);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void generate(File classFile) throws MojoExecutionException {
        String classPath = getClasspathFromPath(classFile);

        Class targetClass = null;
        try {
            targetClass = classLoader.loadClass(classPath);
        } catch (ClassNotFoundException e) {
            throw new MojoExecutionException(this, "ClassNotFoundException", e.getMessage());
        }
        //TODO make a logic to check if Attributes annotation presents
        makeSchemaFile(targetClass);
    }

    private String getClasspathFromPath(File classFile){
        String path = classFile.getPath();
        String classPath = path.substring(path.lastIndexOf("\\target\\classes\\") + "\\target\\classes\\".length());
        classPath = classPath.replaceAll("\\\\", ".");
        classPath = classPath.substring(0, classPath.lastIndexOf("."));
        return  classPath;
    }

    //TODO make a logic to check if Attributes annotation presents
    private boolean isJjchemaAnnotated(Class targetClass) {
        return false;
    }

    private void makeSchemaFile(Class targetClass) throws MojoExecutionException {
        new File(targetSchemaDirectory).mkdirs();

        String targetFilePath = targetSchemaDirectory.concat(targetClass.getSimpleName()).concat(".json");
        JsonNode schemaOfClass = schemaFactory.createSchema(targetClass);

        try (Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(targetFilePath), "UTF-8"))) {
            out.write(schemaOfClass.toString());
        } catch (IOException e) {
            throw new MojoExecutionException(this, "IOException", e.getMessage());
        }
    }
}
