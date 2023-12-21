package com.example.panmovil_panitasproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Animation top, bottom;
    TextView nameEmpresa;
    ImageView perro;
    View rectangulo;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        top = AnimationUtils.loadAnimation(this, R.anim.top_animacion);
        bottom  = AnimationUtils.loadAnimation(this, R.anim.bottom_animacion);

        perro = findViewById(R.id.perroImagenView);
        rectangulo = findViewById(R.id.rectanguloView);
        nameEmpresa = findViewById(R.id.textViewEmpresa);

        perro.setAnimation(top);
        nameEmpresa.setAnimation(bottom);
        rectangulo.setAnimation(bottom);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, ProductosPanesActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2300);

    }

    public void ListaProducto(View view){
        Intent listpro = new Intent(this, ProductosPanesActivity.class);
        startActivity(listpro);
    }

    public void PromocionMain(View view){
        Intent proMain = new Intent(this, PromocionesActivity.class);
        startActivity(proMain);
    }

    public void RegistroMain(View view){
        Intent regsMain = new Intent(this, RegistroActivity.class);
        startActivity(regsMain);
    }

}