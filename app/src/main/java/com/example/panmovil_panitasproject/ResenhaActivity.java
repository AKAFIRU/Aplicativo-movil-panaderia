package com.example.panmovil_panitasproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
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

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ResenhaActivity extends AppCompatActivity {

    private EditText descTxt;
    private TextView usuario, passwword, idproduct, iduser;
    private Button btnEnviar;
    private RatingBar ratingbar;
    RequestQueue rqSend;
    private TextView ratingText, nameProductTextView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resenha);


        nameProductTextView = findViewById(R.id.textViewNameProduct);
        idproduct = findViewById(R.id.textViewProductId);
        usuario = findViewById(R.id.TextViewUser);
        iduser = findViewById(R.id.textViewUserId);
        descTxt = findViewById(R.id.editTextTextMultiLine);
        ratingText = findViewById(R.id.RatingText);

        rqSend = Volley.newRequestQueue(this);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String proNombre = sharedPreferences.getString("proNombre", "");
        String proId = sharedPreferences.getString("proId","");

        nameProductTextView.setText(proNombre);
        idproduct.setText(proId);

        SharedPreferences sharedPreferences2 = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        String correoAlmacenado = sharedPreferences2.getString("correo", "");
        String idUsuarioAlmacenado = sharedPreferences2.getString("idUsuario", "");

        // Llenar los campos de texto con los valores almacenados
        usuario.setText(correoAlmacenado);
        iduser.setText(idUsuarioAlmacenado);

        ratingbar = findViewById(R.id.ratingEstrellas);
        ratingText = findViewById(R.id.RatingText);

        ratingbar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingbar, float rating, boolean fromUser) {
                //Log.d("RatingBar", String.valueOf(rating));
                ratingText.setText(String.valueOf(rating));
            }
        });






    }

    public void MainAct(View view){
        Intent mainAct = new Intent(this, MainActivity.class);
        startActivity(mainAct);
    }
    public void EnviarResenha(View view){
        String url = ConexionApi.URL_BASE + "resenas";

        // Crea el objeto JSON que enviarás en la solicitud POST
        JSONObject jsonBody = new JSONObject();
        try {
            // Agrega todos los parámetros que necesites
            jsonBody.put("pro_id",idproduct.getText().toString()); //Lo captura de un TextView
            jsonBody.put("usumo_id",iduser.getText().toString()); // Lo caputura de un TextView
            jsonBody.put("re_descripcion",descTxt.getText().toString()); // Lo caputura de un Edit Text
            jsonBody.put("re_estrellas", ratingText.getText().toString());

        }catch (JSONException e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error al construir el JSON" , Toast.LENGTH_SHORT).show();
            return;  // Salir del método si hay un error
        }
        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Maneja la respuesta del servidor aquí
                        // Por ejemplo, si esperas un JSON de respuesta:
                        try {

                            int resenaSend = response.getInt("Reseña enviada");
                            Toast.makeText(getApplicationContext(), "" + resenaSend, Toast.LENGTH_SHORT).show();
                            Log.d("RatingText", ratingText.getText().toString());


                            // Resto de tu código aquí...
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("JSON Error", "Error en el JSON de respuesta: " + response.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Maneja errores de la solicitud
                        Toast.makeText(getApplicationContext(), "Error en la solicitud: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("Volley Error", "Error en la solicitud: " + error.getMessage(), error);
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

            @Override
            public byte[] getBody() {
                // Convierte el objeto JSON a un array de bytes
                return jsonBody.toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }

        };
        rqSend.add(postRequest);
        // Regresa al activity main
        MainAct(view);
    }

}