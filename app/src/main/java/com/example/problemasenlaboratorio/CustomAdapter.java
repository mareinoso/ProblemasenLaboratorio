package com.example.problemasenlaboratorio;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class CustomAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<UserModel> userModelArrayList;

    public CustomAdapter(Context context, ArrayList<UserModel> userModelArrayList) {

        this.context = context;
        this.userModelArrayList = userModelArrayList;
    }


    @Override
    public int getCount() {
        return userModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return userModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.lv_item, null, true);

            holder.tvfecha = (TextView) convertView.findViewById(R.id.fecha);
            holder.tvlaboratorio = (TextView) convertView.findViewById(R.id.laboratorio);
            holder.tvrut = (TextView) convertView.findViewById(R.id.rut);
            holder.tvnombre = (TextView) convertView.findViewById(R.id.nombre);
            holder.tvdescripcion = (TextView) convertView.findViewById(R.id.descripcion);


            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)convertView.getTag();
        }

        holder.tvfecha.setText("Fecha: "+userModelArrayList.get(position).getFecha());
        holder.tvlaboratorio.setText("Laboratorio: "+userModelArrayList.get(position).getLaboratorio());
        holder.tvrut.setText("RUT: "+userModelArrayList.get(position).getRut());
        holder.tvnombre.setText("Nombre: "+userModelArrayList.get(position).getNombre());
        holder.tvdescripcion.setText("Descripción: "+userModelArrayList.get(position).getDescripcion());

        return convertView;
    }

    private class ViewHolder {

        protected TextView tvfecha, tvlaboratorio, tvrut, tvnombre, tvdescripcion;
    }

}