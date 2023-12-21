package com.example.panmovil_panitasproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class PromocionesDetailsActivity extends AppCompatActivity {

    private TextView namePro, tpromId, nameProm, PromDesc, TxtFechaIni, TxtFechaFin, PromDescuento, proPrecio;
    private ImageView imaPro;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promociones_details);

        namePro = findViewById(R.id.textViewProNombre);
        tpromId = findViewById(R.id.textViewidPromociones);
        imaPro = findViewById(R.id.imageViewPromocion);
        nameProm = findViewById(R.id.textViewNameProm);
        PromDesc = findViewById(R.id.textViewPromDesc);
        TxtFechaIni = findViewById(R.id.textViewFechaIni);
        TxtFechaFin = findViewById(R.id.textViewFechaFin);
        PromDescuento = findViewById(R.id.textViewDescuento);
        proPrecio = findViewById(R.id.textViewPrecioPro);

        Intent intentprom = getIntent();

        String promoId = intentprom.getStringExtra("promoId");
        String proNombre= intentprom.getStringExtra("proNombre");
        String tpromoNombre = intentprom.getStringExtra("tpromoNombre");
        String tpromoDescripcion = intentprom.getStringExtra("tpromoDescripcion");
        String promoDescuento = intentprom.getStringExtra("promoDescuento");
        String promoFechaini = intentprom.getStringExtra("promoFechaini");
        String promoFechafin = intentprom.getStringExtra("promoFechafin");
        String proImagen = intentprom.getStringExtra("proImagen");
        String proPrecioUnitario = intentprom.getStringExtra("proPrecioUnitario");

        namePro.setText(proNombre);
        tpromId.setText(promoId);

        String imageUrl = "https://panaderia.informaticapp.com/public/Mana%20Pan%20del%20Cielo%20S.A.C./Productos/" + proImagen;
        Picasso.get().load(imageUrl).into(imaPro);

        nameProm.setText(tpromoNombre);
        PromDesc.setText(tpromoDescripcion);
        TxtFechaIni.setText(promoFechaini);
        TxtFechaFin.setText(promoFechafin);
        PromDescuento.setText("Descuento de " + promoDescuento);
        proPrecio.setText("S/. " + proPrecioUnitario);

    }
}