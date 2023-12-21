package com.example.panmovil_panitasproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.panmovil_panitasproject.conexionApi.ConexionApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class EditarResenaActivity extends AppCompatActivity {

    private EditText descEditTxt;
    private TextView idresena, user, productoid, userid, ratingeditText, nameproductEdit;
    private RatingBar ratingBar;
    RequestQueue requestEdit;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_resena);

        idresena = findViewById(R.id.textViewIdResena);
        userid = findViewById(R.id.IdUsertextView);
        user = findViewById(R.id.textViewUserEdit);
        productoid = findViewById(R.id.ProductIdtextView);
        nameproductEdit = findViewById(R.id.NameProductTextView);
        descEditTxt = findViewById(R.id.editTextTextMultiLineDesc);
        ratingBar = findViewById(R.id.ratingBarEdit);
        ratingeditText = findViewById(R.id.RatingTextEdit);

        requestEdit = Volley.newRequestQueue(this);

        SharedPreferences sharedPreferences5 = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String proNombre = sharedPreferences5.getString("proNombre", "");
        String proId = sharedPreferences5.getString("proId","");

        nameproductEdit.setText(proNombre);
        productoid.setText(proId);

        SharedPreferences sharedPreferences202 = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        String correoAlmacenado = sharedPreferences202.getString("correo", "");
        String idUsuarioAlmacenado = sharedPreferences202.getString("idUsuario", "");

        user.setText(correoAlmacenado);
        userid.setText(idUsuarioAlmacenado);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingeditText.setText(String.valueOf(rating));
            }
        });

        /*SharedPreferences sharedPreferencesIdResenas = getSharedPreferences("MyPrefs" , MODE_PRIVATE);
        String reId = sharedPreferencesIdResenas.getString("reId", "");
        idresena.setText(reId);*/

    }

    public void MainAct(View view){
        Intent mainAct = new Intent(this, MainActivity.class);
        startActivity(mainAct);
    }

    public void EditarResenha(View view) {
        SharedPreferences sharedPreferencesIdResenas = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String reId = sharedPreferencesIdResenas.getString("reId", "");

        String resenaId = reId;
        String url = ConexionApi.URL_BASE + "resenas/" + resenaId;

        // Crea los parámetros que enviarás en la solicitud PUT
        Map<String, String> params = new HashMap<>();
        params.put("pro_id", productoid.getText().toString());
        params.put("usumo_id", userid.getText().toString());
        params.put("re_descripcion", descEditTxt.getText().toString());
        params.put("re_estrellas", ratingeditText.getText().toString());

        // Convierte los parámetros a una cadena de consulta codificada
        String requestBody = getEncodedParams(params);

        StringRequest putRequest = new StringRequest(Request.Method.PUT, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Maneja la respuesta del servidor aquí
                        // Por ejemplo, si esperas una cadena de respuesta:
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                        // Resto de tu código aquí...
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
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", ConexionApi.AUTH);
                return headers;
            }

            @Override
            public byte[] getBody() {
                // Convierte la cadena de consulta a un array de bytes
                return requestBody.getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded";
            }
        };

        requestEdit.add(putRequest);
        // Regresa al activity main
        MainAct(view);
    }

    // Método para convertir los parámetros a una cadena de consulta codificada
    private String getEncodedParams(Map<String, String> params) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (sb.length() > 0) {
                sb.append("&");
            }
            try {
                sb.append(URLEncoder.encode(entry.getKey(), "UTF-8"))
                        .append("=")
                        .append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }


}