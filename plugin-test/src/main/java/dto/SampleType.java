package dto;

import com.github.reinert.jjschema.Attributes;

@Attributes(title = "sampleType", additionalProperties = false)
public class SampleType {
    @Attributes(required = true, title = "name")
    private String name;
    @Attributes(required = true, title = "name")
    private String surName;

    public SampleType(String name, String surName) {
        this.name = name;
        this.surName = surName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }
}
