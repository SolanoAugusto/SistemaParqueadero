package com.example.parking;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private TextView registrar;
    private Button btnIngresar;
    //------Variables de Iniciar Seccion---
     EditText usuario, password;
    String str_email,str_password;
    String url = "http://17/parkingPHP/validarUsu.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registrar=(TextView) findViewById(R.id.labRegistrar);

        btnIngresar=findViewById(R.id.btnIngresar);
            usuario=findViewById(R.id.txtusuario);
            password=findViewById(R.id.txtpassword);

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registrar = new Intent(MainActivity.this, pgregistro.class);
                MainActivity.this.startActivity(registrar);
            }
        });

    }

    public void Login(View view) {

        if(usuario.getText().toString().equals("")){
            Toast.makeText(this, "Ingrese el Usuario", Toast.LENGTH_SHORT).show();
        }
        else if(password.getText().toString().equals("")){
            Toast.makeText(this, "Ingrese la contraseña", Toast.LENGTH_SHORT).show();
        }
        else{


            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Por favor espera...");

            progressDialog.show();

            str_email = usuario.getText().toString().trim();
            str_password = password.getText().toString().trim();



            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();

                    if(response.equalsIgnoreCase("ingreso correctamente")){
                        usuario.setText("");
                        password.setText("");
                       // startActivity(new Intent(getApplicationContext(),pgusuario.class));
                        Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, pgusuario.class);
                        intent.putExtra("cedula",str_email);

                        MainActivity.this.startActivity(intent);
                    }
                    else{
                        Toast.makeText(MainActivity.this, "No Existe Registro", Toast.LENGTH_SHORT).show();
                    }

                }
            },new Response.ErrorListener(){

                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("cedula",str_email);
                    params.put("pass",str_password);
                    return params;

                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
            requestQueue.add(request);




        }
    }

    public void moveToRegistration(View view) {
        startActivity(new Intent(getApplicationContext(), pgregistro.class));
        finish();
}
}
