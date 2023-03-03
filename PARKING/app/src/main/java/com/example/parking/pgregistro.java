package com.example.parking;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class pgregistro extends AppCompatActivity {
    private EditText nombre,apellido,cedula,pass,correo;
    private RadioButton masculino,femenino;
    private Button btnRegistrar,btnRegresar;
    //String numrol="2";
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pgregistro);
        // Enlaza las variable con las id del formulario
        nombre=(EditText)findViewById(R.id.txtNombre);
        apellido=(EditText)findViewById(R.id.txtApellido);
        masculino=(RadioButton)findViewById(R.id.rbtmasculino);
        femenino=(RadioButton)findViewById(R.id.rbtfemenino);
        cedula=(EditText) findViewById(R.id.txtCedula);
        pass=(EditText) findViewById(R.id.txtpass);
        correo=(EditText) findViewById(R.id.txtCorreo);
        btnRegistrar=(Button)findViewById(R.id.btnRegistrar);


        //--------------Programacio de boton a dar clic--------------
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nombre.getText().toString().isEmpty()){
                    Toast.makeText(pgregistro.this, "Campo Nombre vacio", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(apellido.getText().toString().isEmpty()){
                        Toast.makeText(pgregistro.this, "Campo Apellido vacio", Toast.LENGTH_SHORT).show();
                }
                    else{
                        if(cedula.getText().toString().isEmpty()){
                            Toast.makeText(pgregistro.this, "Campo Cedula vacio", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            if(pass.getText().toString().isEmpty()){
                                Toast.makeText(pgregistro.this, "Campo Password vacio", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                if(correo.getText().toString().isEmpty()){
                                    Toast.makeText(pgregistro.this, "Campo Correo vacio", Toast.LENGTH_SHORT).show();
                                }
                else  {

                ejecutarServico("http://192.168.124.127/parkingPHP/insertarUsuario.php");
                Intent i = new Intent(pgregistro.this, MainActivity.class);
                    startActivity(i);}
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
                        Toast.makeText(getApplicationContext(),"Usuario Registrador Correctamente", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
                        //Toast.makeText(getApplicationContext(),"Error Conexion no se a registrado", Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws
                            AuthFailureError {
                        Map<String,String> parametros=new
                                HashMap<String,String>();
                        parametros.put("nombre",nombre.getText().toString());
                        parametros.put("apellido",apellido.getText().toString());
                        parametros.put("genero",genero());
                        parametros.put("cedula",cedula.getText().toString());
                        parametros.put("pass",pass.getText().toString());
                        parametros.put("correo",correo.getText().toString());
                       // parametros.put("fk_rol",numrol.toString());
                        return parametros;


                    }
                };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    public String genero() {
        String genero="";
        if (masculino.isChecked() == true) {
            genero ="Masculino";
        } else if (femenino.isChecked() == true) {
            genero ="Femenino";
        }
        return genero;
    }
}