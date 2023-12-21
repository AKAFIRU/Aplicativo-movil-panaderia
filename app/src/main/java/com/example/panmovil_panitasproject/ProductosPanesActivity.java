package com.example.panmovil_panitasproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.example.panmovil_panitasproject.modulos.Products;
import com.example.panmovil_panitasproject.recycleadapter.ProductoPanesAdapter;
import com.example.panmovil_panitasproject.recycleadapter.ProductoPostresAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductosPanesActivity extends AppCompatActivity {

    private List<Products> listproducts, listpanes, listPostres;
    private RequestQueue rq;
    private RecyclerView recyclerView;

    FloatingActionButton log, login, logout;
    Boolean visible = true;
    private ProductoPanesAdapter productoPanesAdapter;
    private ProductoPostresAdapter productoPostresAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos_panes);

        LinearLayout linearLayoutPanes = findViewById(R.id.LinearPanes);
        LinearLayout linearLayoutPromociones = findViewById(R.id.LinearPromociones);
        LinearLayout linearLayoutPostres = findViewById(R.id.LinearPostres);
        log = (FloatingActionButton) findViewById(R.id.BtnLog);
        login = (FloatingActionButton) findViewById(R.id.BtnLogIn);
        logout = (FloatingActionButton) findViewById(R.id.BtnLogOut);

        login.setVisibility(View.GONE); logout.setVisibility(View.GONE); // no es visible en pantalla y no ocupa espacio

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (visible){
                    login.show(); logout.show();
                    visible = false;
                }else {
                    login.hide(); logout.hide();
                    visible = true;
                }
            }
        });

        linearLayoutPostres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent postres = new Intent(ProductosPanesActivity.this, ProductPostresActivity.class);
                startActivity(postres);
            }
        });

        linearLayoutPromociones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar la segunda actividad
                Intent promociones = new Intent(ProductosPanesActivity.this, PromocionesActivity.class);
                startActivity(promociones);
            }
        });

        listproducts = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView2);
        rq = Volley.newRequestQueue(this);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        productoPanesAdapter = new ProductoPanesAdapter(this, listproducts);
        recyclerView.setAdapter(productoPanesAdapter);
        ListarProductsPanes();
    }

    public void limpiarSharedPreferences(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("MisPreferencias", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        Intent mainAct = new Intent(this, MainActivity.class);
        startActivity(mainAct);
    }

    public void LogAct(View view){
        Intent logAct = new Intent(this, LoginActivity.class);
        startActivity(logAct);
    }

    private void ListarProductsPanes(){

        String url = Uri.parse(ConexionApi.URL_BASE + "/productos").buildUpon().build().toString();

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

                        String tproId = objeto.getString("tpro_id");

                        // Agrega esta condición para filtrar por tpro_id igual a 6
                        if (tproId.equals("6")) {
                            String proId = objeto.getString("pro_id");
                            String proNombre = objeto.getString("pro_nombre");
                            String proDescripcion = objeto.getString("pro_descripcion");

                            String proPrecioUnitario = objeto.getString("pro_PrecioUnitario");
                            String urlImage = objeto.getString("pro_imagen");
                            String url = "https://panaderia.informaticapp.com/public/Mana%20Pan%20del%20Cielo%20S.A.C./Productos/" + urlImage;
                            //Picasso.get().load(url).into(imageView);

                            Products products = new Products(proId, proNombre, proDescripcion, tproId, proPrecioUnitario, urlImage);
                            //listproducts = new ArrayList<>();
                            listproducts.add(products);
                            productoPanesAdapter.notifyItemRangeInserted(listproducts.size(), 1);

                            //Toast.makeText(getApplicationContext(), "hola", Toast.LENGTH_SHORT).show();
                            //detalles.append(proNombre +"\n" + proDescripcion + "\n" + proPrecioUnitario + "\n" + urlImage + "\n");

                        }
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

        rq.add(requerimiento);
    }

}