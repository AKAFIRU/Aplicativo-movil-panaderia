package com.example.panmovil_panitasproject.recycleadapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.panmovil_panitasproject.ProductDetailsActivity;
import com.example.panmovil_panitasproject.R;
import com.example.panmovil_panitasproject.modulos.Products;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductoPanesAdapter extends RecyclerView.Adapter<ProductoPanesAdapter.ProductoViewHolder> {

    private Context context;
    private List<Products> productos;
    private LayoutInflater nInflater;

    public ProductoPanesAdapter(Context context, List<Products> productos) {
        this.productos = productos;
        this.nInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public ProductoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.producto_panes_item_layout, parent, false);
        return new ProductoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoViewHolder holder, int position) {
        Products producto = productos.get(position);
        holder.bind(producto);

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductDetailsActivity.class);

                // Poner detalles del producto como extras en el Intent
                intent.putExtra("proId", producto.getProId());
                intent.putExtra("proNombre", producto.getProNombre());
                intent.putExtra("proDescripcion", producto.getProDescripcion());
                intent.putExtra("tproId", producto.getTproId());
                intent.putExtra("proPrecioUnitario", producto.getProPrecioUnitario());
                intent.putExtra("urlImagen", producto.getUrlImagen());

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return productos.size();
    }

    public class ProductoViewHolder extends RecyclerView.ViewHolder {
        private TextView nombreTextView, IdTextView, descripcionTextView, txtTpro, precioUnitarioTextView;
        private ImageView imageView;

        public ProductoViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreTextView = itemView.findViewById(R.id.nombreTextView);
            IdTextView = itemView.findViewById(R.id.idtxt);
            descripcionTextView = itemView.findViewById(R.id.descripcionTextView);
            txtTpro = itemView.findViewById(R.id.textViewTproId);
            precioUnitarioTextView = itemView.findViewById(R.id.precioUnitarioTextView);
            imageView = itemView.findViewById(R.id.imageView);

        }

        public void bind(Products producto) {
            nombreTextView.setText(producto.getProNombre());
            IdTextView.setText("Id: " + producto.getProId());
            descripcionTextView.setText("Descripción: " + producto.getProDescripcion());
            txtTpro.setText(producto.getTproId());
            precioUnitarioTextView.setText("Precio unitario: S/. " + producto.getProPrecioUnitario());

            String url = "https://panaderia.informaticapp.com/public/Mana%20Pan%20del%20Cielo%20S.A.C./Productos/" + producto.getUrlImagen();
            Picasso.get().load(url).into(imageView);
        }
    }
}
