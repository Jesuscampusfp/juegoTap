/*
* Class: Adaptador
*
* Author: Jesus Martin valdivia
*
* CampusFP
*
*
In this class we have to get the values from the database so that they can be displayed
* in the layouts of the ranking we will have to take these data from the Pojo of the class
* User, set the data according to the string
*
*
*
* */

package com.example.juego;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adaptador extends RecyclerView.Adapter<Adaptador.MyHolder>{

    private Context context;
    private List <Usuario> usuarioList;

    //Constructor
    public Adaptador(Context context, List<Usuario> usuarioList) {
        this.context = context;
        this.usuarioList = usuarioList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.jugadores,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int i) {
        /*Obtener el dato del modelo Usuario*/
        String Nombres = usuarioList.get(i).getNombre();
        int Patos = usuarioList.get(i).getPatos();

        String p = String.valueOf(Patos);
        holder.NombreJugador.setText(Nombres);
        holder.PuntuajeJugador.setText(p);

    }

    @Override
    public int getItemCount() {
        return usuarioList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{

        TextView NombreJugador,PuntuajeJugador;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            //Inicializar

            NombreJugador = itemView.findViewById(R.id.NombreJugador);
            PuntuajeJugador = itemView.findViewById(R.id.PuntuajeJugador);
        }
    }
}
