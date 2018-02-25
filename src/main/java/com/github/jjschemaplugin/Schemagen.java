package com.github.jjschemaplugin;


import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;


@Mojo(name = "schemagen", defaultPhase = LifecyclePhase.COMPILE)
public class Schemagen extends AbstractMojo {

    @Parameter(property = "dtoPackage")
    private String dtoPackage;

    public void execute() throws MojoExecutionException, MojoFailureException {

    }
}
