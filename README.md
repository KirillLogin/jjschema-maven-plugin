jjschema-maven-plugin
===============

A maven plugin for automatic json schema generating 
providing by [jjschema](https://github.com/reinert/JJSchema) library

Usage
----------------

```xml
<plugin>
    <groupId>com.github.kugelblitz</groupId>
    <artifactId>jjschema-maven-plugin</artifactId>
    <version>${jjschema-maven-plugin.version}</version>
    <configuration>
        <dtoPackage>com.exmaple.dtopackage</dtoPackage>
        <workingDirectory>${project.build.directory}\\target\\classes\\</workingDirectory>
        <targetDirectory>${project.build.directory}\\target\\schemas</targetDirectory>
    </configuration>
    <executions>
        <execution>
            <goals>
                <goal>schemagen</goal>
            </goals>
            <phase>compile</phase>
        </execution>
    </executions>
</plugin>
```
**dtoPackage** - package with annotated classes you want to be translated to json schema

**workingDirectory** - where are compiled classes; ${project.build.directory}\\target\\classes\\ by deafult

**targetDirectory** - where schema filed should be

Version
----------------
**1.0-SNAPSHOT**:
- basic functionality. Plugin seeks annotated classes in the dtoPackage 
and generates schemas for those classes which have class @Attributes annotation.

TODO
----------------
- tests
- mistake proofing of plugin config
- generating schemas with keepeing dtoPackage inner package structure