package br.ufscar.mds.gerenciador.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

import br.ufscar.mds.gerenciador.R;
import br.ufscar.mds.gerenciador.database.AtividadeDbHelper;

/**
 * Created by Henrique on 14/01/2017.
 */

public class ListViewAtividadesAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;

    public ListViewAtividadesAdapter(Context context) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
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

        holder.textViewTitle.setText("Aqui Vai o Titulo da Atividade");
        holder.textViewDate.setText("Aqui vai a data da atividade");
        //Aqui vai a cor relacionada a atividade
        holder.viewCor.setBackgroundColor(rowView.getResources().getColor(R.color.dark_green));

        return rowView;
    }


}