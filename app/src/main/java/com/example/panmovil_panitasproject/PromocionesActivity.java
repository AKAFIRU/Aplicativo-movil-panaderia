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
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.panmovil_panitasproject.conexionApi.ConexionApi;
import com.example.panmovil_panitasproject.modulos.Promociones;
import com.example.panmovil_panitasproject.recycleadapter.PromocionesAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PromocionesActivity extends AppCompatActivity {

    private List<Promociones> listprom;
    private RequestQueue rq2;
    private RecyclerView recyclerView2;
    FloatingActionButton log3, login3, logout3;
    Boolean visible3 = true;
    private PromocionesAdapter promocionesAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promociones);

        LinearLayout linearLayoutPanes = findViewById(R.id.LinearPanes);
        LinearLayout linearLayoutPostres = findViewById(R.id.LinearPostres);
        log3 = (FloatingActionButton) findViewById(R.id.BtnLog3);
        login3 = (FloatingActionButton) findViewById(R.id.BtnLogIn3);
        logout3 = (FloatingActionButton) findViewById(R.id.BtnLogOut3);

        login3.setVisibility(View.GONE); logout3.setVisibility(View.GONE);

        log3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (visible3){
                    login3.show(); logout3.show();
                    visible3 = false;
                }else {
                    login3.hide(); logout3.hide();
                    visible3 = true;
                }
            }
        });

        linearLayoutPanes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent panes = new Intent(PromocionesActivity.this, ProductosPanesActivity.class);
                startActivity(panes);
            }
        });

        linearLayoutPostres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent postres = new Intent(PromocionesActivity.this, ProductPostresActivity.class);
                startActivity(postres);
            }
        });

        listprom = new ArrayList<>();
        recyclerView2 = findViewById(R.id.recyclerView2);
        rq2 = Volley.newRequestQueue(this);

        recyclerView2.setLayoutManager(new LinearLayoutManager(this));
        promocionesAdapter = new PromocionesAdapter(this, listprom);
        recyclerView2.setAdapter(promocionesAdapter);
        ListarPromociones();

    }

    public void limpiarSharedPreferences3(View view) {
        SharedPreferences sharedPreferences3 = getSharedPreferences("MisPreferencias", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences3.edit();
        editor.clear();
        editor.apply();

        Intent mainAct3 = new Intent(this, MainActivity.class);
        startActivity(mainAct3);
    }

    public void LogAct3(View view){
        Intent logAct3 = new Intent(this, LoginActivity.class);
        startActivity(logAct3);
    }

    public void ListarPromociones(){
        String url = Uri.parse(ConexionApi.URL_BASE + "/promociones").buildUpon().build().toString();

        JsonObjectRequest requerimiento = new JsonObjectRequest(Request.Method.GET,
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

                        String promoId = objeto.getString("promo_id");
                        String proNombre = objeto.getString("pro_nombre");
                        String tpromoNombre = objeto.getString("tpromo_nombre");
                        String tpromoDescripcion = objeto.getString("tpromo_descripcion");
                        String promoDescuento = objeto.getString("promo_descuento");
                        String proPrecioUnitario = objeto.getString("pro_PrecioUnitario");
                        String promoFechaini = objeto.getString("promo_fechaini");
                        String promoFechafin = objeto.getString("promo_fechafin");
                        String proImagen = objeto.getString("pro_imagen");

                        Promociones promociones = new Promociones(promoId, proNombre, tpromoNombre, tpromoDescripcion, promoDescuento, proPrecioUnitario, promoFechaini, promoFechafin, proImagen);
                        listprom.add(promociones);
                        promocionesAdapter.notifyItemRangeInserted(listprom.size(), 1);
                    }


                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                // Manejar la respuesta JSON
                // Aquí puedes trabajar con la respuesta

                System.out.println(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Manejar errores de la solicitud
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", ConexionApi.AUTH);
                return headers;
            }
        };
        rq2.add(requerimiento);
    }
}