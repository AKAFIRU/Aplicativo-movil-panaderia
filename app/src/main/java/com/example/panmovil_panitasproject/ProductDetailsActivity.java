package com.example.panmovil_panitasproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.panmovil_panitasproject.conexionApi.ConexionApi;
import com.example.panmovil_panitasproject.modulos.Resenas;
import com.example.panmovil_panitasproject.recycleadapter.ResenasAdapter;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductDetailsActivity extends AppCompatActivity {

    private TextView proIdDetails, nameProTextView, descripcionProTextView, priceTextView;
    private ImageView imageproView;
    private Button btnresenha, BtnEditar;
    private List<Resenas> listresenas;
    private RecyclerView recyclerViewRe;
    private RequestQueue rqresena;
    private ResenasAdapter resenasAdapter;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        nameProTextView = findViewById(R.id.nameproTextView);
        proIdDetails = findViewById(R.id.textViewIdDetail);
        descripcionProTextView = findViewById(R.id.descripcionproTextView);
        priceTextView = findViewById(R.id.precioproTextView);
        imageproView = findViewById(R.id.imageproView);
        btnresenha = findViewById(R.id.Btnresena);
        //BtnEditar = findViewById(R.id.buttonEditar);

        // Obtener extras del Intent
        Intent intent = getIntent();
        String proId = intent.getStringExtra("proId");
        String proNombre = intent.getStringExtra("proNombre");
        String proDescripcion = intent.getStringExtra("proDescripcion");
        String proPrecioUnitario = intent.getStringExtra("proPrecioUnitario");
        String urlImagen = intent.getStringExtra("urlImagen");

        nameProTextView.setText(proNombre);
        proIdDetails.setText(proId);

        // Cargar la imagen utilizando Picasso
        String imageUrl = "https://panaderia.informaticapp.com/public/Mana%20Pan%20del%20Cielo%20S.A.C./Productos/" + urlImagen;
        Picasso.get().load(imageUrl).into(imageproView);

        descripcionProTextView.setText(proDescripcion);
        priceTextView.setText("S/. " + proPrecioUnitario);

        String proName = nameProTextView.getText().toString();
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("proNombre", proNombre);
        editor.putString("proId", proId);
        editor.apply();

        listresenas = new ArrayList<>();
        recyclerViewRe = findViewById(R.id.recycleResenas);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewRe.setLayoutManager(layoutManager);
        rqresena = Volley.newRequestQueue(this);
        resenasAdapter = new ResenasAdapter(this, listresenas);
        recyclerViewRe.setAdapter(resenasAdapter);
        ListarResenas();

    }

    public void ResenaAct(View view){
        try {
            Intent resenhaAct = new Intent(this, ResenhaActivity.class);
            startActivity(resenhaAct);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al iniciar ResenhaActivity", Toast.LENGTH_SHORT).show();
        }
    }

    private void ListarResenas(){
        String url = Uri.parse(ConexionApi.URL_BASE + "/resenas").buildUpon().build().toString();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                url,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    int totalRegistros = response.getInt("Total de registros");
                    Toast.makeText(getApplicationContext(), "es: " + totalRegistros, Toast.LENGTH_SHORT).show();

                    for (int i = 0; i < totalRegistros; i++) {
                        String valor = response.get("Detalle").toString();
                        JSONArray arreglo = new JSONArray(valor);
                        JSONObject objeto = new JSONObject(arreglo.get(i).toString());

                        String proId = objeto.getString("pro_id");

                        if (proId.equals(proIdDetails.getText().toString())) {
                            String reId = objeto.getString("re_id");
                            String usumoId = objeto.getString("usumo_id");
                            String usumoCorreo = objeto.getString("usumo_Correo");
                            String reEstrellas = objeto.getString("re_estrellas");
                            String reDescripcion = objeto.getString("re_descripcion");

                            String proNombre = objeto.getString("pro_nombre");


                            Resenas resenas = new Resenas(reId, usumoId, usumoCorreo, reEstrellas, reDescripcion, proId, proNombre);
                            listresenas.add(resenas);
                            resenasAdapter.notifyItemRangeInserted(listresenas.size(), 1);

                        }
                    }

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                System.out.println(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        ){
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", ConexionApi.AUTH);
                return headers;
            }
        };

        rqresena.add(request);
    }

    /*public void LogAct(View view){
        Intent logAct = new Intent(this, LoginActivity.class);
        startActivity(logAct);
    }*/
}