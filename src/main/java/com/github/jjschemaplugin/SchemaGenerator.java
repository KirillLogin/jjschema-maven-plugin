package com.github.jjschemaplugin;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.reinert.jjschema.Attributes;
import com.github.reinert.jjschema.v1.JsonSchemaFactory;
import com.github.reinert.jjschema.v1.JsonSchemaV4Factory;

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

    public void generate(File classFile) throws ClassNotFoundException {
        String classPath = getClasspathFromPath(classFile);
        Class targetClass = classLoader.loadClass(classPath);
        if(isJjchemaAnnotated(targetClass)) {
            try {
                makeSchemaFile(targetClass);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private String getTargetSchemaDirectory(String workingDirectory) {
        return null;
    }

    //TODO придумать что-то со стрингбилдером
    private String getClasspathFromPath(File classFile){
        String path = classFile.getPath();
        String classPath = path.substring(path.lastIndexOf("\\target\\classes\\") + "\\target\\classes\\".length());
        classPath = classPath.replaceAll("\\\\", ".");
        classPath = classPath.substring(0, classPath.lastIndexOf("."));
        return  classPath;
    }

    private boolean isJjchemaAnnotated(Class targetClass) {
        Attributes[] attributes = (Attributes[])targetClass.getDeclaredAnnotationsByType(Attributes.class);
        return attributes != null && attributes.length > 0;
    }

    private void makeSchemaFile(Class targetClass) throws IOException {
        boolean isDirCreated = new File(targetSchemaDirectory).mkdirs();
        if(!isDirCreated)
            return;

        String targetFilePath = targetSchemaDirectory.concat(targetClass.getSimpleName()).concat(".json");
        JsonNode schemaOfClass = schemaFactory.createSchema(targetClass);

        try (Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(targetFilePath), "UTF-8"))) {
            out.write(schemaOfClass.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(schemaOfClass);
    }
}
