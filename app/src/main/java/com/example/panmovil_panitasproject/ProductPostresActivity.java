package com.example.panmovil_panitasproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.panmovil_panitasproject.recycleadapter.ProductoPostresAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductPostresActivity extends AppCompatActivity {

    private List<Products> productsList;
    private RequestQueue rqpostres;
    private RecyclerView recyclerViewpostres;
    FloatingActionButton log2, login2, logout2;
    Boolean visible2 = true;
    private ProductoPostresAdapter productoPostresAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_postres);

        LinearLayout linearLayoutPanes = findViewById(R.id.LinearPanes);
        LinearLayout linearLayoutPromociones = findViewById(R.id.LinearPromociones);
        log2 = (FloatingActionButton) findViewById(R.id.BtnLog2);
        login2 = (FloatingActionButton) findViewById(R.id.BtnLogIn2);
        logout2 = (FloatingActionButton) findViewById(R.id.BtnLogOut2);

        login2.setVisibility(View.GONE); logout2.setVisibility(View.GONE);

        log2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (visible2){
                    login2.show(); logout2.show();
                    visible2 = false;
                }else {
                    login2.hide(); logout2.hide();
                    visible2 = true;
                }
            }
        });

        linearLayoutPanes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent panes2 = new Intent(ProductPostresActivity.this, ProductosPanesActivity.class);
                startActivity(panes2);
            }
        });

        linearLayoutPromociones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent promocion = new Intent(ProductPostresActivity.this, PromocionesActivity.class);
                startActivity(promocion);
            }
        });

        productsList = new ArrayList<>();
        recyclerViewpostres = findViewById(R.id.recycleView3);
        rqpostres = Volley.newRequestQueue(this);

        recyclerViewpostres.setLayoutManager(new GridLayoutManager(this, 2));
        productoPostresAdapter = new ProductoPostresAdapter(this, productsList);
        recyclerViewpostres.setAdapter(productoPostresAdapter);

        ListarProductsPostres();

    }

    public void limpiarSharedPreferences2(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("MisPreferencias", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        Intent mainAct2 = new Intent(this, MainActivity.class);
        startActivity(mainAct2);
    }

    public void LogAct2(View view){
        Intent logAct2 = new Intent(this, LoginActivity.class);
        startActivity(logAct2);
    }

    private void ListarProductsPostres(){

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

                        // Agrega esta condición para filtrar por tpro_id igual a 4
                        if (tproId.equals("4")) {
                            String proId = objeto.getString("pro_id");
                            String proNombre = objeto.getString("pro_nombre");
                            String proDescripcion = objeto.getString("pro_descripcion");

                            String proPrecioUnitario = objeto.getString("pro_PrecioUnitario");
                            String urlImage = objeto.getString("pro_imagen");
                            String url = "https://panaderia.informaticapp.com/public/Mana%20Pan%20del%20Cielo%20S.A.C./Productos/" + urlImage;
                            //Picasso.get().load(url).into(imageView);

                            Products products = new Products(proId, proNombre, proDescripcion, tproId, proPrecioUnitario, urlImage);
                            productsList.add(products);
                            productoPostresAdapter.notifyItemRangeInserted(productsList.size(), 1);

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

        rqpostres.add(requerimiento);
    }

}