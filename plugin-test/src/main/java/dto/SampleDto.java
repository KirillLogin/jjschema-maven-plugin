package dto;

import com.github.reinert.jjschema.Attributes;

@Attributes(title = "root", additionalProperties = false)
public class SampleDto {
    @Attributes(required = true, title = "stringParam")
    private String stringParam;

    public String getStringParam() {
        return stringParam;
    }

    public void setStringParam(String stringParam) {
        this.stringParam = stringParam;
    }
}
