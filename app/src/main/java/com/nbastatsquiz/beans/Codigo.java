package com.nbastatsquiz.beans;

public class Codigo {
    private String codigo;
    private String url;

    public Codigo() {
    }

    public Codigo(String codigo, String url) {
        this.codigo = codigo;
        this.url = url;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
