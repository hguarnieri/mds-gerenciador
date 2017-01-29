package br.ufscar.mds.gerenciador.utils;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.ufscar.mds.gerenciador.R;
import br.ufscar.mds.gerenciador.data.Atividade;

/**
 * Created by Henrique on 14/01/2017.
 */

public class ListViewAtividadesAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    private static List<Atividade> activities;

    public ListViewAtividadesAdapter(Context context, List<Atividade> activities) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.activities = activities;
    }

    @Override
    public int getCount() {
        return activities.size();
    }

    @Override
    public Atividade getItem(int position) {
        return activities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return activities.get(position).getId();
    }

    public class Holder {
        TextView textViewTitle;
        TextView textViewDate;
        View viewCor;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder = new Holder();
        View rowView = inflater.inflate(R.layout.list_atividade_item, null);
        holder.textViewTitle = (TextView) rowView.findViewById(R.id.list_atividade_item_titulo);
        holder.textViewDate = (TextView) rowView.findViewById(R.id.list_atividade_item_data);
        holder.viewCor = (View) rowView.findViewById(R.id.list_atividade_item_cor);

        holder.textViewTitle.setText(activities.get(position).getTitulo());
        holder.textViewDate.setText(activities.get(position).getData());
        //Aqui vai a cor relacionada a atividade
        holder.viewCor.setBackgroundColor(rowView.getResources().getColor(R.color.dark_green));

        return rowView;
    }


}