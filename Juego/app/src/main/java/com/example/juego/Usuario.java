/*
* Class Usuario
*
* Author: Jesus Martin Valdivia
* 
* In this Class what he does is Pojo
 * is simply a Java object that does not implement any special interface
 * only has the getter/setters methods and the constructor to get the values
*
*
* */

package com.example.juego;

public class Usuario {
    String Email,Fecha,Nombre;
    int patos;

    public Usuario(){}

    public Usuario(String email, String fecha, String nombre, int patos) {
        Email = email;
        Fecha = fecha;
        Nombre = nombre;
        this.patos = patos;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public int getPatos() {
        return patos;
    }

    public void setPatos(int patos) {
        this.patos = patos;
    }
}
