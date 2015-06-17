package com.jldes.jldes;

import android.util.Log;

import java.io.Serializable;

public class Noticia  implements Serializable{
    private String titulo;
    private String link;
    private String descripcion;
    private String guid;
    private String fecha;
    private String contenido;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String t) {
        titulo = t;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String l) {
        link = l;
    }

    public String getDescripcion() {
        Log.d("Descripcion",descripcion);
        return descripcion;
    }

    public void setDescripcion(String d) {
        descripcion = d;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String g) {
        guid = g;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String f) {
        fecha = f;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
}