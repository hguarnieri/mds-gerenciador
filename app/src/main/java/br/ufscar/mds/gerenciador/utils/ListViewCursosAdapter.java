package br.ufscar.mds.gerenciador.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Random;

import br.ufscar.mds.gerenciador.R;

/**
 * Created by Henrique on 14/01/2017.
 */

public class ListViewCursosAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;

    public ListViewCursosAdapter(Context context) {
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
        TextView textViewTitulo;
        TextView textViewHorario;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder = new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.list_cursos_item, null);
        holder.textViewTitulo = (TextView) rowView.findViewById(R.id.list_curso_item_titulo);
        holder.textViewHorario = (TextView) rowView.findViewById(R.id.list_curso_item_horario);




        return rowView;
    }

}