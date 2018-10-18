package com.luisarceparedes.tareaubigeo.negocio;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.luisarceparedes.tareaubigeo.datos.AccesoDatos;

import java.util.ArrayList;

/**
 * Created by luisarceparedes on 9/17/16.
 */
public class Provincia extends AccesoDatos{
    private String codigo_provincia;
    private String codigo_departamento;
    private String nombre;

    public static ArrayList<Provincia> listaProvincias = new ArrayList<Provincia>();

    public String getCodigo_provincia() {
        return codigo_provincia;
    }

    public void setCodigo_provincia(String codigo_provincia) {
        this.codigo_provincia = codigo_provincia;
    }

    public String getCodigo_departamento() {
        return codigo_departamento;
    }

    public void setCodigo_departamento(String codigo_departamento) {
        this.codigo_departamento = codigo_departamento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void cargarDatosProvincia(String codDepartamento){
        SQLiteDatabase bd = this.getReadableDatabase();
        String sql = "select * from provincia where codigo_departamento = '" + codDepartamento + "' order by 2;";
        Cursor resultado = bd.rawQuery(sql, null);
        listaProvincias.clear();
        while(resultado.moveToNext()){


            Provincia objProv = new Provincia();
            objProv.setCodigo_departamento(resultado.getString(0));
            objProv.setCodigo_provincia(resultado.getString(1));
            objProv.setNombre(resultado.getString(2));
            listaProvincias.add(objProv);
        }
    }

    public String[] listaProvincias(String codDep){
        cargarDatosProvincia(codDep);
        String listaNombresProvincias[] = new String[listaProvincias.size()];
        for(int i = 0; i < listaProvincias.size(); i++){
            Provincia item = listaProvincias.get(i);
            listaNombresProvincias[i] = item.getNombre();
        }
        return listaNombresProvincias;
    }


}
