package br.ufscar.mds.gerenciador.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import br.ufscar.mds.gerenciador.R;
import br.ufscar.mds.gerenciador.data.Semestre;

/**
 * Created by Henrique on 29/01/2017.
 */

public class SemesterArrayAdapter extends ArrayAdapter<Semestre> {

    public SemesterArrayAdapter(Context context, List<Semestre> semesters) {
        super(context, R.layout.semester_adapter, semesters);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        Semestre semester = getItem(position);

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.semester_adapter, parent, false);

        TextView tvName = (TextView) rowView.findViewById(R.id.semester_adapter_name);

        tvName.setText(semester.getNome() + "-" + semester.getAno() + "/" + semester.getPeriodo());

        return rowView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Semestre semester = getItem(position);

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.semester_adapter, parent, false);

        TextView tvName = (TextView) rowView.findViewById(R.id.semester_adapter_name);

        tvName.setText(semester.getAno() + "/" + semester.getPeriodo());

        return rowView;
    }
}
