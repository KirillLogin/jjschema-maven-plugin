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

    public String getStringParam() {
        return stringParam;
    }

    public void setStringParam(String stringParam) {
        this.stringParam = stringParam;
    }

    public boolean isBooleanParam() {
        return booleanParam;
    }

    public void setBooleanParam(boolean booleanParam) {
        this.booleanParam = booleanParam;
    }

    public SampleType getSampleType() {
        return sampleType;
    }

    public void setSampleType(SampleType sampleType) {
        this.sampleType = sampleType;
    }
}
