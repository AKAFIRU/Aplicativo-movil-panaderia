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

import com.example.panmovil_panitasproject.PromocionesDetailsActivity;
import com.example.panmovil_panitasproject.R;
import com.example.panmovil_panitasproject.modulos.Promociones;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PromocionesAdapter extends RecyclerView.Adapter<PromocionesAdapter.PromocionesViewHolder>{

    private Context context;
    private LayoutInflater inflater;
    private List<Promociones> promociones;

    public PromocionesAdapter(Context context, List<Promociones> promociones){
        this.context = context;
        this.promociones = promociones;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public PromocionesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.promociones_item_layout, parent, false);
        return new PromocionesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PromocionesViewHolder holder, int position) {
        Promociones promocion = promociones.get(position);
        holder.bind(promocion);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PromocionesDetailsActivity.class);

                intent.putExtra("promoId",promocion.getPromoId());
                intent.putExtra("proNombre",promocion.getProNombre());
                intent.putExtra("tpromoNombre",promocion.getTpromoNombre());
                intent.putExtra("tpromoDescripcion", promocion.getTpromoDescripcion());
                intent.putExtra("promoDescuento", promocion.getPromoDescuento());
                intent.putExtra("promoFechaini", promocion.getPromoFechaini());
                intent.putExtra("promoFechafin", promocion.getPromoFechafin());
                intent.putExtra("proImagen", promocion.getProImagen());
                intent.putExtra("proPrecioUnitario", promocion.getProPrecioUnitario());

                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return promociones.size();
    }

    public class PromocionesViewHolder extends RecyclerView.ViewHolder {

        private TextView nameTextView;
        private TextView descNameTextView;
        private TextView descuentoTextView;
        private TextView fechaIniTextView;
        private TextView fechaFinTextView;
        private ImageView proImageView;

        public PromocionesViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            descNameTextView = itemView.findViewById(R.id.descNameTextView);
            descuentoTextView = itemView.findViewById(R.id.descuentoTextView);
            fechaIniTextView = itemView.findViewById(R.id.fechaIniTextView);
            fechaFinTextView = itemView.findViewById(R.id.fechaFinTextView);
            proImageView = itemView.findViewById(R.id.proImageView);

        }

        public void bind(Promociones promocion) {
            nameTextView.setText(promocion.getProNombre());
            descNameTextView.setText( promocion.getTpromoNombre());
            descuentoTextView.setText("Descuento del producto: " + promocion.getPromoDescuento());
            fechaIniTextView.setText("Fecha incial: " + promocion.getPromoFechaini());
            fechaFinTextView.setText("Fecha final: " + promocion.getPromoFechafin());

            String url = "https://panaderia.informaticapp.com/public/Mana%20Pan%20del%20Cielo%20S.A.C./Productos/" + promocion.getProImagen();
            Picasso.get().load(url).into(proImageView);
        }
    }
}

