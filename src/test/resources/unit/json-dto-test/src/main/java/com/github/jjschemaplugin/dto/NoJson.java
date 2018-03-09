package com.github.jjschemaplugin.dto;

import com.github.reinert.jjschema.SchemaIgnore;


public class NoJson {
    @SchemaIgnore
    private String perkele;

    public String getPerkele() {
        return perkele;
    }

    public void setPerkele(String perkele) {
        this.perkele = perkele;
    }
}
