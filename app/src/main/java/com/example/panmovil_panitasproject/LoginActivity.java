package com.example.panmovil_panitasproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.panmovil_panitasproject.conexionApi.ConexionApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private EditText loginmailTxt, loginPassTxt;
    private String textAlmacenado;
    private Button BtnLogin;
    private Context context;
    RequestQueue rq3;
    // Constantes para las claves de SharedPreferences
    private static final String PREFS_NAME = "MisPreferencias";
    private static final String KEY_CORREO = "correo";
    private static final String KEY_ID_USUARIO = "idUsuario";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginComponents();
    }
    public void LoginComponents(){
        loginmailTxt = findViewById(R.id.LoginmailTxt);
        loginPassTxt = findViewById(R.id.LoginPassTxt);
        BtnLogin = findViewById(R.id.btnLogin);

        context = getApplicationContext();

        rq3 = Volley.newRequestQueue(context);
    }

    public void RegisrarAct(View view){
        Intent register = new Intent(this, RegistroActivity.class);
        startActivity(register);
    }

    public void Ingresar(View view) {
        String mail = loginmailTxt.getText().toString().trim();
        String password = loginPassTxt.getText().toString().trim();

        if (mail.isEmpty() || password.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Ingrese los datos.", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = ConexionApi.URL_BASE + "usuariosmovil/" + mail + "&" + password;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    try {
                        String valor = response.get("Detalle").toString();
                        JSONArray arreglo = new JSONArray(valor);
                        JSONObject objeto = new JSONObject(arreglo.get(0).toString());

                        String correo = objeto.getString("usumo_Correo");
                        String idUsuario = objeto.getString("usumo_id");

                        // Guardar en SharedPreferences
                        guardarEnSharedPreferences(correo, idUsuario);

                        Toast.makeText(getApplicationContext(), "Bienvenido " + correo, Toast.LENGTH_SHORT).show();

                        Intent mainAct = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(mainAct);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Datos erroneos", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(getApplicationContext(), "Problema en la conexión de la red.", Toast.LENGTH_SHORT).show()
        );
        rq3.add(jsonObjectRequest);
    }

    public void RecuperarContrasena(View view){
        Intent recuperarPass = new Intent(this, RecuperarPasswordActivity.class);
        startActivity(recuperarPass);
    }

    // Método para guardar en SharedPreferences
    private void guardarEnSharedPreferences(String correo, String idUsuario) {
        try {
            SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(KEY_CORREO, correo);
            editor.putString(KEY_ID_USUARIO, idUsuario);
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error al guardar en SharedPreferences", Toast.LENGTH_SHORT).show();
        }
    }

}