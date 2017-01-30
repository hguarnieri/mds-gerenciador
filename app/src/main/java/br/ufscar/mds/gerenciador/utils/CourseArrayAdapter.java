package br.ufscar.mds.gerenciador.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import br.ufscar.mds.gerenciador.R;
import br.ufscar.mds.gerenciador.data.Curso;
import br.ufscar.mds.gerenciador.data.Semestre;

/**
 * Created by Henrique on 29/01/2017.
 */

public class CourseArrayAdapter extends ArrayAdapter<Curso> {

    public CourseArrayAdapter(Context context, List<Curso> courses) {
        super(context, R.layout.course_adapter, courses);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        Curso course = getItem(position);

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.course_adapter, parent, false);

        TextView tvName = (TextView) rowView.findViewById(R.id.course_adapter_name);

        tvName.setText(course.getNome());

        return rowView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Curso course = getItem(position);

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.course_adapter, parent, false);

        TextView tvName = (TextView) rowView.findViewById(R.id.course_adapter_name);

        tvName.setText(course.getNome());

        return rowView;
    }
}
