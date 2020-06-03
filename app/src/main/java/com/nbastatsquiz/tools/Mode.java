package com.nbastatsquiz.tools;

public enum Mode {
    PORCENTAJE_TRIPLES("TRIPLES", "Porcentaje de triples" ),
    TIROS_LIBRES_INTENTADOS("TIROS LIBRES INTENTADOS","Tiros libres intentados");


    private String nombre;
    private String boton;

    Mode(String nombre, String boton) {
        this.nombre = nombre;
        this.boton = boton;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getBoton() {
        return boton;
    }

    public void setBoton(String boton) {
        this.boton = boton;
    }

    //    final public static String PORCENTAJE_TRIPLE = "Porcentaje de triples";
//    final public static String TIROS_LIBRES_INTENTADOS = "Tiros libres intentados";
}
