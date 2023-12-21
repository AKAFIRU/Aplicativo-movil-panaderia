package com.example.panmovil_panitasproject.recycleadapter;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.panmovil_panitasproject.EditarResenaActivity;
import com.example.panmovil_panitasproject.R;
import com.example.panmovil_panitasproject.modulos.Resenas;

import java.util.List;

public class ResenasAdapter extends RecyclerView.Adapter<ResenasAdapter.ResenasViewHolder>{

    private Context context;
    private LayoutInflater layoutInflater;
    private List<Resenas> resenas;

    public ResenasAdapter(Context context, List<Resenas> resenas){
        this.context = context;
        this.resenas = resenas;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ResenasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.resenas_item_layout,parent, false);
        return new ResenasViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResenasViewHolder holder, int position) {
        Resenas resena = resenas.get(position);
        holder.bind(resena);
    }

    @Override
    public int getItemCount() {
        return resenas.size();
    }

    public class ResenasViewHolder extends RecyclerView.ViewHolder{

        private TextView idResena, txtMail, txtDescription, txtRating;
        private Button btnEditarResena;
        private RatingBar ratingResena;

        public ResenasViewHolder(@NonNull View itemView) {
            super(itemView);
            idResena = itemView.findViewById(R.id.idResenaTextView);
            txtMail = itemView.findViewById(R.id.textViewMail);
            txtDescription = itemView.findViewById(R.id.textViewDescription);
            txtRating = itemView.findViewById(R.id.textViewRating);
            ratingResena = itemView.findViewById(R.id.ratingBarResena);
            btnEditarResena = itemView.findViewById(R.id.buttonEditar);
        }

        public void bind(Resenas resena) {

            idResena.setText(resena.getReId());
            txtMail.setText(resena.getUsumoCorreo());
            txtDescription.setText("Descripción: " + resena.getReDescripcion());
            txtRating.setText("Puntuación: " + resena.getReEstrellas());

            // Convertir el valor del texto a un tipo numérico (float)
            float ratingValue = Float.parseFloat(resena.getReEstrellas());

            // Establecer el valor en el RatingBar
            ratingResena.setRating(ratingValue);

            btnEditarResena.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent resenaEdit = new Intent(context, EditarResenaActivity.class);

                    context.startActivity(resenaEdit);
                }
            });

            String reId = idResena.getText().toString();
            SharedPreferences sharedPreferencesIdResenas = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editorId = sharedPreferencesIdResenas.edit();
            editorId.putString("reId", reId);
            editorId.apply();

        }
    }
}
