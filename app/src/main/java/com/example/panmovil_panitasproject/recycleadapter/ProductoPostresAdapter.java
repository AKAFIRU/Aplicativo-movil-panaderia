package com.example.panmovil_panitasproject.recycleadapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.panmovil_panitasproject.ProductDetailsActivity;
import com.example.panmovil_panitasproject.R;
import com.example.panmovil_panitasproject.modulos.Products;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductoPostresAdapter extends RecyclerView.Adapter<ProductoPostresAdapter.ProductViewHolder> {

    private Context context;
    private List<Products> products;
    private LayoutInflater inflater;

    public ProductoPostresAdapter(Context context,List<Products> products){
        this.products = products;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.producto_postres_item_layout, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoPostresAdapter.ProductViewHolder holder, int position) {
        Products products1 = products.get(position);
        holder.bind(products1);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentpostres = new Intent(context, ProductDetailsActivity.class);

                // Poner detalles del producto como extras en el Intent
                intentpostres.putExtra("proId", products1.getProId());
                intentpostres.putExtra("proNombre", products1.getProNombre());
                intentpostres.putExtra("proDescripcion", products1.getProDescripcion());
                intentpostres.putExtra("tproId", products1.getTproId());
                intentpostres.putExtra("proPrecioUnitario", products1.getProPrecioUnitario());
                intentpostres.putExtra("urlImagen", products1.getUrlImagen());

                context.startActivity(intentpostres);
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {

        private TextView nombreTextView2, IdTextView2, descripcionTextView2, txtTpro2, precioUnitarioTextView2;
        private ImageView imageView2;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreTextView2 = itemView.findViewById(R.id.nombreTextView2);
            IdTextView2 = itemView.findViewById(R.id.idtxt2);
            descripcionTextView2 = itemView.findViewById(R.id.descripcionTextView2);
            txtTpro2 = itemView.findViewById(R.id.textViewTproId2);
            precioUnitarioTextView2 = itemView.findViewById(R.id.precioUnitarioTextView2);
            imageView2 = itemView.findViewById(R.id.imageView2);

        }

        public void bind(Products products1) {
            nombreTextView2.setText(products1.getProNombre());
            IdTextView2.setText(products1.getProId());
            descripcionTextView2.setText(products1.getProDescripcion());
            txtTpro2.setText(products1.getTproId());
            precioUnitarioTextView2.setText(products1.getProPrecioUnitario());

            String url2 = "https://panaderia.informaticapp.com/public/Mana%20Pan%20del%20Cielo%20S.A.C./Productos/" + products1.getUrlImagen();
            Picasso.get().load(url2).into(imageView2);

        }
    }
}
