package br.ufscar.mds.gerenciador.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

import br.ufscar.mds.gerenciador.R;
import br.ufscar.mds.gerenciador.data.Curso;

/**
 * Created by Henrique on 14/01/2017.
 */

public class ListViewCursosAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    private static List<Curso> courses;

    public ListViewCursosAdapter(Context context, List<Curso> courses) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.courses = courses;
    }

    @Override
    public int getCount() {
        return courses.size();
    }

    @Override
    public Curso getItem(int position) {
        return courses.get(position);
    }

    @Override
    public long getItemId(int position) {
        return courses.get(position).getId();
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

        holder.textViewTitulo.setText(courses.get(position).getNome());
        holder.textViewHorario.setText(courses.get(position).getHorario1() + '\n');
        holder.textViewHorario.append(courses.get(position).getHorario2());

        return rowView;
    }

}