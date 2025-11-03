package com.example.ecotrack;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HistorialAdapter extends RecyclerView.Adapter<HistorialAdapter.HistorialViewHolder> {

    private List<HistorialItem> listaItems;
    private Context context;
    private SimpleDateFormat dateFormat;


    //recibe la lista y el contexto
    public HistorialAdapter(Context context, List<HistorialItem> listaItems) {
        this.context = context;
        this.listaItems = listaItems;
        this.dateFormat = new SimpleDateFormat("dd 'de' MMMM, yyyy", new Locale("es", "ES"));
    }

    //molde
    @NonNull
    @Override
    public HistorialViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_historial, parent, false);
        return new HistorialViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull HistorialViewHolder holder, int position) {
        HistorialItem item = listaItems.get(position);
        String fechaFormateada = dateFormat.format(new Date(item.getFechaMillis()));

        holder.textTitulo.setText(item.getTitulo());
        holder.textFecha.setText(fechaFormateada);

        if (item.esViaje()) {
            holder.textCo2.setText(String.format("+%.2f kg CO2", item.getCo2()));
            holder.textCo2.setTextColor(context.getColor(R.color.lila_oscuro));

            holder.icono.setImageResource(R.drawable.ic_viaje);
        } else {
            holder.textCo2.setText(String.format("-%.2f kg CO2", item.getCo2()));
            holder.textCo2.setTextColor(context.getColor(R.color.verdeson));

            holder.icono.setImageResource(R.drawable.ic_habito_ahorro);
        }
    }

    @Override
    public int getItemCount() {
        return listaItems.size();
    }

    public static class HistorialViewHolder extends RecyclerView.ViewHolder {

        // Vistas de nuestro "item_historial.xml"
        ImageView icono;
        TextView textTitulo;
        TextView textFecha;
        TextView textCo2;

        public HistorialViewHolder(@NonNull View itemView) {
            super(itemView);
            //vistasmolde
            icono = itemView.findViewById(R.id.item_icono);
            textTitulo = itemView.findViewById(R.id.item_titulo);
            textFecha = itemView.findViewById(R.id.item_fecha);
            textCo2 = itemView.findViewById(R.id.item_co2);
        }
    }
}