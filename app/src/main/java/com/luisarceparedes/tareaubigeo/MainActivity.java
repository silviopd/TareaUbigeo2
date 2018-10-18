package com.luisarceparedes.tareaubigeo;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.luisarceparedes.tareaubigeo.datos.AccesoDatos;
import com.luisarceparedes.tareaubigeo.negocio.Cliente;
import com.luisarceparedes.tareaubigeo.negocio.Departamento;
import com.luisarceparedes.tareaubigeo.negocio.Distrito;
import com.luisarceparedes.tareaubigeo.negocio.Provincia;
import com.luisarceparedes.tareaubigeo.util.Funciones;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener, View.OnTouchListener{

    Spinner spDepartamento, spDistrito, spProvincia;
    EditText txtDNI, txtNombre, txtTelefono;
    Button btnGrabar, btnUbicacion;

    private double latitud=0, longitud=0;
    private int position;

    boolean userSelect;

    private static final int REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spDepartamento = (Spinner)findViewById(R.id.spDepartamento);
        spDistrito = (Spinner)findViewById(R.id.spDistrito);
        spProvincia = (Spinner)findViewById(R.id.spProvincia);

        txtDNI = (EditText)findViewById(R.id.txtDNI);
        txtNombre = (EditText)findViewById(R.id.txtNombre);
        txtTelefono = (EditText)findViewById(R.id.txtTelefono);

        btnGrabar = (Button) findViewById(R.id.btnGrabar);
        btnGrabar.setOnClickListener(this);

        btnUbicacion = (Button) findViewById(R.id.btnUbicacion);
        btnUbicacion.setOnClickListener(this);


        AccesoDatos.aplicacion = this;
        spDepartamento.setOnItemSelectedListener(this);
        spDistrito.setOnItemSelectedListener(this);
        spProvincia.setOnItemSelectedListener(this);

        spDepartamento.setOnTouchListener(this);
        spProvincia.setOnTouchListener(this);

        this.cargarDatosSpinnerDepartamento();

        Bundle p = this.getIntent().getExtras();
        if (p != null){
            userSelect = false;
            position = p.getInt("position");
            Cliente item = Cliente.lista.get(position);
            this.leerDatos(item.getDni());
        }else{
            userSelect = true;
        }



    }

    public void leerDatos(String dni){
        this.txtDNI.setEnabled(false);
        Cursor cursor = new Cliente().leerDatos(dni);
        if (cursor.moveToNext()){
            cargarDatosSpinnerProvincia(cursor.getString(6));
            cargarDatosSpinnerDistrito(cursor.getString(6), cursor.getString(7));

            this.txtDNI.setText(cursor.getString(0));
            this.txtNombre.setText(cursor.getString(1));
            this.txtTelefono.setText(cursor.getString(2));
            Funciones.selectedItemSpinner(this.spDepartamento, cursor.getString(3));
            Funciones.selectedItemSpinner(this.spProvincia, cursor.getString(4));
            Funciones.selectedItemSpinner(this.spDistrito, cursor.getString(5));

            this.latitud = cursor.getDouble(8);
            this.longitud = cursor.getDouble(9);

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if (userSelect) {
            // Your selection handling code here
            userSelect = false;
            System.out.println("touch");


            switch (parent.getId()) {
                case R.id.spDepartamento:
                    String codDepartamento = Departamento.listaDepartamento.get(position).getCodigo_departamento();
                    System.out.println("codigo departamento = " + codDepartamento + "/ nombre departamento = " + Departamento.listaDepartamento.get(position).getNombre());
                    cargarDatosSpinnerProvincia(codDepartamento);
                    userSelect = true;
                    break;
                case R.id.spProvincia:
                    String codDep = Provincia.listaProvincias.get(position).getCodigo_departamento();
                    String codPro = Provincia.listaProvincias.get(position).getCodigo_provincia();
                    System.out.println("codigo departamento = " + codDep + "/  codigo provincia = " + codPro + "/ nombre provincia = " + Provincia.listaProvincias.get(position).getNombre());
                    cargarDatosSpinnerDistrito(codDep, codPro);
                    break;
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void cargarDatosSpinnerDepartamento(){
        String listaNombresDepartamento[] = new Departamento().listaDepartamentos();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listaNombresDepartamento);
        spDepartamento.setAdapter(adapter);
    }

    private void cargarDatosSpinnerProvincia(String codDep){
        String listaNombresProvincia[] = new Provincia().listaProvincias(codDep);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listaNombresProvincia);
        spProvincia.setAdapter(adapter);
    }

    private void cargarDatosSpinnerDistrito(String codDep, String codPro){
        String listaNombresDistrito[] = new Distrito().listaDistritos(codDep, codPro);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listaNombresDistrito);
        spDistrito.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnGrabar:
                String dni = txtDNI.getText().toString();
                String nombre = txtNombre.getText().toString();
                String telefono = txtTelefono.getText().toString();

                if (dni.isEmpty()){
                    Toast.makeText(this, "Ingrese DNI", Toast.LENGTH_LONG).show();
                    txtDNI.requestFocus();
                    return;
                }

                if (dni.length() < 8){
                    Toast.makeText(this, "Ingrese DNI de 8 dígitos", Toast.LENGTH_LONG).show();
                    txtDNI.requestFocus();
                    return;
                }

                if (nombre.isEmpty()){
                    Toast.makeText(this, "Ingrese nombre", Toast.LENGTH_LONG).show();
                    txtNombre.requestFocus();
                    return;
                }

                if (telefono.isEmpty()){
                    Toast.makeText(this, "Ingrese teléfono", Toast.LENGTH_LONG).show();
                    txtTelefono.requestFocus();
                    return;
                }

                String codigoDep = Distrito.listaDistrito.get(spDistrito.getSelectedItemPosition()).getCodigoDepartamento();
                String codigoPro = Distrito.listaDistrito.get(spDistrito.getSelectedItemPosition()).getCodigoProvincia();
                String codigoDis = Distrito.listaDistrito.get(spDistrito.getSelectedItemPosition()).getCodigoDistrito();

                Cliente obj = new Cliente();
                obj.setDni(dni);
                obj.setNombre(nombre);
                obj.setTelefono(telefono);
                obj.setCodigoDepartamento(codigoDep);
                obj.setCodigoProvincia(codigoPro);
                obj.setCodigoDistrito(codigoDis);
                obj.setLatitud(this.latitud);
                obj.setLongitud(this.longitud);

                Toast.makeText(MainActivity.this, "Grabando * Lat: " + this.latitud + ", Long: " + this.longitud, Toast.LENGTH_SHORT).show();


                long resultado = -1;
                if ( txtDNI.isEnabled()){
                    resultado = obj.agregar();
                }else{
                    resultado = obj.editar();
                }


                System.out.println("Resultado: " + resultado);

                if (resultado != -1){
                    Toast.makeText(this, "Grabado ok", Toast.LENGTH_LONG).show();
                    this.finish();
                }
                break;

            case R.id.btnUbicacion:
                Intent m = new Intent(this, ClienteMapa.class);
                Bundle pm = new Bundle();
                if (this.txtDNI.isEnabled()){
                    pm.putInt("position", -1); //cliente nuevo
                }else{
                    pm.putInt("position", position);
                }

                m.putExtras(pm);
                startActivityForResult(m, REQUEST_CODE);

                break;
        }




    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        userSelect = true;
        return false;
    }



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // A contact was picked.  Here we will just display it
                // to the user.
                Bundle p = data.getExtras();
                this.latitud = p.getDouble("latitud");
                this.longitud = p.getDouble("longitud");

                Toast.makeText(this, "Se ha capturado la ubicación del cliente\n\n" +"Lat: " + this.latitud + ", Long: " + this.longitud, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
