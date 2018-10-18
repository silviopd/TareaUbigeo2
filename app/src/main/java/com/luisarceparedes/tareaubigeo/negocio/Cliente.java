package com.luisarceparedes.tareaubigeo.negocio;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.luisarceparedes.tareaubigeo.datos.AccesoDatos;

import java.util.ArrayList;

/**
 * Created by laboratorio_computo on 20/09/2016.
 */
public class Cliente extends AccesoDatos {
    private String dni;
    private String nombre;
    private String telefono;
    private String codigoDepartamento;
    private String codigoProvincia;
    private String codigoDistrito;

    private double latitud;
    private double longitud;

    public static ArrayList<Cliente> lista =
            new ArrayList<Cliente>();


    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCodigoDepartamento() {
        return codigoDepartamento;
    }

    public void setCodigoDepartamento(String codigoDepartamento) {
        this.codigoDepartamento = codigoDepartamento;
    }

    public String getCodigoProvincia() {
        return codigoProvincia;
    }

    public void setCodigoProvincia(String codigoProvincia) {
        this.codigoProvincia = codigoProvincia;
    }

    public String getCodigoDistrito() {
        return codigoDistrito;
    }

    public void setCodigoDistrito(String codigoDistrito) {
        this.codigoDistrito = codigoDistrito;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public long agregar(){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("dni", this.getDni());
        values.put("nombre", this.getNombre());
        values.put("telefono", this.getTelefono());
        values.put("codigo_departamento", this.getCodigoDepartamento());
        values.put("codigo_provincia", this.getCodigoProvincia());
        values.put("codigo_distrito", this.getCodigoDistrito());
        values.put("latitud", this.getLatitud());
        values.put("longitud", this.getLongitud());

        long resultado = db.insert("cliente", null, values);

        return resultado;
    }

    public void cargarLista(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor resultado = db.rawQuery("select * from cliente order by nombre", null);
        lista.clear();
        while(resultado.moveToNext()){
            Cliente obj = new Cliente();
            obj.setNombre(resultado.getString(1));
            obj.setTelefono(resultado.getString(2));
            obj.setDni(resultado.getString(0));
            obj.setLatitud(resultado.getDouble(6));
            obj.setLongitud(resultado.getDouble(7));
            lista.add(obj);
        }
    }


    public long eliminar(String dni){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("cliente", "dni = '" + dni + "'", null);
    }

    public Cursor leerDatos(String dni){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor resultado = db.rawQuery(
                "select " +
                        "c.dni, " +
                        "c.nombre, " +
                        "c.telefono, " +
                        "d.nombre, " +
                        "p.nombre, " +
                        "di.nombre," +
                        "c.codigo_departamento, " +
                        "c.codigo_provincia, " +
                        "c.latitud, " +
                        "c.longitud " +
                        "from cliente c " +
                        "inner join distrito di on ( c.codigo_departamento = di.codigo_departamento and c.codigo_provincia = di.codigo_provincia and c.codigo_distrito = di.codigo_distrito ) " +
                        "inner join provincia p on ( di.codigo_departamento = p.codigo_departamento and di.codigo_provincia = p.codigo_provincia ) " +
                        "inner join departamento d on ( p.codigo_departamento = d.codigo_departamento ) " +
                        "where " +
                        "dni = '" + dni + "'", null);

        return resultado;
    }

    public long editar(){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //values.put("dni", this.getDni());
        values.put("nombre", this.getNombre());
        values.put("telefono", this.getTelefono());
        values.put("codigo_departamento", this.getCodigoDepartamento());
        values.put("codigo_provincia", this.getCodigoProvincia());
        values.put("codigo_distrito", this.getCodigoDistrito());
        values.put("latitud", this.getLatitud());
        values.put("longitud", this.getLongitud());

        long resultado = db.update("cliente", values, "dni = '" + this.getDni() + "'", null);

        return resultado;
    }

}