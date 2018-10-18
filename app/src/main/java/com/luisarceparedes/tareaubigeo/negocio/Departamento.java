package com.luisarceparedes.tareaubigeo.negocio;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.luisarceparedes.tareaubigeo.datos.AccesoDatos;

import java.util.ArrayList;

/**
 * Created by luisarceparedes on 9/17/16.
 */
public class Departamento extends AccesoDatos{

    private String codigo_departamento;
    private String nombre;

    public static ArrayList<Departamento> listaDepartamento = new ArrayList<Departamento>();

    public String getCodigo_departamento() {return codigo_departamento;}

    public void setCodigo_departamento(String codigo_departamento) {
        this.codigo_departamento = codigo_departamento;
    }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void cargarDatosDepartamento(){
        SQLiteDatabase bd = this.getReadableDatabase();
        String sql = "select * from departamento order by 2;";
        Cursor resultado = bd.rawQuery(sql, null);
        listaDepartamento.clear();
        while(resultado.moveToNext()){
            Departamento objDep = new Departamento();
            objDep.setCodigo_departamento(resultado.getString(0));
            objDep.setNombre(resultado.getString(1));
            listaDepartamento.add(objDep);
        }
    }

    public String[] listaDepartamentos(){
        cargarDatosDepartamento();
        String listaNombresDepartamento[] = new String[listaDepartamento.size()];
        for(int i = 0; i < listaDepartamento.size(); i++){
            Departamento item = listaDepartamento.get(i);
            listaNombresDepartamento[i] = item.getNombre();
        }
        return listaNombresDepartamento;
    }
}
