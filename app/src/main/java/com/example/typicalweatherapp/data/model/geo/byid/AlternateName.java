package com.example.typicalweatherapp.data.model.geo.byid;

public class AlternateName {

    private String name;
    private String lang;

    public AlternateName(String name, String lang) {
        this.name = name;
        this.lang = lang;
    }

    public AlternateName() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

}
