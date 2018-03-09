jjschema-maven-plugin [![Build Status](https://travis-ci.org/KirillLogin/jjschema-maven-plugin.svg?branch=develop)](https://travis-ci.org/KirillLogin/jjschema-maven-plugin)
===============

A maven plugin for automatic json schema generating 
providing by [jjschema](https://github.com/reinert/JJSchema) library.

Versions
----------------
**1.0-SNAPSHOT**:
- basic functionality. Plugin loads all the classes in the specified package and generates JSON schemas from its.


Usage
----------------
- Add jjschema dependency to your project
```xml
<dependency>
    <groupId>com.github.reinert</groupId>
    <artifactId>jjschema</artifactId>
    <version>1.10</version>
</dependency>
```


- Add plugin dependency
```xml
<plugin>
    <groupId>com.github.kugelblitz</groupId>
    <artifactId>jjschema-maven-plugin</artifactId>
    <version>${jjschema-maven-plugin.version}</version>
    <configuration>
        <dtoPackage>com.exmaple.dtopackage</dtoPackage>
        <workingDirectory>${project.build.directory}\\classes\\</workingDirectory>
        <targetDirectory>${project.build.directory}\\schemas</targetDirectory>
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

- Annotate your POJO class

```java
package com.exmaple.dtopackage;

import com.github.reinert.jjschema.Attributes;

@Attributes(title = "root",additionalProperties = false)
public class SampleDto {
    @Attributes(required = true, title = "stringParam"
    )
    private String stringParam;
    
    public String getStringParam() { 
        return this.stringParam; 
    }
    
    public void setStringParam(String stringParam) {
        this.stringParam = stringParam; 
    }
}
```
- run mvn clean package and get a schema in target/schemas
```json
{
  "type": "object",
  "description": "root",
  "title": "root",
  "additionalProperties": false,
  "properties": {
    "stringParam": {
      "type": "string",
      "title": "stringParam"
    }
  },
  "required": ["stringParam"],
  "$schema": "http://json-schema.org/draft-04/schema#"
}
```

TODO
----------------
- generates only jjschema-annotated classes
- tests
- mistake proofing of plugin config
- generating schemas with keepeing dtoPackage inner package structure