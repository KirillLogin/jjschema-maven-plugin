package com.github.jjschemaplugin;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.reinert.jjschema.Attributes;
import com.github.reinert.jjschema.v1.JsonSchemaFactory;
import com.github.reinert.jjschema.v1.JsonSchemaV4Factory;
import org.apache.maven.plugin.MojoExecutionException;

import java.io.*;
import java.lang.annotation.Annotation;
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
        this.schemaFactory.setAutoPutDollarSchema(true);

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
        Class attributesClass = null;
        try {
            targetClass = classLoader.loadClass(classPath);
            attributesClass = classLoader.loadClass("com.github.reinert.jjschema.Attributes");
        } catch (ClassNotFoundException e) {
            throw new MojoExecutionException(this, "ClassNotFoundException", e.getMessage());
        }
        if(isJjchemaAnnotated(targetClass, attributesClass)) {
            makeSchemaFile(targetClass);
        }

    }

    //TODO придумать что-то со стрингбилдером
    private String getClasspathFromPath(File classFile){
        String path = classFile.getPath();
        String classPath = path.substring(path.lastIndexOf("\\target\\classes\\") + "\\target\\classes\\".length());
        classPath = classPath.replaceAll("\\\\", ".");
        classPath = classPath.substring(0, classPath.lastIndexOf("."));
        return  classPath;
    }

    //TODO разобраться с аннотациями
    private boolean isJjchemaAnnotated(Class targetClass, Class secondClass) {
        Field[] fields = targetClass.getDeclaredFields();
        System.out.println(fields.length);

        Method[] methods = targetClass.getDeclaredMethods();
        System.out.println(methods.length);

        Annotation[] annotations = targetClass.getDeclaredAnnotations();
        System.out.println(annotations.length);


        System.out.println(targetClass.isAnnotationPresent(secondClass));

        Attributes[] attributes = (Attributes[])targetClass.getDeclaredAnnotationsByType(Attributes.class);
        System.out.println(attributes.length);

        return attributes != null && attributes.length > 0;
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
