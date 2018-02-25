package com.github.jjschemaplugin;


import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.net.URL;
import java.net.URLClassLoader;


@Mojo(name = "schemagen", defaultPhase = LifecyclePhase.COMPILE)
public class Schemagen extends AbstractMojo {

    @Parameter(property = "dtoPackage")
    private String dtoPackage;

    @Parameter(property = "workingDirectory", defaultValue = "${project.build.directory}/classes/")
    private String workingDirectory;

    public void execute() throws MojoExecutionException, MojoFailureException {
        dtoPackage = normalizePackage(dtoPackage);

        URL[] urls = new URL[0];
        try {
            urls = new URL[]{new URL("file:///" + workingDirectory)};

            ClassLoader cl = new URLClassLoader(urls);

            Class cls = cl.loadClass(dtoPackage.concat(".DtoClassName"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String normalizePackage(String dtoPackage){
        return dtoPackage.replaceAll("/", ".").replaceAll("\\\\", ".");
    }
}
