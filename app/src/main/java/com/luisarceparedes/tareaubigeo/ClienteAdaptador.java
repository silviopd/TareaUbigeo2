package com.luisarceparedes.tareaubigeo;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.app.Activity;


import com.luisarceparedes.tareaubigeo.negocio.Cliente;

import java.util.ArrayList;

import static android.support.v4.app.ActivityCompat.startActivity;

/**
 * Created by laboratorio_computo on 22/09/2016.
 */
public class ClienteAdaptador extends BaseAdapter {
    public static ArrayList<Cliente> lista;
    private LayoutInflater layoutInflater;

    public ClienteAdaptador(Context context, ArrayList<Cliente> lista){
        this.layoutInflater = LayoutInflater.from(context);
        this.lista = lista;
    }


    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int position) {
        return lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Holder holder;

        if (convertView == null){
            convertView = layoutInflater.inflate(R.layout.cliente_item, null);
            holder = new Holder();
            holder.lblNombre = (TextView) convertView.findViewById(R.id.lblNombre);
            holder.lblTelefono = (TextView) convertView.findViewById(R.id.lblTelefono);
            holder.imgImagen = (ImageView) convertView.findViewById(R.id.imgImagen);
            convertView.setTag(holder);
        }else{
            holder = (Holder)convertView.getTag();
        }

        Cliente item = Cliente.lista.get(position);
        holder.lblNombre.setText(item.getNombre());
        holder.lblTelefono.setText(item.getTelefono());
        holder.imgImagen.setImageResource(R.drawable.foto);

        return convertView;
    }


    private class Holder{
        TextView lblNombre;
        TextView lblTelefono;
        ImageView imgImagen;

    }

}
