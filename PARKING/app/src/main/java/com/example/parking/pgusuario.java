package com.example.parking;

import static com.example.parking.R.id.btn_generar;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class pgusuario extends AppCompatActivity  {
    TextView tvcedula;
    EditText id,placa,marca,Fechingreso,Horingreso,Horsalida,fk_vehiculo;
    private Button btnGenerar,btnGuardar;
    private Spinner tipo,num_par;
    RequestQueue requestQueue;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pgusuario);
        tvcedula=findViewById(R.id.labNombre);
        Intent intent = getIntent();
        String mostrarCed= intent.getStringExtra("cedula");
        tvcedula.setText(mostrarCed);
        id=(EditText) findViewById(R.id.txtid);
        placa=(EditText) findViewById(R.id.txtPlaca);
        marca=(EditText) findViewById(R.id.txtmarcaV);
        Fechingreso=(EditText) findViewById(R.id.txtFecingreso);
        Horingreso=(EditText) findViewById(R.id.txtHoringreso);
        fk_vehiculo=(EditText)findViewById(R.id.txtid);
        id.setEnabled(false);
        placa.setEnabled(false);
        marca.setEnabled(false);
        Fechingreso.setEnabled(false);
        Horingreso.setEnabled(false);

        btnGenerar=(Button) findViewById(btn_generar);
        btnGuardar=(Button) findViewById(R.id.btnguardar);

        tipo = (Spinner) findViewById(R.id.seleccionar);
        String []opciones={"1","2","3"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, opciones);
        tipo.setAdapter(adapter);

        num_par = (Spinner) findViewById(R.id.spiNum);
        String []opciones1={"1","2","3","4","5"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, opciones1);
        num_par.setAdapter(adapter1);





    btnGenerar.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            buscar("http://192.168.124.127/parkingPHP/buscar.php?id="+tvcedula.getText()+"");
            placa.setEnabled(true);
            marca.setEnabled(true);
            Fechingreso.setEnabled(true);
            Horingreso.setEnabled(true);
        }
    });
     btnGuardar.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             if(placa.getText().toString().isEmpty()){
                 Toast.makeText(pgusuario.this, "Campo Placa vacio", Toast.LENGTH_SHORT).show();
             }
             else{
                 if(marca.getText().toString().isEmpty()){
                     Toast.makeText(pgusuario.this, "Campo Marca vacio", Toast.LENGTH_SHORT).show();
                 }
                 else{
                     if(Fechingreso.getText().toString().isEmpty()){
                         Toast.makeText(pgusuario.this, "Campo Fecha Ingreso vacio", Toast.LENGTH_SHORT).show();
                     }
                     else{
                         if(Horingreso.getText().toString().isEmpty()){
                             Toast.makeText(pgusuario.this, "Campo Hora Ingreso vacio", Toast.LENGTH_SHORT).show();
                         }
                         else{

             ejecutarServico("http://192.168.124.127/parkingPHP/insertarVehiculo.php");

         }
                     }
                 }
             }
         }
     });
    }


        private void ejecutarServico(String URL){
            StringRequest stringRequest=new
                    StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(getApplicationContext(),"Vehiculo Registrado Correctamente", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),pgPuerta.class));
                            id.setText("");
                            placa.setText("");
                            marca.setText("");
                            Fechingreso.setText("");
                            Horingreso.setText("");
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(),error.toString(),
                                    Toast.LENGTH_SHORT).show();
                            //Toast.makeText(getApplicationContext(),"Error Conexion no se a registrado", Toast.LENGTH_SHORT).show();
                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws
                                AuthFailureError {
                            Map<String,String> parametros=new
                                    HashMap<String,String>();
                            parametros.put("placa",placa.getText().toString());
                            parametros.put("marca",marca.getText().toString());
                            parametros.put("fecha",Fechingreso.getText().toString());
                            parametros.put("horaI",Horingreso.getText().toString());
                            parametros.put("fk_cliente",fk_vehiculo.getText().toString());
                            parametros.put("numP",String.valueOf(num_par.getSelectedItemPosition()));
                            parametros.put("tipo", String.valueOf(tipo.getSelectedItem()));



                            return parametros;


                        }
                    };
             requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
    private void buscar (String URL){
        JsonArrayRequest jsonArrayRequest =new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        id.setText(jsonObject.getString("id_cliente"));

                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT);
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"ERROR DE CONEXION",Toast.LENGTH_SHORT);
            }
        }
        );
         requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
}

    public void abrirCalendarios(View view) {
        Calendar cal =Calendar.getInstance();
        int anio= cal.get(Calendar.YEAR);
        int mes= cal.get(Calendar.MONDAY);
        int dia= cal.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dpd = new DatePickerDialog(pgusuario.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
              String fecha=dayOfMonth+"/"+(month+1)+"/"+year;
                Fechingreso.setText(fecha);
            }
        },2018,mes,anio);
        dpd.show();
    }

    public void abrirHora(View view) {
        Calendar c= Calendar.getInstance();
        int hora = c.get(Calendar.HOUR_OF_DAY);
        int min = c.get(Calendar.MINUTE);
        TimePickerDialog tmd = new TimePickerDialog(pgusuario.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Horingreso.setText(hourOfDay+":"+minute);
            }
        },hora, min, false);
        tmd.show();
    }
}




