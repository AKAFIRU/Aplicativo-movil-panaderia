package com.example.panmovil_panitasproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.panmovil_panitasproject.conexionApi.ConexionApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class RegistroActivity extends AppCompatActivity {

    private EditText mailTxt, phoneTxt, passwordTxt, passwordconfirmTxt;
    RequestQueue requestQueue;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        mailTxt = (EditText) findViewById(R.id.MailTxt);
        phoneTxt = (EditText) findViewById(R.id.PhoneTxt);
        passwordTxt = (EditText) findViewById(R.id.PasswordTxt);
        passwordconfirmTxt = (EditText) findViewById(R.id.Password2Txt);

        requestQueue = Volley.newRequestQueue(this);

        if (passwordTxt.getText().toString().equals(passwordconfirmTxt.getText().toString())) {
            // Las contraseñas coinciden
            // No hagas nada aquí, la validación se realiza, pero no se realiza ninguna acción inmediata.
        } else {
            // Las contraseñas no coinciden, muestra un mensaje de error
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
        }

    }

    public void MainAct(View view){
        Intent mainAct = new Intent(this, MainActivity.class);
        startActivity(mainAct);
    }

    public void Registraruser(View view){

        String url = ConexionApi.URL_BASE + "usuariosmovil";

        // Crea el objeto JSON que enviarás en la solicitud POST
        JSONObject jsonBody = new JSONObject();
        try {
            // Agrega todos los parámetros que necesites
            jsonBody.put("usumo_correo", mailTxt.getText().toString());
            jsonBody.put("usumo_contrasena", passwordconfirmTxt.getText().toString());
            jsonBody.put("usumo_telefono", phoneTxt.getText().toString());
            //jsonBody.put("cl_id", cliTxt.getText().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Maneja la respuesta del servidor aquí
                        // Por ejemplo, si esperas un JSON de respuesta:
                        try {
                            int totalRegistros = response.getInt("Total de registros");
                            Toast.makeText(getApplicationContext(), "es: " + totalRegistros, Toast.LENGTH_SHORT).show();

                            // Resto de tu código aquí...

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Maneja errores de la solicitud
                        Toast.makeText(getApplicationContext(), "Error en la solicitud: " + error.getMessage(), Toast.LENGTH_SHORT).show();

                        // Imprime el cuerpo de la respuesta de error para obtener detalles específicos
                        if (error.networkResponse != null && error.networkResponse.data != null) {
                            String errorBody = new String(error.networkResponse.data);
                            Log.e("Error Body", errorBody);
                        }
                    }
                }
        )
        {
            @Override
            public Map<String, String> getHeaders() {
            Map<String, String> headers = new HashMap<>();
            headers.put("Authorization", ConexionApi.AUTH);
            return headers;
        }
        };

        // Añade la solicitud a la cola de Volley
        requestQueue.add(postRequest);
        // Regresa al activity main
        MainAct(view);

    }
}