package com.github.jjschemaplugin.dto;

import com.github.reinert.jjschema.Attributes;

@Attributes(title = "root", description = "root", additionalProperties = false)
public class SampleDto {
    @Attributes(required = true, title = "stringParam")
    private String stringParam;
    @Attributes(required = true, title = "booleanParam")
    private boolean booleanParam;
    @Attributes(required = true, title = "sampleType")
    private SampleType sampleType;

}
