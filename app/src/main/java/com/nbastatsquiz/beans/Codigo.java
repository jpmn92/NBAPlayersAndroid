package com.nbastatsquiz.beans;

public class Codigo {
    private String codigo;
    private String url;
    private int usado;

    public Codigo() {
    }

    public Codigo(String codigo, String url, int usado) {
        this.codigo = codigo;
        this.url = url;
        this.usado = usado;
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

    public int getUsado() {
        return usado;
    }

    public void setUsado(int usado) {
        this.usado = usado;
    }
}
