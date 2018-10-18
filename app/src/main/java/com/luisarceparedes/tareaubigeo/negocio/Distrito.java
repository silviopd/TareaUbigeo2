package com.luisarceparedes.tareaubigeo.negocio;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.luisarceparedes.tareaubigeo.datos.AccesoDatos;

import java.util.ArrayList;

/**
 * Created by luisarceparedes on 9/17/16.
 */
public class Distrito extends AccesoDatos {

    private String codigoDistrito;
    private String codigoProvincia;
    private String codigoDepartamento;
    private String nombre;

    public static ArrayList<Distrito> listaDistrito = new ArrayList<Distrito>();

    public String getCodigoDistrito() {
        return codigoDistrito;
    }

    public void setCodigoDistrito(String codigoDistrito) {
        this.codigoDistrito = codigoDistrito;
    }

    public String getCodigoProvincia() {
        return codigoProvincia;
    }

    public void setCodigoProvincia(String codigoProvincia) {
        this.codigoProvincia = codigoProvincia;
    }

    public String getCodigoDepartamento() {
        return codigoDepartamento;
    }

    public void setCodigoDepartamento(String codigoDepartamento) {
        this.codigoDepartamento = codigoDepartamento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void cargarDatosDistrito(String codDepartamento, String codProvincia){
        SQLiteDatabase bd = this.getReadableDatabase();
        String sql = "select * from distrito where codigo_departamento like '" + codDepartamento + "' and codigo_provincia like '"+ codProvincia +"' order by 2;";
        Cursor resultado = bd.rawQuery(sql, null);
        listaDistrito.clear();
        while(resultado.moveToNext()){
            Distrito objDist = new Distrito();
            objDist.setCodigoDepartamento(resultado.getString(0));
//            System.out.println(resultado.getString(0));
            objDist.setCodigoProvincia(resultado.getString(1));
//            System.out.println(resultado.getString(1));
            objDist.setCodigoDistrito(resultado.getString(2));
//            System.out.println(resultado.getString(2));
            objDist.setNombre(resultado.getString(3));
//            System.out.println(resultado.getString(3));
            listaDistrito.add(objDist);
        }
    }

    public String[] listaDistritos(String codDep, String codPro){
        cargarDatosDistrito(codDep, codPro);
        String listaNombresDistritos[] = new String[listaDistrito.size()];
        for(int i = 0; i < listaDistrito.size(); i++){
            Distrito item = listaDistrito.get(i);
            listaNombresDistritos[i] = item.getNombre();
        }
        return listaNombresDistritos;
    }


}
